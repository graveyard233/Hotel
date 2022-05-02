package com.example.hotel.UI.Manage.OrderManage;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;
import com.example.hotel.Bean.Traveller;
import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Room.TravellerAndIDcardView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.loper7.date_time_picker.DateTimeConfig;
import com.loper7.date_time_picker.dialog.CardDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ManageOrderActivity extends BaseActivity implements View.OnClickListener {

    private Order order_this;

    private TextView order_book_time;
    private TextInputLayout order_roomId;
    private TextView order_choiceStart;
    private TextView order_choiceEnd;
    private TextView order_start_time;
    private TextView order_end_time;

    private Date startTime;
    private Date endTime;
    List<Integer> displayList = new ArrayList<>();

    private TextInputLayout order_price;
    private TextView order_comment;

    private FloatingActionsMenu fab_menu;
    private FloatingActionButton fab_commit;
    private FloatingActionButton fab_delete;

    private RadioGroup radioGroup_roomId;
    private List<Room> list_room;
    
    private List<Traveller> list_traveller = new ArrayList<>();
    private List<TravellerAndIDcardView> list_travellerAndIDcardView;
    private List<Traveller> list_new_traveller;

    private RadioGroup radioGroup_traveller;
    private List<Integer> list_id;
    private List<TravellerAndIDcardView> all_traveller_view = new ArrayList<>();
    private int choice;

    @Override
    protected void initViews() {
        String orderJson = getIntent().getStringExtra("orderJson");
        Gson gson = new Gson();
        order_this = gson.fromJson(orderJson,Order.class);

        order_book_time = findViewById(R.id.manage_order_book_time);
        order_roomId = findViewById(R.id.manage_order_roomId);
        order_choiceStart = findViewById(R.id.manage_order_choiceStart);
        order_choiceEnd = findViewById(R.id.manage_order_choiceEnd);
        order_start_time = findViewById(R.id.manage_order_start_time);
        order_end_time = findViewById(R.id.manage_order_end_time);

        order_price = findViewById(R.id.manage_order_price);
        order_comment = findViewById(R.id.manage_order_comment);

        fab_menu = findViewById(R.id.fab_order_menu);
        fab_commit = findViewById(R.id.fab_order_change_commit);
        fab_delete = findViewById(R.id.fab_order_delete);

        radioGroup_roomId = findViewById(R.id.manage_order_roomID_radioGroup);
        radioGroup_traveller = findViewById(R.id.manage_order_traveller_radioGroup);

        order_book_time.setText("下单时间:" + '\n' + order_this.getCreatedAt());
        order_roomId.getEditText().setText(order_this.getRoomId());
        order_roomId.getEditText().setFocusable(false);
        order_choiceStart.setOnClickListener(this);
        order_choiceEnd.setOnClickListener(this);
        order_start_time.setText(order_this.getStartTime().getDate());
        order_end_time.setText(order_this.getEndTime().getDate());

//        t1.SetTravellerName(order_this.getUser().getUsername());
//        t1.SetIDcard(order_this.getUser().getIDcard());

        order_price.getEditText().setText(order_this.getPrice().toString());
        order_comment.setText(order_this.getUserMassage());

        fab_commit.setOnClickListener(this);
        fab_delete.setOnClickListener(this);


        setTravller();


        displayList.add(DateTimeConfig.YEAR);
        displayList.add(DateTimeConfig.MONTH);
        displayList.add(DateTimeConfig.DAY);
        displayList.add(DateTimeConfig.HOUR);

        startTime = BmobTimeUtil.StringToDate(order_this.getStartTime().getDate());
        endTime = BmobTimeUtil.StringToDate(order_this.getEndTime().getDate());

        initRadioGroup();

        radioGroup_roomId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                order_roomId.getEditText().setText(list_room.get(i).getRoomId());
                List<Date> temp_list = BmobTimeUtil.getDaysBetween
                        (order_start_time.getText().toString(),order_end_time.getText().toString());
                order_price.getEditText().setText
                        (String.valueOf(list_room.get(i).getPrice() *
                                temp_list.size() * list_room.get(i).getDiscount()));
            }
        });
        radioGroup_traveller.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.e("TAG", "i choice: " + i);
                RadioButton radioButton = findViewById(i);
                list_traveller.clear();
                list_travellerAndIDcardView.clear();
                choice = Integer.parseInt(radioButton.getText().toString());
                if (choice == 100){
                    choice = 1;
                }
                for (int j = 0; j < 5; j++) {
                    if (j < choice){
                        all_traveller_view.get(j).setVisibility(View.VISIBLE);
                        Traveller t = new Traveller(all_traveller_view.get(j).getTravellerName(),all_traveller_view.get(j).getIDcard());
                        list_traveller.add(t);
                        TravellerAndIDcardView tad = findViewById(list_id.get(j));
                        list_travellerAndIDcardView.add(tad);
                    } else {
                        all_traveller_view.get(j).setVisibility(View.GONE);
                    }
                }
                Log.e("TAG", "list_traveller size: " + list_traveller.size());
            }
        });
    }

    private void initRadioGroup(){
        for (int i = 0; i < 5; i++) {
            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setId(i * 101);
            radioButton.setButtonDrawable(null);
            radioButton.setTextColor(getResources().getColorStateList(R.color.bottom_nav_selector));
            radioButton.setPadding(25,0,25,0);
            radioButton.setText(String.valueOf(i + 1));
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10,0,10,0);

            radioGroup_traveller.addView(radioButton,lp);

        }
        radioGroup_traveller.check((order_this.getTravellerList().size() - 1) * 101);
        BmobQuery<Room> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Room>() {
            @Override
            public void done(List<Room> list, BmobException e) {
                if (e == null){
                    if (list.size() > 0){
                        list_room = list;
                        for (int i = 0; i < list.size(); i++) {
                            RadioButton radioButton = new RadioButton(getApplicationContext());
                            radioButton.setId(i);
                            radioButton.setButtonDrawable(null);
                            radioButton.setTextColor(getResources().getColorStateList(R.color.bottom_nav_selector));
                            radioButton.setPadding(25,0,25,0);
                            radioButton.setText(list.get(i).getRoomId());
                            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            lp.setMargins(10,0,10,0);


                            radioGroup_roomId.addView(radioButton,lp);
//                            if (order_this.getRoomId().equals(list.get(i).getRoomId())){
//                                radioGroup.check(i);
//                            }
                        }

                    }
                } else {
                    Log.e("TAG", "done: error");
                }
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_order;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.manage_order_choiceStart:
                new CardDatePickerDialog.Builder(this)
                        .setTitle("title")
                        .setMinTime(new Date().getTime())
                        .setMaxTime(BmobTimeUtil.getDateAfterMonth(2).getTime())
                        .setThemeColor(getResources().getColor(R.color.深竹月_浅色))
                        .setDisplayType(displayList)
                        .setWrapSelectorWheel(false)
                        .setOnChoose("确定",aLong -> {
                            startTime = new Date(aLong);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Log.i("TAG", "onClick: start_time:" + startTime);
                            if (order_this.getEndTime() == null){//还没选退房时间
                                order_start_time.setTextColor(getResources().getColor(R.color.time_show));
                                order_start_time.setText(format.format(startTime));
                                order_this.setStartTime(new BmobDate(startTime));
                                return null;
                            } else {
                                //已经选择了退房时间
                                if (startTime.getTime() < endTime.getTime()){//小于退房时间
                                    order_start_time.setTextColor(getResources().getColor(R.color.time_show));
                                    order_start_time.setText(format.format(startTime));
                                    order_this.setStartTime(new BmobDate(startTime));
                                }
                                else {//大于退房时间
                                    Toast.makeText(this,"入住时间大于退房时间",Toast.LENGTH_SHORT).show();
                                    return null;
                                }
                            }
                            return null;

                        }).build().show();
                break;
            case R.id.manage_order_choiceEnd:

                //设置结束时间显示


                new CardDatePickerDialog.Builder(this)
                        .setTitle("title")
                        .setMinTime(new Date().getTime())
                        .setMaxTime(BmobTimeUtil.getDateAfterMonth(2).getTime())
                        .setThemeColor(getResources().getColor(R.color.深竹月_浅色))
                        .setDisplayType(displayList)
                        .setWrapSelectorWheel(false)
                        .setOnChoose("确定",aLong -> {
                            endTime = new Date(aLong);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Log.i("TAG", "onClick: end_time:" + endTime );
                            if (order_this.getStartTime() == null){//还没选入住时间
                                order_end_time.setTextColor(getResources().getColor(R.color.time_show));
                                order_end_time.setText(format.format(endTime));
                                order_this.setEndTime(new BmobDate(endTime));
                                return null;
                            } else {
                                //已经选择了退房时间
                                if (startTime.getTime() < endTime.getTime()){//大于入住时间
                                    order_end_time.setTextColor(getResources().getColor(R.color.time_show));
                                    order_end_time.setText(format.format(endTime));
                                    order_this.setEndTime(new BmobDate(endTime));
                                }
                                else {//小于入住时间
                                    Toast.makeText(this,"退房时间小于入住时间",Toast.LENGTH_SHORT).show();
                                    return null;
                                }
                            }
                            return null;
                        }).build().show();
                break;
            case R.id.fab_order_change_commit:
                if (!order_price.getEditText().getText().toString().equals("")){
                    if (Double.parseDouble(order_price.getEditText().getText().toString()) >= 0){
                        if (checkTraveller() && list_new_traveller.size() > 0){
                            buildOrder();
                        } else {
                            Snackbar.make(fab_menu, "旅客信息没有填完", Snackbar.LENGTH_SHORT).show();
                        }

                    } else {
                        Snackbar.make(fab_menu, "订单价格不可小于0", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(fab_menu, "价格没有输入", Snackbar.LENGTH_SHORT).show();
                }


                break;
            case R.id.fab_order_delete:
                Snackbar.make(fab_menu,"此操作不可逆，确定要删除此订单吗",Snackbar.LENGTH_INDEFINITE)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                System.out.println("yes");
                                deleteOrder();
                            }
                        }).show();
                break;
        }
    }

    private Boolean checkTraveller(){
        list_new_traveller = new ArrayList<>();
        for (int i = 0; i < list_traveller.size(); i++) {
            if (!list_travellerAndIDcardView.get(i).isHasEmpty()){//两个都不是空
                list_new_traveller.add(list_traveller.get(i));
                continue;
            }
            if (//判断两个中是否有一个没填写
                    (list_travellerAndIDcardView.get(i).getIDcard().equals("") && !list_travellerAndIDcardView.get(i).getTravellerName().equals(""))
                            ||
                            (!list_travellerAndIDcardView.get(i).getIDcard().equals("") && list_travellerAndIDcardView.get(i).getTravellerName().equals(""))
            ){
                return false;
            }
        }
        return true;
    }

    private void setTravller() {
        list_id = new ArrayList<>();
        list_travellerAndIDcardView = new ArrayList<>();
        list_id.add(R.id.manage_order_traveller_info_1);
        list_id.add(R.id.manage_order_traveller_info_2);
        list_id.add(R.id.manage_order_traveller_info_3);
        list_id.add(R.id.manage_order_traveller_info_4);
        list_id.add(R.id.manage_order_traveller_info_5);
        for (int i = 0; i < order_this.getTravellerList().size(); i++) {
            TravellerAndIDcardView t = findViewById(list_id.get(i));
            t.SetTravellerName(order_this.getTravellerList().get(i).getTravellerName());
            t.SetIDcard(order_this.getTravellerList().get(i).getIDcard());
            t.setVisibility(View.VISIBLE);
            list_traveller.add(order_this.getTravellerList().get(i));
            list_travellerAndIDcardView.add(t);
        }
        for (int i = 0; i < 5; i++) {
            TravellerAndIDcardView t = findViewById(list_id.get(i));
            all_traveller_view.add(t);
        }
    }

    private void buildOrder(){
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(order_this.getMsgId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                order_this.setRoomId(order_roomId.getEditText().getText().toString());
                order_this.setPrice(Double.parseDouble(order_price.getEditText().getText().toString()));
//                order_this.setStartTime(new BmobDate(BmobTimeUtil.StringToDate(order_start_time.getText().toString())));
//                order_this.setEndTime(new BmobDate(BmobTimeUtil.StringToDate(order_end_time.getText().toString())));
                order_this.setTravellerList(list_new_traveller);
                order_this.setUser(user);
                System.out.println(order_this.toString());

                order_this.update(order_this.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Snackbar.make(fab_menu, "更新成功", Snackbar.LENGTH_LONG).show();
                            fab_menu.collapse();
                        }
                        else {
                            Snackbar.make(fab_menu,"更新失败" + e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void deleteOrder() {
        order_this.delete(order_this.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(fab_menu, "删除成功", Snackbar.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(fab_menu, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
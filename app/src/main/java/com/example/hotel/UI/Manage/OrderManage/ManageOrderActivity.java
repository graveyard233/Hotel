package com.example.hotel.UI.Manage.OrderManage;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.Bean.Order;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
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

        order_book_time.setText("下单时间:" + '\n' + order_this.getCreatedAt());
        order_roomId.getEditText().setText(order_this.getRoomId());
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
                            Log.i("TAG", "onClick: start_time:" + startTime);
                            if (order_this.getEndTime() == null){//还没选退房时间
                                order_start_time.setTextColor(getResources().getColor(R.color.time_show));
                                order_start_time.setText(BmobTimeUtil.DateToString(startTime));
                                order_this.setStartTime(new BmobDate(startTime));
                                return null;
                            } else {
                                //已经选择了退房时间
                                if (startTime.getTime() < endTime.getTime()){//小于退房时间
                                    order_start_time.setTextColor(getResources().getColor(R.color.time_show));
                                    order_start_time.setText(BmobTimeUtil.DateToString(startTime));
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
                            Log.i("TAG", "onClick: end_time:" + endTime );
                            if (order_this.getStartTime() == null){//还没选入住时间
                                order_end_time.setTextColor(getResources().getColor(R.color.time_show));
                                order_end_time.setText(BmobTimeUtil.DateToString(endTime));
                                order_this.setEndTime(new BmobDate(endTime));
                                return null;
                            } else {
                                //已经选择了退房时间
                                if (startTime.getTime() < endTime.getTime()){//大于入住时间
                                    order_end_time.setTextColor(getResources().getColor(R.color.time_show));
                                    order_end_time.setText(BmobTimeUtil.DateToString(endTime));
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
                buildOrder();
                break;
            case R.id.fab_order_delete:
                System.out.println("fuk");
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



    private void setTravller() {
        List<Integer> list_id = new ArrayList<>();
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
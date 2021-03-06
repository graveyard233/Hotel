package com.example.hotel.UI.Room;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.OrderViewInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//implements View.OnClickListener
public class RoomDetailActivity extends BaseActivity  {


    private static final String TAG = "RoomDetailActivity";

    private OrderPresenter orderPresenter = new OrderPresenter();

//    private SampleDecorator decorator = SampleDecorator.get();

    private SampleDecorator decorator;

    private TextView book_text;

    private TextView roomId;

    List<Date> timeList = new ArrayList<>();

    Gson gson = new Gson();

    private Room this_room;
    private String roomJson;


    @Override
    protected void initViews() {

//        Toolbar toolbar = findViewById(R.id.room_detail_toolBar);
//        toolbar.setNavigationOnClickListener(this);
        roomJson = getIntent().getStringExtra("roomJson");
        Gson gson= new Gson();
        this_room = gson.fromJson(roomJson,Room.class);
        Log.i(TAG, "initViews: " + this_room.toString());

        ImageView img = findViewById(R.id.room_detail_header_img);
        Glide.with(getApplicationContext())
                .load(this_room.getImgUrl())
                .placeholder(R.drawable.ic_bottom_room_24)
                .error(R.drawable.ic_launcher_foreground)
                .into(img);

        TextView title = findViewById(R.id.room_detail_header_title);
        title.setText(this_room.getType());

        TextView price = findViewById(R.id.room_detail_header_price);
        double priceNow = this_room.getPrice() * this_room.getDiscount();
        price.setText(String.valueOf(priceNow) + "???");

        TextView isBusy = findViewById(R.id.room_detail_header_isBusy);
        if (this_room.getIsBusy().equals("??????"))
            isBusy.setText("????????????");
        else
            isBusy.setText("????????????????????????????????????");

        TextView discount = findViewById(R.id.room_detail_header_discount);
        discount.append(this_room.getDiscount().toString());

        LinearLayout room_detail_comment = findViewById(R.id.room_detail_comments);
        TextView t1 = findViewById(R.id.comments_text);
        ColorStateList color = t1.getTextColors();
        t1.setTextColor(getResources().getColor(R.color.?????????));
        ViewGroup.LayoutParams lp = t1.getLayoutParams();

        for (int i = 0; i < this_room.getCommentList().size(); i++) {
            if (i == 3)
                break;//??????????????????
            TextView textView = new TextView(this);
            textView.setLayoutParams(lp);
            textView.setText(this_room.getCommentList().get(i));
            textView.setTextColor(color);
            room_detail_comment.addView(textView);
        }



        LinearLayout timeLinearLayout = findViewById(R.id.select_time_Linear);
        TextView et_date = findViewById(R.id.et_date);

        timeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderPresenter.setRoom(this_room);
                orderPresenter.getOrderModel(new OrderViewInterface() {
                    @Override
                    public void getAllOrdersSucceed(List<Order> orders) {
//                        List<Date> timeList = new ArrayList<>();
//                        Log.i(TAG, "getAllOrdersSucceed: " + orders.get(0).toString());
//                        System.out.println(orders.get(0).getStartTime().getDate() + "!");
//                        System.out.println("date:" + BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
//                        timeList.add(BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
//                        timeList.add(BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
//                        decorator = new SampleDecorator();
//                        decorator.setTimes(timeList);
//                        CalendarDialogUtil.showChooseDateDialog(decorator,
//                                RoomDetailActivity.this,
//                                "????????????",
//                                "??????", "??????",
//                                new CalendarDialog.ClickCallBack() {
//                                    @Override
//                                    public void onOk(CalendarDialog dlg) {
//                                        dlg.dismissDlg();
//                                    }
//
//                                    @Override
//                                    public void onCancel(CalendarDialog dlg) {
//                                        dlg.dismissDlg();
//                                    }
//                                }
//                                ,et_date);
                    }

                    @Override
                    public void getAllOrderError() {
                        Log.i(TAG, "getOrderError: ");
                    }

                    @Override
                    public void getOrderById(List<Order> orders) {

                        Log.i(TAG, "getOrdersByIdSucceed: " + orders.get(0).toString());
                        System.out.println(orders.get(0).getStartTime().getDate() + "!");
                        System.out.println("date:" + BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
//                        for (int i = 0; i < orders.size(); i++) {
//                            timeList.add(BmobTimeUtil.StringToDate(orders.get(i).getStartTime().getDate()));
//                            timeList.add(BmobTimeUtil.StringToDate(orders.get(i).getEndTime().getDate()));
//                        }

//                        timeList = BmobTimeUtil.getDaysBetween(orders.get(0).getStartTime().getDate(),
//                                    orders.get(0).getEndTime().getDate());
                        for (int i = 0; i < orders.size(); i++) {
                            if (orders.get(i).getIsPay() == 3)//?????????????????????
                            {
                                continue;
                            }
                            List<Date> list_temp = new ArrayList<>();
                            list_temp = BmobTimeUtil.getDaysBetween(orders.get(i).getStartTime().getDate(),
                                    orders.get(i).getEndTime().getDate());
                            for (int j = 0; j < list_temp.size(); j++) {
                                timeList.add(list_temp.get(j));
                            }
                        }



                        decorator = new SampleDecorator();
                        decorator.setTimes(timeList);
                        CalendarDialogUtil.showChooseDateDialog(decorator,
                                RoomDetailActivity.this,
                                "????????????",
                                "??????", "??????",
                                new CalendarDialog.ClickCallBack() {
                                    @Override
                                    public void onOk(CalendarDialog dlg) {
                                        dlg.dismissDlg();
                                    }

                                    @Override
                                    public void onCancel(CalendarDialog dlg) {
                                        dlg.dismissDlg();
                                    }
                                }
                                ,et_date);
                    }

                    @Override
                    public void getOrderByIdError() {
                        System.out.println("fuck");//??????????????????????????????
                        decorator = new SampleDecorator();
                        CalendarDialogUtil.showChooseDateDialog(decorator,
                                RoomDetailActivity.this,
                                "????????????",
                                "??????", "??????",
                                new CalendarDialog.ClickCallBack() {
                                    @Override
                                    public void onOk(CalendarDialog dlg) {
                                        dlg.dismissDlg();
                                    }

                                    @Override
                                    public void onCancel(CalendarDialog dlg) {
                                        dlg.dismissDlg();
                                    }
                                }
                                ,et_date);
                    }

                    @Override
                    public void addOrder(String objId, int i) {

                    }

                    @Override
                    public void cancelOrder(String objId) {

                    }
                });
            }
        });

        book_text = findViewById(R.id.room_detail_header_book);
        book_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity_book_the_room.class);
                intent.putExtra("ROOM",roomJson);
//                if (timeList.size() > 0){
//                    String s_time_list = gson.toJson(timeList);
//                    intent.putExtra("timeList",s_time_list);
//                }
                startActivityForResult(intent,1);

            }
        });

        roomId = findViewById(R.id.room_detail_roomId);
        roomId.setText("?????????:" + this_room.getRoomId());

//        orderPresenter.getOrderModel(new OrderViewInterface() {
//            @Override
//            public void getAllOrdersSucceed(List<Order> orders) {
//                List<Date> timeList = new ArrayList<>();
//                Log.i(TAG, "getAllOrdersSucceed: " + orders.get(0).toString());
//                System.out.println(orders.get(0).getStartTime().getDate() + "!");
//                System.out.println("date:" + BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
//                timeList.add(BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
//                //??????????????????????????????,????????????date?????????
//                timeLinearLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        decorator.setTimes(timeList);
//                        CalendarDialogUtil.showChooseDateDialog(decorator,
//                                RoomDetailActivity.this,
//                                "???????????????",
//                                "??????", "??????",
//                                new CalendarDialog.ClickCallBack() {
//                            @Override
//                            public void onOk(CalendarDialog dlg) {
//                                dlg.dismissDlg();
//                            }
//                            @Override
//                            public void onCancel(CalendarDialog dlg) {
//                                dlg.dismissDlg();
//                            }
//                        },et_date);
//                    }
//                });
//            }
//
//            @Override
//            public void getOrderError() {
//                Log.i(TAG, "getOrderError: ");
//            }
//        });


        //???????????????
//        Calendar nextYear = Calendar.getInstance();
//        nextYear.add(Calendar.YEAR,1);

//        CalendarPickerView calendarPickerView = findViewById(R.id.myCalendar);
//        Date today = new Date();
//        calendarPickerView.init(today,nextYear.getTime())
//                .withSelectedDate(today)
//                .inMode(CalendarPickerView.SelectionMode.RANGE);
//        ??????
//        https://www.jianshu.com/p/252b355be7ca

//        https://blog.csdn.net/Small_Lee/article/details/51114600
//        https://blog.csdn.net/liubo253/article/details/54927574

//        https://blog.csdn.net/xinluqishi123/article/details/70494854

//        https://blog.csdn.net/huanghaibin_dev/article/details/79040147
//        toolbar.setTitle(this_room.getType());

//        toolbar.setTitle(roomId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("data_return");
                    System.out.println(returnData);
                    Toast.makeText(this,"????????????",Toast.LENGTH_LONG).show();
                    finish();
                }
                if (resultCode == RESULT_CANCELED){
//                    String returnData = data.getStringExtra("data_return");
//                    System.out.println(returnData);
                }
                break;
            default:break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_detail;
    }



//    @Override
//    public void onClick(View view) {
//        finish();
//    }
}

package com.example.hotel.UI.Manage.ReportManage;

import android.util.Log;
import android.view.View;

import com.example.hotel.Bean.Announcement;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;
import com.example.hotel.UI.Order.BmobTimeUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class DrawColumn {
    private static final String TAG = "DrawColumn";

    private List<String> list_roomType = new ArrayList<>();
    private List<Income> list_type_income = new ArrayList<>();
    private List<Room> list_all_room = new ArrayList<>();
    private List<Order> list_order_this_month = new ArrayList<>();
    private ColumnChartView columnChartView;

    public DrawColumn(ColumnChartView columnChartView,List<String> list_roomType, List<Room> list_all_room) {
        this.columnChartView = columnChartView;
        this.list_roomType = list_roomType;
        this.list_all_room = list_all_room;
    }

    public void setList_roomType(List<String> list_roomType) {
        this.list_roomType = list_roomType;
    }

    public void setList_all_room(List<Room> list_all_room) {
        this.list_all_room = list_all_room;
    }

    public void getThisMonthOrderList(){

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        //当月有多少天
        int maxDate = a.get(Calendar.DATE);

        SimpleDateFormat sdf_s = new SimpleDateFormat("yyyy-MM-");
        String start_s = sdf_s.format(new Date()) + "01 00:00:00";
        String end_s = sdf_s.format(new Date()) + maxDate + " 23:59:59";
        BmobQuery<Order> bmobQuery = new BmobQuery<>();
        BmobDate start = new BmobDate(BmobTimeUtil.StringToDate(start_s));
        BmobDate end = new BmobDate(BmobTimeUtil.StringToDate(end_s));
        BmobQuery<Order> oStart = new BmobQuery<>();
        oStart.addWhereGreaterThanOrEqualTo("startTime",start);
        BmobQuery<Order> oEnd = new BmobQuery<>();
        oEnd.addWhereLessThanOrEqualTo("endTime",end);
        List<BmobQuery<Order>> queries = new ArrayList<>();
        queries.add(oStart);
        queries.add(oEnd);

        bmobQuery.and(queries);
        bmobQuery.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null){
                    if (list.size() > 0){
                        list_order_this_month = list;
                        initColumnData();
                        generateView();
                    } else {
                        Log.e(TAG, "这个月没订单" );
                    }
                } else {
                    Log.e(TAG, "error");
                }
            }
        });
    }

    public void drawAColumn(){
        initColumnData();
        generateView();
    }

    private void generateView(){
        ColumnChartData columnChartData;
        //大柱子
        List<Column> columns = new ArrayList<>();


        for (int i = 0; i < list_roomType.size(); i++) {
            //小柱子
            List<SubcolumnValue> values = new ArrayList<>();

            values.add(new SubcolumnValue(list_type_income.get(i).getIncome(), ChartUtils.pickColor()));
            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
        }
        columnChartData = new ColumnChartData(columns);

        Axis axisX = new Axis();
        axisX.setName("房间类型");
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < list_roomType.size(); i++) {
            axisValues.add(new AxisValue(i).setLabel(list_roomType.get(i)));
        }
        axisX.setValues(axisValues);
        axisX.setHasLines(true);

        Axis axisY = new Axis();
        axisY.setName("元");
        axisY.setHasLines(true);

        columnChartData.setAxisXBottom(axisX);
        columnChartData.setAxisYLeft(axisY);


        columnChartView.setColumnChartData(columnChartData);
        columnChartView.setInteractive(true);
        columnChartView.setZoomType(ZoomType.HORIZONTAL);
        columnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        columnChartView.setVisibility(View.VISIBLE);
    }

    private void initColumnData() {
        for (int i = 0; i < list_roomType.size(); i++) {
            list_type_income.add(new Income(list_roomType.get(i),0f));
        }
        List<OrderWithRoomType> list_OrderWithRoomType = new ArrayList<>();
        //初始化orderwithtype
        for (int i = 0; i < list_order_this_month.size(); i++) {
            for (int j = 0; j < list_all_room.size(); j++) {
                if (list_order_this_month.get(i).getRoomId().equals(list_all_room.get(j).getRoomId()) ){
                    list_OrderWithRoomType.add(new OrderWithRoomType
                            (list_order_this_month.get(i),list_all_room.get(j).getType()));
                    break;
                }
            }
        }

        //初始化list type income
        for (int i = 0; i < list_OrderWithRoomType.size(); i++) {
            for (int j = 0; j < list_type_income.size(); j++) {
                if (list_OrderWithRoomType.get(i).getRoomType().equals(list_type_income.get(j).getRoomType())){
                    list_type_income.get(j).income += list_OrderWithRoomType.get(i).getOrder().getPrice();
                }
            }
        }
        for (int i = 0; i < list_type_income.size(); i++) {
            Log.e(TAG, "initColumnData: " + list_type_income.get(i).income);
        }


    }

    private class Income{
        private String roomType;
        private float income;



        public Income(String roomType, float income) {
            this.roomType = roomType;
            this.income = income;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public float getIncome() {
            return income;
        }

        public void setIncome(float income) {
            this.income = income;
        }
    }

    private class OrderWithRoomType{
        private Order order;
        private String roomType;

        public OrderWithRoomType(Order order, String roomType) {
            this.order = order;
            this.roomType = roomType;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }
    }
}

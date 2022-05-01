package com.example.hotel.UI.Manage.ReportManage;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.OrderViewInterface;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


public class ReportFragment extends BaseFragment {

    private LineChartView chart_income;
    private ColumnChartView columnChartView;

    private OrderPresenter orderPresenter = new OrderPresenter();

    Date[] dates;

    private Line line_income ;
    private Line line_cancel;

    private ArrayList<Order> list_not_cancel = new ArrayList<>();
    private List<PointValue> pointValues = new ArrayList<>();
    private List<PointValue> pointValues_cancel = new ArrayList<>();
    private List<AxisValue> axisValues = new ArrayList<>();
    private LineChartData data;
    private ArrayList<Line> lines;

    private FloatingActionButton fab;

    private String month_this;
    private String year_this;


    private Boolean orderListIsOK = false;
    private Boolean roomTypeIsOK = false;
    private List<String> list_roomType = new ArrayList<>();
    private final static int INIT_ROOM_TYPE_FINISH = 1;
    private List<Room> list_all_room = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.report_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_report_24);

        fab = getActivity().findViewById(R.id.manage_activity_floatingActionButton);
        fab.setVisibility(View.GONE);

        SimpleDateFormat sdf_month = new SimpleDateFormat("MM");
        month_this = sdf_month.format(new Date());
        SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
        year_this = sdf_year.format(new Date());
        dates = BmobTimeUtil.getAllDateThisMonth(year_this,month_this);
        System.out.println(dates.length);


        chart_income = find(R.id.chart_income);
        initIncomeData();

        columnChartView = find(R.id.columnChart);
    }

    private void initRoomType() {
        BmobQuery<Room> bmobQuery = new BmobQuery<>();
        bmobQuery.addQueryKeys("type,roomId");
        bmobQuery.findObjects(new FindListener<Room>() {
            @Override
            public void done(List<Room> list, BmobException e) {
                if (e == null){
                    for (int i = 0; i < list.size(); i++) {
                        list_roomType.add(list.get(i).getType());
                        list_all_room.add(list.get(i));
                    }
                    list_roomType = list_roomType.stream().distinct().collect(Collectors.toList());
                    handler.sendEmptyMessage(INIT_ROOM_TYPE_FINISH);
                    roomTypeIsOK = true;
                    Log.e("TAG", "get Room type done: ");
                } else {
                    Log.e("TAG", " get roomTpe error");

                }
            }
        });
    }

    private void initPoint(List<Order> orders,List<Order> cancels,List<Room> list_room) {

        Float x[] = new Float[100];
        Float y[] = new Float[100];
        Float cancel_y[] = new Float[100];
        for (int i = 0; i < 100; i++) {
            x[i] = 0f;y[i] = 0f;cancel_y[i] = 0f;
        }
        setYValue(y,orders,list_room);
        setYValue(cancel_y,cancels,list_room);

        for (int i = 0; i < 31; i++) {
            pointValues.add(new PointValue(i,y[i]));
            pointValues_cancel.add(new PointValue(i,cancel_y[i]));
        }
    }

    private void setYValue(Float[] y, List<Order> orderList, List<Room> list_room){
        List<OrderWithRoomPrice> list_with_price = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            for (int j = 0; j < list_room.size(); j++) {
                if (list_room.get(j).getRoomId().equals(orderList.get(i).getRoomId())){
                    list_with_price.add(new OrderWithRoomPrice(orderList.get(i),list_room.get(j).getPrice()));
                    break;
                }
            }
        }

        for (int i = 0; i < orderList.size(); i++) {
            List<Date> dayList = new ArrayList<>();
            dayList = BmobTimeUtil.getDaysBetween(orderList.get(i).getStartTime().getDate(),
                    orderList.get(i).getEndTime().getDate());
            for (int j = 0; j < dayList.size(); j++) {
                for (int k = 0; k < dates.length; k++) {
                    if (dayList.get(j).getMonth() == dates[k].getMonth()
                            && dayList.get(j).getDate() == dates[k].getDate()){
                        y[k] = y[k] + list_with_price.get(i).getPrice().floatValue();
                    }
                }
            }
        }
    }

    private void initXLable() {
        for (int i = 0; i < dates.length; i++) {
            axisValues.add(new AxisValue(i).setLabel(String.valueOf(dates[i].getDate())));
        }
    }

    private void initLine() {
        lines = new ArrayList<>();

        line_income = new Line();
        line_income.setValues(pointValues);
        line_income.setColor(getResources().getColor(R.color.深竹月));

        line_income.setShape(ValueShape.CIRCLE);
        line_income.setCubic(false);
        line_income.setFilled(true);
//        line_income.setHasLabels(true);
        line_income.setHasLabelsOnlyForSelected(true);

        lines.add(line_income);


        line_cancel = new Line();
        line_cancel.setValues(pointValues_cancel);
        line_cancel.setColor(getResources().getColor(R.color.春梅红));
        line_cancel.setCubic(false);
        line_cancel.setFilled(true);
        line_cancel.setHasPoints(false);

        lines.add(line_cancel);

        data = new LineChartData();
        data.setLines(lines);
    }

    private void initXY(){
        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(getResources().getColor(R.color.深竹月_浅色));
        axisX.setTextSize(25);
        axisX.setMaxLabelChars(8);
        axisX.setValues(axisValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);

        Axis axisY = new Axis();
        axisY.setName("元");
        axisY.setTextColor(getResources().getColor(R.color.深竹月_浅色));
        axisY.setTextSize(15);
        data.setAxisYLeft(axisY);
    }

    private void initIncomeData() {
        orderPresenter.getOrderModel(new OrderViewInterface() {
            @Override
            public void getAllOrdersSucceed(List<Order> orders) {

//                list_not_cancel = new ArrayList<>();
                List<Order> list_cancel = new ArrayList<>();
                list_roomType.clear();
                list_not_cancel.clear();
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getIsPay() != 3){
                        list_not_cancel.add(orders.get(i));
                    } else {
                        list_cancel.add(orders.get(i));
                    }
                }

                BmobQuery<Room> bmobQuery = new BmobQuery<>();
                bmobQuery.findObjects(new FindListener<Room>() {
                    @Override
                    public void done(List<Room> list, BmobException e) {
                        if (e == null){
                            if (list.size() > 0){
                                initXLable();
                                initPoint(list_not_cancel,list_cancel,list);
                                initLine();
                                initXY();

                                chart_income.setLineChartData(data);
                                chart_income.setInteractive(true);
                                chart_income.setZoomType(ZoomType.HORIZONTAL);
                                chart_income.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                                chart_income.setMaxZoom(3f);

                                chart_income.setVisibility(View.VISIBLE);
                                orderListIsOK = true;
                                initRoomType();
                            } else {
                                Log.e("TAG", "error");
                            }
                        } else {
                            Log.e("TAG", "error");
                        }
                    }
                });



            }

            @Override
            public void getAllOrderError() {

            }

            @Override
            public void getOrderById(List<Order> orders) {

            }

            @Override
            public void getOrderByIdError() {

            }

            @Override
            public void addOrder(String objId, int i) {

            }

            @Override
            public void cancelOrder(String objId) {

            }
        });
    }



    final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == INIT_ROOM_TYPE_FINISH && orderListIsOK && roomTypeIsOK){
                    Log.e("TAG", "handleMessage: get");
                    DrawColumn drawColumn = new DrawColumn(columnChartView,list_roomType
                    ,list_all_room);
                    drawColumn.getThisMonthOrderList();
            }


        }
    };

    private class OrderWithRoomPrice{
        private Order order;
        private Double price;

        public OrderWithRoomPrice(Order order, Double price) {
            this.order = order;
            this.price = price;
        }

        public Order getOrder() {
            return order;
        }

        public Double getPrice() {
            return price;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }
}
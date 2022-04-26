package com.example.hotel.UI.Manage.ReportManage;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hotel.Bean.Order;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.OrderViewInterface;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;


public class ReportFragment extends BaseFragment {

    private LineChartView chart_income;

    private OrderPresenter orderPresenter = new OrderPresenter();

    Date[] dates;

    private Line line_income ;
    private List<PointValue> pointValues = new ArrayList<>();
    private List<AxisValue> axisValues = new ArrayList<>();
    private LineChartData data;
    private ArrayList<Line> lines;

    private FloatingActionButton fab;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.report_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_report_24);

        fab = getActivity().findViewById(R.id.manage_activity_floatingActionButton);
        fab.setVisibility(View.GONE);

        dates = BmobTimeUtil.getAllDateThisMonth("2022","04");
        for (int i = 0; i < dates.length; i++) {
            System.out.println(dates[i].getDate());
        }
        System.out.println(dates.length);


        chart_income = find(R.id.chart_income);
        initIncomeData();
    }

    private void initPoint(List<Order> orders) {

        Float x[] = new Float[100];
        Float y[] = new Float[100];
        for (int i = 0; i < 100; i++) {
            x[i] = 0f;y[i] = 0f;
        }
        for (int i = 0; i < orders.size(); i++) {
            List<Date> dayList = new ArrayList<>();
            dayList = BmobTimeUtil.getDaysBetween(orders.get(i).getStartTime().getDate(),
                    orders.get(i).getEndTime().getDate());
            for (int j = 0; j < dayList.size(); j++) {
                for (int k = 0; k < dates.length; k++) {
                    if (dayList.get(j).getMonth() == dates[k].getMonth()
                            && dayList.get(j).getDate() == dates[k].getDate()){
                        System.out.println(dates[k].getDate() + "yes " + k);
//                        pointValues.add(new PointValue(k, orders.get(i).getPrice().floatValue()));
                        y[k] = y[k] + orders.get(i).getPrice().floatValue();


                    }
                }

            }


        }

        for (int i = 0; i < 31; i++) {
            pointValues.add(new PointValue(i,y[i]));
        }
    }

    private void initXLable() {
        for (int i = 0; i < dates.length; i++) {
            axisValues.add(new AxisValue(i).setLabel(String.valueOf(dates[i].getDate())));
        }
    }

    private void initLine() {
        line_income = new Line();
        line_income.setValues(pointValues);
        line_income.setColor(getResources().getColor(R.color.深竹月));
        lines = new ArrayList<>();
        line_income.setShape(ValueShape.CIRCLE);
        line_income.setCubic(false);
        line_income.setFilled(true);
//        line_income.setHasLabels(true);
        line_income.setHasLabelsOnlyForSelected(true);
        lines.add(line_income);
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

                List<Order> list_not_cancel = new ArrayList<>();

                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getIsPay() != 3){
                        list_not_cancel.add(orders.get(i));
                    }
                }

                initXLable();
                initPoint(list_not_cancel);
                initLine();
                initXY();


                chart_income.setLineChartData(data);
                chart_income.setInteractive(true);
                chart_income.setZoomType(ZoomType.HORIZONTAL);
                chart_income.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                chart_income.setMaxZoom(3f);

                chart_income.setVisibility(View.VISIBLE);

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }
}
package com.example.hotel.UI.Order;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Order_isShow;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Order.adapter.OrderRecyclerViewAdapter;
import com.example.hotel.UI.Room.adapter.RoomRecyclerViewAdapter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class OrderFragment extends BaseFragment implements OnRefreshListener {

    private OrderRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    private RefreshLayout refreshLayout;

    private List<Order_isShow> list = new ArrayList<>();
    private List<Order> all_order_list = new ArrayList<>();

    private Boolean is_gone = false;

    private OrderPresenter presenter = new OrderPresenter();

    private BmobUser user;


    @Override
    protected void initViews() {
        user = BmobUser.getCurrentUser();
        refreshLayout = find(R.id.refreshLayout_order);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setOnRefreshListener(this);
//        initList();
        refreshView();

    }

    private void initList(){
        Order order = new Order();
        order.setMsgId("111");
        order.setPrice(20d);
        list.add(new Order_isShow(order));
        list.add(new Order_isShow(order));
        list.add(new Order_isShow(order));
        list.add(new Order_isShow(order));
    }

    private void refreshView(){
        presenter.getOrderModel(new OrderViewInterface() {
            @Override
            public void getAllOrdersSucceed(List<Order> orders) {
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getMsgId().equals(user.getObjectId())){
                        list.add(new Order_isShow(orders.get(i)));
                    }
                    adapter = new OrderRecyclerViewAdapter(list);
                    recyclerView = find(R.id.order_recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);


                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Log.i("TAG", "onItemChildClick: ");
                            if (list.get(position).isShow()){
                                list.get(position).setShow(false);
                            } else {
                                list.get(position).setShow(true);
                            }
                            adapter.setNewData(list);

                        }
                    });

                }
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
        });

//        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
//            @Override
//            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        // TODO: 2022/4/18 准备做刷新，在这里获取数据
        presenter.getOrderModel(new OrderViewInterface() {
            @Override
            public void getAllOrdersSucceed(List<Order> orders) {
                list.clear();
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getMsgId().equals(user.getObjectId())){
                        list.add(new Order_isShow(orders.get(i)));
                    }
                    adapter = new OrderRecyclerViewAdapter(list);
                    recyclerView = find(R.id.order_recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                    refreshLayout.finishRefresh(1000);


                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Log.i("TAG", "onItemChildClick: ");
                            if (list.get(position).isShow()){
                                list.get(position).setShow(false);
                            } else {
                                list.get(position).setShow(true);
                            }
                            adapter.setNewData(list);

                        }
                    });

                }
            }

            @Override
            public void getAllOrderError() {
                refreshLayout.finishRefresh(false);
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
        });

    }
}

package com.example.hotel.UI.Order;

import android.graphics.Color;
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
import com.google.android.material.snackbar.Snackbar;
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
    //https://s1.ax1x.com/2022/04/24/L4qIXt.jpg 图片
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

                    adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                            View view1 = view;
                            view1.setBackgroundColor(Color.parseColor("#DCDCDC"));

                            Snackbar.make(view,"确定要退订吗",Snackbar.LENGTH_INDEFINITE)
                                    .setAction("yes", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // TODO: 2022/4/24
                                            presenter.cancelOrder(orders.get(position), new OrderViewInterface() {
                                                @Override
                                                public void getAllOrdersSucceed(List<Order> orders) {

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
                                            Toast.makeText(getActivity(),"yes",Toast.LENGTH_SHORT).show();
                                            view1.setBackgroundColor(Color.WHITE);
                                        }
                                    })
                                    .setCallback(new Snackbar.Callback(){

                                        @Override
                                        public void onShown(Snackbar sb) {
                                            super.onShown(sb);
                                        }

                                        @Override
                                        public void onDismissed(Snackbar transientBottomBar, int event) {
                                            super.onDismissed(transientBottomBar, event);
                                            switch (event) {

                                                case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                                                case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                                                case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                                                    Toast.makeText(getActivity(), "撤销退订成功", Toast.LENGTH_SHORT).show();
                                                    view1.setBackgroundColor(Color.WHITE);
                                                    break;
                                            }
                                        }
                                    })
                                    .show();
                            return false;
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

            @Override
            public void cancelOrder(String objId) {

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

            @Override
            public void cancelOrder(String objId) {

            }
        });

    }
}

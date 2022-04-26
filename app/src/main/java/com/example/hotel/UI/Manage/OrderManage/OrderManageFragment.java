package com.example.hotel.UI.Manage.OrderManage;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Order_isShow;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.adapter.OrderRecyclerViewAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class OrderManageFragment extends BaseFragment implements OnRefreshListener {


    private OrderRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    private RefreshLayout refreshLayout;

    private List<Order_isShow> list_is_show = new ArrayList<>();
    private Boolean is_gone = false;

    private OrderPresenter presenter = new OrderPresenter();

    private FloatingActionButton fab;


    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.order_manage_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_list_manage_24);
        fab = getActivity().findViewById(R.id.manage_activity_floatingActionButton);
        refreshLayout = find(R.id.refreshLayout_order_manage);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("OrderManageFragment");
            }
        });

        recyclerView = find(R.id.order_manage_recyclerView);
        refreshView();
    }

    private void refreshView() {
        BmobQuery<Order> bmobQuery = new BmobQuery<>();
        list_is_show.clear();
        bmobQuery.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null && list.size() > 0){
                    for (int i = 0; i < list.size(); i++) {
                        list_is_show.add(new Order_isShow(list.get(i)));
                    }
                    adapter = new OrderRecyclerViewAdapter(list_is_show);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Log.i("TAG", "onItemChildClick: ");
                            if (list_is_show.get(position).isShow()){
                                list_is_show.get(position).setShow(false);
                            } else {
                                list_is_show.get(position).setShow(true);
                            }
                            adapter.setNewData(list_is_show);

                        }
                    });


                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            System.out.println("fuck");
                            Order order = list.get(position);
                            Gson gson = new Gson();
                            String orderJson = gson.toJson(order);
                            Intent to_order_manage = new Intent(getActivity(), ManageOrderActivity.class);
                            to_order_manage.putExtra("orderJson",orderJson);
                            startActivity(to_order_manage);
                        }
                    });
                } else if (e==null){
                    Toast.makeText(getActivity(),"无订单",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"获取数据错误:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_manage;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        BmobQuery<Order> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null && list.size() > 0){
                    list_is_show.clear();
                    for (int i = 0; i < list.size(); i++) {
                        list_is_show.add(new Order_isShow(list.get(i)));
                    }
                    adapter = new OrderRecyclerViewAdapter(list_is_show);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Log.i("TAG", "onItemChildClick: ");
                            if (list_is_show.get(position).isShow()){
                                list_is_show.get(position).setShow(false);
                            } else {
                                list_is_show.get(position).setShow(true);
                            }
                            adapter.setNewData(list_is_show);

                        }
                    });

                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Order order = list.get(position);
                            Gson gson = new Gson();
                            String orderJson = gson.toJson(order);
                            Intent to_order_manage = new Intent(getActivity(), ManageOrderActivity.class);
                            to_order_manage.putExtra("orderJson",orderJson);
                            startActivity(to_order_manage);
                        }
                    });
                } else if (e==null){
                    Toast.makeText(getActivity(),"无订单",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"获取数据错误:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishRefresh(1500);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh(refreshLayout);
    }
}
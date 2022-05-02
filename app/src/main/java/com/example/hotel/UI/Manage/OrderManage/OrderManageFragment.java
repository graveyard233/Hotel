package com.example.hotel.UI.Manage.OrderManage;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Order_isShow;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.adapter.OrderRecyclerViewAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class OrderManageFragment extends BaseFragment implements OnRefreshListener, View.OnClickListener {


    private OrderRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    private RefreshLayout refreshLayout;

    private List<Order_isShow> list_is_show = new ArrayList<>();
    private Boolean is_gone = false;

    private OrderPresenter presenter = new OrderPresenter();

    private FloatingActionButton fab;

    private SearchView searchView;
    private TextView doing;
    private TextView finished;
    private TextView canceled;
    private ImageView fanxiang;

    private List<Order_isShow> list_doing = new ArrayList<>();
    private List<Order_isShow> list_finished = new ArrayList<>();
    private List<Order_isShow> list_cancel = new ArrayList<>();


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

        recyclerView = find(R.id.order_manage_recyclerView);
        refreshView();

        searchView = find(R.id.order_manage_searchView);
        doing = find(R.id.order_manage_doing);
        finished = find(R.id.order_manage_finished);
        canceled = find(R.id.order_manage_canceled);
        fanxiang = find(R.id.order_manage_fanxiang);
        doing.setOnClickListener(this);
        finished.setOnClickListener(this);
        canceled.setOnClickListener(this);
        fanxiang.setOnClickListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("搜索的订单编号是 " + query);
                BmobQuery<Order> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("objectId","query").findObjects(new FindListener<Order>() {
                    @Override
                    public void done(List<Order> list, BmobException e) {
                        if (e == null){
                            if (list.size() > 0){
                                List<Order_isShow> newlist = new ArrayList<>();
                                newlist.add(new Order_isShow(list.get(0)));
                                //必须重新设置新的adapter，别问，问就是会出变色bug
                                adapter = new OrderRecyclerViewAdapter(newlist,getActivity());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);

                                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                    @Override
                                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                        Log.i("TAG", "onItemChildClick: ");
                                        if (newlist.get(position).isShow()){
                                            newlist.get(position).setShow(false);
                                        } else {
                                            newlist.get(position).setShow(true);
                                        }
                                        adapter.setNewData(newlist);

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

                            } else {
                                Toast.makeText(getActivity(),"订单不存在",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
                    Collections.reverse(list_is_show);
                    adapter = new OrderRecyclerViewAdapter(list_is_show,getActivity());
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
                    Collections.reverse(list_is_show);
                    adapter = new OrderRecyclerViewAdapter(list_is_show,getActivity());
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.order_manage_doing:
                search_doing();
                break;
            case R.id.order_manage_finished:
                search_finished();
                break;
            case R.id.order_manage_canceled:
                search_cancel();
                break;
            case R.id.order_manage_fanxiang:
                List<Order_isShow> list_fanzhuan = new ArrayList<>();
                list_fanzhuan = adapter.getData();
                Collections.reverse(list_fanzhuan);
                remakeAdapter(list_fanzhuan);
                break;
        }
    }

    private void search_doing(){
        list_doing.clear();
        List<Date> ordered_list = new ArrayList<>();
        for (int i = 0; i < list_is_show.size(); i++) {
            ordered_list.clear();
            ordered_list = BmobTimeUtil.getDaysBetween(list_is_show.get(i).getOrder().getStartTime().getDate(),
                    list_is_show.get(i).getOrder().getEndTime().getDate());
            if (BmobTimeUtil.checkTimeTodayIsInDateList(ordered_list)){
                list_doing.add(list_is_show.get(i));
            }
        }
        remakeAdapter(list_doing);
    }

    private void search_finished(){
        list_finished.clear();
        for (int i = 0; i < list_is_show.size(); i++) {
            if (list_is_show.get(i).getOrder().getIsPay() == 0){
                list_finished.add(list_is_show.get(i));
            }
        }
        remakeAdapter(list_finished);
    }

    private void search_cancel(){
        list_cancel.clear();
        for (int i = 0; i < list_is_show.size(); i++) {
            if (list_is_show.get(i).getOrder().getIsPay() == 3){
                list_cancel.add(list_is_show.get(i));
            }
        }
        remakeAdapter(list_cancel);
    }

    private void remakeAdapter(List<Order_isShow> list_remake){
        adapter = new OrderRecyclerViewAdapter(list_remake,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.i("TAG", "onItemChildClick: ");
                if (list_remake.get(position).isShow()){
                    list_remake.get(position).setShow(false);
                } else {
                    list_remake.get(position).setShow(true);
                }
                adapter.setNewData(list_remake);
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Order order = list_remake.get(position).getOrder();
                Gson gson = new Gson();
                String orderJson = gson.toJson(order);
                Intent to_order_manage = new Intent(getActivity(), ManageOrderActivity.class);
                to_order_manage.putExtra("orderJson",orderJson);
                startActivity(to_order_manage);
            }
        });
    }


}
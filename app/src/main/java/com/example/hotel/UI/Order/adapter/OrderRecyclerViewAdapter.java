package com.example.hotel.UI.Order.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.chad.library.adapter.base.BaseViewHolder;
//import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.hotel.Bean.Order_isShow;
import com.example.hotel.R;

import java.util.ArrayList;
import java.util.List;

public class OrderRecyclerViewAdapter extends BaseQuickAdapter<Order_isShow, BaseViewHolder> {

    private List<Integer> list_traveller_id = new ArrayList<>();


    public OrderRecyclerViewAdapter(List<Order_isShow> data) {
        super(R.layout.order_recycler_item,data);
    }


    public OrderRecyclerViewAdapter(){
        super(R.layout.order_recycler_item);

    }



    @Override
    protected void convert(BaseViewHolder helper, Order_isShow item) {
        helper.setText(R.id.item_order_orderId,item.getOrder().getObjectId())
                .setText(R.id.item_order_book_time,item.getOrder().getCreatedAt())
                .setText(R.id.item_order_room_id,item.getOrder().getRoomId())
                .setText(R.id.item_order_total_pay,item.getOrder().getPrice().toString())
                .setText(R.id.item_order_live_in_time_start,item.getOrder().getStartTime().getDate())
                .setText(R.id.item_order_live_in_time_end,item.getOrder().getEndTime().getDate())
        ;
        list_traveller_id.add(R.id.item_order_traveller1);
        list_traveller_id.add(R.id.item_order_traveller2);
        list_traveller_id.add(R.id.item_order_traveller3);
        list_traveller_id.add(R.id.item_order_traveller4);
        list_traveller_id.add(R.id.item_order_traveller5);
        for (int i = 0; i < item.getOrder().getTravellerList().size(); i++) {
            helper.setText(list_traveller_id.get(i),
                    item.getOrder().getTravellerList().get(i).getTravellerName());
        }

        helper.addOnClickListener(R.id.item_order_linearLayout_img);


        helper.setGone(R.id.item_hide_LinearLayout,item.isShow());

    }
}

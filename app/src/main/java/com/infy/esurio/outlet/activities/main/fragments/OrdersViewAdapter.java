package com.infy.esurio.outlet.activities.main.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.infy.esurio.R;
import com.infy.esurio.outlet.activities.main.MainActivity;
import com.infy.esurio.outlet.app.This;
import com.infy.esurio.outlet.app.services.OrdersService;
import com.infy.esurio.middleware.DTO.OrdersDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OrdersViewAdapter extends RecyclerView.Adapter<OrdersViewAdapter.ViewHolder> {

    private static final String TAG = "FoodcourtsViewAdapter";

    private final ObservableList<OrdersDTO> map;
    private final OrdersListFragment.OnOutletsItemClickedListener listener;

    public OrdersViewAdapter(ObservableList<OrdersDTO> items, OrdersListFragment.OnOutletsItemClickedListener listener) {
        Log.d(TAG, "OrdersViewAdapter");
        this.map = items;
        this.map.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<OrdersDTO>>() {
            @Override
            public void onChanged(ObservableList<OrdersDTO> sender) {
                Log.d(TAG, "onChanged");
                OrdersViewAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<OrdersDTO> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<OrdersDTO> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeMoved(ObservableList<OrdersDTO> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<OrdersDTO> sender, int positionStart, int itemCount) {

            }
        });

        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orders_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = map.get(position);
//        OrdersService.putNumber(map.get(position), holder.tv_orders_number);


        ExpandableListView expandableListView;
        ExpandableListAdapter expandableListAdapter;
        final List<String> expandableListTitle;
        final HashMap<String, List<String>> expandableListDetail;

        expandableListView = holder.expandableListView;
        System.out.println("NULL " + expandableListView);
        expandableListDetail = This.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(This.CONTEXT.self(), expandableListTitle, expandableListDetail);
        System.out.println("NULL " + expandableListAdapter);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(This.CONTEXT.self(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(This.CONTEXT.self(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        This.CONTEXT.self(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView tv_orders_number;
        public OrdersDTO item;

        ExpandableListView expandableListView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            tv_orders_number = view.findViewById(R.id.tv_orders_number);
            expandableListView = view.findViewById(R.id.expandableListView);
        }
    }
}

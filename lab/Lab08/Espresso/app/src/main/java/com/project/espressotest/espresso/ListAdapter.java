package com.project.espressotest.espresso;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder>{

    private List<UserInfo> data;

    public ListAdapter() { this.data = new ArrayList<>();}

    public void setData(List<UserInfo> data){
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_info, null, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        UserInfo userInfo = data.get(position);
        holder.textName.setText(userInfo.getName());
        holder.textAge.setText(userInfo.getAge());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.textName)
        public TextView textName;

        @BindView(R.id.textAge)
        public TextView textAge;

        public Holder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

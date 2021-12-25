package com.example.kpp_lr6.dbhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kpp_lr6.R;
import com.example.kpp_lr6.SearchActivity;

import java.util.List;

public class WheatAdapter extends RecyclerView.Adapter<WheatAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Wheat> wheats;

    private final OnWheatClickListener onClickListener;

    public interface OnWheatClickListener{
        void onWheatClick(Wheat wheat, int position);
    }

    public WheatAdapter(Context context, List<Wheat> wheats, OnWheatClickListener onClickListener) {
        this.wheats = wheats;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public WheatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WheatAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Wheat wheat = wheats.get(position);
        holder.nameView.setText(wheat.getName());
        holder.seasonView.setText(wheat.getSeason());
        holder.periodView.setText(wheat.getPeriod());
        holder.productivityView.setText(wheat.getProductivity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onWheatClick(wheat, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wheats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nameView, seasonView, periodView, productivityView;

        ViewHolder(View view){
            super(view);

            nameView = view.findViewById(R.id.itm_name);
            seasonView = view.findViewById(R.id.itm_season);
            periodView = view.findViewById(R.id.itm_period);
            productivityView = view.findViewById(R.id.itm_productivity);
        }
    }
}

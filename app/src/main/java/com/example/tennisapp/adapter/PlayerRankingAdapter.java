package com.example.tennisapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisapp.PlayerDetailActivity;
import com.example.tennisapp.R;
import com.example.tennisapp.model.PlayerRanking;

import java.util.List;

public class PlayerRankingAdapter extends RecyclerView.Adapter<PlayerRankingAdapter.ViewHolder> {

    private List<PlayerRanking> rankingList;
    private Context context;

    public PlayerRankingAdapter(Context context, List<PlayerRanking> rankingList) {
        this.context = context;
        this.rankingList = rankingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayerRanking item = rankingList.get(position);

        holder.tvRank.setText(String.valueOf(item.getPosition()));
        holder.tvPlayerName.setText(item.getPlayer().getName());
        holder.tvCountry.setText(item.getPlayer().getCountry().getName());
        holder.tvPoints.setText(item.getPoint() + " pts");

        // Klik item → buka detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlayerDetailActivity.class);
            intent.putExtra(PlayerDetailActivity.EXTRA_NAME,
                    item.getPlayer().getName());
            intent.putExtra(PlayerDetailActivity.EXTRA_COUNTRY,
                    item.getPlayer().getCountry().getName());
            intent.putExtra(PlayerDetailActivity.EXTRA_RANK,
                    item.getPosition());
            intent.putExtra(PlayerDetailActivity.EXTRA_POINTS,
                    item.getPoint());
            intent.putExtra(PlayerDetailActivity.EXTRA_BIRTHDAY,
                    item.getPlayer().getBirthday());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvPlayerName, tvCountry, tvPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvPoints = itemView.findViewById(R.id.tvPoints);
        }
    }
}
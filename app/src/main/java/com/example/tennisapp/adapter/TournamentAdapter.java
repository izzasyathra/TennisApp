package com.example.tennisapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisapp.R;
import com.example.tennisapp.model.Tournament;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {

    private List<Tournament> tournamentList;

    public TournamentAdapter(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tournament, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tournament item = tournamentList.get(position);

        holder.tvTournamentName.setText(item.getName());
        holder.tvCourt.setText(item.getCourt() != null ?
                item.getCourt().getName() : "Unknown");
        holder.tvTier.setText(item.getTier());
        holder.tvCountry.setText(item.getCountry() != null ?
                item.getCountry().getName() : "Unknown");

        // Format tanggal
        String date = item.getDate();
        if (date != null && date.length() >= 10) {
            holder.tvDate.setText(date.substring(0, 10));
        } else {
            holder.tvDate.setText("-");
        }
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTournamentName, tvDate, tvCourt, tvTier, tvCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTournamentName = itemView.findViewById(R.id.tvTournamentName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCourt = itemView.findViewById(R.id.tvCourt);
            tvTier = itemView.findViewById(R.id.tvTier);
            tvCountry = itemView.findViewById(R.id.tvCountry);
        }
    }
}
package com.example.tennisapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisapp.R;

import java.util.List;

public class MatchHistoryAdapter extends RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder> {

    private List<String[]> matchList;

    public MatchHistoryAdapter(List<String[]> matchList) {
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] match = matchList.get(position);

        // match[1] = player1, match[2] = player2
        // match[3] = p1_sets, match[4] = p2_sets
        // match[5] = format, match[6] = winner
        // match[7] = date, match[8] = set_scores

        holder.tvMatchWinner.setText("🏆 " + match[6] + " MENANG!");
        holder.tvMatchScore.setText(match[1] + "  " +
                match[3] + " - " + match[4] + "  " + match[2]);

        // Tampilkan detail per set
        String setScoresStr = match.length > 8 ? match[8] : "";
        if (setScoresStr != null && !setScoresStr.isEmpty()) {
            String[] sets = setScoresStr.split("\\|");
            StringBuilder sbSets = new StringBuilder();
            for (int i = 0; i < sets.length; i++) {
                if (i > 0) sbSets.append("  |  ");
                sbSets.append("Set ").append(i + 1)
                        .append(": ").append(sets[i]);
            }
            holder.tvMatchSetDetail.setText(sbSets.toString());
            holder.tvMatchSetDetail.setVisibility(View.VISIBLE);
        } else {
            holder.tvMatchSetDetail.setVisibility(View.GONE);
        }

        // Format tanggal
        String date = match[7];
        if (date != null && date.length() > 16) {
            date = date.substring(0, 16);
        }
        holder.tvMatchInfo.setText(match[5] + " • " + date);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatchWinner, tvMatchScore,
                tvMatchSetDetail, tvMatchInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatchWinner = itemView.findViewById(R.id.tvMatchWinner);
            tvMatchScore = itemView.findViewById(R.id.tvMatchScore);
            tvMatchSetDetail = itemView.findViewById(R.id.tvMatchSetDetail);
            tvMatchInfo = itemView.findViewById(R.id.tvMatchInfo);
        }
    }
}
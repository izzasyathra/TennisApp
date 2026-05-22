package com.example.tennisapp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisapp.R;
import com.example.tennisapp.adapter.MatchHistoryAdapter;
import com.example.tennisapp.database.DatabaseHelper;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private Button btnClearHistory;
    private MatchHistoryAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<String[]> matchList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,
                container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btnClearHistory = view.findViewById(R.id.btnClearHistory);
        dbHelper = new DatabaseHelper(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnClearHistory.setOnClickListener(v -> showClearDialog());

        loadHistory();
        return view;
    }

    private void loadHistory() {
        matchList = dbHelper.getMatchHistory();
        adapter = new MatchHistoryAdapter(matchList);
        recyclerView.setAdapter(adapter);

        if (matchList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showClearDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Hapus Semua History")
                .setMessage("Yakin mau hapus semua riwayat match?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    dbHelper.deleteAllMatches();
                    loadHistory();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
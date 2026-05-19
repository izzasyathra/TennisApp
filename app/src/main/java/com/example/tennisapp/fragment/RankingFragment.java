package com.example.tennisapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisapp.R;
import com.example.tennisapp.adapter.PlayerRankingAdapter;
import com.example.tennisapp.api.ApiClient;
import com.example.tennisapp.api.ApiService;
import com.example.tennisapp.database.DatabaseHelper;
import com.example.tennisapp.model.PlayerRanking;
import com.example.tennisapp.model.RankingResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout layoutError;
    private TextView tvError;
    private Button btnRefresh;
    private PlayerRankingAdapter adapter;
    private List<PlayerRanking> rankingList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewRanking);
        progressBar = view.findViewById(R.id.progressBar);
        layoutError = view.findViewById(R.id.layoutError);
        tvError = view.findViewById(R.id.tvError);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        dbHelper = new DatabaseHelper(getContext());

        adapter = new PlayerRankingAdapter(getContext(), rankingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnRefresh.setOnClickListener(v -> loadRankings());
        loadRankings();

        return view;
    }

    private void loadRankings() {
        progressBar.setVisibility(View.VISIBLE);
        layoutError.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<RankingResponse> call = apiService.getPlayerRankings(
                ApiService.API_KEY,
                ApiService.API_HOST
        );

        call.enqueue(new Callback<RankingResponse>() {
            @Override
            public void onResponse(@NonNull Call<RankingResponse> call,
                                   @NonNull Response<RankingResponse> response) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getData() != null
                        && !response.body().getData().isEmpty()) {

                    rankingList.clear();
                    rankingList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);

                    // Simpan ke SQLite
                    dbHelper.saveRankings(rankingList);

                } else {
                    loadFromDatabase();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RankingResponse> call,
                                  @NonNull Throwable t) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                loadFromDatabase();
            }
        });
    }

    private void loadFromDatabase() {
        List<PlayerRanking> localData = dbHelper.getRankings();
        if (!localData.isEmpty()) {
            rankingList.clear();
            rankingList.addAll(localData);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            tvError.setText("Menampilkan data offline");
            layoutError.setVisibility(View.VISIBLE);
        } else {
            showError("Tidak ada koneksi internet");
        }
    }

    private void showError(String message) {
        layoutError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvError.setText(message);
    }
}
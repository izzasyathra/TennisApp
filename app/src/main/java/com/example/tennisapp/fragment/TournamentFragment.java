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
import com.example.tennisapp.adapter.TournamentAdapter;
import com.example.tennisapp.api.ApiClient;
import com.example.tennisapp.api.ApiService;
import com.example.tennisapp.database.DatabaseHelper;
import com.example.tennisapp.model.Tournament;
import com.example.tennisapp.model.TournamentResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout layoutError;
    private TextView tvError;
    private Button btnRefresh;
    private TournamentAdapter adapter;
    private List<Tournament> tournamentList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tournament, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTournament);
        progressBar = view.findViewById(R.id.progressBar);
        layoutError = view.findViewById(R.id.layoutError);
        tvError = view.findViewById(R.id.tvError);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        dbHelper = new DatabaseHelper(getContext());

        adapter = new TournamentAdapter(tournamentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnRefresh.setOnClickListener(v -> loadTournaments());
        loadTournaments();

        return view;
    }

    private void loadTournaments() {
        progressBar.setVisibility(View.VISIBLE);
        layoutError.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        int currentYear = java.util.Calendar.getInstance()
                .get(java.util.Calendar.YEAR);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<TournamentResponse> call = apiService.getTournamentCalendar(
                ApiService.API_KEY,
                ApiService.API_HOST,
                currentYear
        );

        call.enqueue(new Callback<TournamentResponse>() {
            @Override
            public void onResponse(@NonNull Call<TournamentResponse> call,
                                   @NonNull Response<TournamentResponse> response) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getData() != null
                        && !response.body().getData().isEmpty()) {

                    tournamentList.clear();
                    tournamentList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);

                    // Simpan ke SQLite
                    dbHelper.saveTournaments(tournamentList);

                } else {
                    loadFromDatabase();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TournamentResponse> call,
                                  @NonNull Throwable t) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                loadFromDatabase();
            }
        });
    }

    private void loadFromDatabase() {
        List<Tournament> localData = dbHelper.getTournaments();
        if (!localData.isEmpty()) {
            tournamentList.clear();
            tournamentList.addAll(localData);
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
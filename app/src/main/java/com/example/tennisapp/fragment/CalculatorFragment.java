package com.example.tennisapp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tennisapp.R;
import com.example.tennisapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CalculatorFragment extends Fragment {

    private TextView tvP1Header, tvP2Header;
    private TextView tvP1Sets, tvP2Sets;
    private TextView tvP1Games, tvP2Games;
    private TextView tvP1Points, tvP2Points;
    private TextView tvStatus;
    private Button btnScoreP1, btnScoreP2, btnReset;
    private EditText etPlayer1Name, etPlayer2Name;
    private RadioGroup radioGroupFormat;

    private int p1Sets = 0, p2Sets = 0;
    private int p1Games = 0, p2Games = 0;
    private int p1Points = 0, p2Points = 0;
    private boolean matchOver = false;
    private int setsToWin = 2;

    // Track skor games per set
    private List<String> setScores = new ArrayList<>();

    private final int[] POINT_VALUES = {0, 15, 30, 40};
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator,
                container, false);

        dbHelper = new DatabaseHelper(getContext());

        tvP1Header = view.findViewById(R.id.tvP1Header);
        tvP2Header = view.findViewById(R.id.tvP2Header);
        tvP1Sets = view.findViewById(R.id.tvP1Sets);
        tvP2Sets = view.findViewById(R.id.tvP2Sets);
        tvP1Games = view.findViewById(R.id.tvP1Games);
        tvP2Games = view.findViewById(R.id.tvP2Games);
        tvP1Points = view.findViewById(R.id.tvP1Points);
        tvP2Points = view.findViewById(R.id.tvP2Points);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnScoreP1 = view.findViewById(R.id.btnScoreP1);
        btnScoreP2 = view.findViewById(R.id.btnScoreP2);
        btnReset = view.findViewById(R.id.btnReset);
        etPlayer1Name = view.findViewById(R.id.etPlayer1Name);
        etPlayer2Name = view.findViewById(R.id.etPlayer2Name);
        radioGroupFormat = view.findViewById(R.id.radioGroupFormat);

        radioGroupFormat.setOnCheckedChangeListener((group, checkedId) -> {
            setsToWin = (checkedId == R.id.radioBo3) ? 2 : 3;
            resetMatch();
        });

        btnScoreP1.setOnClickListener(v -> {
            if (!matchOver) addPoint(1);
        });
        btnScoreP2.setOnClickListener(v -> {
            if (!matchOver) addPoint(2);
        });
        btnReset.setOnClickListener(v -> resetMatch());

        updateUI();
        return view;
    }

    private void addPoint(int player) {
        if (player == 1) p1Points++;
        else p2Points++;
        checkGame();
        updateUI();
    }

    private void checkGame() {
        boolean deuce = p1Points >= 3 && p2Points >= 3;
        if (deuce) {
            if (p1Points - p2Points >= 2) winGame(1);
            else if (p2Points - p1Points >= 2) winGame(2);
        } else {
            if (p1Points >= 4) winGame(1);
            else if (p2Points >= 4) winGame(2);
        }
    }

    private void winGame(int player) {
        if (player == 1) p1Games++;
        else p2Games++;
        p1Points = 0;
        p2Points = 0;
        checkSet();
    }

    private void checkSet() {
        boolean p1WinSet = false;
        boolean p2WinSet = false;

        if (p1Games >= 6 && p1Games - p2Games >= 2) p1WinSet = true;
        else if (p2Games >= 6 && p2Games - p1Games >= 2) p2WinSet = true;
        else if (p1Games >= 7) p1WinSet = true;
        else if (p2Games >= 7) p2WinSet = true;

        if (p1WinSet) {
            // Simpan skor games set ini sebelum direset
            setScores.add(p1Games + "-" + p2Games);
            p1Sets++;
            p1Games = 0;
            p2Games = 0;
            checkMatch();
        } else if (p2WinSet) {
            // Simpan skor games set ini sebelum direset
            setScores.add(p1Games + "-" + p2Games);
            p2Sets++;
            p1Games = 0;
            p2Games = 0;
            checkMatch();
        }
    }

    private void checkMatch() {
        if (p1Sets >= setsToWin) {
            matchOver = true;
            String name = etPlayer1Name.getText().toString().trim();
            String winner = name.isEmpty() ? "Player 1" : name;
            tvStatus.setText("🏆 " + winner + " MENANG!");
            tvStatus.setTextColor(getResources().getColor(
                    android.R.color.holo_blue_dark, null));
            showSaveDialog(winner);
        } else if (p2Sets >= setsToWin) {
            matchOver = true;
            String name = etPlayer2Name.getText().toString().trim();
            String winner = name.isEmpty() ? "Player 2" : name;
            tvStatus.setText("🏆 " + winner + " MENANG!");
            tvStatus.setTextColor(getResources().getColor(
                    android.R.color.holo_red_dark, null));
            showSaveDialog(winner);
        }
    }

    private void showSaveDialog(String winner) {
        String p1 = etPlayer1Name.getText().toString().trim();
        String p2 = etPlayer2Name.getText().toString().trim();
        if (p1.isEmpty()) p1 = "Player 1";
        if (p2.isEmpty()) p2 = "Player 2";
        String format = setsToWin == 2 ? "Best of 3" : "Best of 5";

        // Buat string set scores: "6-4|6-3"
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < setScores.size(); i++) {
            if (i > 0) sb.append("|");
            sb.append(setScores.get(i));
        }
        String setScoresStr = sb.toString();

        String finalP1 = p1;
        String finalP2 = p2;

        new AlertDialog.Builder(getContext())
                .setTitle("🏆 Match Selesai!")
                .setMessage(winner + " menang!\n" +
                        finalP1 + " " + p1Sets + " - " +
                        p2Sets + " " + finalP2 +
                        "\nSimpan hasil match ini?")
                .setPositiveButton("Simpan", (dialog, which) -> {
                    dbHelper.saveMatch(finalP1, finalP2,
                            p1Sets, p2Sets, format,
                            winner, setScoresStr);
                    Toast.makeText(getContext(),
                            "✅ Hasil match tersimpan!",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void resetMatch() {
        p1Sets = 0; p2Sets = 0;
        p1Games = 0; p2Games = 0;
        p1Points = 0; p2Points = 0;
        matchOver = false;
        setScores.clear();
        updateUI();
        tvStatus.setText("Match belum dimulai");
        tvStatus.setTextColor(getResources().getColor(
                android.R.color.holo_blue_dark, null));
    }

    private void updateUI() {
        String p1Name = etPlayer1Name != null ?
                etPlayer1Name.getText().toString().trim() : "";
        String p2Name = etPlayer2Name != null ?
                etPlayer2Name.getText().toString().trim() : "";

        tvP1Header.setText(p1Name.isEmpty() ? "P1" : p1Name);
        tvP2Header.setText(p2Name.isEmpty() ? "P2" : p2Name);
        tvP1Sets.setText(String.valueOf(p1Sets));
        tvP2Sets.setText(String.valueOf(p2Sets));
        tvP1Games.setText(String.valueOf(p1Games));
        tvP2Games.setText(String.valueOf(p2Games));

        if (p1Points >= 3 && p2Points >= 3) {
            if (p1Points == p2Points) {
                tvP1Points.setText("Deuce");
                tvP2Points.setText("Deuce");
            } else if (p1Points > p2Points) {
                tvP1Points.setText("Ad");
                tvP2Points.setText("-");
            } else {
                tvP1Points.setText("-");
                tvP2Points.setText("Ad");
            }
        } else {
            tvP1Points.setText(String.valueOf(
                    p1Points < 4 ? POINT_VALUES[p1Points] : 40));
            tvP2Points.setText(String.valueOf(
                    p2Points < 4 ? POINT_VALUES[p2Points] : 40));
        }

        if (!matchOver) {
            tvStatus.setText("Set " + (p1Sets + p2Sets + 1)
                    + " • Game sedang berjalan");
        }

        btnScoreP1.setText("Point " +
                (p1Name.isEmpty() ? "P1" : p1Name));
        btnScoreP2.setText("Point " +
                (p2Name.isEmpty() ? "P2" : p2Name));
    }
}
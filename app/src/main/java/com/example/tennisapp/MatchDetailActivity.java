package com.example.tennisapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MatchDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MATCH = "extra_match";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Match Detail");
            }
        }

        // Ambil data dari Intent
        String[] match = (String[]) getIntent()
                .getSerializableExtra(EXTRA_MATCH);
        if (match == null) return;

        // match[0] = id
        // match[1] = player1
        // match[2] = player2
        // match[3] = p1_sets
        // match[4] = p2_sets
        // match[5] = format
        // match[6] = winner
        // match[7] = date
        // match[8] = set_scores

        TextView tvDetailWinner = findViewById(R.id.tvDetailWinner);
        TextView tvDetailTotalScore = findViewById(R.id.tvDetailTotalScore);
        TextView tvDetailP1Name = findViewById(R.id.tvDetailP1Name);
        TextView tvDetailP2Name = findViewById(R.id.tvDetailP2Name);
        TextView tvDetailFormat = findViewById(R.id.tvDetailFormat);
        TextView tvDetailDate = findViewById(R.id.tvDetailDate);
        LinearLayout layoutSetScores = findViewById(R.id.layoutSetScores);

        // Set data
        tvDetailWinner.setText("🏆 " + match[6] + " MENANG!");
        tvDetailTotalScore.setText(match[1] + "  " +
                match[3] + " - " + match[4] + "  " + match[2]);
        tvDetailP1Name.setText(match[1]);
        tvDetailP2Name.setText(match[2]);
        tvDetailFormat.setText(match[5]);

        // Format tanggal
        String date = match[7];
        if (date != null && date.length() > 24) {
            date = date.substring(0, 24);
        }
        tvDetailDate.setText(date);

        // Tampilkan set scores
        String setScoresStr = match[8];
        if (setScoresStr != null && !setScoresStr.isEmpty()) {
            String[] sets = setScoresStr.split("\\|");
            for (int i = 0; i < sets.length; i++) {
                String[] scores = sets[i].split("-");
                if (scores.length == 2) {
                    // Buat row untuk setiap set
                    View rowView = LayoutInflater.from(this)
                            .inflate(android.R.layout.simple_list_item_1,
                                    layoutSetScores, false);

                    LinearLayout row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setPadding(0, 8, 0, 8);

                    // Set number
                    TextView tvSetNum = new TextView(this);
                    tvSetNum.setLayoutParams(new LinearLayout
                            .LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    tvSetNum.setText("Set " + (i + 1));
                    tvSetNum.setTextSize(14);

                    // P1 score
                    TextView tvP1Score = new TextView(this);
                    tvP1Score.setLayoutParams(new LinearLayout
                            .LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    tvP1Score.setText(scores[0]);
                    tvP1Score.setTextSize(16);
                    tvP1Score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tvP1Score.setTypeface(null,
                            android.graphics.Typeface.BOLD);

                    // P2 score
                    TextView tvP2Score = new TextView(this);
                    tvP2Score.setLayoutParams(new LinearLayout
                            .LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    tvP2Score.setText(scores[1]);
                    tvP2Score.setTextSize(16);
                    tvP2Score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tvP2Score.setTypeface(null,
                            android.graphics.Typeface.BOLD);

                    // Warnai pemenang set
                    int p1Score = Integer.parseInt(scores[0].trim());
                    int p2Score = Integer.parseInt(scores[1].trim());
                    if (p1Score > p2Score) {
                        tvP1Score.setTextColor(
                                getResources().getColor(
                                        android.R.color.holo_blue_dark,
                                        null));
                    } else {
                        tvP2Score.setTextColor(
                                getResources().getColor(
                                        android.R.color.holo_blue_dark,
                                        null));
                    }

                    row.addView(tvSetNum);
                    row.addView(tvP1Score);
                    row.addView(tvP2Score);
                    layoutSetScores.addView(row);
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
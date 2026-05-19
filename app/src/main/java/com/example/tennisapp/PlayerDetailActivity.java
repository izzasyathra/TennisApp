package com.example.tennisapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PlayerDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_COUNTRY = "extra_country";
    public static final String EXTRA_RANK = "extra_rank";
    public static final String EXTRA_POINTS = "extra_points";
    public static final String EXTRA_BIRTHDAY = "extra_birthday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Player Detail");
            }
        }

        // Ambil data dari Intent
        String name = getIntent().getStringExtra(EXTRA_NAME);
        String country = getIntent().getStringExtra(EXTRA_COUNTRY);
        int rank = getIntent().getIntExtra(EXTRA_RANK, 0);
        int points = getIntent().getIntExtra(EXTRA_POINTS, 0);
        String birthday = getIntent().getStringExtra(EXTRA_BIRTHDAY);

        // Set ke View
        TextView tvRankBadge = findViewById(R.id.tvRankBadge);
        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailCountry = findViewById(R.id.tvDetailCountry);
        TextView tvDetailRank = findViewById(R.id.tvDetailRank);
        TextView tvDetailPoints = findViewById(R.id.tvDetailPoints);
        TextView tvDetailBirthday = findViewById(R.id.tvDetailBirthday);
        TextView tvDetailCountryFull = findViewById(R.id.tvDetailCountryFull);

        tvRankBadge.setText("#" + rank);
        tvDetailName.setText(name);
        tvDetailCountry.setText(country);
        tvDetailRank.setText("#" + rank);
        tvDetailPoints.setText(points + " pts");
        tvDetailCountryFull.setText(country);

        // Format birthday
        if (birthday != null && birthday.length() >= 10) {
            tvDetailBirthday.setText(birthday.substring(0, 10));
        } else {
            tvDetailBirthday.setText("-");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
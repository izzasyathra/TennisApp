package com.example.tennisapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_RANK = "extra_rank";
    public static final String EXTRA_COUNTRY = "extra_country";
    public static final String EXTRA_POINTS = "extra_points";
    public static final String EXTRA_BIRTHDAY = "extra_birthday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        TextView btnBack = findViewById(R.id.btnBack);
        TextView tvName = findViewById(R.id.tvPlayerName);
        TextView tvRank = findViewById(R.id.tvPlayerRank);
        TextView tvCountry = findViewById(R.id.tvPlayerCountry);
        TextView tvPoints = findViewById(R.id.tvPlayerPoints);
        TextView tvBirthday = findViewById(R.id.tvPlayerBirthday);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        int rank = getIntent().getIntExtra(EXTRA_RANK, 0);
        String country = getIntent().getStringExtra(EXTRA_COUNTRY);
        int points = getIntent().getIntExtra(EXTRA_POINTS, 0);
        String birthday = getIntent().getStringExtra(EXTRA_BIRTHDAY);

        tvName.setText(name);
        tvRank.setText(String.valueOf(rank));
        tvCountry.setText(country);
        tvPoints.setText(points + " pts");
        tvBirthday.setText(birthday != null ? birthday : "-");

        btnBack.setOnClickListener(v -> finish());
    }
}
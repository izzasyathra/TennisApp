package com.example.tennisapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tennisapp.model.PlayerRanking;
import com.example.tennisapp.model.Tournament;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tennis_db";
    private static final int DB_VERSION = 4;

    private static final String TABLE_RANKING = "ranking";
    private static final String COL_ID = "id";
    private static final String COL_POSITION = "position";
    private static final String COL_POINT = "point";
    private static final String COL_PLAYER_ID = "player_id";
    private static final String COL_PLAYER_NAME = "player_name";
    private static final String COL_COUNTRY = "country";
    private static final String COL_BIRTHDAY = "birthday";

    private static final String TABLE_TOURNAMENT = "tournament";
    private static final String COL_TOUR_ID = "id";
    private static final String COL_TOUR_NAME = "name";
    private static final String COL_TOUR_DATE = "date";
    private static final String COL_TOUR_TIER = "tier";
    private static final String COL_TOUR_COURT = "court";
    private static final String COL_TOUR_COUNTRY = "country";

    private static final String TABLE_MATCH = "match_history";
    private static final String COL_MATCH_ID = "id";
    private static final String COL_MATCH_P1 = "player1";
    private static final String COL_MATCH_P2 = "player2";
    private static final String COL_MATCH_P1_SETS = "p1_sets";
    private static final String COL_MATCH_P2_SETS = "p2_sets";
    private static final String COL_MATCH_FORMAT = "format";
    private static final String COL_MATCH_WINNER = "winner";
    private static final String COL_MATCH_DATE = "date";
    private static final String COL_MATCH_SET_SCORES = "set_scores";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRanking = "CREATE TABLE " + TABLE_RANKING + " ("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_POSITION + " INTEGER, "
                + COL_POINT + " INTEGER, "
                + COL_PLAYER_ID + " INTEGER, "
                + COL_PLAYER_NAME + " TEXT, "
                + COL_COUNTRY + " TEXT, "
                + COL_BIRTHDAY + " TEXT)";

        String createTournament = "CREATE TABLE " + TABLE_TOURNAMENT + " ("
                + COL_TOUR_ID + " INTEGER PRIMARY KEY, "
                + COL_TOUR_NAME + " TEXT, "
                + COL_TOUR_DATE + " TEXT, "
                + COL_TOUR_TIER + " TEXT, "
                + COL_TOUR_COURT + " TEXT, "
                + COL_TOUR_COUNTRY + " TEXT)";

        String createMatch = "CREATE TABLE " + TABLE_MATCH + " ("
                + COL_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MATCH_P1 + " TEXT, "
                + COL_MATCH_P2 + " TEXT, "
                + COL_MATCH_P1_SETS + " INTEGER, "
                + COL_MATCH_P2_SETS + " INTEGER, "
                + COL_MATCH_FORMAT + " TEXT, "
                + COL_MATCH_WINNER + " TEXT, "
                + COL_MATCH_DATE + " TEXT, "
                + COL_MATCH_SET_SCORES + " TEXT)";

        db.execSQL(createRanking);
        db.execSQL(createTournament);
        db.execSQL(createMatch);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURNAMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
        onCreate(db);
    }

    // ===== RANKING =====

    public void saveRankings(List<PlayerRanking> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RANKING, null, null);
        for (PlayerRanking item : list) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ID, item.getId());
            cv.put(COL_POSITION, item.getPosition());
            cv.put(COL_POINT, item.getPoint());
            cv.put(COL_PLAYER_ID, item.getPlayer().getId());
            cv.put(COL_PLAYER_NAME, item.getPlayer().getName());
            cv.put(COL_COUNTRY, item.getPlayer().getCountry().getName());
            cv.put(COL_BIRTHDAY, item.getPlayer().getBirthday());
            db.insert(TABLE_RANKING, null, cv);
        }
        db.close();
    }

    public List<PlayerRanking> getRankings() {
        List<PlayerRanking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RANKING
                + " ORDER BY " + COL_POSITION, null);

        if (cursor.moveToFirst()) {
            do {
                PlayerRanking item = new PlayerRanking();
                item.setId(cursor.getLong(
                        cursor.getColumnIndexOrThrow(COL_ID)));
                item.setPosition(cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_POSITION)));
                item.setPoint(cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_POINT)));

                PlayerRanking.PlayerDetail player =
                        new PlayerRanking.PlayerDetail();
                player.setId(cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_PLAYER_ID)));
                player.setName(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_PLAYER_NAME)));
                player.setBirthday(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_BIRTHDAY)));

                PlayerRanking.PlayerDetail.Country country =
                        new PlayerRanking.PlayerDetail.Country();
                country.setName(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_COUNTRY)));
                player.setCountry(country);
                item.setPlayer(player);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // ===== TOURNAMENT =====

    public void saveTournaments(List<Tournament> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOURNAMENT, null, null);
        for (Tournament item : list) {
            ContentValues cv = new ContentValues();
            cv.put(COL_TOUR_ID, item.getId());
            cv.put(COL_TOUR_NAME, item.getName());
            cv.put(COL_TOUR_DATE, item.getDate());
            cv.put(COL_TOUR_TIER, item.getTier());
            cv.put(COL_TOUR_COURT, item.getCourt() != null ?
                    item.getCourt().getName() : "");
            cv.put(COL_TOUR_COUNTRY, item.getCountry() != null ?
                    item.getCountry().getName() : "");
            db.insert(TABLE_TOURNAMENT, null, cv);
        }
        db.close();
    }

    public List<Tournament> getTournaments() {
        List<Tournament> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_TOURNAMENT, null);

        if (cursor.moveToFirst()) {
            do {
                Tournament item = new Tournament();
                item.setId(cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_TOUR_ID)));
                item.setName(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_TOUR_NAME)));
                item.setDate(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_TOUR_DATE)));
                item.setTier(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_TOUR_TIER)));

                Tournament.Court court = new Tournament.Court();
                court.setName(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_TOUR_COURT)));
                item.setCourt(court);

                Tournament.Country country = new Tournament.Country();
                country.setName(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_TOUR_COUNTRY)));
                item.setCountry(country);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean isRankingEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RANKING, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count == 0;
    }

    public boolean isTournamentEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_TOURNAMENT, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count == 0;
    }

    // ===== MATCH HISTORY =====

    public void saveMatch(String p1, String p2, int p1Sets,
                          int p2Sets, String format,
                          String winner, String setScores) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MATCH_P1, p1);
        cv.put(COL_MATCH_P2, p2);
        cv.put(COL_MATCH_P1_SETS, p1Sets);
        cv.put(COL_MATCH_P2_SETS, p2Sets);
        cv.put(COL_MATCH_FORMAT, format);
        cv.put(COL_MATCH_WINNER, winner);
        cv.put(COL_MATCH_DATE, new java.util.Date().toString());
        cv.put(COL_MATCH_SET_SCORES, setScores);
        db.insert(TABLE_MATCH, null, cv);
        db.close();
    }

    public List<String[]> getMatchHistory() {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_MATCH +
                        " ORDER BY " + COL_MATCH_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String[] match = new String[9];
                match[0] = String.valueOf(cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_MATCH_ID)));
                match[1] = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_MATCH_P1));
                match[2] = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_MATCH_P2));
                match[3] = String.valueOf(cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_MATCH_P1_SETS)));
                match[4] = String.valueOf(cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_MATCH_P2_SETS)));
                match[5] = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_MATCH_FORMAT));
                match[6] = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_MATCH_WINNER));
                match[7] = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_MATCH_DATE));
                match[8] = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                COL_MATCH_SET_SCORES));
                list.add(match);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteAllMatches() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MATCH, null, null);
        db.close();
    }
}
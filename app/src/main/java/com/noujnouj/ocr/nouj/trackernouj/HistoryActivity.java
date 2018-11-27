package com.noujnouj.ocr.nouj.trackernouj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    public RecyclerView mRecyclerView;
    public UserMoodsAdapter mUserMoodsAdapter;
    public ArrayList<Mood> mHistoryMoods;
    public Button mCommentButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = findViewById(R.id.history_activity_recycler_view);
        mCommentButton = findViewById(R.id.buttonViewMessage);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Prefs prefs = Prefs.getInstance(this);
        mHistoryMoods = prefs.getUserMoods();

        mUserMoodsAdapter = new UserMoodsAdapter(this, mHistoryMoods);
        mRecyclerView.setAdapter(mUserMoodsAdapter);
    }
}

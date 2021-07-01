package com.example.thankage.fragments.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.thankage.R;

public class SettingActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting4);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("문의하기");

        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int curId = item.getItemId();
        if (curId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
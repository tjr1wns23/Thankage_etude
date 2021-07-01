package com.example.thankage.fragments.setting;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.CompoundButtonCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.thankage.R;
import com.example.thankage.editor.EditorActivity;

public class SettingActivity1 extends AppCompatActivity {

    SwitchCompat switchCompat1, switchCompat2, switchCompat3;
    TextView tv_state1, tv_state2, tv_state3;
    String string_state1, string_state2, string_state3;
    Boolean bool_state1, bool_state2, bool_state3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("언어 및 알림 설정");

        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게

        SharedPreferences pref = getSharedPreferences("pref", SettingActivity1.MODE_PRIVATE);

        bool_state1 = pref.getBoolean("bool_state1", true);
        bool_state2 = pref.getBoolean("bool_state2", true);
        bool_state3 = pref.getBoolean("bool_state3", true);

        switchCompat1 = findViewById(R.id.s1_switch1);
        switchCompat2 = findViewById(R.id.s1_switch2);
        switchCompat3 = findViewById(R.id.s1_switch3);
        tv_state1 = findViewById(R.id.s1_tv_state1);
        tv_state2 = findViewById(R.id.s1_tv_state2);
        tv_state3 = findViewById(R.id.s1_tv_state3);

        s1SetText(bool_state1,tv_state1);
        s1SetText(bool_state2,tv_state2);
        s1SetText(bool_state3,tv_state3);
        
        switchCompat1.setChecked(bool_state1);
        switchCompat1.setOnCheckedChangeListener((buttonView, isChecked) -> s1SetText(isChecked,tv_state1));

        switchCompat2.setChecked(bool_state2);
        switchCompat2.setOnCheckedChangeListener((buttonView, isChecked) -> s1SetText(isChecked,tv_state2));

        switchCompat3.setChecked(bool_state3);
        switchCompat3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            s1SetText(isChecked,tv_state3);
            Intent intent = new Intent(getApplicationContext(), EditorActivity.class);

        });

    }

    private void s1SetText (boolean b, TextView a) {
        if (b) {
            a.setTextColor(getResources().getColor(R.color.teal_200));
            a.setText("켜짐");
        } else {
            a.setTextColor(getResources().getColor(R.color.gray));
            a.setText("꺼짐");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences pref = getSharedPreferences("pref", SettingActivity1.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("bool_state1", switchCompat1.isChecked());
        editor.putBoolean("bool_state2", switchCompat2.isChecked());
        editor.putBoolean("bool_state3", switchCompat3.isChecked());

        editor.apply();
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
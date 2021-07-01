package com.example.thankage.fragments.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thankage.R;
import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.login.LoginActivity;
import com.example.thankage.model.userInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity0 extends AppCompatActivity {

    String id, phone, nickName, pw, joinDate, sex, birth;
    TextView setting0_id, setting0_pw, setting0_phone, setting0_nickName, setting0_joinDate, setting0_sex, setting0_birth;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("프로필");

        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        phone = intent.getStringExtra("phone");
        pw = intent.getStringExtra("pw");
        nickName = intent.getStringExtra("nickName");
        joinDate = intent.getStringExtra("joinDate");
        sex = intent.getStringExtra("sex");
        birth = intent.getStringExtra("birth");

        setting0_id = findViewById(R.id.setting0_id);
        setting0_pw = findViewById(R.id.setting0_pw);
        setting0_phone = findViewById(R.id.setting0_phone);
        setting0_nickName = findViewById(R.id.setting0_nickName);
        setting0_joinDate = findViewById(R.id.setting0_joinDate);
        setting0_sex = findViewById(R.id.setting0_sex);
        setting0_birth = findViewById(R.id.setting0_birth);

        setting0_id.setText(id);
        setting0_phone.setText(phone);
        setting0_pw.setText(pw);
        setting0_nickName.setText(nickName);
        setting0_joinDate.setText(joinDate);
        setting0_sex.setText(sex);
        setting0_birth.setText(birth);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int curId = item.getItemId();
        if (curId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void click_nickName(View view) {
        final LinearLayout linear = (LinearLayout) View.inflate(SettingActivity0.this, R.layout.modify_nickname_alertdialog, null);

        EditText modifyNickName = (EditText) linear.findViewById(R.id.modify_nickName);
        modifyNickName.setText(nickName);

        new AlertDialog.Builder(SettingActivity0.this)
                .setView(linear)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = modifyNickName.getText().toString();

                        // 여기에 디비연동과 나머지들 넣자
                        modify_nickName(id, value);


                        dialog.dismiss();
                    }
                }).show();
    }

    private void modify_nickName(String id, String value) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<userInfo> call = apiInterface.modify_nickName(id, value);

        call.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse(@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    if (success) {
                        nickName = value;
                        setting0_nickName.setText(nickName);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SettingActivity0.this, "예외 상황",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Toast.makeText(SettingActivity0.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }



}
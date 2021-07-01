package com.example.thankage.fragments.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thankage.R;
import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.login.LoginActivity;
import com.example.thankage.model.userInfo;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity5 extends AppCompatActivity {

    TextView secession_id;
    EditText secession_pw;
    Button secession_btn;
    String id, pw, input_pw;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting5);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("회원탈퇴");

        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pw = intent.getStringExtra("pw");

        secession_id = findViewById(R.id.secession_id);
        secession_id.setText(id);
        secession_pw = findViewById(R.id.secession_pw);

        secession_btn = findViewById(R.id.setting5_secession_btn);
        secession_btn.setOnClickListener(v -> {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingActivity5.this);
            alertDialog.setTitle("알림");
            alertDialog.setMessage("\n정말 탈퇴하시겠습니까?");
            alertDialog.setNegativeButton("취소",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.setPositiveButton("확인", (dialog, which) -> {

                input_pw = secession_pw.getText().toString();
                try {
                    input_pw = LoginActivity.SHA256.encrypt(input_pw);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                        if (!input_pw.equals(pw)) {
                            Toast.makeText(SettingActivity5.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            secession(id);
                            Toast.makeText(SettingActivity5.this, "탈퇴가 완료되었습니다.", Toast.LENGTH_LONG).show();
                            Intent logout_intent = new Intent(getApplicationContext(), LoginActivity.class);
                            logout_intent.putExtra("state", 10);
                            startActivity(logout_intent);
                        }
                        dialog.dismiss();
                    }

            );

            alertDialog.show();
        });

    }

    private void secession(String id) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<userInfo> call = apiInterface.secession(id);

        call.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse(@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SettingActivity5.this);
                    if (success) {

                        builder.setTitle("회원탈퇴 안내").setMessage("\n 회원탈퇴가 완료되었습니다.");
                        builder.setPositiveButton("확인", (dialog, which) -> {
                            dialog.dismiss();
                            finish(); // back to main activity
                        });
                        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        // if error, still in this activity
                        builder.setTitle("회원탈퇴 안내").setMessage("\n" + response.body().getMessage());
                        builder.setPositiveButton("확인", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Toast.makeText(SettingActivity5.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
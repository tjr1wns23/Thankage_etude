package com.example.thankage.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.thankage.R;
import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.model.userInfo;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    EditText info_id, info_pw, info_pwCheck, info_nickName, info_phone1, info_phone2, info_phone3, info_birth;

    RadioButton maleButton, femaleButton;
    String sex = "male"; // 성별 기본값 male 설정, 라디오 버튼에도 기본값으로 "남"이 체크되어있다.
    String encryption;

    Button join_ok;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        info_id = (EditText) findViewById(R.id.info_id);
        info_pw = (EditText) findViewById(R.id.info_pw);
        info_nickName = (EditText) findViewById(R.id.info_nickName);
        info_phone1 = (EditText) findViewById(R.id.info_phone1);
        info_phone2 = (EditText) findViewById(R.id.info_phone2);
        info_phone3 = (EditText) findViewById(R.id.info_phone3);
        info_birth = (EditText) findViewById(R.id.info_birth);
        info_pwCheck = (EditText) findViewById(R.id.info_pwCheck);

        maleButton = (RadioButton) findViewById(R.id.maleButton);
        femaleButton = (RadioButton) findViewById(R.id.femaleButton);

        maleButton.setOnClickListener(v ->  sex = "male" );
        femaleButton.setOnClickListener(v ->  sex = "female" ); // 체크박스 선택시 성별 택스트로 저장.


        join_ok = (Button) findViewById(R.id.join_ok); // 회원가입 버튼 클릭 이벤트
        join_ok.setOnClickListener(v -> {
            //save
            String id = info_id.getText().toString().trim();
            String pw = info_pw.getText().toString().trim();
            String pwCheck = info_pwCheck.getText().toString().trim();
            String nickName = info_nickName.getText().toString().trim();
            String phone1 = info_phone1.getText().toString().trim();
            String phone2 = info_phone2.getText().toString().trim();
            String phone3 = info_phone3.getText().toString().trim();

            try {
                encryption = LoginActivity.SHA256.encrypt(pw);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            String birth = info_birth.getText().toString().trim();

            if (id.isEmpty()) {
                info_id.setError("아이디를 입력하세요.");
            } else if (pw.isEmpty()) {
                info_pw.setError("비밀번호를 입력하세요.");
            } else if (!pw.equals(pwCheck)) {
                info_pwCheck.setError("비밀번호가 다릅니다.");
            } else if (nickName.isEmpty()) {
                info_nickName.setError("닉네임을 입력하세요.");
            } else if ( birth.length() != 8 ) {
                info_birth.setError("- 없이 8자리로 입력하세요.");
            } else {
                saveUserInfo(id, encryption, nickName,phone1 + "-" + phone2 + "-" + phone3, sex, birth);
            }
        });

    }

    private void saveUserInfo(final String id, String pw, String nickName, String phone, String sex, String birth) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<userInfo> call = apiInterface.join(id, pw, nickName, phone, sex, birth);

        call.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse(@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    // Toast.makeText(JoinActivity.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    if (success) {

                        builder.setTitle("회원가입 안내").setMessage("\n 회원가입이 완료되었습니다.");
                        builder.setPositiveButton("확인", (dialog, which) -> {
                            dialog.dismiss();
                            finish(); // back to main activity
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        // if error, still in this activity
                        builder.setTitle("회원가입 안내").setMessage("\n" + response.body().getMessage());
                        builder.setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<userInfo> call, @NonNull Throwable t) {

                Toast.makeText(JoinActivity.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}
package com.example.thankage.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;
import android.widget.Toast;

import com.example.thankage.main.MainActivity;
import com.example.thankage.R;
import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.model.userInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText input_id, input_pw;
    int pwLength;
    String string_id, string_pw, string_encryption, fake_pw;
    Button login_btn, join_btn;


    ApiInterface apiInterface;

    public int INTENT_STATE1, INTENT_STATE2; // 로그인 상태 유지를 위한 조건 변수

    // TextView tt; // xml 파일에서 visibility 를 gone 으로 숨겼다. intent state 를 확인하기 위해 사용.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        INTENT_STATE1 = pref.getInt("state1", 0);
        string_id = pref.getString("id", "");
        string_encryption = pref.getString("pw", "");
        fake_pw = pref.getString("fake_pw", "");


        Intent intent = getIntent();
        INTENT_STATE2 = intent.getIntExtra("state", 20);
        // 어플 최초 실행시 받아오는 intent 가 없으므로 무조건 defaultValue 가 입력된다.
        // 이를 해결하기 위해 조건 변수 2개를 사용했다.
        if ( INTENT_STATE2 == 10 ) {
            INTENT_STATE1 = 10;
        } // 처음 실행 이후 로그아웃 버튼 클릭으로 로그인 화면으로 돌아오면, INTENT_STATE2 에 의해 INTENT_STATE1 이 바뀐다.
        // 이렇게 해준 이유는 직접 INTENT_STATE1을 건드리게 되면 재시작 시 초기값으로 돌아가기 때문이다.

        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("state1", INTENT_STATE1);
        editor.putString("id", string_id);
        editor.putString("pw", string_encryption);
        editor.putString("fake_pw", fake_pw);

        editor.apply(); // onBackPressed 에서 앱 전체종료를 사용하면서, onPause 가 호출되지 않고 종료된다.
        // 그래서 여기에서 editor 를 사용해 상태변수를 저장해준다.

        // tt = findViewById(R.id.textView14);
        // tt.setText("" + INTENT_STATE1 + "   " + INTENT_STATE2); // 이 코드는 로그인 조건변수를 보기위한 부분.

        join_btn = findViewById(R.id.join_btn);
        join_btn.setOnClickListener(v -> {
            Intent login_intent = new Intent(getApplicationContext(), JoinActivity.class);
            startActivity(login_intent);
        }); // 회원 가입 버튼 클릭시 회원 가입창으로 이동시켜주는 부분.

        input_id = findViewById(R.id.input_id);
        input_pw = findViewById(R.id.input_pw); // 입력된 id, pw를 저장한다.

        if (INTENT_STATE1 == 20 && INTENT_STATE2 == 20) { // 조건 변수 상황에 따라 로그인 상태로 보내주는 부분.

            Intent auto_intent = new Intent(getApplicationContext(), MainActivity.class);
            auto_intent.putExtra("id", string_id);
            auto_intent.putExtra("pw", string_encryption);
            auto_intent.putExtra("fake_pw", fake_pw);

            startActivity(auto_intent);
        } // 2개의 조건 변수가 모두 20인 경우에 로그인 상태로 바로 보내준다.

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(v -> { // 로그인 버튼 클릭시

            string_id = input_id.getText().toString();
            string_pw = input_pw.getText().toString();
            pwLength = string_pw.length();
            fake_pw = "AbcAbcAbcAbcAbcAbc".substring(0, pwLength);

            try {
                string_encryption = SHA256.encrypt(string_pw);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if (string_id.isEmpty()) {
                input_id.setError("아이디를 입력하세요.");
            } else if (string_pw.isEmpty()) {
                input_pw.setError("비밀번호를 입력하세요.");
            } else {
                LoginCheck(string_id, string_encryption); // 아래 함수 참조
            }

        });

    }

    private void LoginCheck(final String id, String pw) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<userInfo> call = apiInterface.login(id, pw);

        call.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse(@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    if (success) { // 로그인에 성공하면
                        INTENT_STATE1 = 20; // 로그인 상태 변수를 20으로 저장. INTENT_STATE1, 2 모두 20인 경우 로그인 상태가 유지된다.

                        Intent login_intent = new Intent(getApplicationContext(), MainActivity.class);
                        login_intent.putExtra("id", string_id);
                        login_intent.putExtra("pw", string_encryption);
                        login_intent.putExtra("fake_pw", fake_pw);

                        startActivity(login_intent);
                    } else { // 로그인에 실패하면 안내창 띄워서 회원정보가 일치하지 않음을 알려줌.
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("로그인 안내").setMessage("\n"+response.body().getMessage());
                        builder.setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        input_pw.setText(""); // 로그인 실패시 비밀번호 입력란 초기화
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<userInfo> call, @NonNull Throwable t) { // db 연결 예외처리
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class SHA256 {

        public static String encrypt(String text) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            return bytesToHex(md.digest());
        }

        private static String bytesToHex(byte[] bytes) {
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        }

    }

    @Override
    protected void onPause() { // 로그인 또는 회원가입 버튼을 눌러 다른 Activity 로 이동시 상태 변수 값을 저장해준다.
        super.onPause();
        // Toast.makeText(this,"onPause 호출",Toast.LENGTH_SHORT).show();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("state1", INTENT_STATE1); // INTENT_STATE2 는 어차피 항상 20으로 초기화 되므로 저장할 필요가 없다.
        editor.putString("id", string_id);
        editor.putString("pw", string_encryption);
        editor.putString("fake_pw", fake_pw);

        editor.apply();
    }

    @Override
    public void onBackPressed() { // 현재 띄워진 액티비티 뿐 아니라 어플 자체를 종료 하고 싶어서 호출
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}
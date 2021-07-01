package com.example.thankage.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.thankage.R;
import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.camp.CampActivity;
import com.example.thankage.fragments.setting.SettingActivity0;
import com.example.thankage.fragments.setting.SettingActivity1;
import com.example.thankage.fragments.setting.SettingActivity3;
import com.example.thankage.fragments.setting.SettingActivity4;
import com.example.thankage.fragments.setting.SettingActivity5;
import com.example.thankage.login.LoginActivity;
import com.example.thankage.model.userInfo;
import com.google.android.material.tabs.TabLayout;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    int a_id;
    String id, pw, fake_pw, nickName, phone, gender, birth, date, coin, heart;

    ApiInterface apiInterface;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } // 메뉴 xml 을 불러와 액션바에 세팅해줌

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        if (curId == R.id.menu_search) {
            Toast.makeText(this, "미구현", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    } // 메뉴 버튼을 눌렀을 때 나오는 액션을 여기에 코딩한다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter); // 뷰페이저 어뎁터 연결

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager); // 텝 레이아웃 연결

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pw = intent.getStringExtra("pw");
        fake_pw = intent.getStringExtra("fake_pw");

        LoginUserInfo(id, pw);

    }

    private void LoginUserInfo(final String id, String pw) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<userInfo> call = apiInterface.login(id, pw);

        call.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse(@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    if (success) {

                        nickName = response.body().getNickName();
                        phone = response.body().getPhone();
                        gender = response.body().getSex();
                        birth = response.body().getBirth();
                        date = response.body().getJoinDate();
                        coin = response.body().getCoin();
                        heart = response.body().getHeart();

                    } else {
                        Toast.makeText(MainActivity.this, "로그인된 유저가 없음. 잘못된 호출입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openSSolve(View view) {
        Intent intent = new Intent(getApplicationContext(), SSolveActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public String sendFragment5_nick() { // 5번 프래그먼트로 텍스트를 보내주기 위해 만든 함수다.
        return nickName;
    }

    public String sendFragment5_coin() { // 5번 프래그먼트로 텍스트를 보내주기 위해 만든 함수다.
        return coin;
    }

    public String sendFragment5_heart() { // 5번 프래그먼트로 텍스트를 보내주기 위해 만든 함수다.
        return heart;
    }

    public void openCamp(View view) {
        Intent intent = new Intent(getApplicationContext(), CampActivity.class);
        intent.putExtra("loginId", id);
        intent.putExtra("nickName", nickName);
        startActivity(intent);
    }

    public void openMall(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thankage.com/ko/"));
        startActivity(intent);
    }

    public void logout(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("로그아웃");
        alertDialog.setMessage("\n로그아웃 하시겠습니까?");
        alertDialog.setNegativeButton("아니오", (dialog, which) -> dialog.dismiss());
        alertDialog.setPositiveButton("예",
                (dialog, which) -> {
                    Intent logout_intent = new Intent(getApplicationContext(), LoginActivity.class);
                    logout_intent.putExtra("state", 10);
                    startActivity(logout_intent);
                    dialog.dismiss();
                });

        alertDialog.show();
    }

    public void setting0(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity0.class);
        intent.putExtra("id", id);
        intent.putExtra("pw", fake_pw);
        intent.putExtra("nickName", nickName);
        intent.putExtra("phone", phone);
        intent.putExtra("sex", gender);
        intent.putExtra("birth", birth);
        intent.putExtra("joinDate", date);

        startActivity(intent);

    }

    public void setting1(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity1.class);
        startActivity(intent);
    }

    public void setting3(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity3.class);
        startActivity(intent);
    }

    public void setting4(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity4.class);
        startActivity(intent);
    }

    public void setting5(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity5.class);
        intent.putExtra("id", id);
        intent.putExtra("pw", pw);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() { // 현재 띄워진 액티비티 뿐 아니라 어플 자체를 종료 하고 싶어서 호출
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    public void openAd(View view) {
        Intent intent = new Intent(getApplicationContext(), AdvertisementActivity.class);
        Random random = new Random();
        a_id = random.nextInt(3)+1;
        intent.putExtra("a_id", a_id);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
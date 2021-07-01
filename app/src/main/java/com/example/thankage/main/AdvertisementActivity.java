package com.example.thankage.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.thankage.R;
import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.login.LoginActivity;
import com.example.thankage.model.Advertisement;
import com.example.thankage.model.userInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementActivity extends AppCompatActivity {

    private static final int COUNT_DOWN_INTERVAL = 1000;

    private int count = 0;
    int a_id, ad_sec;
    TextView left_time, total_time, tv_ad_id, tv_ad_name, tv_ad_sec;
    String id, ad_name;
    private CountDownTimer countDownTimer;

    VideoView videoView;

    ProgressBar progressBar;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        Intent intent = getIntent();
        a_id = intent.getIntExtra("a_id", 0);
        id = intent.getStringExtra("id");

        ConAdvertisementDB(a_id);

        videoView = (VideoView)findViewById(R.id.videoView);
        if (a_id == 1) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ad_5);
        } else if (a_id == 2) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ad_10);
        } else if (a_id == 3) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ad_15);
        }
        videoView.start();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tv_ad_id = (TextView) findViewById(R.id.tv_ad_id);
        tv_ad_name = (TextView) findViewById(R.id.tv_ad_name);
        tv_ad_sec = (TextView) findViewById(R.id.tv_ad_sec);
        left_time = (TextView) findViewById(R.id.left_time);
        total_time = (TextView) findViewById(R.id.total_time);

        tv_ad_id.setText("" + a_id);

    }

    private void ConAdvertisementDB(int aid) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Advertisement> call = apiInterface.getAdvertisement(aid);

        call.enqueue(new Callback<Advertisement>() {
            @Override
            public void onResponse(@NonNull Call<Advertisement> call, @NonNull Response<Advertisement> response) {
                if (response.isSuccessful() && response.body() != null) {

                        ad_name = response.body().getAd_name();
                        ad_sec = response.body().getAd_sec();

                        tv_ad_name.setText(ad_name);
                        tv_ad_sec.setText("" + ad_sec);
                        total_time.setText(" / " + ad_sec);
                        progressBar.setMax(ad_sec);

                    countDownTimer(ad_sec+1);
                    countDownTimer.start();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Advertisement> call, @NonNull Throwable t) {
                Toast.makeText(AdvertisementActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewCount(int a) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Advertisement> call = apiInterface.AdvertisementCount(a);

        call.enqueue(new Callback<Advertisement>() {
            @Override
            public void onResponse(@NonNull Call<Advertisement> call, @NonNull Response<Advertisement> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
//                        Toast.makeText(AdvertisementActivity.this, "성공", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Advertisement> call, @NonNull Throwable t) {
                Toast.makeText(AdvertisementActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void heartCount(String id, int a) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<userInfo> call = apiInterface.heartCount(id, a);

        call.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse(@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {
//                Toast.makeText(AdvertisementActivity.this, "이거 나와야돼", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Toast.makeText(AdvertisementActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void countDownTimer(int a) {

        countDownTimer = new CountDownTimer(a*1000, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(count);
                left_time.setText(String.valueOf(count));
                count++;
            }

            @Override
            public void onFinish() {
                videoView.pause();
                AlertDialog.Builder builder = new AlertDialog.Builder(AdvertisementActivity.this);
                builder.setTitle("광고 시청 안내").setMessage("\n 광고 시청 및 하트 적립이 완료되었습니다.");
                builder.setPositiveButton("확인", (dialog, which) -> {
                    dialog.dismiss();
                    viewCount(a_id);
                    heartCount(id, a_id);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        } catch (Exception e) {
            countDownTimer = null;
        }
    }
}
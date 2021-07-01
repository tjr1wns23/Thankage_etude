package com.example.thankage.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thankage.R;
import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.login.LoginActivity;
import com.example.thankage.model.quiz;
import com.example.thankage.model.userInfo;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SSolveActivity extends AppCompatActivity {

    private int count = 20;
    int correctAnswerCount = 0, curNum = 0, selected_num=0, answer;

    TextView ss_head, tv_problem, left_time2;
    ProgressBar progressBar2;
    private CountDownTimer countDownTimer;
    RadioGroup radioGroup;
    RadioButton RBtn1, RBtn2, RBtn3, RBtn4;

    int[] a = new int[3]; // 처음 문제 3개를 랜덤으로 뽑아오기 위한 숫자 배열.

    String id;
    String[] quiz_array = {
                  "", "", "", "", "", "",
                  "", "", "", "", "", "",
                  "", "", "", "", "", ""}; // 문제, 보기 4개, 정답 을 가져오기 위한 문자열 배열. 한줄에 6개 이다.

    ApiInterface apiInterface;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    } // 메뉴 버튼을 눌렀을 때 나오는 액션을 여기에 코딩한다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_solve);

        Intent intent = getIntent();
        id = intent.getStringExtra("id"); // coin 증가시킬 id 를 MainActivity 에서 미리 받아서 저장해놓는다.

        ActionBar actionBar = getSupportActionBar(); // 액션바 호출
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게
        actionBar.setTitle("혼자풀기"); // 액션바 제목 설정

        Random r = new Random();
        for (int i = 0; i <= 2; i++) {
            a[i] = r.nextInt(10) + 1;
            for (int j = 0; j < i; j++) {
                if (a[i] == a[j]) {
                    i--;
                }
            }
        }       // 랜덤 수 출력.  출처: https://geojun.tistory.com/36 [Jungle(정글)]

        ConQuizDB(a[0], a[1], a[2]);

        ss_head = (TextView) findViewById(R.id.ss_head);
        ss_head.setText("정답 개수 "+correctAnswerCount);

        tv_problem = (TextView) findViewById(R.id.tv_problem);
        left_time2 = (TextView) findViewById(R.id.left_time2);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setMax(20);

        radioGroup = (RadioGroup) findViewById(R.id.ss_radioGroup);
        RBtn1 = (RadioButton) findViewById(R.id.radioButton1);
        RBtn2 = (RadioButton) findViewById(R.id.radioButton2);
        RBtn3 = (RadioButton) findViewById(R.id.radioButton3);
        RBtn4 = (RadioButton) findViewById(R.id.radioButton4);

    }

    private void ConQuizDB(int i, int i1, int i2) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<quiz> call = apiInterface.quiz(i, i1, i2);
        call.enqueue(new Callback<quiz>() {
            @Override
            public void onResponse(@NonNull Call<quiz> call, @NonNull Response<quiz> response) {
                if (response.isSuccessful() && response.body() != null) {

                    quiz_array[0] = response.body().getProblem_1();
                    quiz_array[1] = response.body().getChoice1_1();
                    quiz_array[2] = response.body().getChoice2_1();
                    quiz_array[3] = response.body().getChoice3_1();
                    quiz_array[4] = response.body().getChoice4_1();
                    quiz_array[5] = response.body().getAnswer_1();

                    quiz_array[6] = response.body().getProblem_2();
                    quiz_array[7] = response.body().getChoice1_2();
                    quiz_array[8] = response.body().getChoice2_2();
                    quiz_array[9] = response.body().getChoice3_2();
                    quiz_array[10] = response.body().getChoice4_2();
                    quiz_array[11] = response.body().getAnswer_2();

                    quiz_array[12] = response.body().getProblem_3();
                    quiz_array[13] = response.body().getChoice1_3();
                    quiz_array[14] = response.body().getChoice2_3();
                    quiz_array[15] = response.body().getChoice3_3();
                    quiz_array[16] = response.body().getChoice4_3();
                    quiz_array[17] = response.body().getAnswer_3();

                    startQuiz();
                }
            }

            @Override
            public void onFailure(@NonNull Call<quiz> call, @NonNull Throwable t) {
                Toast.makeText(SSolveActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    private void startQuiz() {

        tv_problem.setText(quiz_array[0]);

        RBtn1.setText(quiz_array[1]);
        RBtn2.setText(quiz_array[2]);
        RBtn3.setText(quiz_array[3]);
        RBtn4.setText(quiz_array[4]);

        answer = Integer.valueOf(quiz_array[5]);

        countDownTimer();
        countDownTimer.start();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                curNum++;
                countDownTimer.cancel();

                if (RBtn1.isChecked()) {
                    selected_num = 1;
                } else if (RBtn2.isChecked()) {
                    selected_num = 2;
                } else if (RBtn3.isChecked()) {
                    selected_num = 3;
                } else if (RBtn4.isChecked()) {
                    selected_num = 4;
                }

                if (selected_num == answer) { // 이 숫자는 무엇일까..
                    correctAnswerCount++;
                    dialogShow("정답", "정답입니다.");
                    ss_head.setText("정답 개수 "+correctAnswerCount);

                } else {
                    dialogShow("오답", "틀렸습니다.");

                }

                RBtn1.setChecked(false);
                RBtn2.setChecked(false);
                RBtn3.setChecked(false);
                RBtn4.setChecked(false);

            }
        });
    }

    public void dialogShow(String title, String contents) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SSolveActivity.this);
        builder.setCancelable(false);
        builder.setTitle(title).setMessage("\n" + contents);
        builder.setPositiveButton("확인", (dialog, which) -> {
            countDownTimer.start();
            nextQuiz(curNum);
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void countDownTimer() {

        countDownTimer = new CountDownTimer(21 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar2.setProgress(count);
                left_time2.setText(String.valueOf(count));
                count--;
            }

            @Override
            public void onFinish() {
                curNum++;
                dialogShow("시간초과", "시간초과입니다.");
                countDownTimer.cancel();
            }
        };
    }

    public void nextQuiz(int curNum) {
        count = 20;
        if (curNum == 1 || curNum == 2) {
            tv_problem.setText(quiz_array[curNum*6]);

            RBtn1.setText(quiz_array[curNum*6 + 1]);
            RBtn2.setText(quiz_array[curNum*6 + 2]);
            RBtn3.setText(quiz_array[curNum*6 + 3]);
            RBtn4.setText(quiz_array[curNum*6 + 4]);

            answer = Integer.valueOf(quiz_array[curNum*6 + 5]);

        } else if (curNum == 3) {

            RBtn1.setClickable(false);
            RBtn2.setClickable(false);
            RBtn3.setClickable(false);
            RBtn4.setClickable(false);
            countDownTimer.cancel();

            AlertDialog.Builder builder = new AlertDialog.Builder(SSolveActivity.this);
            builder.setCancelable(false);
            builder.setTitle("결과").setMessage("\n"+correctAnswerCount+"개 정답으로 코인 "+correctAnswerCount+"개가 적립되었습니다.");
            builder.setPositiveButton("확인", (dialog, which) -> {
                coinCount(id, correctAnswerCount);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    public void coinCount(String id, int a) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<userInfo> call = apiInterface.coinCount(id, a);

        call.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse(@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {
//                Toast.makeText(AdvertisementActivity.this, "이거 나와야돼", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Toast.makeText(SSolveActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
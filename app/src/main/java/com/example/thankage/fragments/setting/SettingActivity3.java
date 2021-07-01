package com.example.thankage.fragments.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thankage.R;

import java.util.ArrayList;

public class SettingActivity3 extends AppCompatActivity {

    ArrayList<String> InfoList = new ArrayList<String>();

    ListView listView; // ListView 참조 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting3);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("프로그램 정보");

        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게

        InfoList.add("개인정보 보호방침");
        InfoList.add("서비스 이용약관");
        InfoList.add("유료서비스 이용약관");
        InfoList.add("Open Source License"); // 문자열 데이터 ArrayList 에 추가

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, InfoList);
        //ListView가 보여줄 뷰를 만들어내는 Adapter 객체 생성
        //ArrayAdapter : 문자열 데이터들을 적절한 view 로 1:1로 만들어서 List 형태로 ListView 에 제공하는 객체
        //첫번째 파라미터 : Context객체 ->MainActivity가 Context를 상속했기 때문에 this로 제공 가능
        //두번째 파라미터 : 문자열 데이터를 보여줄 뷰. ListView에 나열되는 하나의 아이템 단위의 뷰 모양
        //세번째 파라미터 : adapter가 뷰로 만들어줄 대량의 데이터들
        //본 예제에서는 문자열만 하나씩 보여주면 되기 때문에 두번째 파라미터의 뷰 모먕은 Android 시스템에서 제공하는
        //기본 Layout xml 파일을 사용함.

        listView = (ListView) findViewById(R.id.setting3_listView);
        listView.setAdapter(adapter); // 위에 만들어진 Adapter 를 ListView 에 설정

        listView.setOnItemClickListener(listner);

    }

    AdapterView.OnItemClickListener listner = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast.makeText(SettingActivity3.this, InfoList.get(position), Toast.LENGTH_SHORT).show();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingActivity3.this);
            alertDialog.setTitle(InfoList.get(position));
            alertDialog.setMessage(InfoList.get(position));
            alertDialog.setNegativeButton("닫기", (dialog, which) -> dialog.dismiss());

            alertDialog.show();

        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int curId = item.getItemId();
        if (curId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
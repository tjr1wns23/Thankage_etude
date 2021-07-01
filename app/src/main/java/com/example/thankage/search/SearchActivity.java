package com.example.thankage.search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.thankage.R;
import com.example.thankage.editor.EditorActivity;
import com.example.thankage.model.Note;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView{

    private static final int INTENT_EDIT = 200;
    ActionBar actionBar;
    ImageButton imageButton;
    EditText editText;
    String search_value, loginId, nickName;

    RecyclerView search_recyclerView;

    SearchPresenter presenter;
    SearchAdapter adapter;
    SearchAdapter.ItemClickListener itemClickListener;

    List<Note> note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent getIntent = getIntent();
        loginId = getIntent.getStringExtra("loginId");
        nickName = getIntent.getStringExtra("nickName");

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false); // 액션바에 표시되는 제목
        actionBar.setDisplayShowHomeEnabled(false); // 홈 아이콘

        // layout 을 actionBar 에 포팅
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.search_actionbar, null);
        actionBar.setCustomView(mCustomView);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> finish()); // imageButton 에 뒤로가기 설정

        editText = findViewById(R.id.editText);
        editText.setOnEditorActionListener((v, actionId, event) -> {

            search_value = editText.getText().toString();
            if (search_value.length() < 2) {
                Toast.makeText(this, "2글자 이상입력하세요", Toast.LENGTH_SHORT).show();
            } else {

                ProgressDialog progressDialog = new ProgressDialog(this); // 검색중일 때 알림창 생성.
                progressDialog.setMessage("검색 중");
                progressDialog.show();

                presenter = new SearchPresenter(this);
                presenter.getData(search_value); // 데이터를 읽어옴

                progressDialog.dismiss(); // 검색 완료 후 알림창 닫음. 잘 나온다. 다만 어플 실행시에는 연결이 빨라서 안나오는 것처럼 보인다.

                itemClickListener = ((view, position) -> {
                    int id = note.get(position).getId();
                    String title = note.get(position).getTitle();
                    String notes = note.get(position).getNote();
                    String image_name = note.get(position).getImage_name();
                    int color = note.get(position).getColor();

                    Intent intent = new Intent(this, EditorActivity.class);
                    intent.putExtra("nickName", nickName);
                    intent.putExtra("loginId", loginId);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("note", notes);
                    intent.putExtra("color", color);
                    intent.putExtra("image_name", image_name);
                    startActivityForResult(intent, INTENT_EDIT);


                }); // 클릭시 팅기는 에러 있었음. 원인은 오타!
            }

            return true;
        });

        search_recyclerView = findViewById(R.id.search_recyclerView);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onGetResult(List<Note> notes) {
        adapter = new SearchAdapter(this, notes, itemClickListener);
        adapter.notifyDataSetChanged();
        search_recyclerView.setAdapter(adapter);

        note = notes;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
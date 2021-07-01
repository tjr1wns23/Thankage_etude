package com.example.thankage.camp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.thankage.R;
import com.example.thankage.search.SearchActivity;
import com.example.thankage.editor.EditorActivity;
import com.example.thankage.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CampActivity extends AppCompatActivity implements CampView {

    private static final int INTENT_ADD = 100;
    private static final int INTENT_EDIT = 200;

    String loginId, nickName;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    CampPresenter presenter;
    CampAdapter adapter;
    CampAdapter.ItemClickListener itemClickListener;

    List<Note> note;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camp, menu);
        return true;
    } // 메뉴 xml 을 불러와 액션바에 세팅해줌

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int curId = item.getItemId();
        if (curId == R.id.menu_search) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.putExtra("nickName", nickName);
            startActivity(intent);
        } else if (curId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    } // 메뉴 버튼을 눌렀을 때 나오는 액션을 여기에 코딩한다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp);

        Intent getIntent = getIntent();
        loginId = getIntent.getStringExtra("loginId");
        nickName = getIntent.getStringExtra("nickName");

        ActionBar actionBar = getSupportActionBar(); // 액션바 호출
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게
        actionBar.setTitle("KSEEK 캠프"); // 액션바 제목 설정

        swipeRefresh = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                intent.putExtra("nickName", nickName);
                intent.putExtra("loginId", loginId);
                intent.putExtra("image_name","no_image");
                startActivityForResult(intent, INTENT_ADD);
            }
        });
//        fab.setOnClickListener(view ->
//                startActivityForResult(
//                        new Intent(this, EditorActivity.class),
//                        INTENT_ADD) // fab 버튼 이벤트로 add intent 와 함께 EditorActivity 로 전달.
//        );

        presenter = new CampPresenter(this);
        presenter.getData();

        swipeRefresh.setOnRefreshListener(
                () -> presenter.getData()

        );

        itemClickListener = ((view, position) -> {
            int id = note.get(position).getId();
            String title = note.get(position).getTitle();
            String notes = note.get(position).getNote();
            String image_name = note.get(position).getImage_name();
            int color = note.get(position).getColor(); // position 이 일치하는 DB data 의 정보를 가져옴

            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra("nickName", nickName);
            intent.putExtra("loginId", loginId);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("note", notes);
            intent.putExtra("image_name", image_name); // 가져온 정보를 저장함.
            startActivityForResult(intent, INTENT_EDIT);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_ADD && resultCode == RESULT_OK) {
            presenter.getData(); // reload data
        } else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK) {
            presenter.getData(); // reload data
        }
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Note> notes) {
        adapter = new CampAdapter(this, notes, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        note = notes;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(CampActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        // finish(); // back to main activity
        presenter.getData(); // reload data
    }

}
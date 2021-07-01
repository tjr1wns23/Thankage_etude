package com.example.thankage.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.thankage.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;

public class EditorActivity extends AppCompatActivity implements EditorView {

    TextView tv_title, tv_imgName;
    ImageView gallery_image, selected_image;
    ImageButton del_image_btn;
    EditText et_note;
    ProgressDialog progressDialog;


    EditorPresenter presenter;

    int color, id, REQUEST_CODE = 100, del_image_code, get_notify_state, Edit_notify_state;
    String title, note, image_name, nickName, loginId;

    Menu actionMenu;

    Spinner spinner;
    View layout, layout2;

    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ActionBar actionBar = getSupportActionBar(); // 액션바 호출
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true); // 액션바에 뒤로가기 버튼 보이게
        actionBar.setTitle("글쓰기"); // 액션바 제목 설정

        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        loginId = intent.getStringExtra("loginId");
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        image_name = intent.getStringExtra("image_name");
        color = intent.getIntExtra("color", 0);
        get_notify_state = intent.getIntExtra("notify_state", 10);

        final String[] Team_menu = {"전체", "버그리포트", "미디어사업팀", "Wizlab", "BI팀", "크리에이티브팀"};
        spinner = (Spinner) findViewById(R.id.et_spinner);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Team_menu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        layout = findViewById(R.id.et_constraintLayout);
        layout2 = findViewById(R.id.et_constraintLayout2);

        tv_title = findViewById(R.id.title);
        tv_title.setText(nickName);
        tv_imgName = findViewById(R.id.tv_imageName);
        et_note = findViewById(R.id.note);

        gallery_image = findViewById(R.id.iv_gallery_img);
        gallery_image.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent1, REQUEST_CODE);
        });
        selected_image = findViewById(R.id.iv_selected_image);
        if (image_name.equals("no_image")) {
            layout2.setVisibility(View.GONE);
        } else {
            Glide.with(this).load("http://10.0.2.2/" + image_name)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(selected_image);
        }

        del_image_btn = findViewById(R.id.del_image_btn);
        del_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = null;
                del_image_code = 100;
                layout2.setVisibility(View.GONE);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        presenter = new EditorPresenter(this);

        setDataFromIntentExtra();

    }

    @Override //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 시간 코드 생성
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
                    String currentTime = dateFormat.format(System.currentTimeMillis());

                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    layout2.setVisibility(View.VISIBLE);
                    selected_image.setImageBitmap(bitmap);
                    tv_imgName.setText(currentTime);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(EditorActivity.this, "사진을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id != 0) {
            if (!title.equals(nickName)) {
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(false);
            } else {
                actionMenu.findItem(R.id.edit).setVisible(true);
                actionMenu.findItem(R.id.delete).setVisible(true);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(false);
            }
        } else {
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String title = tv_title.getText().toString().trim();
        String note = et_note.getText().toString().trim();
        int color = -5864785;
        String image;
        String imageName;

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.save:
                //save
                if (note.isEmpty()) {
                    et_note.setError("내용을 입력해 주세요.");
                } else {

                    if (bitmap == null) {
                        image = "noImage";
                        imageName = "no_image";
                    } else {
                        image = presenter.imageToString(bitmap);
                        imageName = "zz_" + loginId + "_" + tv_imgName.getText().toString().trim() + ".jpeg";
                    }

                    presenter.saveNote(title, note, color, imageName, image);
                    // 여기에 알림설정 관련해서 집어넣는다.
                    createNotification();
                }
                return true;

            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.update:
                //Update
                if (note.isEmpty()) {
                    et_note.setError("내용을 입력해 주세요.");
                } else {

                    if (bitmap == null && del_image_code == 100) {
                        image = "noImage";
                        imageName = "no_image";
                    } else if (bitmap == null && del_image_code != 100) {
                        image = "noImage";
                        imageName = image_name;
                    } else if (bitmap != null && image_name.length() > 12) {
                        image = presenter.imageToString(bitmap);
                        imageName = image_name;
                    } else if (bitmap != null && image_name.length() < 12) {
                        image = presenter.imageToString(bitmap);
                        imageName = "zz_" + loginId + "_" + tv_imgName.getText().toString().trim() + ".jpeg";
                    } else {
                        image = presenter.imageToString(bitmap);
                        imageName = image_name;
                    }

                    presenter.updateNote(id, title, note, color, imageName, image);
                }

                return true;

            case R.id.delete:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("주의");
                alertDialog.setMessage("\n삭제 하시겠습니까?");
                alertDialog.setNegativeButton("네", (dialog, which) -> {
                    dialog.dismiss();
                    presenter.deleteNote(id);
                });
                alertDialog.setPositiveButton("아니오",
                        (dialog, which) -> dialog.dismiss());

                alertDialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {
            tv_title.setText(title);
            et_note.setText(note);
            spinner.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);

            getSupportActionBar().setTitle("게시글 보기");
            readMode();

        } else {
            editMode();
            tv_title.setVisibility(View.GONE);
        }
    }

    private void editMode() {
        spinner.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
        del_image_btn.setVisibility(View.VISIBLE);
        tv_title.setFocusableInTouchMode(true);
        et_note.setFocusableInTouchMode(true);
        tv_title.setFocusable(true);
        et_note.setFocusable(true);
    }

    private void readMode() {
        tv_title.setFocusable(false);
        et_note.setFocusable(false);
        tv_title.setFocusableInTouchMode(false);
        et_note.setFocusableInTouchMode(false);
    }

    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.no_bg_logo);
        builder.setContentTitle("새 글 알림");
        builder.setContentText("새 글이 작성되었습니다.");

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        notificationManager.notify(1, builder.build());
    }

}
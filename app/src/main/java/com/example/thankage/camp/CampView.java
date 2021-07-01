package com.example.thankage.camp;

import java.util.List;
import com.example.thankage.model.Note;

public interface CampView {

    void hideLoading();

    void onGetResult(List<Note> notes);
    void onErrorLoading(String message);

    void onRequestSuccess(String message);

}

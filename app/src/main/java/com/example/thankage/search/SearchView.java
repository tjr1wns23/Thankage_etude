package com.example.thankage.search;

import com.example.thankage.model.Note;

import java.util.List;

public interface SearchView {
    void onGetResult(List<Note> notes);
    void onErrorLoading(String message);
}

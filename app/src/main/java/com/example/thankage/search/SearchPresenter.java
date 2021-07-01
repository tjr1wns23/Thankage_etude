package com.example.thankage.search;

import androidx.annotation.NonNull;

import com.example.thankage.api.ApiClient;
import com.example.thankage.api.ApiInterface;
import com.example.thankage.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {

    private SearchView view;

    public SearchPresenter(SearchView view) {
        this.view = view;
    }

    void getData(String search_value) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Note>> call = apiInterface.getSearch(search_value); // getSearch 로 검색용 php 파일 연결
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(@NonNull Call<List<Note>> call, @NonNull Response<List<Note>> response) {
                if (response.isSuccessful() && response.body() != null) {
                        view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Note>> call, @NonNull Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }
}

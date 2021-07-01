package com.example.thankage.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thankage.R;
import com.example.thankage.camp.CampAdapter;
import com.example.thankage.camp.CampPresenter;

public class BlankFragment2 extends Fragment {

    RecyclerView recyclerView;

    CampPresenter presenter;
    CampAdapter adapter;

    Context ct;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false);


    }


    public void openCamp(View view) { }

}
package com.example.thankage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thankage.R;
import com.example.thankage.main.MainActivity;


public class BlankFragment4 extends Fragment {

    TextView tv_heart;
    String heart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_blank4, container, false);

        MainActivity activity = (MainActivity) getActivity();
        heart = activity.sendFragment5_heart();

        tv_heart = v.findViewById(R.id.f4_tv_heart);
        tv_heart.setText(heart);

        return v;
    }

    public void openMall(View view) {
    }
}
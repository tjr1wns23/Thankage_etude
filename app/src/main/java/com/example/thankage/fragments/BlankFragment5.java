package com.example.thankage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thankage.main.MainActivity;
import com.example.thankage.R;


public class BlankFragment5 extends Fragment {

    private String nickName, coin, heart;
    private TextView setting_nick, setting_coin, setting_heart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank5, container, false);

        MainActivity activity = (MainActivity) getActivity();
        nickName = activity.sendFragment5_nick();
        coin = activity.sendFragment5_coin();
        heart = activity.sendFragment5_heart();

        setting_nick = v.findViewById(R.id.settingNickname);
        setting_coin = v.findViewById(R.id.setting_coin);
        setting_heart = v.findViewById(R.id.setting_heart);

        setting_nick.setText(nickName);
        setting_coin.setText(coin);
        setting_heart.setText(heart);

        return v;
    }

    public void openMall(View view) { }

    public void setting0(View view) { }

    public void setting1(View view) { }

    public void setting3(View view) { }

    public void setting4(View view) { }

    public void setting5(View view) { }

    public void logout(View view) { }

    public void openAd(View view) { }

}
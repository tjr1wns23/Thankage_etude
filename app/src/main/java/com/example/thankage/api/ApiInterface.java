package com.example.thankage.api;

import com.example.thankage.model.Advertisement;
import com.example.thankage.model.Note;
import com.example.thankage.model.quiz;
import com.example.thankage.model.userInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("join.php")
    Call<userInfo> join (
            @Field("id") String id,
            @Field("pw") String pw,
            @Field("nickName") String nickName,
            @Field("phone") String phone,
            @Field("sex") String sex,
            @Field("birth") String birth
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<userInfo> login (
            @Field("id") String id,
            @Field("pw") String pw
    );

    @FormUrlEncoded
    @POST("modify_nickName.php")
    Call<userInfo> modify_nickName (
            @Field("id") String id,
            @Field("nickName") String nickName
    );

    @FormUrlEncoded
    @POST("heart_count.php")
    Call<userInfo> heartCount(
            @Field("id") String id,
            @Field("count") int count
    );

    @FormUrlEncoded
    @POST("coin_count.php")
    Call<userInfo> coinCount(
            @Field("id") String id,
            @Field("count") int count
    );

//    @FormUrlEncoded
//    @POST("modify_phone.php")
//    Call<userInfo> modify_phone (
//            @Field("id") String id,
//            @Field("phone") String phone
//    );
//
//    @FormUrlEncoded
//    @POST("modify_pw.php")
//    Call<userInfo> modify_pw (
//            @Field("id") String id,
//            @Field("pw") String pw
//    );

    @FormUrlEncoded
    @POST("secession.php")
    Call<userInfo> secession ( @Field("id") String id );

    @FormUrlEncoded
    @POST("save.php")
    Call<Note> saveNote(
        @Field("title") String title,
        @Field("note") String note,
        @Field("color") int color,
        @Field("image_name") String image_name,
        @Field("image") String image
    );

    @GET("notes.php")
    Call<List<Note>> getNote();

    @FormUrlEncoded
    @POST("update.php")
    Call<Note> updateNote(
            @Field("id") int id,
            @Field("title") String title,
            @Field("note") String note,
            @Field("color") int color,
            @Field("image_name") String image_name,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<Note> deleteNote ( @Field("id") int id );

    @FormUrlEncoded
    @POST("search.php")
    Call<List<Note>> getSearch( @Field("search_value") String search_value );

    @FormUrlEncoded
    @POST("advertisement.php")
    Call<Advertisement> getAdvertisement( @Field("id") int id );

    @FormUrlEncoded
    @POST("advertisement_count.php")
    Call<Advertisement> AdvertisementCount( @Field("id") int id );

    @FormUrlEncoded
    @POST("quiz.php")
    Call<quiz> quiz (
            @Field("id1") int id1,
            @Field("id2") int id2,
            @Field("id3") int id3
    );

}

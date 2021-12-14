package com.example.priceviewer;


import com.google.gson.annotations.SerializedName;


public class Post {

    private int userId;

    private Integer id;  // eğer null obje yaratılırsa serialize yaparken Integer olursa ignore ediliyormuş . o endenine int yerine Integer kullanıldı.

    private String title;

    @SerializedName("body")
    private String test;

    //Bu constructor post item için yapıldı ve içine post ıd eklenmedi çünkü post id server tarafından yaratılıyorç
    public Post(int userId, String title, String test) {
        this.userId = userId;
        this.title = title;
        this.test = test;
    }

    public Post() {

    }
    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTest() {
        return test;
    }
}

package com.example.pscproba46;

import com.google.gson.annotations.SerializedName;

public class Author {


    @SerializedName("user_nicename")
    private String author;

    public String getAuthor() {
        return author;
    }
}

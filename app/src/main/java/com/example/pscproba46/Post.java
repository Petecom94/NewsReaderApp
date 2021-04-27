package com.example.pscproba46;

public class Post {



    private String id;
private String link;
private String jetpack_featured_media_url;
private String slug;
private String formatted_date;
private Author author_meta;
private Title title;
private Rendered excerpt;


    public String getId() {
        return id;
    }

    public Rendered getExcerpt() {
        return excerpt;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor_meta() {
        return author_meta;
    }

    public String getSlug() {
        return slug;
    }

    public String getFormatted_date() {
        return formatted_date;
    }

   /* public String getDisplay_name() {
        return author_meta;
    }*/




    public String getimgLink() {
        return jetpack_featured_media_url;
    }

    public String getLink() {
        return link;
    }


}





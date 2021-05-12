package com.example.pscproba46;

public class CikkekAdat {
    private String id;
    private LinkCikk title;
    private String formatted_date;
    private AuthorCikkek author_meta;
    private String link;
    private String fimg_url;

    public AlcimCikkek getExcerpt() {
        return excerpt;
    }

    private AlcimCikkek excerpt;

    public String getId() {
        return id;
    }

    public LinkCikk getTitle() {
        return title;
    }

    public String getFormatted_date() {
        return formatted_date;
    }

    public AuthorCikkek getAuthor_meta() {
        return author_meta;
    }

    public String getLink() {
        return link;
    }

    public String getFimg_url() {
        return fimg_url;
    }


}

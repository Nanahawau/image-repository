package com.nana.imagerepository.Model;

public class ImageResponse {

    private Long id;
    private String url;

    public ImageResponse() {
    }
    public ImageResponse( String url) {
        this.url = url;

    }

    public ImageResponse(Long id, String url) {
        this.id = id;
        this.url = url;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

package com.nana.imagerepository.Model;

public class ImageResponse {

    private String name;
    private String url;
    private String permission;
    private Long size;

    public ImageResponse() {
    }

    public ImageResponse(String name, String url, String permission, Long size) {
        this.name = name;
        this.url = url;
        this.permission = permission;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}

package com.nana.imagerepository.Model;

public class PermissionDTO {

    private Long imageId;
    private String isPrivate;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
                "imageId=" + imageId +
                ", isPrivate='" + isPrivate + '\'' +
                '}';
    }
}

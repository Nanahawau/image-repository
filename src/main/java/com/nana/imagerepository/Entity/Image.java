package com.nana.imagerepository.Entity;

import com.nana.imagerepository.Model.ImagePermission;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String filePath;
    @Column(unique = true)
    private Boolean isPrivate = true;


    public Image() {
    }

    public Image(String userId, String filePath, Boolean isPrivate) {
        this.userId = userId;
        this.filePath = filePath;
        this.isPrivate = isPrivate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", filePath='" + filePath + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}

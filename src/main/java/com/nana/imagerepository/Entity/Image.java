package com.nana.imagerepository.Entity;

import com.nana.imagerepository.model.ImagePermissiion;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Arrays;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String size;
    private String description;
    @Lob
    private byte[] data;
    private ImagePermissiion permission;


    public Image() {
    }

    public Image(String title, String size, String description, byte[] data, ImagePermissiion permission) {
        this.title = title;
        this.size = size;
        this.description = description;
        this.data = data;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public ImagePermissiion getPermission() {
        return permission;
    }

    public void setPermission(ImagePermissiion permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", size='" + size + '\'' +
                ", description='" + description + '\'' +
                ", data=" + Arrays.toString(data) +
                ", permission=" + permission +
                '}';
    }
}

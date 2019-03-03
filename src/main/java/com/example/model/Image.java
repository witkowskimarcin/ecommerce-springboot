package com.example.model;

import org.json.JSONPropertyIgnore;
import org.json.JSONPropertyName;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private byte[] image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JSONPropertyIgnore
    public byte[] getImage() {
        return image;
    }

    @JSONPropertyName("image")
    public String generateBase64Image() {
        return Base64.getEncoder().encodeToString(image);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

package com.example.entity;

import javax.persistence.*;
import java.util.Base64;
import java.util.Objects;

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

//    @JSONPropertyIgnore
    public byte[] getImage() {
        return image;
    }

//    @JSONPropertyName("image")
    public String generateBase64Image() {
        return Base64.getEncoder().encodeToString(this.image);
    }

//    @JSONPropertyName("image")

//    public String getImage() {
//        return Base64.getEncoder().encodeToString(image);
//    }

    public void setImageBase64(String image){
        this.image = Base64.getDecoder().decode(image);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}

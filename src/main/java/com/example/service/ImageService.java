package com.example.service;

import com.example.entity.Image;
import com.example.model.ImageModel;
import com.example.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ImageService
{
    Image saveImage(ImageModel image);
    List<Image> saveImages(List<ImageModel> images);
}

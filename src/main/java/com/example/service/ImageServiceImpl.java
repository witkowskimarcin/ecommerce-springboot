package com.example.service;

import com.example.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService
{
    private ImageRepository imageRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ImageServiceImpl(ImageRepository imageRepository, Mappers mappers)
    {
        this.imageRepository = imageRepository;
        this.mappers = mappers;
    }
}

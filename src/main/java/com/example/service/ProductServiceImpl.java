package com.example.service;

import com.example.entity.Image;
import com.example.entity.Product;
import com.example.entity.Subcategory;
import com.example.model.ImageModel;
import com.example.model.ProductModel;
import com.example.repository.ImageRepository;
import com.example.repository.ProductRepository;
import com.example.repository.SubcategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService
{
    private ProductRepository productRepository;
    private SubcategoryRepository subcategoryRepository;
    private ImageRepository imageRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(ProductRepository productRepository, SubcategoryRepository subcategoryRepository, ImageRepository imageRepository, Mappers mappers)
    {
        this.productRepository = productRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.imageRepository = imageRepository;
        this.mappers = mappers;
    }

    @Override
    public ProductModel getProductById(Long id)
    {
        Product p = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product id: "+id+ " does not exist"));
        ProductModel product = mappers.mapProductEntityToModel(p);
        List<ImageModel> images = p.getImages().stream().map(this::prepareImageModel).collect(Collectors.toList());
        product.setImages(images);

        return product;
    }

    public ImageModel prepareImageModel(Image i){
        ImageModel image = new ImageModel();
        image.setId(i.getId());
        image.setImage(i.generateBase64Image());
        return image;
    }

    @Override
    public List<ProductModel> getProductsBySubcategoryId(Long id)
    {
        return productRepository
                .findAll()
                .stream()
                .map(mappers::mapProductEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(Long id, ProductModel product)
    {
        logger.info("TUTAJ+ "+product.getImages().get(0).getImage());
        Subcategory s = subcategoryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Subcategory id: "+id+" does no exist"));
        Product p = mappers.mapProductModelToEntity(product);
//        List<Image> images = saveImages(product.getImages());
        List<Image> images = imageRepository.saveAll(product.getImages()
                .stream()
                .map(this::prepareImage)
                .collect(Collectors.toList()));
        logger.info(images.get(0).getId()+"");
        p.setImages(images);
        p.setSubcategory(s);
        productRepository.save(p);
        s.getProducts().add(p);
        subcategoryRepository.save(s);
        logger.info("Add product id: "+p.getId());
    }

    @Override
    public void editProduct(ProductModel product)
    {
        Product p = productRepository.findById(product.getId()).orElseThrow(
                ()->new RuntimeException("Product id: "+product.getId()+" does not exist"));
        p.setQuantity(product.getQuantity());
        p.setDescription(product.getDescription());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        productRepository.save(p);
        logger.info("Edit product id: "+p.getId());
    }

    @Override
    public void removeProduct(Long id)
    {
        productRepository.deleteById(id);
        logger.info("Remove product id: "+id);
    }

    @Override
    public Image saveImage(ImageModel image)
    {
        Image i = mappers.mapImageModelToEntity(image);
        i.setImageBase64(image.getImage());
        return imageRepository.save(i);
    }

    @Override
    public List<Image> saveImages(List<ImageModel> images)
    {
        List<Image> imgs = new ArrayList<>();
        for (int i = 0; i <images.size() ; i++)
        {
            Image img = mappers.mapImageModelToEntity(images.get(i));
            img.setImageBase64(images.get(i).getImage());
        }
        return imageRepository.saveAll(imgs);
    }

    public Image prepareImage(ImageModel i){
        Image image = new Image();
        image.setImageBase64(i.getImage());
        return image;
    }
}

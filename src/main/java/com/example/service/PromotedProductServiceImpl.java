package com.example.service;

import com.example.entity.Image;
import com.example.entity.Product;
import com.example.entity.PromotedProduct;
import com.example.model.ImageModel;
import com.example.model.PromotedProductModel;
import com.example.repository.ProductRepository;
import com.example.repository.PromotedProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotedProductServiceImpl implements PromotedProductService
{
    private PromotedProductRepository promotedProductRepository;
    private ProductRepository productRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PromotedProductServiceImpl(PromotedProductRepository promotedProductRepository, ProductRepository productRepository, Mappers mappers)
    {
        this.promotedProductRepository = promotedProductRepository;
        this.productRepository = productRepository;
        this.mappers = mappers;
    }

    @Override
    public void addPromotedProduct(Long id)
    {
        Product p = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product id: "+id+" does not exist" ));
        PromotedProduct pp = new PromotedProduct();
        pp.setProduct(p);
        promotedProductRepository.save(pp);
        logger.info("addPromotedProduct id: "+pp.getProduct().getId());
    }

    @Override
    public void removePromotedProduct(Long id)
    {
        promotedProductRepository.deleteById(id);
        logger.info("removePromotedProduct id: "+id);
    }

    @Override
    public void deleteById(long id)
    {
        promotedProductRepository.deleteById(id);
        logger.info("deleteById id: "+id);
    }

    @Override
    public List<PromotedProductModel> getAll()
    {
        return promotedProductRepository
                .findAll()
                .stream()
                .map(this::preparePromotedProductModel).collect(Collectors.toList());
    }

    private PromotedProductModel preparePromotedProductModel(PromotedProduct pro){
        PromotedProductModel pp = mappers.mapPromotedProductEntityToModel(pro);
        List<ImageModel> images = pro.getProduct().getImages().stream().map(this::prepareImageModel).collect(Collectors.toList());
        pp.getProduct().setImages(images);
        return pp;
    }

    private ImageModel prepareImageModel(Image i){
        ImageModel image = new ImageModel();
        image.setId(i.getId());
        image.setImage(i.generateBase64Image());
        return image;
    }
}

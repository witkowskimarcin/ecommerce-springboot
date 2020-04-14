package com.example.service;

import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.model.*;
import com.example.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService, OpportunityService, PromotedProductService, ImageService
{
    private ProductRepository productRepository;
    private SubcategoryRepository subcategoryRepository;
    private ImageRepository imageRepository;
    private OpportunityRepository opportunityRepository;
    private PromotedProductRepository promotedProductRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(ProductRepository productRepository, SubcategoryRepository subcategoryRepository, ImageRepository imageRepository, OpportunityRepository opportunityRepository, PromotedProductRepository promotedProductRepository, Mappers mappers)
    {
        this.productRepository = productRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.imageRepository = imageRepository;
        this.opportunityRepository = opportunityRepository;
        this.promotedProductRepository = promotedProductRepository;
        this.mappers = mappers;
    }

    @Override
    public ProductModel getProductById(Long id)
    {
        Product p = productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Product id: "+id+ " does not exist"));
        ProductModel product = mappers.mapProductEntityToModel(p);
        List<ImageModel> images = p.getImages().stream().map(this::prepareImageModel).collect(Collectors.toList());
        product.setImages(images);

        return product;
    }

    public ProductModel prepareProductModel(Product p){
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
        List<Product> products = productRepository
                .findAllBySubcategory(subcategoryRepository.findById(id).orElseThrow(
                        ()->new ResourceNotFoundException("Subcategory id: "+id+" does no exist")));
        return products
                .stream()
                .map(this::prepareProductModel)
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(Long id, ProductModel product)
    {
        Subcategory s = subcategoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Subcategory id: "+id+" does no exist"));
        Product p = mappers.mapProductModelToEntity(product);
        List<Image> images = imageRepository.saveAll(product.getImages()
                .stream()
                .map(this::prepareImage)
                .collect(Collectors.toList()));
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
                ()->new ResourceNotFoundException("Product id: "+product.getId()+" does not exist"));
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

    @Override
    public CategoryModel getCategoryOfProduct(Long id)
    {
        return mappers
                .mapCategoryEntityToModel(productRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product id: "+id+" does not exist"))
                .getSubcategory()
                .getCategory());
    }

    @Override
    public SubcategoryModel getSubcategoryOfProduct(Long id)
    {
        return mappers
                .mapSubcategoryEntityToModel(productRepository
                        .findById(id)
                        .orElseThrow(()->new ResourceNotFoundException("Product id: "+id+" does not exist"))
                        .getSubcategory());
    }

    public Image prepareImage(ImageModel i){
        Image image = new Image();
        image.setImageBase64(i.getImage());
        return image;
    }

    @Override
    public OpportunityModel getOpportunity()
    {
        List<Opportunity> opps = opportunityRepository.findAll();
        if (opps.size()<=0) throw new ResourceNotFoundException("Opportunity does not exist");
        OpportunityModel o = mappers.mapOpportunityEntityToModel(opps.get(0));
        o.setProduct(prepareProductModel(opps.get(0).getProduct()));
        return o;
    }

    @Override
    public void setOpportunity(OpportunityModel opportunity)
    {
        opportunityRepository.deleteAll();
        opportunityRepository.save(mappers.mapOpportunityModelToEntity(opportunity));
        logger.info("Set opportunity");
    }

    @Override
    public void unsetOpportunity()
    {
        opportunityRepository.deleteAll();
        logger.info("Unset opportunity");
    }

    @Override
    public void addPromotedProduct(Long id)
    {
        Product p = productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Product id: "+id+" does not exist" ));
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
    public List<PromotedProductModel> getAllPromotedProducts()
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
}

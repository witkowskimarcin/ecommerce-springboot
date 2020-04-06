package com.example.service;

import com.example.model.ProductModel;
import com.example.model.PromotedProductModel;
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
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PromotedProductServiceImpl(PromotedProductRepository promotedProductRepository, Mappers mappers)
    {
        this.promotedProductRepository = promotedProductRepository;
        this.mappers = mappers;
    }

    @Override
    public void addPromotedProduct(PromotedProductModel pp)
    {
        promotedProductRepository.save(mappers.mapPromotedProductModelToEntity(pp));
        logger.info("addPromotedProduct");
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
                .map(mappers::mapPromotedProductEntityToModel).collect(Collectors.toList());
    }
}

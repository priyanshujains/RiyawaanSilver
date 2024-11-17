package com.riyawansilver.services;

import com.riyawansilver.Exceptions.ProductNotFoundException;
import com.riyawansilver.dtos.GetProductDto;
import com.riyawansilver.dtos.ProductDto;
import com.riyawansilver.models.Category;
import com.riyawansilver.models.Products;
import com.riyawansilver.repositories.ProductRepo;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo){
        this.productRepo=productRepo;
    }

    public ProductDto addProduct(ProductDto productDto , MultipartFile imageFile) throws IOException {

        Products products=convertDtoToProduct(productDto);


        products.setImageName(imageFile.getName());
        products.setImageType(imageFile.getContentType());
        products.setImageData(imageFile.getBytes());
        Products product=productRepo.save(products);

        return convertProductToDto(product);

    }

    public List<ProductDto> getAllProducts() {
        List<Products> productsList=productRepo.findAll();
        List<ProductDto> productDtoList=new ArrayList<>();

        for(Products p:productsList){
            productDtoList.add(convertProductToDto(p));
        }
        return productDtoList;
    }

    public Products convertDtoToProduct(ProductDto productDto){
        Products products=new Products();

        products.setName(productDto.getName());
        products.setDescription(productDto.getDescription());
        products.setPrice(productDto.getPrice());
        products.setCategory(new Category());
        products.getCategory().setName("garlic");
        products.setQuantity(productDto.getQuantity());

        return products;
    }

    public ProductDto convertProductToDto(Products products){
        ProductDto productDto=new ProductDto();

        productDto.setName(products.getName());
        productDto.setDescription(products.getDescription());
        productDto.setPrice(products.getPrice());
        productDto.setAvailable(products.isAvailable());
        productDto.setCategory(products.getCategory().getName());
        productDto.setQuantity(products.getQuantity());
        productDto.setId(products.getId());

        return productDto;

    }


    public ProductDto getSingleProduct(long id) throws ProductNotFoundException{
        Optional<Products> optionalProduct=productRepo.findById(id);

        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id:"+id+"dose not exist");
        }

            return convertProductToDto(optionalProduct.get());
    }

    public Products ImageData(long productId) throws ProductNotFoundException {
        Optional<Products> products=productRepo.findById(productId);

        if(products.isEmpty()){throw new ProductNotFoundException("Product with id:"+productId+"dose not exist");}

       return products.get();

    }

    public ProductDto updateproduct(Long id, ProductDto productDto, MultipartFile imageFile) throws IOException {
        Optional<Products> optionalProducts=productRepo.findById(id);
        if(optionalProducts.isEmpty())throw new RuntimeException("product not exist");
        Products product=optionalProducts.get();
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setAvailable(productDto.isAvailable());
        Products products=productRepo.save(product);

            return convertProductToDto(products);
    }

    public void delete(Long id) {
        Optional<Products> product=productRepo.findById(id);
        if(product.isEmpty())throw new RuntimeException("Product not exist");

        productRepo.delete(product.get());
    }
}


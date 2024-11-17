package com.riyawansilver.controllers;

import com.riyawansilver.Exceptions.ProductNotFoundException;
import com.riyawansilver.dtos.GetProductDto;
import com.riyawansilver.dtos.ProductDto;
import com.riyawansilver.models.Products;
import com.riyawansilver.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private ProductService productService;

    @Autowired
    public AdminController(ProductService productService){
        this.productService=productService;
    }
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestPart("productDto") ProductDto productDto, @RequestPart() MultipartFile imageFile)
                                                                        throws IOException    {

            return new ResponseEntity<>(productService.addProduct(productDto, imageFile), HttpStatus.OK);


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestPart("productDto") ProductDto productDto,
                                           @RequestPart() MultipartFile imageFile)
                                            throws IOException    {
        return  new ResponseEntity<>(productService.updateproduct(id,productDto,imageFile),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productService.delete(id);
       return new ResponseEntity<>(HttpStatus.OK);

    }

}

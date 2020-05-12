package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.service.ProductService;
import org.gkk.bioshopapp.web.api.model.product.ProductDetailsResponseModel;
import org.gkk.bioshopapp.web.api.model.product.ProductDiscountTableResponseModel;
import org.gkk.bioshopapp.web.api.model.product.ProductEditResponseModel;
import org.gkk.bioshopapp.web.api.model.product.ProductTableResponceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductApiController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductTableResponceModel>> getAllProducts() {
        List<ProductTableResponceModel> products = this.productService.getProductTable()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTableResponceModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product-table")
    public ResponseEntity<List<ProductTableResponceModel>> getProductTable() {
        List<ProductTableResponceModel> products = this.productService.getProductTable()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTableResponceModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/promotion-table")
    public ResponseEntity<List<ProductDiscountTableResponseModel>> getPromotionalProductTable() {
        List<ProductDiscountTableResponseModel> products =
                this.productService.getDiscountedProducts(LocalDateTime.now())
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductDiscountTableResponseModel.class))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ProductDetailsResponseModel> detailsProduct(@PathVariable String id) {
        ProductDetailsResponseModel product =
                this.modelMapper.map(this.productService.getProductDetailsModel(id), ProductDetailsResponseModel.class);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ProductEditResponseModel> editProduct(@PathVariable String id) {
        ProductEditResponseModel product =
                this.modelMapper.map(this.productService.getProductEditModelById(id), ProductEditResponseModel.class);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}

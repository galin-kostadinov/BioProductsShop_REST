package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.model.product.ProductDiscountTableServiceModel;
import org.gkk.bioshopapp.service.service.ProductService;
import org.gkk.bioshopapp.web.api.model.ProductTableResponceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
        List<ProductTableResponceModel> products= this.productService.getProductTable()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTableResponceModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product-table")
    public ResponseEntity<List<ProductTableResponceModel>> getProductTable() {
        List<ProductTableResponceModel> products= this.productService.getProductTable()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTableResponceModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/promotion-table")
    public ResponseEntity<List<ProductDiscountTableServiceModel>> getPromotionalProductTable(ModelAndView model) {
        List<ProductDiscountTableServiceModel> products = this.productService.getDiscountedProducts(LocalDateTime.now());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

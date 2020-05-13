package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.model.product.ProductCreateServiceModel;
import org.gkk.bioshopapp.service.model.product.ProductEditServiceModel;
import org.gkk.bioshopapp.service.service.ProductService;
import org.gkk.bioshopapp.web.api.model.product.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductTableResponceModel>> getAllProducts() {
        List<ProductTableResponceModel> products = this.productService.getProductTable()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTableResponceModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductTableResponceModel>> getProductTable() {
        List<ProductTableResponceModel> products = this.productService.getProductTable()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTableResponceModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/promotion-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductDiscountTableResponseModel>> getPromotionalProductTable() {
        List<ProductDiscountTableResponseModel> products =
                this.productService.getDiscountedProducts(LocalDateTime.now())
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductDiscountTableResponseModel.class))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductDetailsResponseModel> detailsProduct(@PathVariable String id) {
        ProductDetailsResponseModel product =
                this.modelMapper.map(this.productService.getProductDetailsModel(id), ProductDetailsResponseModel.class);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductEditResponseModel> editProduct(@PathVariable String id) {
        ProductEditResponseModel product =
                this.modelMapper.map(this.productService.getProductEditModelById(id), ProductEditResponseModel.class);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void editProductConfirm(@PathVariable String id, ProductEditRequestModel model, HttpServletResponse response) throws IOException {
        this.productService.editProduct(id, this.modelMapper.map(model, ProductEditServiceModel.class));

        response.sendRedirect("/product/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductEditResponseModel> deleteProduct(@PathVariable String id) {
        ProductEditResponseModel product =
                this.modelMapper.map(this.productService.getProductEditModelById(id), ProductEditResponseModel.class);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProductConfirm(@PathVariable String id, HttpServletResponse response) throws IOException {
        this.productService.deleteProduct(id);

        response.sendRedirect("/product/product-table");
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void create(ProductCreateRequestModel model, BindingResult bindingResult, HttpSession session,
                       HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            response.sendRedirect("/product/create-product");
        }

        ProductCreateServiceModel serviceModel = this.modelMapper.map(model, ProductCreateServiceModel.class);

        try {
            String username = session.getAttribute("username").toString();
            this.productService.create(serviceModel, username);

            response.sendRedirect("/product");
        } catch (Exception e) {
            response.sendRedirect("/product/create-product");
        }
    }
}

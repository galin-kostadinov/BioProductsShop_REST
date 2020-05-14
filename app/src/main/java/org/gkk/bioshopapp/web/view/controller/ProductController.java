package org.gkk.bioshopapp.web.view.controller;

import org.gkk.bioshopapp.web.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @GetMapping({"/", ""})
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Products")
    public ModelAndView getAllProducts(ModelAndView model) {
        return super.view("product/products", model);
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Create Product")
    public ModelAndView getCreateForm(ModelAndView model) {
        return super.view("product/create-product", model);
    }

    @GetMapping("/product-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Product Table")
    public ModelAndView getProductTable(ModelAndView model) {
        return super.view("product/product-table", model);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Product Details")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView model) {
        model.addObject("productId", id);
        return super.view("product/details-product", model);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit Product")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView model) {
        model.addObject("productId", id);
        return super.view("product/edit-product", model);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Delete Product")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView model) {
        model.addObject("productId", id);
        return super.view("product/delete-product", model);
    }

    @GetMapping("/promote/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Promote Product")
    public ModelAndView getPromoteForm(@PathVariable String id, ModelAndView model) {
        model.addObject("productId", id);
        return super.view("product/promote-form", model);
    }

    @GetMapping("/promotion-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Promotional Table")
    public ModelAndView getPromotionalProductTable(ModelAndView model) {
        return super.view("product/promotion-table", model);
    }
}

package com.test.rajesh.demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbandala on 7/14/2015.
 */
public class Catalog {
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("ProductList")
    @Expose
    private List<Product> Products;

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The -id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The Product
     */
    public List<Product> getProduct() {
        return Products;
    }

    /**
     *
     * @param Product
     * The Product
     */
    public void setProduct(List<Product> Product) {
        this.Products = Product;
    }
}

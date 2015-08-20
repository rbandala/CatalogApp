package com.test.rajesh.demo.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by rbandala on 8/20/2015.
 */
public class Updates {


    @Expose
    private String CityCode;
    @Expose
    private String SupplierID;
    @Expose
    private List<Product> ProductList;

    /**
     *
     * @return
     * The CityCode
     */
    public String getCityCode() {
        return CityCode;
    }

    /**
     *
     * @param CityCode
     * The CityCode
     */
    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    /**
     *
     * @return
     * The SupplierID
     */
    public String getSupplierID() {
        return SupplierID;
    }

    /**
     *
     * @param SupplierID
     * The SupplierID
     */
    public void setSupplierID(String SupplierID) {
        this.SupplierID = SupplierID;
    }

    /**
     *
     * @return
     * The catalogModel
     */
    public List<Product> getCatalogModel() {
        return ProductList;
    }

    /**
     *
     * @param productList
     * The catalogModel
     */
    public void setCatalogModel(List<Product> productList) {
       this.ProductList = productList;
    }
}

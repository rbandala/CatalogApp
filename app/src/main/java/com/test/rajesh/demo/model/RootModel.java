package com.test.rajesh.demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbandala on 7/14/2015.
 */
public class RootModel {
    @SerializedName("CatalogList")
    @Expose
    private List<Catalog> Catalog = new ArrayList<Catalog>();

    /**
     *
     * @return
     * The Catalog
     */
    public List<Catalog> getCatalog() {
        return Catalog;
    }

    /**
     *
     * @param Catalog
     * The Catalog
     */
    public void setCatalog(List<Catalog> Catalog) {
        this.Catalog = Catalog;
    }
}

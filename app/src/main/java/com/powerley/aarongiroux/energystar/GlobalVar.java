package com.powerley.aarongiroux.energystar;

import java.util.ArrayList;
import android.app.Application;

public class GlobalVar extends Application{
    private static ArrayList <Product> productList;

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public static void setProductList(ArrayList<Product> someProducts) {
        productList = someProducts;
    }
}


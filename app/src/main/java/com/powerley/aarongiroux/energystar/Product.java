package com.powerley.aarongiroux.energystar;

import java.util.ArrayList;

public class Product {
    private int pd_id, airflow_Low, product_size, airflow_med, airflow_high;
    private String brand_name, model_name, product_type, motor_warranty;
    private String model_number, markets, additionalInfo;
    private ArrayList<Product> productArrayList = new ArrayList<Product>();

    public Product(){

    }

    public int getProductId() {
        return pd_id;
    }

    public void setProductId(int pd_id) {
        this.pd_id = pd_id;
    }

    public String getModelNumber() {
        return model_number;
    }

    public void setModelNumber(String model_number) {
        this.model_number = model_number;
    }

    public String getBrandName() {
        return brand_name;
    }

    public void setBrandName(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModelName() {
        return model_name;
    }

    public void setModelName(String model_name) {
        this.model_name = model_name;
    }

    public String getProductType() {
        return product_type;
    }

    public void setProductType(String product_type) {
        this.product_type = product_type;
    }

    public int getProductSize() {
        return product_size;
    }

    public void setProductSize(int product_size) {
        this.product_size = product_size;
    }

    public int getAirflowMed() {
        return airflow_med;
    }

    public void setAirflowMed(int airflow_med) {
        this.airflow_med = airflow_med;
    }

    public int getAirflowHigh() {
        return airflow_high;
    }

    public void setAirflowHigh(int airflow_high) {
        this.airflow_high = airflow_high;
    }

    public int getAirflowLow() {
        return airflow_Low;
    }

    public void setAirflowLow(int airflow_Low) {
        this.airflow_Low = airflow_Low;
    }

    public void setProductList(ArrayList<Product> products){
        productArrayList = products;
    }

    public String getMotorWarranty() {
        return motor_warranty;
    }

    public void setMotorWarranty(String motor_warranty) {
        this.motor_warranty = motor_warranty;
    }

    public String getMarkets() {
        return markets;
    }

    public void setMarkets(String markets) {
        this.markets = markets;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public ArrayList<Product> getProductList(){
        return productArrayList;
    }
}


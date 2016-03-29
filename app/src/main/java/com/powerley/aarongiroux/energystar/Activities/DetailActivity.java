package com.powerley.aarongiroux.energystar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.powerley.aarongiroux.energystarps.R;

public class DetailActivity extends AppCompatActivity {

    private TextView modelNumber, brandName, modelName, productSize, motorWarranty, markets;
    private TextView airflowLow, airflowMed, airflowHigh, productType, additionalInfo;
    private String brandNameTxt, modelNameTxt, productTypeTxt, motorWarrantyTxt;
    private String modelNumberTxt, marketsTxt;
    private int airflowLowValue, airflowMedValue, airflowHighValue, productSizeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);

        // grab all values passed from previous activity and store in variables
        Intent intent = getIntent();
        if (null != intent) {
            modelNumberTxt = intent.getStringExtra("Model Number");
            brandNameTxt = intent.getStringExtra("Brand Name");
            modelNameTxt = intent.getStringExtra("Model Name");
            productTypeTxt = intent.getStringExtra("Product Type");
            productSizeValue = intent.getIntExtra("Product Size", 0);
            airflowLowValue = intent.getIntExtra("Airflow Low", 0);
            airflowMedValue = intent.getIntExtra("Airflow Med", 0);
            airflowHighValue = intent.getIntExtra("Airflow High", 0);
            motorWarrantyTxt = intent.getStringExtra("Motor Warranty");
            marketsTxt = intent.getStringExtra("Markets");
        }

        modelNumber = (TextView)findViewById(R.id.modelNumber);
        brandName = (TextView)findViewById(R.id.brandName);
        modelName = (TextView)findViewById(R.id.modelName);
        productType = (TextView)findViewById(R.id.productType);
        productSize = (TextView)findViewById(R.id.productSize);
        airflowLow = (TextView)findViewById(R.id.airflowLow);
        airflowMed = (TextView)findViewById(R.id.airflowMed);
        airflowHigh = (TextView)findViewById(R.id.airflowHigh);
        motorWarranty = (TextView)findViewById(R.id.motorWarranty);
        markets = (TextView)findViewById(R.id.markets);

        modelNumber.setText(modelNumberTxt);
        brandName.setText(brandNameTxt);
        modelName.setText(modelNameTxt);
        productType.setText(productTypeTxt);
        productSize.setText("" + productSizeValue);
        airflowLow.setText("" + airflowLowValue);
        airflowMed.setText("" + airflowMedValue);
        airflowHigh.setText("" + airflowHighValue);
        motorWarranty.setText(motorWarrantyTxt);
        markets.setText(marketsTxt);
    }
}

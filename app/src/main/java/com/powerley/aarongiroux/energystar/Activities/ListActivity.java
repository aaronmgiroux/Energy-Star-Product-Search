package com.powerley.aarongiroux.energystar.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.powerley.aarongiroux.energystar.GlobalVar;
import com.powerley.aarongiroux.energystar.Product;
import com.powerley.aarongiroux.energystar.ProductAdapter;
import com.powerley.aarongiroux.energystarps.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ArrayList<Product> searchResults = new ArrayList<Product>(GlobalVar.getProductList());
    private ProductAdapter arrayAdapter;
    private EditText searchEntryText;
    private Button searchBtn, sortBtn;
    private Boolean sortAsc = true;
    private ListView searchList;
    private Product selectedProduct;
    private int selectedProductAirflowLow, selectedProductSize;
    private int selectedProductAirflowMed, selectedProductAirflowHigh;
    private String selectedProductBrandName, selectedProductModelName, searchString, selectedMarkets;
    private String selectedModelNumber, selectedProductType, selectedMotorWarranty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);

        searchEntryText = (EditText)findViewById(R.id.searchEntryText);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        sortBtn = (Button)findViewById(R.id.sortBtn);
        searchList = (ListView)findViewById(R.id.searchList);

        arrayAdapter = new ProductAdapter(ListActivity.this, R.layout.list_product, searchResults);
        searchList.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            searchString = searchEntryText.getText().toString();
            AsyncCallSearch task = new AsyncCallSearch(ListActivity.this);
            task.execute();
            }
        });

        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortAsc) {
                    sortAsc = false;
                    sortBtn.setText("Dsc");
                } else {
                    sortAsc = true;
                    sortBtn.setText("Asc");
                }
                callSort(sortAsc);
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                selectedProduct = (Product) (searchList.getItemAtPosition(myItemInt));
                selectedModelNumber = selectedProduct.getModelNumber();
                selectedProductBrandName = selectedProduct.getBrandName();
                selectedProductModelName = selectedProduct.getModelName();
                selectedProductType = selectedProduct.getProductType();
                selectedProductSize = selectedProduct.getProductSize();
                selectedProductAirflowLow = selectedProduct.getAirflowLow();
                selectedProductAirflowMed = selectedProduct.getAirflowMed();
                selectedProductAirflowHigh = selectedProduct.getAirflowHigh();
                selectedMotorWarranty = selectedProduct.getMotorWarranty();
                selectedMarkets = selectedProduct.getMarkets();

                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("Model Number", selectedModelNumber);
                intent.putExtra("Brand Name", selectedProductBrandName);
                intent.putExtra("Model Name", selectedProductModelName);
                intent.putExtra("Product Type", selectedProductType);
                intent.putExtra("Product Size", selectedProductSize);
                intent.putExtra("Airflow Low", selectedProductAirflowLow);
                intent.putExtra("Airflow Med", selectedProductAirflowMed);
                intent.putExtra("Airflow High", selectedProductAirflowHigh);
                intent.putExtra("Motor Warranty", selectedMotorWarranty);
                intent.putExtra("Markets", selectedMarkets);
                startActivity(intent);
            }
        });
    }

    public void search(ArrayList<Product> list, String searchString) {
        searchResults.clear();
        if(searchString.trim().equalsIgnoreCase("")){
            for(Iterator<Product> it = list.iterator(); it.hasNext() ;) {
                Product p = it.next();
                searchResults.add(p);
            }
        }
        else{
            for(Iterator<Product> it = list.iterator(); it.hasNext() ;) {
                Product p = it.next();
                if(p.getModelNumber().toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if(p.getBrandName().toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if(p.getModelName().toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if (p.getProductType().toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if (Integer.toString(p.getProductSize()).toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if (Integer.toString(p.getAirflowLow()).toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if (Integer.toString(p.getAirflowMed()).toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if (Integer.toString(p.getAirflowHigh()).toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if (p.getMotorWarranty().toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
                if (p.getMarkets().toLowerCase().contains(searchString.toLowerCase())) {searchResults.add(p);}
            }
        }
    }

    public void callSort(Boolean isAscending){
        if(isAscending){
            Collections.sort(searchResults, new sortAscending());
        }
        else{
            Collections.sort(searchResults, new sortDescending());
        }

        arrayAdapter = new ProductAdapter(ListActivity.this, R.layout.list_product, searchResults);
        searchList.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    public class sortAscending implements Comparator<Product>
    {
        public int compare(Product left, Product right) {
            return left.getAirflowLow() - right.getAirflowLow();
        }
    }

    public class sortDescending implements Comparator<Product>
    {
        public int compare(Product left, Product right) {
            return  right.getAirflowLow() - left.getAirflowLow();
        }
    }

    public class AsyncCallSearch extends AsyncTask<String, Void, Void> {
        private ProgressDialog dialog;
        private ListActivity activity;
        private Context context;

        public AsyncCallSearch(ListActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected Void doInBackground(String... params) {
            search(GlobalVar.getProductList(), searchString.trim().toLowerCase());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            arrayAdapter = new ProductAdapter(ListActivity.this, R.layout.list_product, searchResults);
            searchList.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setTitle("Product Search");
            this.dialog.setMessage("Searching...");
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
        }
    }
}

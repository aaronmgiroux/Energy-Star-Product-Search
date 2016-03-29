package com.powerley.aarongiroux.energystar.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.powerley.aarongiroux.energystar.GlobalVar;
import com.powerley.aarongiroux.energystar.Product;
import com.powerley.aarongiroux.energystarps.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = "WEB SERVICE";
    private int statusCode = 200;
    private final String URL = "http://data.energystar.gov/resource/mj8j-2jhz.json";
    private Button productSearchBtn;
    private Product product;
    HttpResponse httpResponse;
    private ArrayList<Product> products = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);

        productSearchBtn = (Button)findViewById(R.id.productSearchBtn);

        productSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create instance for AsyncCallWS to run tasks in background
                AsyncCallGetProducts task = new AsyncCallGetProducts(MainActivity.this);
                task.execute();
            }
        });

    }

    public class AsyncCallGetProducts extends AsyncTask<String, Void, Void> {
        private ProgressDialog dialog;
        private MainActivity activity;
        private Context context;

        public AsyncCallGetProducts(MainActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");

            InputStream inputStream = null;
            String result = "";

            try {
                HttpClient httpclient = new DefaultHttpClient();
                httpResponse = httpclient.execute(new HttpGet(URL));
                HttpEntity resultEntity = httpResponse.getEntity();
                result = (EntityUtils.toString(resultEntity));

            } catch (Exception e) {
                Log.d("Result", e.getLocalizedMessage());
            }

            try {
                JSONArray json = new JSONArray(result);

                for(int i=0;i<json.length();i++){
                    JSONObject obj = json.getJSONObject(i);
                    product = new Product();
                    product.setProductId(obj.getInt("pd_id"));
                    product.setModelNumber(obj.getString("model_number"));
                    product.setBrandName(obj.getString("brand_name"));
                    product.setModelName(obj.getString("model_name"));
                    product.setProductType(obj.getString("product_type"));
                    product.setProductSize(obj.getInt("ceiling_fan_size_diameters_in_inches"));
                    product.setAirflowLow(obj.getInt("airflow_efficiency_cfm_watt_low"));
                    product.setAirflowMed(obj.getInt("airflow_efficiency_cfm_watt_medium"));
                    product.setAirflowHigh(obj.getInt("airflow_efficiency_cfm_watt_high"));
                    product.setMotorWarranty(obj.getString("ceiling_fan_motor_warranty_years"));
                    product.setMarkets(obj.getString("markets"));

                    products.add(product);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if(statusCode == 200){
                GlobalVar.setProductList(products);
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), ("Status: " + statusCode + " - Network Error"),
                        Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            this.dialog.setTitle("Product Search");
            this.dialog.setMessage("Loading...");
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

}

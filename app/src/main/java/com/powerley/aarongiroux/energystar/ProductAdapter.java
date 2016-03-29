package com.powerley.aarongiroux.energystar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerley.aarongiroux.energystarps.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    int resource;
    String response;
    Context context;
    TextView airflow, brandName, modelName, productType, productSize;

    public ProductAdapter(Context context, int resource, ArrayList<Product> items) {
        super(context, resource, items);
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout alertView;
        Product product = getItem(position);

        if(convertView==null)
        {
            alertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, alertView, true);
        }
        else
        {
            alertView = (LinearLayout) convertView;
        }

        brandName =(TextView)alertView.findViewById(R.id.brandName);
        modelName =(TextView)alertView.findViewById(R.id.modelName);
        productType =(TextView)alertView.findViewById(R.id.productType);
        productSize =(TextView)alertView.findViewById(R.id.productSize);
        airflow =(TextView)alertView.findViewById(R.id.airflow);

        brandName.setText(product.getBrandName() + ":");
        modelName.setText(product.getModelName());
        productType.setText(product.getProductType());
        productSize.setText("" + product.getProductSize());
        airflow.setText("" + product.getAirflowLow() + "/" + product.getAirflowMed() + "/" + product.getAirflowHigh());

        return alertView;
    }
}

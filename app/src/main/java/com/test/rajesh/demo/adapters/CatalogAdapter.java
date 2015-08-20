package com.test.rajesh.demo.adapters;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.rajesh.demo.R;
import com.test.rajesh.demo.model.Catalog;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rbandala on 7/14/2015.
 */
public class CatalogAdapter extends BaseAdapter{
    private Catalog result;
    private Context context;
    LayoutInflater mInflater;
    private boolean showGrid;
    public CatalogAdapter(Catalog catalog,Context mContext,boolean isGrid)
    {
        this.result = catalog;
        mInflater = LayoutInflater.from(mContext);
        context = mContext;
        showGrid = isGrid;
    }
    @Override
    public int getCount() {
        return result.getProduct().size();
    }

    @Override
    public Object getItem(int position) {
        return result.getProduct().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder mHolder;
        if(convertView == null) {
            if(showGrid) {
                view = mInflater.inflate(R.layout.list_item, parent, false);
            }
            else{
                view = mInflater.inflate(R.layout.list_iten_2, parent, false);
            }
            mHolder = new ViewHolder();

            mHolder.productName = (TextView)view.findViewById(R.id.txt_product_name);
            mHolder.costPrice = (TextView)view.findViewById(R.id.txt_cost_price);
            mHolder.sellingPrice = (TextView)view.findViewById(R.id.txt_selling_price);
            mHolder.isAvailable = (TextView)view.findViewById(R.id.txt_available);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }

        mHolder.productName.setText(result.getProduct().get(position).getName());
        mHolder.costPrice.setText("$ "+result.getProduct().get(position).getCostPrice());
        mHolder.sellingPrice.setText("$ "+result.getProduct().get(position).getSellingPrice());

        if(result.getProduct().get(position).getAvailable()) {
            mHolder.isAvailable.setText("Yes");
            mHolder.isAvailable.setTextColor(context.getResources().getColor(R.color.iaa_txt_green));
        }
        else{
            mHolder.isAvailable.setText("No");
            mHolder.isAvailable.setTextColor(context.getResources().getColor(R.color.iaa_red));
        }
        view.setTag(mHolder);

        return view;
    }
    static class ViewHolder {

        TextView productName;
        TextView costPrice;
        TextView sellingPrice;
        TextView isAvailable;

    }
}

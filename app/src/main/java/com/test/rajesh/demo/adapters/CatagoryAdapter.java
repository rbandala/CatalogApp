package com.test.rajesh.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.test.rajesh.demo.R;
import com.test.rajesh.demo.model.Catalog;
import com.test.rajesh.demo.model.RootModel;

/**
 * Created by rbandala on 7/14/2015.
 */
public class CatagoryAdapter extends BaseAdapter {
    private RootModel result;
    private Context context;
    LayoutInflater mInflater;
    public CatagoryAdapter(RootModel catalog,Context mContext)
    {
        this.result = catalog;
        mInflater = LayoutInflater.from(mContext);
        context = mContext;
    }
    @Override
    public int getCount() {
        return result.getCatalog().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder mHolder;
        if(convertView == null) {
            view = mInflater.inflate(R.layout.spinner_lineitem, parent, false);
            mHolder = new ViewHolder();

            mHolder.catagoryName = (TextView)view.findViewById(R.id.txt_spinner_line);

        } else {
            mHolder = (ViewHolder) view.getTag();
        }

        mHolder.catagoryName.setText(result.getCatalog().get(i).getId());

        view.setTag(mHolder);

        return view;
    }
    static class ViewHolder {

        TextView catagoryName;


    }
}

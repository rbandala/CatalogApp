package com.test.rajesh.demo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.test.rajesh.demo.adapters.CatagoryAdapter;
import com.test.rajesh.demo.adapters.CatalogAdapter;
import com.test.rajesh.demo.model.Catalog;
import com.test.rajesh.demo.model.Product;
import com.test.rajesh.demo.model.RootModel;
import com.test.rajesh.demo.model.Updates;
import com.test.rajesh.demo.uitls.Constants;
import com.test.rajesh.demo.uitls.DropboxUtils;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends ActionBarActivity {

    private static AsyncHttpClient httpClient = new AsyncHttpClient();
    private String url = "http://fresheshop.azurewebsites.net/service.svc/GetCityBasedCatalog/CityCode/%1$s/?SupplierID=%2$s";
    private String postUrl = "http://fresheshop.azurewebsites.net/service.svc/PostCityBasedCatalog";
    private RootModel rootModel;
    private GridView resultGrid;
    private Spinner spnCatagories;
    private Catalog catalog ;
    private CatalogAdapter catalogAdapter;
    private boolean isgrid;
    private  DropboxUtils dropboxUtils;

    private Button saveFile;
    private Updates returnList;
    public  String cityCode;
    private  String supplierId;
    private List<Product> finalProductList;
    private Updates finalUpdate;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        context = this;
        cityCode = intent.getStringExtra(Constants.CITY_CODE);
        supplierId = intent.getStringExtra(Constants.SUPPLIER_CODE);
        url = String.format(url, cityCode, supplierId);
        rootModel = new RootModel();
        finalProductList = new ArrayList<Product>();
        resultGrid = (GridView)findViewById(R.id.grd_products);
        saveFile = (Button)findViewById(R.id.btn_save_file);
        saveFile.setBackgroundColor(getResources().getColor(R.color.iaa_but_green_disable));
        saveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        spnCatagories = (Spinner)findViewById(R.id.ddl_items);
        isgrid = true;
        spnCatagories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(rootModel != null){

                    catalog = rootModel.getCatalog().get(i);
                    catalogAdapter = new CatalogAdapter(catalog,MainActivity.this,isgrid);
                    resultGrid.setAdapter(catalogAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



       resultGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               showEditPopup(catalog.getProduct().get(i), i);
           }
       });
        getResults(new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                rootModel = gson.fromJson(responseString, RootModel.class);
                CatagoryAdapter catagoryAdapter = new CatagoryAdapter(rootModel, MainActivity.this);
                spnCatagories.setAdapter(catagoryAdapter);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                rootModel = gson.fromJson(response.toString(), RootModel.class);
                CatagoryAdapter catagoryAdapter = new CatagoryAdapter(rootModel, MainActivity.this);
                spnCatagories.setAdapter(catagoryAdapter);
            }
        });
    }

    private void bindData(String jsonString){
        Gson gson = new Gson();
        rootModel = gson.fromJson(jsonString, RootModel.class);
        CatagoryAdapter catagoryAdapter = new CatagoryAdapter(rootModel, MainActivity.this);
        spnCatagories.setAdapter(catagoryAdapter);
    }

    private void saveData(){
        finalUpdate = new Updates();
        finalUpdate.setCityCode(cityCode);
        finalUpdate.setSupplierID(supplierId);
        finalUpdate.setCatalogModel(finalProductList);

        try {
            postUpdates(new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    finalProductList.clear();
                    saveFile.setBackgroundColor(getResources().getColor(R.color.iaa_but_green_disable));
                    Toast.makeText(context, "Data Saved to server", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context, "Error Occured try to save again", Toast.LENGTH_LONG).show();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final int REQUEST_LINK_TO_DBX = 0;
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == this.RESULT_OK) {
                String jsonString;
                try {
                    jsonString = dropboxUtils.getDatafileContents();
                    bindData(jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // ... Link failed or was cancelled by the user.
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showEditPopup(final Product product, final int index){
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.update_popup);
        dialog.setTitle("Update SalePrice");
        TextView popupProduct = (TextView)dialog.findViewById(R.id.txt_popup_product_name);
        TextView popupCostPrice = (TextView)dialog.findViewById(R.id.txt_popup_cost_price);
        final EditText popupSalePrice = (EditText)dialog.findViewById(R.id.edt_sale_price);
        final CheckBox popupIsAvailable = (CheckBox)dialog.findViewById(R.id.chk_available);

        popupProduct.setText(product.getName());
        popupCostPrice.setText(product.getCostPrice());
        popupSalePrice.setText(product.getSellingPrice());
        popupSalePrice.setSelection(popupSalePrice.getText().length());
        popupSalePrice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                boolean isValid=popupSalePrice.getText().toString().matches("\\d{10}\\.\\d{2}");
                return isValid;
            }
        });
        popupIsAvailable.setChecked(product.getAvailable());

        Button btnSave=(Button)dialog.findViewById(R.id.save);
        Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setSellingPrice(popupSalePrice.getText().toString());
                product.setAvailable(popupIsAvailable.isChecked());
                List<Product> productList = catalog.getProduct();
                productList.set(index, product);
                finalProductList.add(product);
                catalog.setProduct(productList);
                catalogAdapter.notifyDataSetChanged();
                resultGrid.setAdapter(catalogAdapter);
                saveFile.setBackgroundColor(getResources().getColor(R.color.iaa_but_green));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_grid) {
            resultGrid.invalidateViews();
            resultGrid.setNumColumns(2);
            isgrid = true;
            catalogAdapter = new CatalogAdapter(catalog,MainActivity.this,isgrid);
            resultGrid.setAdapter(catalogAdapter);
        }
        if (id == R.id.action_list) {
            resultGrid.invalidateViews();
            resultGrid.setNumColumns(1);
            isgrid = false;
            catalogAdapter = new CatalogAdapter(catalog,MainActivity.this,isgrid);
            resultGrid.setAdapter(catalogAdapter);
        }

        return super.onOptionsItemSelected(item);
    }
    private   void getResults(AsyncHttpResponseHandler responseHandler){
        httpClient.get(this,url,responseHandler);
    }
    private   void postUpdates(AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        StringEntity entity = new StringEntity(gson.toJson(finalUpdate));
        httpClient.post(this,postUrl,entity,"application/json",responseHandler);
    }

}

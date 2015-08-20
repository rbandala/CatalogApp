package com.test.rajesh.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.rajesh.demo.uitls.Constants;


public class LoginActivity extends ActionBarActivity {
    private EditText cityCode;
    private EditText supplierID;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context context = this;
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                supplierID = (EditText)findViewById(R.id.edt_supplier_id);
                cityCode = (EditText)findViewById(R.id.edt_cty_code);
                if(TextUtils.isEmpty(supplierID.getText()) || TextUtils.isEmpty(cityCode.getText()))
                {
                    Toast.makeText(context,"Enter CityCode and Supplier ID",Toast.LENGTH_LONG).show();

                }
                else{
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Constants.CITY_CODE, cityCode.getText().toString());
                    intent.putExtra(Constants.SUPPLIER_CODE, supplierID.getText().toString());


                    startActivity(intent);
                    finish();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

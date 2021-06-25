package com.example.mygrocerystore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.models.NewProductsDetailedModel;

import java.io.Serializable;

public class ProductPayment extends AppCompatActivity {
    Toolbar toolbar;
    TextView subTotal,discount,shipping,total;
    Button payBtn;
    int amount;
    NewProductsDetailedModel newProductsDetailedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_payment);

        newProductsDetailedModel = (NewProductsDetailedModel) getIntent().getSerializableExtra("product");
        amount = getIntent().getIntExtra("amount", 0);

        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view ->{
            finish();
        });

        payBtn = findViewById(R.id.pay_btn);


        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductPayment.this,ProductPlaceOrder.class);
                intent.putExtra("amount", amount);
                intent.putExtra("itemList", (Serializable) newProductsDetailedModel);
                startActivity(intent);
            }
        });

    }
}
package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.models.MyCartModel;
import com.example.mygrocerystore.models.NewProductsDetailedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ProductPlaceOrder extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    NewProductsDetailedModel newProductsDetailedModel;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        amount = getIntent().getIntExtra("amount", 0);
        newProductsDetailedModel = (NewProductsDetailedModel) getIntent().getSerializableExtra("itemList");


        if (newProductsDetailedModel != null){
            String totalQuantity = String.valueOf(amount/Integer.parseInt(newProductsDetailedModel.getPrice()));
            String saveCurrentDate,saveCurrentTime;
            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("productName", newProductsDetailedModel.getName());
                cartMap.put("productPrice",newProductsDetailedModel.getPrice());
                cartMap.put("currentDate", saveCurrentDate);
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("totalQuantity", totalQuantity);
                cartMap.put("totalPrice", amount);

                firestore.collection("CurrentUser").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        Toast.makeText(ProductPlaceOrder.this, "Your Order Has Been Placed", Toast.LENGTH_SHORT).show();

                    }
                });
            }


    }
}
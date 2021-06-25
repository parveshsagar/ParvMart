package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.adapters.AddressAdapter;
import com.example.mygrocerystore.models.AddressModel;
import com.example.mygrocerystore.models.MyCartModel;
import com.example.mygrocerystore.models.NewProductsDetailedModel;
import com.example.mygrocerystore.models.PopularModel;
import com.example.mygrocerystore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{

    Button addAddress;
    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button addAddressBtn,paymentBtn;
    Toolbar toolbar;
    String mAddress = "";
    List<MyCartModel> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        list = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");


        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view ->{
            finish();
        });

        // get Data from detailed activity
        Object obj = getIntent().getSerializableExtra("item");


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.address_recycler);
        addAddressBtn = findViewById(R.id.add_address_btn);
        paymentBtn = findViewById(R.id.payment_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(),addressModelList,this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        AddressModel addressModel = documentSnapshot.toObject(AddressModel.class);
                        addressModelList.add(addressModel);
                        addressAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                double amount = 0.0;
                if (obj instanceof ViewAllModel){
                    ViewAllModel viewAllModel = (ViewAllModel) obj;
                    amount = viewAllModel.getPrice();
                }

                Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount", amount);
                intent.putExtra("itemList", (Serializable) list);
                startActivity(intent);
            }
        });



        addAddress = findViewById(R.id.add_address_btn);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
            }
        });
    }

    private void orderFromNewProduct(int quantity, NewProductsDetailedModel newProductsDetailedModel) {

    }

    @Override
    public void setAddress(String address) {

        mAddress = address;

    }
}
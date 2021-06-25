package com.example.mygrocerystore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygrocerystore.activities.AddAddressActivity;
import com.example.mygrocerystore.activities.AddressActivity;
import com.example.mygrocerystore.activities.PlacedOrderActivity;
import com.example.mygrocerystore.activities.ProductAddress;
import com.example.mygrocerystore.adapters.NavCategoryAdapter;
import com.example.mygrocerystore.adapters.NewProductsDetailedAdapter;
import com.example.mygrocerystore.models.NavCategoryModel;
import com.example.mygrocerystore.models.NewProductsDetailedModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class newProductsFragment extends Fragment {

    TextView quantity;
    int totalQuantity = 1;
    Button buyNow;


    ImageView addItem, removeItem;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<NewProductsDetailedModel> productsDetailedModelList;
    NewProductsDetailedAdapter productsDetailedAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_products, container, false);

        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.prod_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        productsDetailedModelList = new ArrayList<>();
        productsDetailedAdapter = new NewProductsDetailedAdapter(getActivity(), productsDetailedModelList);
        recyclerView.setAdapter(productsDetailedAdapter);

        buyNow = root.findViewById(R.id.buynow);
        quantity = root.findViewById(R.id.quantity);
        addItem = root.findViewById(R.id.add_item);
        removeItem = root.findViewById(R.id.remove_item);


        db.collection("NavNewProductsDetailed")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            NewProductsDetailedModel newProductsDetailedModel = document.toObject(NewProductsDetailedModel.class);
                            productsDetailedModelList.add(newProductsDetailedModel);
                            productsDetailedAdapter.notifyDataSetChanged();

                        }
                        productsDetailedAdapter.setOnClickListener(new NewProductsDetailedAdapter.OnClickListener() {
                            @Override
                            public void onPlusClick(TextView quantity, int position) {
                                int totalQ = Integer.parseInt(quantity.getText().toString());

                                if (totalQ < 10) {
                                    totalQ = totalQ + 1;
                                    quantity.setText(String.valueOf(totalQ));
                                }

                                NewProductsDetailedModel newProductsDetailedModel = productsDetailedModelList.get(position);
                                Toast.makeText(getContext(), "You Added an item" , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onMinusClick(TextView quantity, int position) {
                                int totalQ = Integer.parseInt(quantity.getText().toString());

                                if (totalQ > 1) {
                                    totalQ = totalQ - 1;
                                    quantity.setText(String.valueOf(totalQ));
                                }
                                Toast.makeText(getContext(), "You removed an item", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onBuy(TextView textView, int position) {
                                NewProductsDetailedModel newProductsDetailedModel = productsDetailedModelList.get(position);
                                int totalQ = Integer.parseInt(textView.getText().toString());

                                Intent intent = new Intent(getContext(), ProductAddress.class);
                                intent.putExtra("dataFrom", "newProduct");
                                intent.putExtra("quantity", totalQ);
                                intent.putExtra("data", newProductsDetailedModel);


                                startActivity(intent);
                            }
                        });
                    } else {

                        Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });


        return root;
    }

}
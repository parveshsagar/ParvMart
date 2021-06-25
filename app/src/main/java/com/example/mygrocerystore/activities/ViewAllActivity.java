package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.adapters.ViewAllAdapter;
import com.example.mygrocerystore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;
    Toolbar toolbar;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view ->{
            finish();
        });
        progressBar = findViewById(R.id.progressbar);



        String type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelList);
        recyclerView.setAdapter(viewAllAdapter);

        //////getting fruits
        if (type != null && type.equalsIgnoreCase("fruit")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fruit").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
        }
        //////getting vegetable
        if (type != null && type.equalsIgnoreCase("vegetable")) {
            firestore.collection("AllProducts").whereEqualTo("type", "vegetable").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);


                            }
                        }
                    });
        }
        //////getting fish
        if (type != null && type.equalsIgnoreCase("fish")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fish").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);


                            }
                        }
                    });
        }
        //////getting egg
        if (type != null && type.equalsIgnoreCase("egg")) {
            firestore.collection("AllProducts").whereEqualTo("type", "egg").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);


                            }
                        }
                    });
        }
        //////getting Milk
        if (type != null && type.equalsIgnoreCase("milk")) {
            firestore.collection("AllProducts").whereEqualTo("type", "milk").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);


                            }
                        }
                    });
        }

        //////getting products
        if (type != null && type.equalsIgnoreCase("products")) {
            firestore.collection("AllProducts").whereEqualTo("type", "products").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);


                            }
                        }
                    });
        }

        /////getting products
        if (type != null && type.equalsIgnoreCase("carrot")) {
            firestore.collection("AllProducts").whereEqualTo("type", "carrot").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);


                            }
                        }
                    });
        }
    }

}
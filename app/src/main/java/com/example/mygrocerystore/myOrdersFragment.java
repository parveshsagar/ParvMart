package com.example.mygrocerystore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.media.MediaBrowserService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mygrocerystore.adapters.MyCartAdapter;
import com.example.mygrocerystore.adapters.MyOrdersAdapter;
import com.example.mygrocerystore.models.MyCartModel;
import com.example.mygrocerystore.models.MyOrdersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 *
 */
public class myOrdersFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    RecyclerView recyclerView;
    MyOrdersAdapter ordersAdapter;
    List<MyOrdersModel> ordersModelList;



    public myOrdersFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ordersModelList = new ArrayList<>();
        ordersAdapter = new MyOrdersAdapter(getActivity(), ordersModelList);
        recyclerView.setAdapter(ordersAdapter);

        db.collection("CurrentUser").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .collection("MyOrder").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult()).getDocuments()){

                            String documentId = documentSnapshot.getId();

                            try {
                                MyOrdersModel ordersModel = documentSnapshot.toObject(MyOrdersModel.class);


                                assert ordersModel != null;
                                // for deleting
                                ordersModel.setDocumentId(documentId);

                                ordersModelList.add(ordersModel);
                                ordersAdapter.notifyDataSetChanged();

                            }catch (Exception e){
                                Log.d("ErrorHere", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                });
        return root;
    }
}
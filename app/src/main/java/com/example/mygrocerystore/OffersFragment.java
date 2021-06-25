package com.example.mygrocerystore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.media.MediaBrowserService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygrocerystore.adapters.NewProductsDetailedAdapter;
import com.example.mygrocerystore.adapters.OffersAdapter;
import com.example.mygrocerystore.models.NewProductsDetailedModel;
import com.example.mygrocerystore.models.OffersModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlinx.coroutines.FlowPreview;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment {

    TextView quantity;
    int totalQuantity =1;
    Button buyNow;


    ImageView addItem,removeItem;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<OffersModel> offersModelList;
    OffersAdapter offersAdapter;



    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_offers, container, false);

        quantity = root.findViewById(R.id.quantity);
        buyNow = root.findViewById(R.id.buynow);
        addItem = root.findViewById(R.id.add_item);
        recyclerView = root.findViewById(R.id.prod_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        removeItem = root.findViewById(R.id.remove_item);
        offersModelList = new ArrayList<>();
        offersAdapter  = new OffersAdapter(getActivity(),offersModelList);
        recyclerView.setAdapter(offersAdapter);


        db = FirebaseFirestore.getInstance();

        db.collection("OffersDetailed")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            OffersModel offersModel = document.toObject(OffersModel.class);
                            offersModelList.add(offersModel);
                            offersAdapter.notifyDataSetChanged();

                        }
                    } else {

                        Toast.makeText(getActivity(),"Error"+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });


    return root;
    }
}
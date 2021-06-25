package com.example.mygrocerystore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.R;

import com.example.mygrocerystore.adapters.HomeAdapter;
import com.example.mygrocerystore.adapters.PopularAdapters;

import com.example.mygrocerystore.adapters.RecommendedAdapter;
import com.example.mygrocerystore.models.HomeCategory;
import com.example.mygrocerystore.models.PopularModel;
import com.example.mygrocerystore.models.RecommendedModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;


    RecyclerView popularRec,homeCatRec,recommendedRec;
    FirebaseFirestore db;

    //popular items
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;

    //Home Category
    List<HomeCategory> homeCategoryList;
    HomeAdapter homeAdapter;

    //Recommended
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container,false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //popular items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapters  = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            PopularModel popularModel = document.toObject(PopularModel.class);
                            popularModelList.add(popularModel);
                            popularAdapters.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    } else {

                        Toast.makeText(getActivity(),"Error"+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });
        //explore items
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeCategoryList = new ArrayList<>();
        homeAdapter  = new HomeAdapter(getActivity(),homeCategoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            HomeCategory homeCategory = document.toObject(HomeCategory.class);
                            homeCategoryList.add(homeCategory);
                            homeAdapter.notifyDataSetChanged();
                        }
                    } else {

                        Toast.makeText(getActivity(),"Error"+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });

        //Recommended items
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter  = new RecommendedAdapter(getActivity(),recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                            recommendedModelList.add(recommendedModel);
                            recommendedAdapter.notifyDataSetChanged();
                        }
                    } else {

                        Toast.makeText(getActivity(),"Error"+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });


        return root;
    }


}
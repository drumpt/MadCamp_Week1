package com.example.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentGallery extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<ImageCard> lstImageCard; // 이미지카드를 저장할 배열

    public FragmentGallery(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstImageCard = new ArrayList<>();
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_call));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_collections));
//        lstImageCard.add(new ImageCard("Hyebin","4st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","5st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","6st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_call));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_collections));
//        lstImageCard.add(new ImageCard("Hyebin","4st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","5st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","6st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_call));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_collections));
//        lstImageCard.add(new ImageCard("Hyebin","4st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","5st card",R.drawable.ic_account_box_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","6st card",R.drawable.ic_account_box_black_36dp));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.gallery_fragment,container,false);
        myrecyclerview = (RecyclerView)v.findViewById(R.id.gallery_recyclerview);
        RecyclerViewAdapterGallery recyclerAdapter = new RecyclerViewAdapterGallery(getContext(),lstImageCard);
        myrecyclerview.setLayoutManager((new GridLayoutManager(getContext(),3)));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }
}

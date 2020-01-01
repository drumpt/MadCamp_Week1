package com.example.recyclerview;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentGallery extends Fragment {
    View v;
    private Bundle bundle;
    private RecyclerView myrecyclerview;
    private RecyclerViewAdapterGallery recyclerAdapter;
    private List<ImageCard> lstImageCard = new ArrayList<>(); // 이미지카드를 저장할 배열
    private ArrayList<Uri> uris = new ArrayList<>();

    public FragmentGallery() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Data setting
        super.onCreate(savedInstanceState);
//        for(int i=0;i<20;i++){
////            lstImageCard.add(new ImageCard());
////        }
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","1st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","2st card",R.drawable.ic_account_circle_black_36dp));
//        lstImageCard.add(new ImageCard("Hyebin","3st card",R.drawable.ic_account_circle_black_36dp));

    }

    //    public void setCard(ArrayList<Uri> uriList){
//        for(int i=0;i<uriList.size();i++){
//            ImageCard card = lstImageCard.get(i);
//            card.setUri(uriList.get(i));
//        }
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View setting
        Log.d("@@@@@@@@@@@@TAG@@@@@@@@", "onCreateView222 called");
        v = inflater.inflate(R.layout.gallery_fragment, container, false); // 갤러리 뿌릴 프래그먼트를 인플레이트
        myrecyclerview = (RecyclerView) v.findViewById(R.id.gallery_recyclerview);
        myrecyclerview.setLayoutManager((new GridLayoutManager(getContext(), 3)));
        recyclerAdapter = new RecyclerViewAdapterGallery(getContext(), lstImageCard);
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        Log.d("@@@@@TAG@@@@", "setArguments called");
        super.setArguments(args);
        bundle = args;
        ArrayList<String> lstTmp = bundle.getStringArrayList("result_images");
        Log.d("12313242345345", lstTmp.get(0));
        Log.d("12313242345345", lstTmp.get(1));
        uris.clear();
        for (int i = 0; i < lstTmp.size(); i++) {
            uris.add(Uri.parse(lstTmp.get(i)));
            Log.d("@@@@@TAG@@@@", uris.get(i).toString());
        }
        ImageCard imageCard;
        ArrayList<ImageCard> imageCards = new ArrayList<>();
        lstImageCard.clear();
        for (int i = 0; i < lstTmp.size(); i++) {
            imageCard = new ImageCard();
            imageCard.setUri(uris.get(i));
            imageCard.setTitle("" + i);
            imageCard.setDescription("" + i);
            imageCards.add(imageCard);
            Log.d("TAG", "" + imageCard.getTitle() + imageCard.getUri());
        }
        lstImageCard.addAll(imageCards);
        recyclerAdapter.notifyDataSetChanged();
//        recyclerAdapter = new RecyclerViewAdapterGallery(getContext(), lstImageCard);
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        FragmentGallery fragmentGallery = new FragmentGallery();
//        fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), fragmentGallery);
//        fragmentTransaction.commitAllowingStateLoss();
    }
}
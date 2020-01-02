package com.example.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;

public class FragmentRestaurant extends Fragment {
    View v;
    private ImageView startButton;
    private TextView countText;
    final static int MAX_COUNT = 3;

    Calendar calendar = Calendar.getInstance();
    int month = Calendar.getInstance().get(Calendar.MONTH); // month+1
    int date = Calendar.getInstance().get(Calendar.DATE);
    int year = Calendar.getInstance().get(Calendar.YEAR);
    private String dateString = month + "/" + date + "/" + year;

    public FragmentRestaurant() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.restaurant_fragment, container, false);
        final int count = readCount("myfile.txt");
        countText = (TextView) v.findViewById(R.id.count_left);
        countText.setText("오늘 남은 재시도 횟수 : " + count);
        startButton = (ImageView) v.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentRestaurant2 fragmentRestaurant2 = new FragmentRestaurant2();
                    fragmentTransaction.replace(R.id.restaurant1, fragmentRestaurant2);
                    fragmentTransaction.commitAllowingStateLoss();
                } else Toast.makeText(getContext(), "남은 맛집이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    public int readCount(String filename) {
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            FileInputStream fis = getContext().openFileInput(filename);//파일명
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine().trim(); // 파일에서 한줄을 읽어옴
            if (str == "") return MAX_COUNT; // 최초 실행
            else {
                String[] tmpList = str.split(" ");
                if (tmpList[0].equals(dateString)) return Integer.parseInt(tmpList[1]);
                else return MAX_COUNT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MAX_COUNT;
        }
    }

    public int writeCount(String filename) {
        int previous_count = readCount(filename);
        String fullResult;
        try {
            if (previous_count == 0) return previous_count;
            else {
                FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                previous_count--;
                fullResult = dateString + " " + previous_count;
                PrintWriter out = new PrintWriter(fos);
                out.println(fullResult);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return previous_count;
    }
}
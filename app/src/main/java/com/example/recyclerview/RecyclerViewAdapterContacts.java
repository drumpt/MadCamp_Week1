package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterContacts extends RecyclerView.Adapter<RecyclerViewAdapterContacts.MyViewHolder> {
    static Context mContext;
    static ArrayList<ContactItem> mData;

    public RecyclerViewAdapterContacts(Context context, ArrayList<ContactItem> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone_number());
        holder.img.setImageBitmap(mData.get(position).getPhoto_bitmap());
//        holder.img.setImageResource(mData.get(position).getPhoto_id());
        holder.call_button.setImageResource(mData.get(position).getCall_photo_id());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv_name;
        private TextView tv_phone;
        private ImageView call_button;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tv_name = (TextView) itemView.findViewById(R.id.name);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_number);
            call_button = (ImageView) itemView.findViewById(R.id.call_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // fragment
                }
            });
            call_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String phone_number_with_tel = "tel:" + mData.get(position).getPhone_number();
//                    String phone_number_with_tel = "tel:" + "010-4177-5808";
                    Uri number = Uri.parse(phone_number_with_tel);
                    Intent callIntent = new Intent(Intent.ACTION_CALL, number);
                    v.getContext().startActivity(callIntent);
                }
            });
        }
    }
}
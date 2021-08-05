package com.example.studeaze;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> { //Image Adapter class

    private ArrayList<ImageModel> mList;
    private Context context;
    //constructor
    public ImageAdapter(Context context , ArrayList<ImageModel> mList){

        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    //Create view holder
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_item, parent ,false);
        return new MyViewHolder(v);
    }

    @Override
    //Bind view holder
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    //Function to get item count
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
            imageView = itemView.findViewById(R.id.m_image);
        }
    }
}
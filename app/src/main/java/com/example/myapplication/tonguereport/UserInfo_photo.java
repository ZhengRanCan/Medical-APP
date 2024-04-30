package com.example.myapplication.tonguereport;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

// 该文件是RecyclerView的适配器，负责将照片数据绑定到RecyclerView中，即显示列表的相关文件，不要动就行
public class UserInfo_photo extends RecyclerView.Adapter<UserInfo_photo.PhotoViewHolder> {
    private final List<Bitmap> photosList;
    private OnItemClickListener listener;

    public UserInfo_photo(List<Bitmap> photosList) {
        this.photosList = photosList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_userinfo_photo, parent, false);
        return new PhotoViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Bitmap photo = photosList.get(position);
        holder.imageView.setImageBitmap(photo);
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PhotoViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_photo);

            itemView.setOnClickListener(v->{
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}

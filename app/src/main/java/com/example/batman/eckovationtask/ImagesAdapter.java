package com.example.batman.eckovationtask;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.batman.eckovationtask.entities.ImageData;
import com.example.batman.eckovationtask.utils.ImageDownloader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private Context context;
    private List<ImageData> imageDataList;
    private LoaderManager loaderManager;

    public ImagesAdapter(Context context, List<ImageData> imageDataList, LoaderManager loaderManager) {
        this.context = context;
        this.imageDataList = imageDataList;
        this.loaderManager = loaderManager;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {

        final ImageData imagesData = imageDataList.get(position);
        Log.d("TAG", "URL: " + imagesData.getPhotoUrl());

        new ImageDownloader(context, holder.imageView, imagesData.getPhotoUrl(), position, loaderManager);
    }

    @Override
    public int getItemCount() {
        if (imageDataList == null) return 0;
        return imageDataList.size();
    }

    public void swapList(List<ImageData> newList) {
        imageDataList = newList;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

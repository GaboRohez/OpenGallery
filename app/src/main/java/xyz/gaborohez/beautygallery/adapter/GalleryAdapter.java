package xyz.gaborohez.beautygallery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import xyz.gaborohez.beautygallery.databinding.ItemImagesBinding;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private List<String> images;
    protected PhotoListener listener;

    public interface PhotoListener{
        void onPhotoClick(int position);
    }

    public GalleryAdapter(Context context, List<String> images, PhotoListener listener) {
        this.context = context;
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemImagesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String image = images.get(position);

        Glide.with(context)
                .load(image)
                .into(holder.binding.imageView);
        /*Glide.with(context)
                .load(image)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.binding.imageView);*/


        holder.itemView.setOnClickListener(v -> listener.onPhotoClick(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemImagesBinding binding;

        public ViewHolder(ItemImagesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

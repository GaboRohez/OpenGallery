package xyz.gaborohez.beautygallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.List;

import xyz.gaborohez.beautygallery.databinding.ItemImagesBinding;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private static final String TAG = "GalleryAdapter";

    private Context context;
    private List<String> images;
    protected PhotoListener listener;

    public interface PhotoListener{
        void onPhotoClick(String path);
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

        Log.d(TAG, "onBindViewHolder: "+image);
        /*Glide.with(context)
                .load(image)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.binding.imageView);*/

        /*BitmapFactory.Options options = new BitmapFactory.Options();
        // will results in a much smaller image than the original
        options.inSampleSize = 8;
        Bitmap b = BitmapFactory.decodeFile(image, options);
        holder.binding.imageView.setImageBitmap(b);*/


        holder.itemView.setOnClickListener(v -> listener.onPhotoClick(image));
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

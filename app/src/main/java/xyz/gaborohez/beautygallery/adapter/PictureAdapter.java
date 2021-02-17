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
import xyz.gaborohez.beautygallery.databinding.ItemPictureBinding;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private static final String TAG = "GalleryAdapter";

    private Context context;
    private List<String> images;;

    public PictureAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPictureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemPictureBinding binding;

        public ViewHolder(ItemPictureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

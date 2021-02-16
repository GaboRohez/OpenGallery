package xyz.gaborohez.beautygallery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import xyz.gaborohez.beautygallery.databinding.ItemFolderBinding;
import xyz.gaborohez.beautygallery.databinding.ItemImagesBinding;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private static final String TAG = "FolderAdapter";

    private Context context;
    private List<String> images;
    protected PhotoListener listener;

    public interface PhotoListener{
        void onPhotoClick(String path);
    }

    public FolderAdapter(Context context, List<String> images, PhotoListener listener) {
        this.context = context;
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String image = images.get(position);

        String[] path = image.split("/");
        holder.binding.tvName.setText(path[path.length-2]);

        Glide.with(context)
                .load(image)
                .into(holder.binding.imageView);

        Log.d(TAG, "onBindViewHolder: "+image);


        holder.itemView.setOnClickListener(v -> listener.onPhotoClick(image));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemFolderBinding binding;

        public ViewHolder(ItemFolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

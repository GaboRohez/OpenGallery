package xyz.gaborohez.beautygallery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import xyz.gaborohez.beautygallery.app.OpenGalleryApp;
import xyz.gaborohez.beautygallery.data.FolderPOJO;
import xyz.gaborohez.beautygallery.databinding.ItemFolderBinding;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private static final String TAG = "FolderAdapter";

    private Context context;
    private List<FolderPOJO> folders;
    protected PhotoListener listener;

    public interface PhotoListener{
        void onPhotoClick(int position);
    }

    public FolderAdapter(Context context, List<FolderPOJO> folders, PhotoListener listener) {
        this.context = context;
        this.folders = folders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FolderPOJO folder = folders.get(position);

        String[] path = folder.getPath().split("/");
        holder.binding.tvName.setText(path[path.length-2]);
        holder.binding.tvSize.setText(OpenGalleryApp.resourcesManager.getElements(String.valueOf(folders.get(position).getTotalItems())));

        Glide.with(context)
                .load(folders.get(position).getPath())
                .centerCrop()
                .into(holder.binding.imageView);

        holder.itemView.setOnClickListener(v -> listener.onPhotoClick(position));
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemFolderBinding binding;

        public ViewHolder(ItemFolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

package xyz.gaborohez.beautygallery.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import xyz.gaborohez.beautygallery.data.FolderPOJO;

public class GalleryViewModel extends ViewModel {

    private int itemPosition;
    private String albumTitle;
    private List<String> photos = new ArrayList<>();
    private List<List<String>> album = new ArrayList<>();

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<List<String>> getAlbum() {
        return album;
    }

    public void setAlbum(List<List<String>> album) {
        this.album = album;
    }

    public void addAlbum(List<String> album){
        this.album.add(album);
    }
}

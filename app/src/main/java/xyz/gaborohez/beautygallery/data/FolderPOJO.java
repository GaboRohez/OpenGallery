package xyz.gaborohez.beautygallery.data;

public class FolderPOJO {

    private String path;
    private int totalItems;

    public FolderPOJO() {
    }

    public FolderPOJO(String path, int totalItems) {
        this.path = path;
        this.totalItems = totalItems;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    @Override
    public String toString() {
        return "FolderPOJO{" +
                "path='" + path + '\'' +
                ", totalItems=" + totalItems +
                '}';
    }
}

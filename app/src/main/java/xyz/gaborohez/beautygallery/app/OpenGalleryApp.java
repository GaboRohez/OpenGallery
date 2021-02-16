package xyz.gaborohez.beautygallery.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import xyz.gaborohez.beautygallery.R;

public class OpenGalleryApp extends Application {

    public static Context context;
    public static ResourcesManager resourcesManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();
        resourcesManager = new ResourcesManager(getResources());
    }

    public static class ResourcesManager {
        private Resources resources;

        public ResourcesManager(Resources resources) {
            this.resources = resources;
        }

        public String getAppName(){
            return resources.getString(R.string.app_name);
        }
    }

}

package cf.dashika.momyalbum;

import android.app.Application;
import cf.dashika.momyalbum.Util.MediaLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

public class MamyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Album.initialize(
                AlbumConfig.newBuilder(this)
                        .setAlbumLoader(new MediaLoader())
                        .build()
        );
    }
}

package android.particles.com.retrofit.component.util;

import android.content.Context;
import android.particles.com.retrofit.modules.search.domin.Translation;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by YLM on 2016/10/24.
 */
public class Util {

    //获取屏幕的高度
    public static float getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

}

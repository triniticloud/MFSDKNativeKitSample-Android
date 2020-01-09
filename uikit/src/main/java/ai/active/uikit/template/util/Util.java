package ai.active.uikit.template.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;


public class Util {

    public static Drawable getDrawable(Context ctx, @DrawableRes int drawable) {
        return ContextCompat.getDrawable(ctx, drawable);
    }
}

package ai.active.uikit.template.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public final class TextUtil {

    private static final String NON_BREAKING_SPACE = " &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;";

    public static String appendNonBreakingSpace(String str) {
        return str + NON_BREAKING_SPACE;
    }

    public static String getHash(@NonNull String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i);
        }
        return String.valueOf(h);
    }

    public static String getFileName(@NonNull String url) {
        String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    public static String removeNull(String string) {
        if (TextUtils.isEmpty(string))
            return string;

        if (string.contains("<null>"))
            string = string.replaceAll("<null>", "");

        if (string.contains("null"))
            string = string.replaceAll("null", "");

        return string.trim();
    }
}

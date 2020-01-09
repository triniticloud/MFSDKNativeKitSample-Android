package ai.active.uikit.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class SansFranciscoTextView extends android.support.v7.widget.AppCompatTextView {

    public SansFranciscoTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public SansFranciscoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public SansFranciscoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {

        Typeface customFont = FontProvider.getTypeface(context, "sanfrancisco-regular.ttf");

        if (getTypeface() != null) {
            int style = getTypeface().getStyle();

            if (style == Typeface.BOLD) {
                customFont = FontProvider.getTypeface(context, "sanfrancisco-bold.ttf");
            }

        }

        setTypeface(customFont);

    }
}

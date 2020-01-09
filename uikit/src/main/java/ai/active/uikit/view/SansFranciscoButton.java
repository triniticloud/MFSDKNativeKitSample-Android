package ai.active.uikit.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class SansFranciscoButton extends android.support.v7.widget.AppCompatButton {

    public SansFranciscoButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public SansFranciscoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public SansFranciscoButton(Context context, AttributeSet attrs, int defStyleAttr) {
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

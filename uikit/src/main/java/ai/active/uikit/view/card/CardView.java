package ai.active.uikit.view.card;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ai.active.uikit.R;

public class CardView extends FrameLayout {

    public CardView(@NonNull Context context) {
        super(context);
        init();
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(getResources().getDrawable(R.drawable.ic_bubble_white_card));
        } else {
            this.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bubble_white_card));
        }
    }


}

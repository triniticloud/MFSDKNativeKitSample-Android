package ai.active.uikit.view.card;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MFClipFrameLayout extends FrameLayout {
    private Clipper mHelper;

    public MFClipFrameLayout(Context context) {
        super(context);
        mHelper = new Clipper(this, null);
    }

    public MFClipFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new Clipper(this, attrs);
    }

    public MFClipFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new Clipper(this, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MFClipFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mHelper = new Clipper(this, attrs);
    }

    public void draw(Canvas canvas) {
        Bitmap offscreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas offscreenCanvas = new Canvas(offscreenBitmap);

        super.draw(offscreenCanvas);

        if (mHelper.maskBitmap == null) {
            mHelper.maskBitmap = mHelper.createMask(canvas.getWidth(), canvas.getHeight(), mHelper.cornerRadius);
        }

        offscreenCanvas.drawBitmap(mHelper.maskBitmap, 0f, 0f, mHelper.maskPaint);
        canvas.drawBitmap(offscreenBitmap, 0f, 0f, mHelper.paint);
    }
}

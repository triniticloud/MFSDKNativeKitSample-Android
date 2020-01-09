package ai.active.uikit.view.card;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import ai.active.uikit.R;


class Clipper {
    Paint paint;
    Paint maskPaint;
    float cornerRadius;
    private float topCornerRadius;
    private float bottomCornerRadius;
    Bitmap maskBitmap;
    private float paddingRadius;

    Clipper(View view, AttributeSet attrs) {
        init(view, attrs);
    }

    private void init(View view, AttributeSet attrs) {
        final TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.MFClipView);
        cornerRadius = array.getDimension(R.styleable.MFClipView_clipRound, 0);
        topCornerRadius = array.getDimension(R.styleable.MFClipView_clipTopCorner, -1);
        bottomCornerRadius = array.getDimension(R.styleable.MFClipView_clipBottomCorner, -1);

        paddingRadius = array.getDimension(R.styleable.MFClipView_clipPadding, 0);
        array.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        view.setWillNotDraw(false);
    }

    Bitmap createMask(int width, int height, float cornerRadius) {

        if (topCornerRadius != -1) {
            return createTopMask(width, height, topCornerRadius);
        } else if (bottomCornerRadius != -1) {
            return createBottomMask(width, height, bottomCornerRadius);
        } else {
            return createAllMask(width, height, cornerRadius);
        }
    }

    private Bitmap createAllMask(int width, int height, float cornerRadius) {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        canvas.drawRect(0, 0, width, height, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(paddingRadius, paddingRadius, width - paddingRadius, height - paddingRadius), cornerRadius, cornerRadius, paint);

        return mask;
    }

    private Bitmap createTopMask(int width, int height, float cornerRadius) {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        canvas.drawRect(0, 0, width, height, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(paddingRadius, paddingRadius, width - paddingRadius, height - paddingRadius), cornerRadius, cornerRadius, paint);

        // mask bottom rounded corner
        canvas.drawRect(0  , 0 + cornerRadius, width, height, paint);
        return mask;
    }

    private Bitmap createBottomMask(int width, int height, float cornerRadius) {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        canvas.drawRect(0, 0, width, height, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(paddingRadius, paddingRadius, width - paddingRadius, height - paddingRadius), cornerRadius, cornerRadius, paint);

        // Mask top rounded corner
        canvas.drawRect(0  , 0, width, height - cornerRadius, paint);
        return mask;
    }
}

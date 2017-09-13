package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.annotation.Nullable;
import android.view.View;

import model.Dot;

public class DotView extends View {

    private Paint paint;
    private Dot dot;
    private Bitmap mDrawBitmap;
    private Canvas mBitmapCanvas;
    private Paint mDrawPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(dot != null) {
            if (mDrawBitmap == null) {
                mDrawBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                mBitmapCanvas = new Canvas(mDrawBitmap);
            }
            paint.setColor(Color.rgb(dot.getColorR(), dot.getColorG(), dot.getColorB()));
            mBitmapCanvas.drawCircle(dot.getCenterX(), dot.getCenterY(),
                    dot.getRadius(), paint);
            canvas.drawBitmap(mDrawBitmap, 0, 0, mDrawPaint);
        }
    }

    public DotView(Context context){
        super(context);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs,
                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }

    public void clearDot(){
        mDrawBitmap.eraseColor(Color.TRANSPARENT);
    }
}

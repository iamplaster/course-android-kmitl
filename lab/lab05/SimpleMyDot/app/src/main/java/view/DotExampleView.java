package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import model.DotSimple;


public class DotExampleView extends View {

    private Paint paint;
    private DotSimple dotsimple;



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

            paint.setColor(Color.rgb(dotsimple.getColorR(), dotsimple.getColorG(), dotsimple.getColorB()));
            canvas.drawCircle(dotsimple.getCenterX(), dotsimple.getCenterY(),
                    dotsimple.getRadius(), paint);

    }

    public DotExampleView(Context context){
        super(context);
        paint = new Paint();
    }

    public DotExampleView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
    }

    public DotExampleView(Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public void setDot(DotSimple dot) {
        this.dotsimple = new DotSimple();
        this.dotsimple.setColorR(dot.getColorR());
        this.dotsimple.setColorG(dot.getColorG());
        this.dotsimple.setColorB(dot.getColorB());
        this.dotsimple.setCenterX(130);
        this.dotsimple.setCenterY(130);
        this.dotsimple.setRadius(100);

    }

}

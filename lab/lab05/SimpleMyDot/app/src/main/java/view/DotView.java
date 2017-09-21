package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

import model.Dot;
import model.DotSimple;


public class DotView extends View {

    private Paint paint;
    private DotSimple dotsimple;
    private ArrayList<DotSimple> dotlist = new ArrayList<>();


    private Bitmap mDrawBitmap;
    private Canvas mBitmapCanvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0 ; i < dotlist.size() ; i++){
            paint.setColor(Color.rgb(dotlist.get(i).getColorR(), dotlist.get(i).getColorG(), dotlist.get(i).getColorB()));
            canvas.drawCircle(dotlist.get(i).getCenterX(), dotlist.get(i).getCenterY(),
                    dotlist.get(i).getRadius(), paint);

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
        this.dotsimple = new DotSimple();
        this.dotsimple.setColorR(dot.getColorR());
        this.dotsimple.setColorG(dot.getColorG());
        this.dotsimple.setColorB(dot.getColorB());
        this.dotsimple.setCenterX(dot.getCenterX());
        this.dotsimple.setCenterY(dot.getCenterY());
        this.dotsimple.setRadius(dot.getRadius());
        dotlist.add(this.dotsimple);


    }

    public void setDotsimple(DotSimple dot) {
        this.dotsimple = new DotSimple();
        this.dotsimple.setColorR(dot.getColorR());
        this.dotsimple.setColorG(dot.getColorG());
        this.dotsimple.setColorB(dot.getColorB());
        this.dotsimple.setCenterX(dot.getCenterX());
        this.dotsimple.setCenterY(dot.getCenterY());
        this.dotsimple.setRadius(dot.getRadius());
        dotlist.add(this.dotsimple);


    }

    public void clearDot(){
        dotlist.clear();
        this.setWillNotDraw(false);
    }

    public int checkDot(int x, int y){
        for(int j=(dotlist.size()-1); j >= 0; j--){
            int centerX = dotlist.get(j).getCenterX();
            int centerY = dotlist.get(j).getCenterY();
            double distance = Math.sqrt(Math.pow(centerX - x,2)) +
                    Math.sqrt(Math.pow(centerY - y, 2));
            if (distance <= dotlist.get(j).getRadius()){
                return j;
            }
        }
        return -1;
    }

    public void clearDotPoint(int point){
        dotlist.remove(point);
    }

    public Bitmap captureView(){
        if (mDrawBitmap == null) {
            mDrawBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mBitmapCanvas = new Canvas(mDrawBitmap);
        }
        mBitmapCanvas.drawColor(Color.WHITE);
        for(int i=0 ; i < dotlist.size() ; i++){
            paint.setColor(Color.rgb(dotlist.get(i).getColorR(), dotlist.get(i).getColorG(), dotlist.get(i).getColorB()));
            mBitmapCanvas.drawCircle(dotlist.get(i).getCenterX(), dotlist.get(i).getCenterY(),
                    dotlist.get(i).getRadius(), paint);

        }
        return mDrawBitmap;

    }

    public DotSimple getDot(int point){
        return dotlist.get(point);
    }

    public ArrayList<DotSimple> getDotlist() {
        return dotlist;
    }

    public void setDotlist(ArrayList<DotSimple> dotlist) {
        this.dotlist = dotlist;
    }
}

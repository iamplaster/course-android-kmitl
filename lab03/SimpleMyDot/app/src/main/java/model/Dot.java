package model;

public class Dot {

    public interface  OnDotChangedListener{
        void onDotChanged(Dot dot);
    }

    private OnDotChangedListener listener;

    public void setListener(OnDotChangedListener listener){
        this.listener = listener;
    }

    private int centerX;
    private int centerY;
    private int radius;
    private int colorR;
    private int colorG;
    private int colorB;

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }


    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
        this.listener.onDotChanged(this);
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
        this.listener.onDotChanged(this);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Dot(OnDotChangedListener listener, int centerX, int centerY, int radius, int colorR, int colorG, int colorB){
        this.listener = listener;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public Dot(int centerX, int centerY, int radius, int colorR, int colorG, int colorB){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }


}

package kmitl.lab03.teejuta58070060.simplemydot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

import model.Dot;
import view.DotView;

public class MainActivity extends AppCompatActivity
        implements Dot.OnDotChangedListener{

    private DotView dotView;
    private Dot dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dot = new Dot(this,0,0,60,255,255,255);
        dotView = (DotView) findViewById(R.id.dotView);
    }

    @Override
    public void onDotChanged(Dot dot) {
        dotView.setDot(dot);
        dotView.invalidate();

    }

    public void onRandomDot (View view) {
        Random random = new Random();
        int centerX = random.nextInt(this.dotView.getWidth());
        int centerY = random.nextInt(this.dotView.getHeight());
        int colorR = random.nextInt(255);
        int colorG = random.nextInt(255);
        int colorB = random.nextInt(255);
        this.dot.setCenterX(centerX);
        this.dot.setCenterY(centerY);
        this.dot.setColorR(colorR);
        this.dot.setColorG(colorG);
        this.dot.setColorB(colorB);
    }

    public void onCleanDot (View view){
        dotView.clearDot();
    }
}

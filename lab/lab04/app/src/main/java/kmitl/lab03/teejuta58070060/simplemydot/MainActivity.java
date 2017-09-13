package kmitl.lab03.teejuta58070060.simplemydot;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.Dot;
import view.DotView;

public class MainActivity extends AppCompatActivity
        implements Dot.OnDotChangedListener{

    private DotView dotView;
    private Dot dot;
    private Button button3;
    private File imagePath = new File(Environment.getExternalStorageDirectory() + "/sc.jpg");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dot = new Dot(this,0,0,60,255,255,255);
        dotView = (DotView) findViewById(R.id.dotView);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popup = new PopupMenu(MainActivity.this, button3);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        switch (item.getItemId()){
                            case R.id.capscreen:
                                Bitmap bmpscr = capturePicture();
                                savePic(bmpscr);
                                sharePic();
                                return true;
                            case R.id.cappic:
                                Bitmap bmppic = dotView.captureView();
                                savePic(bmppic);
                                sharePic();

                                return true;
                            case R.id.clearscreen:
                                dotView.clearDot();
                                dotView.invalidate();
                                return true;
                            default:
                                return MainActivity.super.onOptionsItemSelected(item);
                        }
                    }});
                popup.show();
            }
        });
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
        int radius = ThreadLocalRandom.current().nextInt(60,180);
        this.dot.setRadius(radius);
        this.dot.setColorR(colorR);
        this.dot.setColorG(colorG);
        this.dot.setColorB(colorB);
        this.dot.setCenterX(centerX);
        this.dot.setCenterY(centerY);

    }


    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(dotView.checkDot(Math.round(event.getX()-10), Math.round(event.getY()-220)) == -1){
                Random random = new Random();
                int colorR = random.nextInt(255);
                int colorG = random.nextInt(255);
                int colorB = random.nextInt(255);
                int radius = ThreadLocalRandom.current().nextInt(60,120);
                this.dot.setRadius(radius);
                this.dot.setColorR(colorR);
                this.dot.setColorG(colorG);
                this.dot.setColorB(colorB);
                this.dot.setCenterX(Math.round(event.getX()-10));
                this.dot.setCenterY(Math.round(event.getY()-220));
            }else{
                dotView.clearDotPoint(dotView.checkDot(Math.round(event.getX()-10), Math.round(event.getY()-220)));
                dotView.invalidate();
            }
        }

        return super.onTouchEvent(event);
    }

    public Bitmap capturePicture(){
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();

    }

    public void savePic(Bitmap bm){
        isStoragePermissionGranted();

        FileOutputStream fo;
        try{
            fo = new FileOutputStream(imagePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fo);
            fo.flush();
            fo.close();
        } catch (FileNotFoundException e) {
            Log.e("ERROR", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }

    public void sharePic(){
        Uri uri = Uri.fromFile(imagePath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        String shareTxt = "My Screen";
        intent.putExtra(Intent.EXTRA_SUBJECT, " ");
        intent.putExtra(Intent.EXTRA_TEXT, shareTxt);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(intent, "Share to"));
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

}

package kmitl.lab03.teejuta58070060.simplemydot;




import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.Dot;
import model.DotSimple;
import view.DotView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DotViewFragment extends Fragment implements Dot.OnDotChangedListener{



    private DotView dotView;
    private Dot dot;
    private File imagePath = new File(Environment.getExternalStorageDirectory() + "/sc.jpg");
    private View view;
    private Float touchX;
    private Float touchY;
    private DotSimple editDot;
    private ArrayList<DotSimple> dotlist;

    public DotViewFragment() {
        // Required empty public constructor
    }

    public interface OnEditClickedListener{
        void onEditClicked(DotSimple dot, ArrayList<DotSimple> dotlist, int point);
    }

    private OnEditClickedListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dot_view, container, false);
        dotView = view.findViewById(R.id.dotViewFragment);
        if (((MainActivity)getActivity()).isAfterEdit()){
            dotView.setDotlist(((MainActivity)getActivity()).getDotlist());
            dotView.clearDotPoint(((MainActivity)getActivity()).getPoint());
            dotView.setDotsimple(((MainActivity)getActivity()).getEditdot());
            ((MainActivity)getActivity()).setAfterEdit(false);
        }
        dot = new Dot(this,0,0,60,255,255,255);

        view.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                touchX = event.getX();
                touchY = event.getY();
                return false;
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(dotView.checkDot(Math.round(touchX-15), Math.round(touchY-15)) == -1){

                }else{
                    int dotpoint = dotView.checkDot(Math.round(touchX-15), Math.round(touchY-15));
                    editDot = dotView.getDot(dotpoint);
                    dotlist = dotView.getDotlist();
                    listener.onEditClicked(editDot, dotlist, dotpoint);
                }
                return true;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dotView.checkDot(Math.round(touchX-15), Math.round(touchY-15)) == -1){
                    Random random = new Random();
                    int colorR = random.nextInt(255);
                    int colorG = random.nextInt(255);
                    int colorB = random.nextInt(255);
                    int radius = ThreadLocalRandom.current().nextInt(60,120);
                    dot.setRadius(radius);
                    dot.setColorR(colorR);
                    dot.setColorG(colorG);
                    dot.setColorB(colorB);
                    dot.setCenterX(Math.round(touchX-15));
                    dot.setCenterY(Math.round(touchY-15));
                }else{
                    dotView.clearDotPoint(dotView.checkDot(Math.round(touchX-15), Math.round(touchY-15)));
                    dotView.invalidate();
                }
            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditClickedListener){
            listener = (OnEditClickedListener) context;
        }
    }

    @Override
    public void onDotChanged(Dot dot) {
        dotView.setDot(dot);
        dotView.invalidate();

    }

    public void onRandomDot () {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());

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

    public Bitmap capturePicture(){
        View rootView = view.getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();

    }

    public Bitmap captureDot(){
        Bitmap img = dotView.captureView();
        return img;
    }

    public void savePic(Bitmap bm){
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

    public void clearScreen(){
        dotView.clearDot();
        dotView.invalidate();
    }





}

package kmitl.lab03.teejuta58070060.simplemydot;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import model.DotSimple;
import view.DotExampleView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditDotFragment extends Fragment {

    private DotSimple editDot;
    private ArrayList<DotSimple> dotlist;
    private int point;
    private DotExampleView dotExampleView;
    private TextView xPositionText;
    private TextView yPositionText;
    private TextView radiusText;
    private Button submitButton;
    private SeekBar seekR;
    private SeekBar seekG;
    private SeekBar seekB;
    private SeekBar seekX;
    private SeekBar seekY;
    private SeekBar seekRadius;


    public EditDotFragment() {
        // Required empty public constructor
    }

    public interface OnFinishClickedListener{
        void finishClickedListener(DotSimple dot, ArrayList<DotSimple> dotlist, int point);
    }
    private OnFinishClickedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFinishClickedListener){
            listener = (OnFinishClickedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_dot, container, false);
        dotExampleView = view.findViewById(R.id.dotExampleView);
        editDot = ((MainActivity)getActivity()).getEditdot();
        dotlist = ((MainActivity)getActivity()).getDotlist();
        point = ((MainActivity)getActivity()).getPoint();


        radiusText = view.findViewById(R.id.radiusText);
        xPositionText = view.findViewById(R.id.xPositionText);
        yPositionText = view.findViewById(R.id.yPositionText);
        submitButton = view.findViewById(R.id.submitButton);

        seekR = view.findViewById(R.id.seekBar);
        seekR.setProgress(editDot.getColorR());

        seekG = view.findViewById(R.id.seekBar2);
        seekG.setProgress(editDot.getColorG());

        seekB = view.findViewById(R.id.seekBar3);
        seekB.setProgress(editDot.getColorB());

        seekX = view.findViewById(R.id.seekBar4);
        seekX.setProgress(editDot.getCenterX());

        seekY = view.findViewById(R.id.seekBar5);
        seekY.setProgress(editDot.getCenterY());

        seekRadius = view.findViewById(R.id.seekBar6);
        seekRadius.setProgress(editDot.getRadius()-60);

        seekR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editDot.setColorR(seekR.getProgress());
                dotExampleView.setDot(editDot);
                dotExampleView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editDot.setColorG(seekG.getProgress());
                dotExampleView.setDot(editDot);
                dotExampleView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editDot.setColorB(seekB.getProgress());
                dotExampleView.setDot(editDot);
                dotExampleView.invalidate();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editDot.setCenterX(seekX.getProgress());
                xPositionText.setText("X Position: " + seekX.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editDot.setCenterY(seekY.getProgress());
                yPositionText.setText("Y Position: " + seekY.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editDot.setRadius(seekBar.getProgress() + 60);
                radiusText.setText("Radius (Size): " + (seekBar.getProgress() + 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.finishClickedListener(editDot, dotlist, point);
            }
        });


        dotExampleView.setDot(editDot);
        dotExampleView.invalidate();
        radiusText.setText("Radius (Size): " + editDot.getRadius());
        xPositionText.setText("X Position: " + editDot.getCenterX());
        yPositionText.setText("Y Position: " + editDot.getCenterY());
        return view;
    }

}

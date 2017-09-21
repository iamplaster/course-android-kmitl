package kmitl.lab03.teejuta58070060.simplemydot;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import model.DotSimple;


public class MainActivity extends AppCompatActivity implements DotViewFragment.OnEditClickedListener{


    private Button button;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, new DotViewFragment()).commit();

        fragmentManager.executePendingTransactions();
        final DotViewFragment mainfragment = (DotViewFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);


        button3 = (Button) findViewById(R.id.button3);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainfragment.onRandomDot();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popup = new PopupMenu(MainActivity.this, button3);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        switch (item.getItemId()){
                            case R.id.capscreen:
                                Bitmap bmpscr = mainfragment.capturePicture();
                                mainfragment.savePic(bmpscr);
                                mainfragment.sharePic();
                                return true;
                            case R.id.cappic:
                                Bitmap bmppic = mainfragment.captureDot();
                                mainfragment.savePic(bmppic);
                                mainfragment.sharePic();

                                return true;
                            case R.id.clearscreen:
                                mainfragment.clearScreen();
                                return true;
                            default:
                                return MainActivity.this.onOptionsItemSelected(item);
                        }
                    }});
                popup.show();
            }
        });
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


    @Override
    public void onEditClicked(DotSimple editdot) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, new EditDotFragment())
                .addToBackStack(null)
                .commit();

    }
}

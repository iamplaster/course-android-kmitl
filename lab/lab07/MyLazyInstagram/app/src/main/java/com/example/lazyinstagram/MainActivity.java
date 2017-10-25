package com.example.lazyinstagram;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lazyinstagram.adapter.PostAdapter;
import com.example.lazyinstagram.adapter.PostAdapterList;
import com.example.lazyinstagram.api.LazyInstagramApi;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private PostAdapter postAdapter;
    private PostAdapterList postAdapterList;
    private RecyclerView recyclerView;
    private boolean isPostGrid = true;
    private int accPosition = 1;
    private RecyclerView.LayoutManager layoutManagerGrid = new GridLayoutManager(this, 3);
    private RecyclerView.LayoutManager layoutManagerList = new LinearLayoutManager(this);
    private int[] followCheck = {0, 0, 0};
    private int[] updateFollow = {0, 0, 0};
    private boolean flagUpdateFollow = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        postAdapter = new PostAdapter(this);
        postAdapterList = new PostAdapterList(this);

        recyclerView = findViewById(R.id.list);

        if(isPostGrid){
            recyclerView.setLayoutManager(layoutManagerGrid);
        }

        Button swap = findViewById(R.id.swap);
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPostGrid){
                    recyclerView.setLayoutManager(layoutManagerList);
                    isPostGrid = false;
                    recyclerView.setAdapter(postAdapterList);
                }else {
                    recyclerView.setLayoutManager(layoutManagerGrid);
                    isPostGrid = true;
                    recyclerView.setAdapter(postAdapter);
                }
                postAdapter.notifyDataSetChanged();
            }
        });

        Spinner accSpinner = findViewById(R.id.accSpinner);
        accSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.rgb(10, 10, 10));
                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
                ((TextView) adapterView.getChildAt(0)).setText("@" + adapterView.getItemAtPosition(i).toString());
                if(i == adapterView.getCount()-1){
                    accPosition = 0;
                }else{
                    accPosition = i + 1;
                }
                getUserProfile(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    private  void getUserProfile(String name) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LazyInstagramApi.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LazyInstagramApi api = retrofit.create(LazyInstagramApi.class);

        Call<UserProfile> call = api.getProfile(name);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(response.isSuccessful()){
                    UserProfile userProfile = response.body();
                    postAdapter.setPostList(userProfile.getPosts());
                    postAdapterList.setPostList(userProfile.getPosts());
                    if(isPostGrid){
                        recyclerView.setAdapter(postAdapter);
                    }else {
                        recyclerView.setAdapter(postAdapterList);
                    }

                    if(accPosition == 0){
                        if(updateFollow[updateFollow.length-1] == 0){
                            flagUpdateFollow = true;
                        }else {
                            flagUpdateFollow = false;
                        }
                    }else{
                        if(updateFollow[accPosition-1] == 0){
                            flagUpdateFollow = true;
                        }else {
                            flagUpdateFollow = false;
                        }
                    }

                    if(flagUpdateFollow){
                        if(userProfile.getIsFollow().equals("true")){
                            if(accPosition == 0){
                                followCheck[followCheck.length-1] = 1;
                                updateFollow[followCheck.length-1] = 1;
                            }else {
                                followCheck[accPosition-1] = 1;
                                updateFollow[accPosition-1] = 1;
                            }
                        }else{
                            if(accPosition == 0){
                                followCheck[followCheck.length-1] = 0;
                                updateFollow[followCheck.length-1] = 1;
                            }else {
                                followCheck[accPosition-1] = 0;
                                updateFollow[accPosition-1] = 1;
                            }
                        }
                    }

                    final Button followbttn = findViewById(R.id.followBttn);
                    setFollowingBttn(followbttn);
                    followbttn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(accPosition == 0){
                                setFollowing(followCheck.length-1);
                            }else{
                                setFollowing(accPosition-1);
                            }
                            setFollowingBttn(followbttn);
                        }
                    });

                    ImageView imgProfile = findViewById(R.id.ImgProfile);
                    Glide.with(MainActivity.this).load(userProfile.getUrlProfile()).into(imgProfile);

                    TextView txtpost = findViewById(R.id.txtPost);
                    txtpost.setText(userProfile.getPost());

                    TextView txtfollower = findViewById(R.id.txtFollower);
                    txtfollower.setText(userProfile.getFollower());

                    TextView txtfollowing = findViewById(R.id.txtFollowing);
                    txtfollowing.setText(userProfile.getFollowing());

                    TextView txtbio = findViewById(R.id.txtBio);
                    txtbio.setText(userProfile.getBio());


                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.d("TAG", "ON FAILURE");
            }
        });
    }

    public void setFollowing(int position){
        if(followCheck[position] == 0){
            followCheck[position] = 1;
        }else{
            followCheck[position] = 0;
        }
    }

    public void setFollowingBttn(Button followbttn){
        if(accPosition == 0){
            if(followCheck[followCheck.length-1] == 1){
                followbttn.setText("Following");
                followbttn.setBackgroundColor(Color.rgb(144,164,174));
            }else{
                followbttn.setText("Follow");
                followbttn.setBackgroundColor(Color.rgb(3,169,244));
            }

        }else {
            if(followCheck[accPosition-1] == 1){
                followbttn.setText("Following");
                followbttn.setBackgroundColor(Color.rgb(144,164,174));
            }else{
                followbttn.setText("Follow");
                followbttn.setBackgroundColor(Color.rgb(3,169,244));
            }

        }
    }


}

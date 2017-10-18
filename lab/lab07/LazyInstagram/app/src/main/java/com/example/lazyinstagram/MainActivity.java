package com.example.lazyinstagram;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private String[] account = {"android", "nature", "cartoon"};
    private int accPosition = 2;
    private RecyclerView.LayoutManager layoutManagerGrid = new GridLayoutManager(this, 3);
    private RecyclerView.LayoutManager layoutManagerList = new LinearLayoutManager(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserProfile("nature");

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
        Button swapuser = findViewById(R.id.userswap);
        swapuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserProfile(account[accPosition]);
                if(accPosition == 2){
                    accPosition = 0;
                }else {
                    accPosition++;
                }

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

                    TextView txtname = (TextView) findViewById(R.id.TxtName);
                    txtname.setText("@" + userProfile.getUser());

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


}

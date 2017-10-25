package com.example.lazyinstagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lazyinstagram.Post;
import com.example.lazyinstagram.R;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Holder>{
    private final Context context;
    private List<Post> postList = null;
    private int count;
    private LayoutInflater inflater;
    private View itemView;


    public PostAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        itemView = inflater.inflate(R.layout.post_item, null, false);
        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ImageView image = holder.image;
        Glide.with(context).load(postList.get(position).getUrl()).into(image);

        TextView comment = holder.comment;
        TextView like = holder.like;
        String commentCount = postList.get(position).getComment();
        String likeCount = postList.get(position).getLike();
        if(commentCount.length() > 3){
            commentCount = Character.toString(commentCount.charAt(0)) + "K";
        }
        if(likeCount.length() > 3){
            likeCount = Character.toString(likeCount.charAt(0)) + "K";
        }
        comment.setText("C: "+ commentCount);
        like.setText("L: " + likeCount);

    }

    @Override
    public int getItemCount() {
        if(postList == null){
            count = 0;
        }else {
            count = postList.size();
        }
        return count;
    }

    static class Holder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView comment;
        public TextView like;

        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_main);
            comment = itemView.findViewById(R.id.txtComment);
            like = itemView.findViewById(R.id.txtLike);

        }
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }



}

package com.example.lazyinstagram;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("follower")
    @Expose
    private String follower;
    @SerializedName("following")
    @Expose
    private String following;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    @SerializedName("urlProfile")
    @Expose
    private String urlProfile;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("isFollow")
    @Expose
    private String isFollow;

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getBio() {
        return bio;
    }

    public String getFollower() {
        return follower;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getFollowing() {
        return following;

    }

    public String getPost() {
        return post;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

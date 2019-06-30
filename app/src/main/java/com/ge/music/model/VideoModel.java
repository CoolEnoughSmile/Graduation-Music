package com.ge.music.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoModel implements Parcelable {

    private String videoName;
    private String poster;
    private String url;
    private String playCount;
    private String singer;
    private int videoId;

    public VideoModel() {
    }

    public VideoModel(String videoName, String poster, String url, String playCount, String singer,int videoId) {
        this.videoName = videoName;
        this.poster = poster;
        this.url = url;
        this.playCount = playCount;
        this.singer = singer;
        this.videoId = videoId;
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "videoName='" + videoName + '\'' +
                ", poster='" + poster + '\'' +
                ", url='" + url + '\'' +
                ", playCount='" + playCount + '\'' +
                ", singer='" + singer + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoName);
        dest.writeString(poster);
        dest.writeString(url);
        dest.writeString(playCount);
        dest.writeString(singer);
        dest.writeInt(videoId);
    }

    public VideoModel(Parcel source) {
        videoName = source.readString();
        poster = source.readString();
        url = source.readString();
        playCount = source.readString();
        singer = source.readString();
        videoId = source.readInt();
    }
}

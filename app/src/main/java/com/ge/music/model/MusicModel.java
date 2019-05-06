package com.ge.music.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicModel implements Parcelable {

    private String musicName;
    private String poster;
    private String url;
    private String playCount;
    private String singer;
    private String album;
    private String lrc;

    public MusicModel() {
    }

    public MusicModel(String musicName, String poster, String url, String playCount, String singer, String album, String lrc) {
        this.musicName = musicName;
        this.poster = poster;
        this.url = url;
        this.playCount = playCount;
        this.singer = singer;
        this.album = album;
        this.lrc = lrc;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "musicName='" + musicName + '\'' +
                ", poster='" + poster + '\'' +
                ", url='" + url + '\'' +
                ", playCount='" + playCount + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", lrc='" + lrc + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musicName);
        dest.writeString(poster);
        dest.writeString(url);
        dest.writeString(playCount);
        dest.writeString(singer);
        dest.writeString(album);
        dest.writeString(lrc);
    }

    public MusicModel(Parcel source) {
        musicName = source.readString();
        poster = source.readString();
        url = source.readString();
        playCount = source.readString();
        singer = source.readString();
        album = source.readString();
        lrc = source.readString();
    }

    /**
     * 负责反序列化
     */
    public static final Creator<MusicModel> CREATOR = new Creator<MusicModel>() {
        /**
         * 从序列化对象中，获取原始的对象
         * @param source
         * @return
         */
        @Override
        public MusicModel createFromParcel(Parcel source) {
            return new MusicModel(source);
        }

        /**
         * 创建指定长度的原始对象数组
         * @param size
         * @return
         */
        @Override
        public MusicModel[] newArray(int size) {
            return new MusicModel[0];
        }
    };
}

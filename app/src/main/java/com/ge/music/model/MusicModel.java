package com.ge.music.model;

public class MusicModel {

    private String musicName;
    private String poster;
    private String url;
    private String playCount;
    private String singer;
    private String album;

    public MusicModel() {
    }

    public MusicModel(String musicName, String poster, String url, String playCount, String singer, String album) {
        this.musicName = musicName;
        this.poster = poster;
        this.url = url;
        this.playCount = playCount;
        this.singer = singer;
        this.album = album;
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

    @Override
    public String toString() {
        return "MusicModel{" +
                "musicName='" + musicName + '\'' +
                ", poster='" + poster + '\'' +
                ", url='" + url + '\'' +
                ", playCount='" + playCount + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}

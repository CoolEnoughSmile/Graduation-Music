package com.ge.music.media;

import com.ge.music.model.MusicModel;

public interface MusicPlayerStatusChangeListener {


    void onMusicChange(MusicModel musicModel);

    void onPlayerError();

    void onComplete();
}

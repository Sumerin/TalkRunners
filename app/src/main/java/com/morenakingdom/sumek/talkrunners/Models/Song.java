package com.morenakingdom.sumek.talkrunners.Models;

import android.support.annotation.NonNull;

/**
 * Created by sumek on 2/17/18.
 */

public class Song implements Comparable <Song> {
    private long id;
    private String title;
    private String artist;

    public Song(long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public int compareTo(@NonNull Song b) {
        return title.compareTo( b.getTitle() );
    }
}

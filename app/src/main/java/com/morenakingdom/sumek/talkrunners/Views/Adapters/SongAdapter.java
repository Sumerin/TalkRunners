package com.morenakingdom.sumek.talkrunners.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.morenakingdom.sumek.talkrunners.Models.Song;
import com.morenakingdom.sumek.talkrunners.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sumek on 2/17/18.
 */

public class SongAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List <Song> songs;

    public SongAdapter(Context context, List <Song> songs) {
        this.inflater = LayoutInflater.from( context );
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get( position );
    }

    @Override
    @SuppressWarnings("SuspiciousCall")
    public long getItemId(int position) {
        return songs.indexOf( getItem( position ) );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            if (inflater != null) {
                convertView = inflater.inflate( R.layout.song, null );
            }

            holder = new ViewHolder();

            holder.title = convertView.findViewById( R.id.title );
            holder.artist = convertView.findViewById( R.id.artist );

            Song song = (Song) getItem( position );

            holder.title.setText( song.getTitle() );
            holder.artist.setText( song.getArtist() );
        }
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView artist;
    }
}

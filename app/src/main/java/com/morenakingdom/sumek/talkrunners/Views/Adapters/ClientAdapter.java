package com.morenakingdom.sumek.talkrunners.Views.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.R;

import java.util.List;

/**
 * Adapter to handling the listView of clients.
 * Created by sumek on 1/6/18.
 */
public class ClientAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    List <Client> clients;


    public ClientAdapter(Context context, List <Client> clients) {
        this.inflater = LayoutInflater.from( context );
        this.clients = clients;
    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int position) {
        return clients.get( position );
    }

    @Override
    @SuppressWarnings("SuspiciousCall")
    public long getItemId(int position) {
        return clients.indexOf( getItem( position ) );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            if (inflater != null) {
                convertView = inflater.inflate( R.layout.client, null );
            }

            holder = new ViewHolder();

            holder.username = convertView.findViewById( R.id.username );
            holder.ipAddress = convertView.findViewById( R.id.ipAddress );

            Client client = (Client) getItem( position );

            holder.username.setText( client.getNickname() );
            holder.ipAddress.setText( client.getFullIp() );

        }
        return convertView;
    }

    private class ViewHolder {
        ImageView pic;
        TextView username;
        TextView ipAddress;
    }
}

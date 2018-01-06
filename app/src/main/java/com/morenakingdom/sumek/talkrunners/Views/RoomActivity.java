package com.morenakingdom.sumek.talkrunners.Views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.morenakingdom.sumek.talkrunners.Controllers.MainController;
import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.HandlerMessageType;
import com.morenakingdom.sumek.talkrunners.R;

public class RoomActivity extends AppCompatActivity {
    private ListView list;
    List <Client> clients;
    ClientAdapter adapter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_room );

        initHandler();

        list = findViewById( R.id.listView1 );

        //clients = MainController.getInstance().getClients();
        this.clients = new ArrayList <>();


        testData();

        adapter = new ClientAdapter( this, this.clients );

        list.setAdapter( adapter );
    }

    private void testData() {

        Thread test = new Thread( () -> {
            try {
                Thread.sleep( 3000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );
            this.clients.add( new Client( "Adam", "192.168.0.1", 1256 ) );
            this.clients.add( new Client( "Andrzej", "192.168.0.2", 1236 ) );
            this.clients.add( new Client( "Witek", "192.168.0.4", 1256 ) );
            this.clients.add( new Client( "Antek", "192.168.0.6", 1256 ) );
            this.clients.add( new Client( "Alojzy", "192.168.0.9", 1256 ) );

            Message msg = handler.obtainMessage();
            msg.what = HandlerMessageType.UPDATE_LISTVIEW;
            handler.sendMessage( msg );
        } );

        test.start();
    }

    private void initHandler() {
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HandlerMessageType.UPDATE_LISTVIEW) {
                    adapter.notifyDataSetChanged();
                }
                super.handleMessage( msg );
            }
        };
    }
}

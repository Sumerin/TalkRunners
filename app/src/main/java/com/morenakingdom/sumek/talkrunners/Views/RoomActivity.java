package com.morenakingdom.sumek.talkrunners.Views;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.morenakingdom.sumek.talkrunners.Controllers.MainController;
import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.HandlerMessageType;
import com.morenakingdom.sumek.talkrunners.R;
import com.morenakingdom.sumek.talkrunners.Views.Adapters.ClientAdapter;

/**
 * Prototype till functionality will be completed.
 */
public class RoomActivity extends AppCompatActivity {
    private ListView list;
    List <Client> clients;
    ClientAdapter adapter;
    static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_room );

        initHandler();

        list = findViewById( R.id.clientsList );

        this.clients = MainController.getInstance().getClients();

        adapter = new ClientAdapter( this, this.clients );

        list.setAdapter( adapter );
    }

    @SuppressLint("HandlerLeak")// handler reference only here
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

    public void synchronize(View view) {
        MainController.getInstance().syncClientsList();
        //adapter.clients.addAll( MainController.getInstance().getClients() );
        //adapter.notifyDataSetChanged();
    }

}




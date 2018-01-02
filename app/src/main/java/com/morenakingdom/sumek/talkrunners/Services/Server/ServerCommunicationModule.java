package com.morenakingdom.sumek.talkrunners.Services.Server;

import android.provider.ContactsContract;

import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.Command;
import com.morenakingdom.sumek.talkrunners.Models.ControlData;
import com.morenakingdom.sumek.talkrunners.Services.CommunicationModule;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by sumek on 1/2/18.
 */

class ServerCommunicationModule extends CommunicationModule {

    private ServerService serverService;


    ServerCommunicationModule(ServerService serverService, Socket socket) {
        super( socket );
        this.serverService = serverService;
    }


    @Override
    protected void processData(ControlData data) {
        switch (data.header) {
            case INTRODUCE:
                serverService.addClient( data.client );
                break;
            case SYNC_REQUEST:
                System.out.println( "Sync_Request Acquired" );
                sendSync();
                break;

            default:
                //TODO:Exception
        }
    }

    private void sendSync() {
        try {
            for ( Client client : serverService.clients ) {
                send( Command.SYNC_RESPONSE, client );
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO:Exception
        }
    }

}

package com.morenakingdom.sumek.talkrunners.Services.Server;

import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.Command;
import com.morenakingdom.sumek.talkrunners.Models.ControlData;
import com.morenakingdom.sumek.talkrunners.Services.ReceiverDataModule;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by sumek on 1/2/18.
 */

class ServerReceiverDataModule extends ReceiverDataModule {

    private ServerService serverService;


    ServerReceiverDataModule(ServerService serverService, Socket socket) {
        super( socket );
        this.serverService = serverService;
    }


    @Override
    protected void processData(ControlData data) {
        System.out.println( "Server: " + data.header + " Aquiared!!" );
        switch (data.header) {
            case INTRODUCE:
                serverService.addClient( data.client );
                break;
            case SYNC_REQUEST:
                sendSync();
                break;

            default:
                //TODO:Exception
        }
    }

    private void sendSync() {
        try {
            for ( Client client : serverService.clients ) {
                forceSend( Command.SYNC_RESPONSE, client );
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO:Exception
        }
    }

}

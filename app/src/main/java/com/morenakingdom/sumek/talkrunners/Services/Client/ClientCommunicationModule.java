package com.morenakingdom.sumek.talkrunners.Services.Client;

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

class ClientCommunicationModule extends CommunicationModule {

    private ClientService clientService;

    protected ClientCommunicationModule(ClientService clientService, Socket socket) {
        super( socket );
        this.clientService = clientService;
        sendSyncRequest();
    }

    private void sendSyncRequest() {
        try {
            ControlData data = new ControlData();
            data.header = Command.SYNC_REQUEST;
            data.client = null;
            outputStream.writeObject( data );
            System.out.println( "Sync_Request sended" );
        } catch (IOException e) {
            e.printStackTrace();
            //TODO:Exception
        }
    }

    @Override
    protected void processData(ControlData data) {

        switch (data.header) {
            case SYNC_RESPONSE:
                clientService.addClient( data.client );
                break;

            default:
                //TODO:Exception
        }
    }

}

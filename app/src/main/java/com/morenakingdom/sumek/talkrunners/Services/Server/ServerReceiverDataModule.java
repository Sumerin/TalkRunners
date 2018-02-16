package com.morenakingdom.sumek.talkrunners.Services.Server;

import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.Command;
import com.morenakingdom.sumek.talkrunners.Models.ControlData;
import com.morenakingdom.sumek.talkrunners.Services.ReceiverDataModule;

import java.io.IOException;
import java.net.Socket;

/**
 * Full implementation of handling the income packet and process it.
 * Created by sumek on 1/2/18.
 */
class ServerReceiverDataModule extends ReceiverDataModule {

    /**
     * Handler to the related service.
     */
    private ServerService serverService;

    /**
     * @param serverService Related service.
     * @param socket        Socket with the connection to client.
     */
    ServerReceiverDataModule(ServerService serverService, Socket socket) {
        super( socket );
        this.serverService = serverService;
    }

    /**
     * Implementation of resolving and executing incoming message.
     *
     * @param data Incoming data
     */
    @Override
    protected void processData(ControlData data) {
        System.out.println( "Server: " + data.header + " Acquired!!" );
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

    /**
     * Sending the actual clients as a response for request.
     */
    private void sendSync() {
        try {
            for ( Client client : serverService.getClients() ) {
                forceSend( Command.SYNC_RESPONSE, client );
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO:Exception
        }
    }

}

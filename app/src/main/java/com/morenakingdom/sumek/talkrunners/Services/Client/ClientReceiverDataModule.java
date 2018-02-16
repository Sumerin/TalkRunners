package com.morenakingdom.sumek.talkrunners.Services.Client;

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
class ClientReceiverDataModule extends ReceiverDataModule {

    /**
     * Handler to the related service.
     */
    private ClientService clientService;

    /**
     * @param clientService Related service.
     * @param socket        Socket with the connection to server.
     * @throws IOException Sth wrong with streams.
     */
    ClientReceiverDataModule(ClientService clientService, Socket socket) throws IOException {
        super( socket );
        this.clientService = clientService;
        Client cl = new Client( android.os.Build.MODEL, socket.getLocalAddress().toString(), socket.getLocalPort() );
        forceSend( Command.INTRODUCE, cl );
        forceSend( Command.SYNC_REQUEST, null );
    }

    /**
     * Implementation of resolving and executing incoming message.
     *
     * @param data Incoming data
     */
    @Override
    protected void processData(ControlData data) {
        System.out.println( "Client: " + data.header + " Aquiared!!" );
        switch (data.header) {
            case SYNC_RESPONSE:
                clientService.addClient( data.client );
                break;
            case PLAY_TO:
                break;
            case STOP_MUSIC:
                break;
            default:
                //TODO:Exception
        }
    }

}

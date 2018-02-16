package com.morenakingdom.sumek.talkrunners.Services.Server;

import com.morenakingdom.sumek.talkrunners.Services.Module;

import java.io.IOException;
import java.net.Socket;

/**
 * The module handling the queue of incoming connection request.
 * Created by sumek on 1/2/18.
 */
class ConnectionEstablishedModule extends Module {

    /**
     * Handler to the related service.
     */
    private ServerService serverService;

    /**
     * @param serverService Related service.
     */
    ConnectionEstablishedModule(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket sock = serverService.getServerSocket().accept();
                startCommunication( sock );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create an instance of module that should handle the incoming message and add it to ServerService resource
     *
     * @param sock With associated connection.
     */
    private void startCommunication(Socket sock) {
        ServerReceiverDataModule listener = new ServerReceiverDataModule( serverService, sock );
        Thread th = new Thread( listener );
        th.setName( listener.getClass().getName() );
        th.start();

        listener.setThread( th );

        serverService.addClientListener( listener );

        System.out.println( "New connection established!!!" );
    }

}

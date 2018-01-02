package com.morenakingdom.sumek.talkrunners.Services.Server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by sumek on 1/2/18.
 */

class ConnectionEstablishedModule implements Runnable {

    private ServerService serverService;

    ConnectionEstablishedModule(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket sock = serverService.serverSocket.accept();
                serverService.addClient(sock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

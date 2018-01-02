package com.morenakingdom.sumek.talkrunners.Services.Server;

import java.net.Socket;

/**
 * Created by sumek on 1/2/18.
 */

public class SocketListnerModule implements Runnable {

    private ServerService serverService;

    private Socket socket;

    SocketListnerModule(ServerService serverService, Socket socket) {

        this.serverService = serverService;
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}

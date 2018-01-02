package com.morenakingdom.sumek.talkrunners.Services.Client;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by sumek on 1/2/18.
 */

public class ClientService {

    Socket commandSocket;

    DatagramSocket udpSocket;

    public ClientService(Socket sock) {
        this.commandSocket = sock;
    }

    void runAudioModule() {

    }
}

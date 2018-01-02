package com.morenakingdom.sumek.talkrunners.Models;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * Created by sumek on 1/1/18.
 */

public class ClientDep {
    public Socket tcpConnection;
    public DatagramSocket udpConnection;

    public ClientDep(Socket sock) {
        this.tcpConnection = sock;
    }
}

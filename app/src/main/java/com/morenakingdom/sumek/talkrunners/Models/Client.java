package com.morenakingdom.sumek.talkrunners.Models;

import java.net.DatagramSocket;
import java.net.Socket;

/**
 * Created by sumek on 1/1/18.
 */

public class Client {
    public Socket tcpConnection;
    public DatagramSocket udpConnection;

    public Client(Socket sock) {
        this.tcpConnection = sock;
    }
}

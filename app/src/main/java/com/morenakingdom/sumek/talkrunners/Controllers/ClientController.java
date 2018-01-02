package com.morenakingdom.sumek.talkrunners.Controllers;

import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.Models.Data;
import com.morenakingdom.sumek.talkrunners.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by sumek on 12/17/17.
 */

public class ClientController implements ConnectionController {

    private static ClientController instance = null;

    private Socket tcpSocket;

    public static ClientController getInstance() {

        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    private ClientController() {

    }

    @Override
    public void close() {

    }

    public void connect(String ip) throws ClientException {

        System.out.println("Connecting...");

        new Thread(() ->
        {
            try {
                InetAddress addr = InetAddress.getByName(ip);
                tcpSocket = new Socket(addr, DEFAULT_PORT);
            } catch (Exception e) {
                e.printStackTrace();
                String message = String.format("Cannot connect to the server %s : %s", ip, e.getMessage());
            }
        }).start();


    }

}

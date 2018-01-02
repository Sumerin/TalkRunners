package com.morenakingdom.sumek.talkrunners.Controllers;

import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.Services.Client.ClientService;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by sumek on 12/17/17.
 */

public class ClientController implements ConnectionController {

    private static ClientController instance = null;

    private Socket tcpSocket;

    private ClientService service;

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



        new Thread(() ->
        {
            try {
                System.out.println( "Connecting..." );
                InetAddress addr = InetAddress.getByName(ip);
                tcpSocket = new Socket(addr, DEFAULT_PORT);
                service = new ClientService( tcpSocket );
            } catch (Exception e) {
                e.printStackTrace();
                String message = String.format("Cannot connect to the server %s : %s", ip, e.getMessage());
                //TODO:Exception
            }
        }).start();


    }

}

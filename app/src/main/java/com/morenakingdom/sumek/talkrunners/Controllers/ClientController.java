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

    private ClientService service;

    public static ClientController getInstance() {

        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    private ClientController() {
        service = new ClientService();
    }

    @Override
    public void close() {

    }

    public void connect(String ip) throws ClientException {

        new Thread( () ->
        {
            service.connect( ip, DEFAULT_PORT );
        }).start();

    }

}

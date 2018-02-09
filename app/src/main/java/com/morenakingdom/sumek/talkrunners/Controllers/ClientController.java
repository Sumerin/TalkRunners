package com.morenakingdom.sumek.talkrunners.Controllers;

import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Services.Client.ClientService;

import java.util.List;

/**
 * Created by sumek on 12/17/17.
 */

public class ClientController implements ServerController {

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

        try {
            Thread connectingThread = new Thread( () ->
            {
                service.connect( ip, DEFAULT_PORT );
            } );

            connectingThread.start();
            connectingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //TODO: exception
        }

    }

    public List <Client> getClients() {
        return service.getClients();
    }

    public void syncClientsList() {
        service.synchronizeUsers();
    }
}

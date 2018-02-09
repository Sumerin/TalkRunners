package com.morenakingdom.sumek.talkrunners.Controllers;

import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.Exceptions.ServerException;
import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Services.Server.ServerService;

import java.util.List;

/**
 * Created by sumek on 12/17/17.
 */

public class MainController {

    private ServerService serverService;

    private static MainController instance = null;

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public void createServer() throws ServerException {
        createServer( ServerController.DEFAULT_PORT );
    }

    public void createServer(int port) throws ServerException {
        serverService = new ServerService(port);
        serverService.initConnectionEstablishedModule();
    }

    public void connect(String ip) throws ClientException {
        ClientController.getInstance().connect(ip);
    }

    public List <Client> getClients() {
        return ClientController.getInstance().getClients();
    }

    public void syncClientsList() {
        ClientController.getInstance().syncClientsList();
    }
}

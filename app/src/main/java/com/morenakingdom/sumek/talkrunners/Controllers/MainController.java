package com.morenakingdom.sumek.talkrunners.Controllers;

import android.text.Editable;

import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.Exceptions.ServerException;
import com.morenakingdom.sumek.talkrunners.Services.Server.ServerService;

import java.sql.Connection;

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
        createServer(ConnectionController.DEFAULT_PORT);
    }

    public void createServer(int port) throws ServerException {
        serverService = new ServerService(port);
    }

    public void connect(String ip) throws ClientException {
        ClientController.getInstance().connect(ip);
    }
}

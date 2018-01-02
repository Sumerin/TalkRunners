package com.morenakingdom.sumek.talkrunners.Services.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.morenakingdom.sumek.talkrunners.Exceptions.ServerException;
import com.morenakingdom.sumek.talkrunners.Models.Client;

/**
 * Created by sumek on 1/1/18.
 */

public class ServerService {

    ServerSocket serverSocket;

    List<Client> clients = null;

    private Thread connectionEstablished;

    private List<Thread> socketListeners;


    public ServerService(int port) throws ServerException {
        try {

            clients = new ArrayList<>();
            this.socketListeners = new ArrayList<>();
            this.serverSocket = new ServerSocket(port);

            initConnectionEstablishedModule();

        } catch (IOException e) {
            e.printStackTrace();
            String message = String.format("Cannot create server: %s", e.getMessage());
            throw new ServerException(message);
        }
    }


    void initConnectionEstablishedModule() {
        this.connectionEstablished = new Thread(new ConnectionEstablishedModule(this));
        this.connectionEstablished.start();
    }

    void addClient(Socket sock) {
        clients.add(new Client(sock));

        SocketListnerModule listener = new SocketListnerModule(this, sock);
        Thread th = new Thread(listener);
        th.start();

        socketListeners.add(th);

        System.out.println("New connection established!!!");

    }
}

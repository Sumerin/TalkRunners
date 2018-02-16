package com.morenakingdom.sumek.talkrunners.Services.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import com.morenakingdom.sumek.talkrunners.Exceptions.ServerException;
import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Services.Module;

/**
 * ServerService handles the role of api for whole functionality.
 * All connection resource and information are stored here.
 * Created by sumek on 1/1/18.
 */
public class ServerService {

    /**
     * Socket of the server income association queue.
     */
    private ServerSocket serverSocket;

    /**
     * List of All clients connected to the server.
     */
    private List <Client> clients = null; // should be clients dep

    /**
     * Handle fo the module of incoming association.
     */
    private ConnectionEstablishedModule connectionEstablished;

    /**
     * List of handle to the incoming communication modules.
     */
    private List <ServerReceiverDataModule> clientsListeners;

    List <Client> getClients() {
        return new ArrayList <>( clients );
    }

    ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * @param port Specified the server queue socket
     * @throws ServerException Maybe be useful in future.
     */
    public ServerService(int port) throws ServerException {
        try {
            clients = new ArrayList<>();
            this.clientsListeners = new ArrayList <>();
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            String message = String.format("Cannot create server: %s", e.getMessage());
            throw new ServerException(message);
        }
    }

    /**
     * Initialize the listening for connection.
     */
    public void initConnectionEstablishedModule() {
        this.connectionEstablished = new ConnectionEstablishedModule( this );
        Thread th = new Thread( connectionEstablished );
        th.setName( this.connectionEstablished.getClass().getName() );
        th.start();

        this.connectionEstablished.setThread( th );
    }

    /**
     * Add client to the clients collection. Thread save.
     *
     * @param client Client connected.
     */
    @SuppressWarnings("SynchronizeOnNonFinalField")
    void addClient(Client client) {
        synchronized (clients) {
            clients.add( client );

            System.out.println( "New Client" );
        }
    }

    /**
     * Add handler of the 'client income' module.
     *
     * @param listener Module which handles income connection.
     */
    void addClientListener(ServerReceiverDataModule listener) {
        clientsListeners.add( listener );
    }

    @Override
    protected void finalize() throws Throwable {
        cleanThreads();
        super.finalize();
    }

    /**
     * Release the resources type of thread and sockets (part).
     */
    public void cleanThreads() {
        if (connectionEstablished != null) {
            connectionEstablished.releaseThread();

            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (clientsListeners != null) {
            for ( Module mod : clientsListeners ) {
                mod.releaseThread();
            }
            clientsListeners = null;
        }
    }
}

package com.morenakingdom.sumek.talkrunners.Services.Client;

import com.morenakingdom.sumek.talkrunners.Models.Client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sumek on 1/2/18.
 */

public class ClientService {

    Socket commandSocket;

    DatagramSocket udpSocket;

    List <Client> clients = null;

    private Thread clientServerCommunicationThread;

    private ClientCommunicationModule clientServerCommunication;

    public ClientService(Socket sock) {
        clients = Collections.synchronizedList( new ArrayList <>() );
        this.commandSocket = sock;
        initClientCommunicationModule();
    }

    void runAudioModule() {

    }

    void initClientCommunicationModule() {
        this.clientServerCommunication = new ClientCommunicationModule( this, commandSocket );
        this.clientServerCommunicationThread = new Thread( clientServerCommunication );
        clientServerCommunicationThread.start();

    }

    public void addClient(Client client) {
        clients.add( client );
        System.out.println( "Client Added" );
    }

    @Override
    public void finalize() throws Throwable {
        clientServerCommunicationThread.interrupt();
        super.finalize();
    }
}

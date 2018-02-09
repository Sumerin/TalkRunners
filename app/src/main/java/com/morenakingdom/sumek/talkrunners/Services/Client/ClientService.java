package com.morenakingdom.sumek.talkrunners.Services.Client;

import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.Command;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
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

    private ClientReceiverDataModule clientServerCommunication;


    public ClientService() {
        clients = Collections.synchronizedList( new ArrayList <>() );
    }

    public ClientService(Socket sock) {
        this();
        this.commandSocket = sock;
    }

    void runAudioModule() {

    }

    void addClient(Client client) {
        clients.add( client );
        System.out.println( "Client Added" );
    }

    public List <Client> getClients() {
        return clients;
    }

    @Override
    public void finalize() throws Throwable {
        clientServerCommunicationThread.interrupt();
        super.finalize();
    }

    public void initClientCommunicationModule() throws ClientException {
        try {
            this.clientServerCommunication = new ClientReceiverDataModule( this, commandSocket );
            this.clientServerCommunicationThread = new Thread( clientServerCommunication );
            this.clientServerCommunicationThread.setName( this.clientServerCommunication.getClass().getName() );
            clientServerCommunicationThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            String message = String.format( "Initilization of communication module failed : %s", e.getMessage() );
            throw new ClientException( message );
        }


    }

    public void connect(String ip, int port) {

        try {
            System.out.println( "Connecting..." );
            InetAddress addr = InetAddress.getByName( ip );
            commandSocket = new Socket( addr, port );
            initClientCommunicationModule();
        } catch (Exception e) {
            e.printStackTrace();
            String message = String.format( "Connection to the server %s failed: %s", ip, e.getMessage() );
            //TODO:Exception
        }
    }

    public void synchronizeUsers() {
        try {
            clients.clear();
            clientServerCommunication.forceSend( Command.SYNC_REQUEST, null );
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println( "Excpetion SynchronizeUsers()" );
        }
    }
}

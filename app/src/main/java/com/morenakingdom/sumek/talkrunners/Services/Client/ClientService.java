package com.morenakingdom.sumek.talkrunners.Services.Client;

import com.morenakingdom.sumek.talkrunners.Exceptions.ClientException;
import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.Command;
import com.morenakingdom.sumek.talkrunners.Services.Bluetooth.RandomClass;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sumek on 1/2/18.
 */

public class ClientService {

    private static final int UDP_PORT = 5000;

    Socket commandSocket = null;

    DatagramSocket udpSocket = null;

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

    public AudioModule GetAudioModule() {
        try {
            initUdpSocket();
            System.out.println( "IP: " + RandomClass.getIPAddress( true ) + ":" + udpSocket.getLocalPort() );
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return new AudioModule( udpSocket );
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

    private void initClientCommunicationModule() throws ClientException {
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

    private void initUdpSocket() throws SocketException {
        if (udpSocket == null) {
            udpSocket = new DatagramSocket( UDP_PORT );
        }
    }

    public void connect(String ip, int port) {

        try {
            System.out.println( "Connecting..." );
            InetAddress addr = InetAddress.getByName( ip );
            commandSocket = new Socket( addr, port );
            initClientCommunicationModule();
            initUdpSocket();
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

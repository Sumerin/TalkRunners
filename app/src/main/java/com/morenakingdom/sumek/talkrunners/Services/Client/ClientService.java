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
 * ClientService handles the role of api for whole functionality.
 * All connection resource and information are stored here.
 * Created by sumek on 1/2/18.
 */
public class ClientService {

    private static final int UDP_PORT = 5000;

    /**
     * Socket to the server.
     */
    private Socket commandSocket = null;

    /**
     * Socket for the voice communication.
     */
    private DatagramSocket udpSocket = null;

    /**
     * Collection of other users associated with the server.
     */
    private List <Client> clients = null;

    /**
     * Handle to the incoming message module.
     */
    private ClientReceiverDataModule clientServerCommunication;

    /**
     * Copy of users associated with the server.
     */
    public List <Client> getClients() {
        return new ArrayList <>( clients );
    }

    public ClientService() {
        clients = Collections.synchronizedList( new ArrayList <>() );
    }// need to be normalize here is synchronized collection i server service is synchronized(list){}

    /**
     * For future Unit test.
     *
     * @param sock Mock of sock
     */
    public ClientService(Socket sock) {
        this();
        this.commandSocket = sock;
    }

    /**
     * Module in projection state finally it should be internal.
     * Creates/Awakes the module which stop the music and play the voice message.
     *
     * @return Module responsible for voice playing
     */
    public AudioModule GetAudioModule() {
        try {
            initUdpSocket();//is here only for test
            System.out.println( "IP: " + RandomClass.getIPAddress() + ":" + udpSocket.getLocalPort() );
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return new AudioModule( udpSocket );
    }

    /**
     * Add client to the collection of clients associated with server.
     * @param client Client connected to the server.
     */
    void addClient(Client client) {
        clients.add( client );
        System.out.println( "Client Added" );
    }


    @Override
    public void finalize() throws Throwable {
        clientServerCommunication.releaseThread();
        super.finalize();
    }

    /**
     * Initialize the handling of incoming message.
     * @throws ClientException For the future.
     */
    private void initClientCommunicationModule() throws ClientException {
        try {
            this.clientServerCommunication = new ClientReceiverDataModule( this, commandSocket );
            Thread th = new Thread( clientServerCommunication );
            th.setName( this.clientServerCommunication.getClass().getName() );
            th.start();
            this.clientServerCommunication.setThread( th );
        } catch (IOException e) {
            e.printStackTrace();
            String message = String.format( "Initialization of communication module failed : %s", e.getMessage() );
            throw new ClientException( message );
        }
    }

    /**
     * Initalize of the udpSocket used for voice communication.
     * @throws SocketException For the future.
     */
    private void initUdpSocket() throws SocketException {
        if (udpSocket == null) {
            udpSocket = new DatagramSocket( UDP_PORT );
        }
    }

    /**
     * Connect to the Server. Have to be called in Thread other then UI. (not sure)
     * @param ip Server IP.
     * @param port Server Port.
     */
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

    /**
     * Currently not working.
     * Clear the clients list and ask server for new one.
     */
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

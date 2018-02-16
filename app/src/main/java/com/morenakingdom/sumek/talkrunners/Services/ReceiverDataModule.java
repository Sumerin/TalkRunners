package com.morenakingdom.sumek.talkrunners.Services;

import com.morenakingdom.sumek.talkrunners.Models.Client;
import com.morenakingdom.sumek.talkrunners.Models.Command;
import com.morenakingdom.sumek.talkrunners.Models.ControlData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * base class for the handing income communication between server and client.
 * Created by sumek on 1/2/18.
 */
public abstract class ReceiverDataModule extends Module {

    /**
     * Socket of the connection between server and client.
     */
    private Socket socket;

    /**
     * streams from the socket.
     */
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    /**
     * Assign and invokes stream initialization.
     *
     * @param socket socket for the connection.
     */
    protected ReceiverDataModule(Socket socket) {
        this.socket = socket;
        initStreams();
    }

    /**
     * Stream initialization
     */
    private void initStreams() {
        try {
            this.outputStream = new ObjectOutputStream( new BufferedOutputStream( this.socket.getOutputStream() ) );
            forceSend( Command.CONNECT, null );
            this.inputStream = new ObjectInputStream( new BufferedInputStream( this.socket.getInputStream() ) );
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: Exception
        }
    }

    /**
     * Send the packet.
     *
     * @param header Type of command to be executed on the other side
     * @param client Associated client with the command.
     * @throws IOException Sth is wrong with output.
     */
    public void send(Command header, Client client) throws IOException {
        ControlData data = new ControlData();
        data.header = header;
        data.client = client;
        outputStream.writeObject( data );
    }

    /**
     * Force the packet to be send. It use send method
     *
     * @param header Type of command to be executed on the other side
     * @param client Associated client with the command.
     * @throws IOException Sth is wrong with output
     */
    public void forceSend(Command header, Client client) throws IOException {
        send( header, client );
        outputStream.flush();
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                ControlData data = (ControlData) inputStream.readObject();
                processData( data );
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                //TODO:Exception
            }
        }

    }

    /**
     * Process the incoming data need to be implemented depends on side.
     * @param data Incoming data
     */
    protected abstract void processData(ControlData data);


    @Override
    public void finalize() throws Throwable {
        forceSend( Command.DISCONNECT, null );
        socket.close();
        super.finalize();
    }
}

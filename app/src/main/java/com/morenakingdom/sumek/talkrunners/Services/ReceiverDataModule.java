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
 * Created by sumek on 1/2/18.
 */

/**
 * Gets the command between server and client.
 */
public abstract class ReceiverDataModule implements Runnable {

    protected Socket socket;

    protected ObjectInputStream inputStream;

    protected ObjectOutputStream outputStream;

    protected ReceiverDataModule(Socket socket) {
        this.socket = socket;
        initStreams();
    }


    protected void initStreams() {
        try {
            this.outputStream = new ObjectOutputStream( new BufferedOutputStream( this.socket.getOutputStream() ) );
            forceSend( Command.CONNECT, null );
            this.inputStream = new ObjectInputStream( new BufferedInputStream( this.socket.getInputStream() ) );
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: Exception
        }
    }

    public void send(Command header, Client client) throws IOException {
        ControlData data = new ControlData();
        data.header = header;
        data.client = client;
        outputStream.writeObject( data );
    }

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

    protected abstract void processData(ControlData data);


    @Override
    public void finalize() throws Throwable {
        forceSend( Command.DISCONNECT, null );
        super.finalize();
    }
}

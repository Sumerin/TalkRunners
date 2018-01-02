package com.morenakingdom.sumek.talkrunners.Services;

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

public abstract class CommunicationModule implements Runnable {

    protected Socket socket;

    protected ObjectInputStream inputStream;

    protected ObjectOutputStream outputStream;


    protected CommunicationModule(Socket socket) {
        this.socket = socket;
        initStreams();
    }


    protected void initStreams() {
        try {
            this.outputStream = new ObjectOutputStream( new BufferedOutputStream( this.socket.getOutputStream() ) );
            System.out.println( "OUTPUT Stream Initilized!!!" + this.getClass().getSimpleName() );
            this.inputStream = new ObjectInputStream( new BufferedInputStream( this.socket.getInputStream() ) );
            System.out.println( "INPUT Stream Initilized!!!" + this.getClass().getSimpleName() );
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: Exception
        }
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
        sendClose();
        super.finalize();
    }

    private void sendClose() throws IOException {
        ControlData data = new ControlData();
        data.header = Command.DISCONNECT;
        data.client = null;
        outputStream.writeObject( data );
    }
}

package com.morenakingdom.sumek.talkrunners.Services.Client;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import com.morenakingdom.sumek.talkrunners.Services.Module;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * Prototype of handling the voice income message;
 *  Created by sumek on 1/2/18.
 */
public class AudioModule extends Module {

    private final static String TEMP_FILE = "TalkRunnerTemp.dat";
    private final static int BUFFER_SIZE = 16384;
    private static final int INITIAL_KB_BUFFER = 96 * 10 / 8;

    private DatagramSocket udp;
    private File downloadTempFile;
    private Context context;
    private int totalKbRead = 0;
    private MediaPlayer mediaPlayer;
    private boolean isInterrupted;
    private int counter = 0;
    private final Handler handler = new Handler();

    AudioModule(DatagramSocket udp) {
        this.udp = udp;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        try {
            downloadAudioIncrement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadAudioIncrement() throws IOException {

        downloadTempFile = new File( context.getCacheDir(), TEMP_FILE );

        FileOutputStream out = new FileOutputStream( downloadTempFile );
        byte buf[] = new byte[BUFFER_SIZE];
        DatagramPacket datagramPacket = new DatagramPacket( buf, buf.length );

        int totalBytesRead = 0, incrementalBytesRead = 0;
        do {
            udp.receive( datagramPacket );
            int numread = datagramPacket.getLength();
            if (numread <= 0)
                break;
            out.write( datagramPacket.getData(), 0, numread );
            totalBytesRead += numread;
            incrementalBytesRead += numread;
            totalKbRead = totalBytesRead / 1000;

            testMediaBuffer();
            //fireDataLoadUpdate();
        } while (validateNotInterrupted());
    }

    private boolean validateNotInterrupted() {
        if (isInterrupted) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                //mediaPlayer.release();
            }
            return false;
        } else {
            return true;
        }
    }

    private void testMediaBuffer() {
        Runnable updater = () -> {
            if (mediaPlayer == null) {
                //  Only create the MediaPlayer once we have the minimum buffered data
                if (totalKbRead >= INITIAL_KB_BUFFER) {
                    try {
                        startMediaPlayer();
                    } catch (Exception e) {
                        System.out.println( "Error copying buffered content. " + e.getMessage() );
                    }
                }
            } else if (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() <= 1000) {
                //  NOTE:  The media player has stopped at the end so transfer any existing buffered data
                //  We test for < 1second of data because the media player can stop when there is still
                //  a few milliseconds of data left to play
                transferBufferToMediaPlayer();
            }
        };
        //new Thread(updater).start();
        handler.post( updater );
    }

    private void startMediaPlayer() {
        try {
            File bufferedFile = new File( context.getCacheDir(), "playingMedia" + (counter++) + ".dat" );

            // We double buffer the data to avoid potential read/write errors that could happen if the
            // download thread attempted to write at the same time the MediaPlayer was trying to read.
            // For example, we can't guarantee that the MediaPlayer won't open a file for playing and leave it locked while
            // the media is playing.  This would permanently deadlock the file download.  To avoid such a deadlock,
            // we move the currently loaded data to a temporary buffer file that we start playing while the remaining
            // data downloads.
            moveFile( downloadTempFile, bufferedFile );

            //Log.e(getClass().getName(),"Buffered File path: " + bufferedFile.getAbsolutePath());
            //Log.e(getClass().getName(),"Buffered File length: " + bufferedFile.length()+"");

            mediaPlayer = createMediaPlayer( bufferedFile );

            // We have pre-loaded enough content and started the MediaPlayer so update the buttons & progress meters.
            mediaPlayer.start();
            //startPlayProgressUpdater();
            //playButton.setEnabled(true);
        } catch (IOException e) {
            System.out.println( "AudioModule: " + e.getMessage() );
            //Log.e(getClass().getName(), "Error initializing the MediaPlayer.", e);
        }
    }

    private MediaPlayer createMediaPlayer(File mediaFile) throws IOException {
        MediaPlayer mPlayer = new MediaPlayer();
        mPlayer.setOnErrorListener(
                (mp, what, extra) -> {
                    //Log.e(getClass().getName(), "Error in MediaPlayer: (" + what +") with extra (" +extra +")" );
                    return false;
                } );

        //  It appears that for security/permission reasons, it is better to pass a FileDescriptor rather than a direct path to the File.
        //  Also I have seen errors such as "PVMFErrNotSupported" and "Prepare failed.: status=0x1" if a file path String is passed to
        //  setDataSource().  So unless otherwise noted, we use a FileDescriptor here.
        FileInputStream fis = new FileInputStream( mediaFile );
        mPlayer.setDataSource( fis.getFD() );
        mPlayer.prepare();
        return mPlayer;
    }

    private void transferBufferToMediaPlayer() {
        try {
            // First determine if we need to restart the player after transferring data...e.g. perhaps the user pressed pause
            boolean wasPlaying = mediaPlayer.isPlaying();
            int curPosition = mediaPlayer.getCurrentPosition();

            // Copy the currently downloaded content to a new buffered File.  Store the old File for deleting later.
            File oldBufferedFile = new File( context.getCacheDir(), "playingMedia" + counter + ".dat" );
            File bufferedFile = new File( context.getCacheDir(), "playingMedia" + (counter++) + ".dat" );

            //  This may be the last buffered File so ask that it be delete on exit.  If it's already deleted, then this won't mean anything.  If you want to
            // keep and track fully downloaded files for later use, write caching code and please send me a copy.
            bufferedFile.deleteOnExit();
            moveFile( downloadTempFile, bufferedFile );

            // Pause the current player now as we are about to create and start a new one.  So far (Android v1.5),
            // this always happens so quickly that the user never realized we've stopped the player and started a new one
            mediaPlayer.pause();

            // Create a new MediaPlayer rather than try to re-prepare the prior one.
            mediaPlayer = createMediaPlayer( bufferedFile );
            mediaPlayer.seekTo( curPosition );

            //  Restart if at end of prior buffered content or mediaPlayer was previously playing.
            //  NOTE:  We test for < 1second of data because the media player can stop when there is still
            //  a few milliseconds of data left to play
            boolean atEndOfFile = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() <= 1000;
            if (wasPlaying || atEndOfFile) {
                mediaPlayer.start();
            }

            // Lastly delete the previously playing buffered File as it's no longer needed.
            oldBufferedFile.delete();

        } catch (Exception e) {
            //Log.e(getClass().getName(), "Error updating to newly loaded content.", e);
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void interrupt() {
        //playButton.setEnabled(false);
        isInterrupted = true;
        validateNotInterrupted();
    }

    private void moveFile(File oldLocation, File newLocation) throws IOException {

        if (oldLocation.exists()) {
            BufferedInputStream reader = new BufferedInputStream( new FileInputStream( oldLocation ) );
            BufferedOutputStream writer = new BufferedOutputStream( new FileOutputStream( newLocation, false ) );
            try {
                byte[] buff = new byte[8192];
                int numChars;
                while ((numChars = reader.read( buff, 0, buff.length )) != -1) {
                    writer.write( buff, 0, numChars );
                }
            } catch (IOException ex) {
                throw new IOException( "IOException when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() );
            } finally {
                try {
                    writer.close();
                    reader.close();
                } catch (IOException ex) {
                    System.out.println( "AudioModule: " + ex.getMessage() );
                    //Log.e(getClass().getName(),"Error closing files when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() );
                }
            }
        } else {
            throw new IOException( "Old location does not exist when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() );
        }
    }

}

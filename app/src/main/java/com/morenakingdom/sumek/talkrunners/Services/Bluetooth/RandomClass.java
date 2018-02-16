package com.morenakingdom.sumek.talkrunners.Services.Bluetooth;

import android.bluetooth.BluetoothAdapter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


/**
 * Random clas for some randoms stuff testing manually.
 * Created by sumek on 2/9/18.
 */
public class RandomClass {
    public static String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        return myDevice.getName();
    }

    public static String getIPAddress() {
        try {
            List <NetworkInterface> interfaces = Collections.list( NetworkInterface.getNetworkInterfaces() );
            for ( NetworkInterface intf : interfaces ) {
                List <InetAddress> addrs = Collections.list( intf.getInetAddresses() );
                for ( InetAddress addr : addrs ) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();

                        boolean isIPv4 = sAddr.indexOf( ':' ) < 0;
                        if (isIPv4) {
                            return sAddr;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println( "Getting Ip Failed." );
        }
        return "";
    }
}

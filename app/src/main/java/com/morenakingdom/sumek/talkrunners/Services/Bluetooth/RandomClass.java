package com.morenakingdom.sumek.talkrunners.Services.Bluetooth;

import android.bluetooth.BluetoothAdapter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by sumek on 2/9/18.
 */

public class RandomClass {
    public static String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        return deviceName;
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List <NetworkInterface> interfaces = Collections.list( NetworkInterface.getNetworkInterfaces() );
            for ( NetworkInterface intf : interfaces ) {
                List <InetAddress> addrs = Collections.list( intf.getInetAddresses() );
                for ( InetAddress addr : addrs ) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf( ':' ) < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf( '%' ); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring( 0, delim ).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }
}

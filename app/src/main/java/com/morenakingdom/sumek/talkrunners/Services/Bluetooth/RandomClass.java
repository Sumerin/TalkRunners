package com.morenakingdom.sumek.talkrunners.Services.Bluetooth;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by sumek on 2/9/18.
 */

public class RandomClass {
    public static String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        return deviceName;
    }
}

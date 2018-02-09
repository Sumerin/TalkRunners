package com.morenakingdom.sumek.talkrunners.Models;

import java.io.Serializable;

/**
 * Created by sumek on 1/2/18.
 */

public enum Command implements Serializable {
    SYNC_REQUEST,
    SYNC_RESPONSE,
    CONNECT,
    DISCONNECT,
    PLAY_TO,
    STOP_MUSIC,
    INTRODUCE
}

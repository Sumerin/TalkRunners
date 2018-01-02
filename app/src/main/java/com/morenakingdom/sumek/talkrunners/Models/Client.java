package com.morenakingdom.sumek.talkrunners.Models;

import java.io.Serializable;

/**
 * Created by sumek on 1/2/18.
 */

public class Client implements Serializable {
    String nickname;
    String ip;
    int port;

    public Client(String nickname, String ip, int port) {
        this.nickname = nickname;
        this.ip = ip;
        this.port = port;
    }
}

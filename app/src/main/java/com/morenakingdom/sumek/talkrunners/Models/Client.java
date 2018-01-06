package com.morenakingdom.sumek.talkrunners.Models;

import java.io.Serializable;

/**
 * Created by sumek on 1/2/18.
 */

public class Client implements Serializable {

    private String nickname;
    private String ip;
    private int port;

    public Client(String nickname, String ip, int port) {
        this.nickname = nickname;
        this.ip = ip;
        this.port = port;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getFullIp() {
        return ip + ":" + port;
    }
}

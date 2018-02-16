package com.morenakingdom.sumek.talkrunners.Models;

import java.io.Serializable;

/**
 * Data exchanged between server and client.
 * Created by sumek on 1/2/18.
 */
public class ControlData implements Serializable {

    /**
     * Type of action.
     */
    public Command header;
    /**
     * Related client to the action
     */
    public Client client;
}

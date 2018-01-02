package com.morenakingdom.sumek.talkrunners.Services.Server;

import com.morenakingdom.sumek.talkrunners.Exceptions.ServerException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sumek on 1/2/18.
 */
public class ServerServiceTest {

    final static int PORT = 1234;

    ServerService service;

    @Before
    public void setUp() throws ServerException {
        service = new ServerService( PORT );
    }

    @After
    public void cleanUp() {
        service = null;
    }

    @Test
    public void initConnectionEstablishedModule() throws Exception {


    }

}
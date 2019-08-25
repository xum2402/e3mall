package com.xum.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-activemq.xml")
public class msgCunsumerTest {

    @Test
    public void msgConsumer() throws IOException {

        System.in.read();
    }
}

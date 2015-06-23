package com.ilopezluna.uniques.controller;

import com.ilopezluna.uniques.configuration.QueueConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ignasi on 9/6/15.
 */

@RestController
public class HitController {

    private final static Logger logger = LoggerFactory.getLogger(HitController.class);

    private final AtomicLong atomicLong = new AtomicLong(0l);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/hit")
    public void hit(@RequestParam(value="id") int id) {
        atomicLong.incrementAndGet();
        rabbitTemplate.convertAndSend(QueueConfiguration.queueName, id);
    }

    @RequestMapping("/count")
    public AtomicLong count() {
        return atomicLong;
    }
}

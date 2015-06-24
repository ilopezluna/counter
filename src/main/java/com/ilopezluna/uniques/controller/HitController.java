package com.ilopezluna.uniques.controller;

import com.ilopezluna.uniques.configuration.QueueConfiguration;
import com.ilopezluna.uniques.receiver.HitMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ignasi on 9/6/15.
 */

@RestController
public class HitController {

    private final static Logger logger = LoggerFactory.getLogger(HitController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/hit")
    public void hit(@RequestParam(value="id") int id) {
        final HitMessage hitMessage = new HitMessage(id);
        logger.debug("Send message to queue: " + id);
        rabbitTemplate.convertAndSend(QueueConfiguration.queueName, hitMessage);
    }
}

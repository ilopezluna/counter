package com.ilopezluna.uniques.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ignasi on 9/6/15.
 */

@RestController
public class UniquesController {

    private final static Logger logger = LoggerFactory.getLogger(UniquesController.class);
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hit")
    public void hit(@RequestParam(value="name", defaultValue="World") String name) {
        logger.info("Counter: " + counter);
    }
}

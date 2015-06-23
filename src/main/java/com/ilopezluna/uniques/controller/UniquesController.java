package com.ilopezluna.uniques.controller;

import com.ilopezluna.uniques.service.UniquesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Created by ignasi on 23/6/15.
 */
@RestController
public class UniquesController {

    private final static Logger logger = LoggerFactory.getLogger(UniquesController.class);

    @Autowired
    private UniquesService uniquesService;

    @RequestMapping("/uniques")
    public int uniques() {
        return uniquesService.count(LocalDate.now());
    }
}

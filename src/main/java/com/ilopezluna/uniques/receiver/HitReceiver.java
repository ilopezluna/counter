package com.ilopezluna.uniques.receiver;

import com.ilopezluna.uniques.service.UniquesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ignasi on 23/6/15.
 */
public class HitReceiver {

    private final static Logger logger = LoggerFactory.getLogger(HitReceiver.class);

    private final UniquesService uniquesService;

    public HitReceiver(UniquesService uniquesService) {
        this.uniquesService = uniquesService;
    }

    public void receiveMessage(int id) {
        uniquesService.hit(id);
    }
}

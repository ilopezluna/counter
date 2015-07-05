package com.ilopezluna.uniques.receiver;

import com.ilopezluna.uniques.dto.HitMessage;
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

    public void receiveMessage(HitMessage hitMessage) {
        logger.debug("Message received: " + hitMessage);
        if (hitMessage.getPath() != null && hitMessage.getLocalDate() != null) {
            uniquesService.hit(hitMessage.getId(), hitMessage.getPath(), hitMessage.getLocalDate());
        }
        else if(hitMessage.getLocalDate() != null) {
            uniquesService.hit(hitMessage.getLocalDate(), hitMessage.getId());
        }
        else if(hitMessage.getPath() != null) {
            uniquesService.hit(hitMessage.getPath(), hitMessage.getId());
        }
        else {
            uniquesService.hit(hitMessage.getId());
        }
    }
}

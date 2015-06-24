package com.ilopezluna.uniques.receiver;

import com.ilopezluna.uniques.service.UniquesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by ignasi on 24/6/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class HitReceiverTest {

    @Mock
    private UniquesService uniquesService;

    private HitReceiver hitReceiver;

    @Before
    public void setUp() throws Exception {
        hitReceiver = new HitReceiver(uniquesService);
    }

    @Test
    public void testReceiveMessage() throws Exception {
        int id = 1;
        String path = "path";
        LocalDate now = LocalDate.now();

        HitMessage hitMessage = new HitMessage(id);
        hitReceiver.receiveMessage(hitMessage);
        verify(uniquesService, times(1)).hit(id);

        hitMessage = new HitMessage(id, path);
        hitReceiver.receiveMessage(hitMessage);
        verify(uniquesService, times(1)).hit(path, id);

        hitMessage = new HitMessage(id, null, now);
        hitReceiver.receiveMessage(hitMessage);
        verify(uniquesService, times(1)).hit(now, id);

        hitMessage = new HitMessage(id, path, now);
        hitReceiver.receiveMessage(hitMessage);
        verify(uniquesService, times(1)).hit(id, path, now);
    }
}
package com.ilopezluna.uniques.controller;

import com.ilopezluna.uniques.receiver.HitMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ignasi on 13/6/15.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class HitsControllerTest {

    private MockMvc mvc;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setUp() throws Exception {
        HitController hitController = new HitController();
        ReflectionTestUtils.setField(hitController, "rabbitTemplate", rabbitTemplate);
        mvc = MockMvcBuilders.standaloneSetup(hitController).build();
    }

    @Test
    public void testHit() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hit?id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("")));
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(HitMessage.class));
    }

}
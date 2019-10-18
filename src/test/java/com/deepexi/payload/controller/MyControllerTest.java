package com.deepexi.payload.controller;

import com.deepexi.payload.PayloadSpringBootStarterTestApplicationTests;
import com.deepexi.payload.config.ApplicationErrorAutoConfiguration;
import com.deepexi.payload.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MyControllerTest extends PayloadSpringBootStarterTestApplicationTests {

    @Autowired
    private ApplicationContext context;


    @Test
    public void get() throws Exception {
        MvcResult result = super.mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.getString("code"), "1");
        ObjectMapper objectMapper = new ObjectMapper();
        User payload = objectMapper.readValue(jsonObject.getString("payload"), User.class);
        Assert.assertNotNull(payload);

    }



}

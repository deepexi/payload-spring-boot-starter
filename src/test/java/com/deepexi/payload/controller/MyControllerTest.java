package com.deepexi.payload.controller;

import com.deepexi.payload.PayloadSpringBootStarterTestApplicationTests;
import com.deepexi.payload.config.PayloadProperties;
import com.deepexi.payload.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static com.deepexi.payload.utils.PayloadMapUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MyControllerTest extends PayloadSpringBootStarterTestApplicationTests {

    @Test
    public void get() throws Exception {
        MvcResult result = super.mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.getString("code"), "1");
        ObjectMapper objectMapper = new ObjectMapper();
        User payload = objectMapper.readValue(jsonObject.getString(getFieldValue(PAYLOAD_DEFAULT_VALUE)), User.class);
        Assert.assertNotNull(payload);

    }

    @Test
    public void getCustomerSuccess() throws Exception {
        MvcResult result = super.mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertTrue(jsonObject.getBoolean(getFieldValue(SUCCESS_DEFAULT_VALUE)));


    }



}

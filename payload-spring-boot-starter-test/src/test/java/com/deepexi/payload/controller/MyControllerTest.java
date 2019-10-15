package com.deepexi.payload.controller;

import com.deepexi.payload.PayloadSpringBootStarterTestApplicationTests;
import com.deepexi.payload.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MyControllerTest extends PayloadSpringBootStarterTestApplicationTests {

    @Test
    public void get() throws Exception {
        MvcResult result = super.mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.getInt("code"), 1);
        ObjectMapper objectMapper = new ObjectMapper();
        User payload = objectMapper.readValue(jsonObject.getString("payload"), User.class);
        Assert.assertNotNull(payload);

    }

    @Test
    public void getErrorOfHtml() throws Exception {
        MvcResult result = super.mockMvc.perform(MockMvcRequestBuilders.get("/users/0")
                .contentType(MediaType.TEXT_HTML))
                .andReturn();
        Assert.assertNotNull(result);
    }

    @Test
    public void getErrorOfJson() throws Exception {
        MvcResult result = super.mockMvc.perform(MockMvcRequestBuilders.get("/users/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println("result = " + result.getResponse().getContentAsString());
    }
}

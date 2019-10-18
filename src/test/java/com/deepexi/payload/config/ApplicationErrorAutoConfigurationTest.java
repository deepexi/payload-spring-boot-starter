package com.deepexi.payload.config;

import com.deepexi.payload.exception.CustomerException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationErrorAutoConfigurationTest {

    private ServerProperties serverProperties = new ServerProperties();
    private PayloadProperties payloadProperties = new PayloadProperties();

//    private DefaultErrorAttributes errorAttributes = new DefaultErrorAttributes();

    private DefaultErrorAttributes errorAttributes = new ApplicationErrorAutoConfiguration(serverProperties, payloadProperties);

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private WebRequest webRequest = new ServletWebRequest(this.request);

    @Before
    public void setUp()  {
        webRequest = new ServletWebRequest(this.request);
    }

    @Test
    public void includeTimeStamp()  {
        this.request.addHeader("accept", "text/html");
        Map<String, Object> attributes = this.errorAttributes
                .getErrorAttributes(this.webRequest, false);
        assertThat(attributes.get("timestamp")).isInstanceOf(Date.class);

    }


    @Test
    public void mvcError()  {
        this.request.addHeader("accept", "text/html");

        RuntimeException ex = new RuntimeException("Test");
        ModelAndView modelAndView = this.errorAttributes.resolveException(this.request, null, null, ex);
        this.request.setAttribute("javax.servlet.error.exception", new RuntimeException("Ignored"));
        Map<String, Object> attributes = this.errorAttributes.getErrorAttributes(this.webRequest, false);
        assertThat(this.errorAttributes.getError(this.webRequest)).isSameAs(ex);
        assertThat(modelAndView).isNull();
        assertThat(attributes.get("message")).isEqualTo("Test");

    }

    @Test
    public void payloadBizError()  {
        this.request.addHeader("accept", "application/json");
        this.request.setAttribute("javax.servlet.error.status_code",404);

        this.request.setAttribute("javax.servlet.error.exception", new CustomerException());
        Map<String, Object> attributes = this.errorAttributes.getErrorAttributes(this.webRequest, false);
        assertThat(attributes.get("message")).isEqualTo("No message available");
        assertThat(attributes.get("code")).isEqualTo("00000-0000-0001");
    }

    @Test
    public void payloadBizError2()  {
        this.request.addHeader("accept", "application/json");
        this.request.setAttribute("javax.servlet.error.status_code",404);


        this.request.setAttribute("javax.servlet.error.exception", new RuntimeException());
        Map<String, Object> attributes = this.errorAttributes.getErrorAttributes(this.webRequest, false);
        assertThat(attributes.get("code")).isEqualTo("-1");
    }

    @Test
    public void payloadSystemError()  {
        this.request.addHeader("accept", "application/json");
        this.request.setAttribute("javax.servlet.error.status_code",500);

        this.request.setAttribute("javax.servlet.error.exception", new RuntimeException());
        Map<String, Object> attributes = this.errorAttributes.getErrorAttributes(this.webRequest, false);
        assertThat(attributes.get("code")).isEqualTo("-2");
    }
}
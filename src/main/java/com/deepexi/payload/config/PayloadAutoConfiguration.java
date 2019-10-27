package com.deepexi.payload.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(PayloadProperties.class)
public class PayloadAutoConfiguration implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Autowired
    private PayloadProperties payloadProperties;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(this.adapter.getReturnValueHandlers());
        for (HandlerMethodReturnValueHandler item : handlers) {
            int index = handlers.indexOf(item);
            if (RequestResponseBodyMethodProcessor.class.isAssignableFrom(item.getClass())) {
                handlers.add(index, new RequestResponseBodyMethodProcessorProxy((RequestResponseBodyMethodProcessor) item, payloadProperties));
                handlers.remove(item);
                break;
            }
        }
        adapter.setReturnValueHandlers(handlers);
    }

}
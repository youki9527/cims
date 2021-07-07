package com.cims.Config;

import com.cims.service.ScheduleInfoService;
import com.cims.util.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    //配置注入ScheduleInfoService
    @Autowired
    public void setScheduleInfoService(ScheduleInfoService scheduleInfoService){
        MyWebSocket.scheduleInfoService =scheduleInfoService;
    }


}
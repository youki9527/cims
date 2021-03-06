package com.cims.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cims.Config.GetHttpSessionConfig;
import com.cims.pojo.ManagerInfo;
import com.cims.pojo.ScheduleInfo;
import com.cims.service.ScheduleInfoService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfig.class)//获取httpsession中信息
@Component
public class MyWebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //声明service对象（先要配置）
    public static ScheduleInfoService scheduleInfoService;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws JsonProcessingException {

        //把用户信息从bhttpsession中取出
        HttpSession httpsession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        ManagerInfo managerInfo = (ManagerInfo) httpsession.getAttribute("manager");

        //获取客户端客户经理id
        System.out.println("经理ID为" + managerInfo.getMId());

        //创建scheduleInfo对象
        ScheduleInfo scheduleInfo=new ScheduleInfo();
        scheduleInfo.setScheduleMId(managerInfo.getMId());

        //调用scheduleInfoService方法
        HashMap<String, Object> map = new HashMap<String, Object>();
        map= scheduleInfoService.remind(scheduleInfo);

        //将java map对象转换为json对象
         JSONObject json = JSONObject.fromObject(map);
         //将json对象转换为字符串
        String str = json.toString();
//        ObjectMapper mapMapper= new ObjectMapper();
//        String json = "";
//        json= mapMapper.writeValueAsString(map);


        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
           // sendMessage(json);
            sendMessage(str);
            sendMessage("当前在线人数为" + getOnlineCount());
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        System.out.println("来自客户端的消息:" + message);
        System.out.println(session);
        //群发消息
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}

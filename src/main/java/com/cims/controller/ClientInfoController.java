package com.cims.controller;

import com.cims.pojo.ClientInfo;
import com.cims.service.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/client")
public class ClientInfoController {
    @Autowired
    ClientInfoService clientInfoByManagerService;

    //访问client-list页面
    @RequestMapping("/list")
    public String list(ClientInfo client, ModelMap m, HttpServletRequest request){
        //查询分页数据
        HashMap<String, Object> map=clientInfoByManagerService.select(client,request);
        //把数据传到前端
        m.put("clientInfo",map);
        //把用户输入的查询条件传到前端
        m.put("vv",client.getConValue());


        return "/admin/client-list";
    }

    //访问client-list-zhufu页面
    @RequestMapping("/zhufu")
    public String zhufu(ClientInfo client, ModelMap m, HttpServletRequest request){

        //查询分页数据
        HashMap<String, Object> map=clientInfoByManagerService.selectByBirth(client,request);
        //把数据传到前端
        m.put("clientInfoZhufu",map);
        return "/admin/client-zhufu";
    }


    //注入上传文件的绝对物理目录路径
    @Value("${file.address}")
    private String fileAddres;

    //注入访问上传文件的静态资源路径
    @Value("${file.staticPath}")
    private String fileStaicPath;
    //图片上传
    @ResponseBody
    @RequestMapping("/upload")
    public Map upload(MultipartFile file, HttpServletRequest request){
        //记录图片后缀
        String prefix="";
        //记录当天日期
        String dateStr="";
        try{
            if(file!=null){
                //获取上传图片的后缀
                String originalName = file.getOriginalFilename();
                prefix=originalName.substring(originalName.lastIndexOf(".")+1);

                //生成随机一个uuid字符串作为上传图片的名称，保持图片名称的唯一性
                String uuid = UUID.randomUUID()+"";
                //获取当天日期作为上传图片的上一级目录
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = simpleDateFormat.format(date);
                //得到上传图片的全路径
                String filepath = fileAddres+"\\" + dateStr+"\\"+uuid+"." + prefix;
                //打印查看上传路径
                System.out.println(filepath);

                File files=new File(filepath);
                //判读目录是否存在，不存在就创建
                if(!files.isDirectory()){
                    files.mkdirs();
                }
                //上传图片
                file.transferTo(files);
                //记录图片在项目中的路径，状态码，并传到前端
                Map<String,Object> map2=new HashMap<>();
                map2.put("src",fileStaicPath+"/"+ dateStr+"/"+uuid+"." + prefix);
                Map<String,Object> map=new HashMap<>();
                map.put("code",0);
                map.put("msg","");
                map.put("data",map2);

                return map;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("msg","");
        return map;

    }


    //excel导出
    @RequestMapping("/excelWrite")
    public void excelWrite(ClientInfo client, HttpServletResponse response, HttpServletRequest request) {
        clientInfoByManagerService.excelWrite(client,response,request);
    }

    //打开新增页面
    @RequestMapping("/addPage")
    public String addPage(ClientInfo client, ModelMap m){
        return "/admin/client-add";
    }

    //打开修改页面
    @RequestMapping("/editPage")
    public String editPage(com.cims.pojo.ClientInfo client, ModelMap m){
        //根据CId查询
        com.cims.pojo.ClientInfo u =clientInfoByManagerService.selectByCId(client);
        //把数据传递到前端
        m.addAttribute("client",u);
        return "/admin/client-edit";
    }

    //处理注册请求
    @RequestMapping("/zhuce")
    @ResponseBody
    public HashMap<String,Object> zhuce(ClientInfo client, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
        //访问注册方法
        String info = clientInfoByManagerService.zhuce(client,request);
        map.put("info",info);
        return map;
    }

    //处理修改的ajax请求
    @RequestMapping("/edit")
    @ResponseBody
    public HashMap<String,Object> edit(ClientInfo client, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = clientInfoByManagerService.update(client,request);
        map.put("info",info);
        return map;
    }

    //处理删除的ajax请求
    @RequestMapping("/delCMname")
    @ResponseBody
    public HashMap<String,Object> delCMname(ClientInfo client){
        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = clientInfoByManagerService.delCMid(client);
        map.put("info",info);
        return map;
    }


    @RequestMapping("/sendEmail")
    @ResponseBody
    public HashMap<String,Object> sendEmail(@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("value") String value, HttpServletRequest request){

        return clientInfoByManagerService.sendEmail(email,name,value,request);
    }

}

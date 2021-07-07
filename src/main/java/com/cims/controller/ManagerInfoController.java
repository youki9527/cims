package com.cims.controller;

import com.cims.pojo.ManagerInfo;
import com.cims.service.ManagerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/manager")
public class ManagerInfoController {

    @Autowired
    ManagerInfoService managerInfoService;

    //访问manager-list页面
    @RequestMapping("/list")
    public String list(ManagerInfo manager, ModelMap m){
        HashMap<String,Object> map = managerInfoService. select(manager);
        m.put("info",map);
        //把查询条件传到前端
        m.put("vv",manager.getConValue());

        m.put("conValue",manager.getConValue());
       m.put("condition",manager.getCondition());
        return "/admin/manager-list";
    }

    //处理表格ajax数据
    @RequestMapping("/listAjax")
    @ResponseBody
    public HashMap<String,Object> listAjax(ManagerInfo manager){
        return managerInfoService.select(manager);
    }

    //修改
    @RequestMapping("/editPage")
    public String editPage(ManagerInfo manager,ModelMap m){
        ManagerInfo managerInfo1 = managerInfoService.selectByMId(manager);
        m.addAttribute("manager",managerInfo1);
        return "/admin/manager-edit";
}

    //处理修改客户经理修改自己信息的ajax请求
    @RequestMapping("/updateInfo")
    @ResponseBody
    public HashMap<String,Object> updateInfo(ManagerInfo manager, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String info = managerInfoService.updateInfo(manager);
        if (info.equals("修改成功")){
            //从session中获取当前经理信息
            HttpSession session = request.getSession();
            //存入用户对象
            session.setAttribute("manager",manager);
        }
        System.out.println(info);
        map.put("info",info);
        return map;
    }

    //处理修改的ajax请求
    @RequestMapping("/edit")
    @ResponseBody
    public HashMap<String,Object> edit(ManagerInfo manager){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String info = managerInfoService.update(manager);
        System.out.println(info);
        map.put("info",info);
        return map;
    }

    //处理删除的AJAX请求
    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String,Object> del(ManagerInfo manager){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String info = managerInfoService.del(manager);
        System.out.println(info);
        map.put("info",info);
        return map;
    }

    //excel导出
    @RequestMapping("/excelWrite")
    public void excelWrite(HttpServletResponse response){
        managerInfoService.excelWrite(response);
    }

    //访问用户新增页面
    @RequestMapping("/add")
    public String add(){
        return "/admin/manager-add";
    }

    //增加客户经理,新增的ajax请求
    @RequestMapping("/saveAdd")
    @ResponseBody
    public HashMap<String,Object> saveAdd(ManagerInfo manager){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String info = managerInfoService.saveAdd(manager);
        System.out.println(info);
        map.put("info",info);
        return map;
    }

    //获取图片上传的配置路径
    @Value("${file.address}")
    String  fileAddress;

    //用户访问的图片路径
    @Value("${file.staticPath}")
    String  upload;


    //头像更改
    @RequestMapping("/upload")
    @ResponseBody
    public HashMap<String,Object> upload(MultipartFile file){
        HashMap<String,Object> map = new HashMap<String,Object>();

        //上传文件
        try {

            //定义一个文件上传的目录
            String timeDir="";
            //获取时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            timeDir = sdf.format(new Date());

            //定义上传文件的前缀，
            String pre="";
            //是为了保证文件上传后 存到服务器的文件名是唯一的
            pre = UUID.randomUUID()+"";

            //获取文件的后缀
            String hou="";
            if(file!=null){
                //.jpg
                String originalName = file.getOriginalFilename();
                hou=originalName.substring(originalName.lastIndexOf(".")+1);

            }

            //定义文件上传的全路径
            String filePath= fileAddress+"\\"+timeDir+"\\"+pre+"."+hou;

            //创建file对象
            File f = new File(filePath);

            //判断目录是否存在，不存在就创建目录
            if(!f.isDirectory()){
                //创建目录
                f.mkdirs();
            }
            //上传文件
            file.transferTo(f);
            //设置上传成功的提示信息
            map.put("code",0);
            //返回给前端 用户访问这个图片的路径
            String path = upload+"\\"+timeDir+"\\"+pre+"."+hou;
            map.put("src",path);

            return  map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置上传失败的提示信息
        map.put("code",1);

        return map;
    }

}

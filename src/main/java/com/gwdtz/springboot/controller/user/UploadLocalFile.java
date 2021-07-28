package com.gwdtz.springboot.controller.user;

import com.gwdtz.springboot.entity.UserLoginDo;
import com.gwdtz.springboot.entity.layui.WangEditor;
import com.gwdtz.springboot.utils.RedisUtil;
import com.gwdtz.springboot.utils.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springboot
 * @Date: 2021-6-11 08:01
 * @Author: Miss.Yanjc
 * @Description:
 */
@Api(description = "本地文件上传")
@RequestMapping(value = "/upload")
@RestController
public class UploadLocalFile {
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.Accessurl}")
    private String Accessurl;

    @ApiOperation(value = "富文本框上传")
    @PostMapping(value = "/uploadEditorImg")
    @ResponseBody
    public WangEditor uploadEditorImg(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws Exception {
        UserLoginDo userLoginDo=(UserLoginDo) RedisUtil.hmget(request.getParameter("username"),UserLoginDo.class);
        String deptcode=userLoginDo.getDeptlevelcode();
        Map<String, Object> map =new HashMap<String, Object>();
        UploadUtils uploadUtils=new UploadUtils();
        uploadUtils.setUploadFolder(uploadFolder);
        uploadUtils.setAccessurl(Accessurl);
        uploadUtils.setDir("file");
        uploadUtils.setUserdir(deptcode);
        WangEditor wangEditor = uploadUtils.uploadMethod(1,file,request,response);
        return wangEditor;
    }
    @ApiOperation(value = "附件上传")
    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public WangEditor uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws Exception {
        UserLoginDo userLoginDo=(UserLoginDo) RedisUtil.hmget(request.getParameter("username"),UserLoginDo.class);
        String deptcode=userLoginDo.getDeptlevelcode();
        Map<String, Object> map =new HashMap<String, Object>();
        UploadUtils uploadUtils=new UploadUtils();
        uploadUtils.setUploadFolder(uploadFolder);
        uploadUtils.setAccessurl(Accessurl);
        uploadUtils.setDir("file");
        uploadUtils.setUserdir(deptcode);
        WangEditor wangEditor = uploadUtils.uploadMethod(2,file,request,response);
        return wangEditor;
    }

    @DeleteMapping(value = "/deleteZW")
    public void delete(HttpServletRequest request) {
        String url = request.getParameter("url").substring(7);
        try {
            String path = uploadFolder+ url;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @ApiOperation(value = "富文本框中图片上传")
//    @ResponseBody
//    @PostMapping(value="/uploadimgFile")
//    public void uploadimgFile(@RequestParam String callBackPath, @RequestParam(value = "imgFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        UploadUtils uploadUtils = new UploadUtils();
//        uploadUtils.setUploadFolder(uploadFolder);
//        uploadUtils.setAccessurl(Accessurl);
//        uploadUtils.setDir("image");
//        uploadUtils.setUserdir("KindEditor");
//        Map<String, Object> map = uploadUtils.uploadMethod(file, request, response);
//        map.put("error", 0);
//        map.put("url", "");
//        String urlString = "";
//        urlString = uploadUtils.getUrlString() + callBackPath + "?error=0&url=" + map.get("fileUrl");
//        System.out.println(urlString);
//        response.sendRedirect(urlString);
//    }
    private String getError(String htmlurl,String message, String callBackPath) throws UnsupportedEncodingException {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("error", 1);
        msg.put("message", message);
        String urlString = htmlurl + callBackPath + "?error=1&message=" + URLEncoder.encode(message, "UTF-8");

        return urlString;
    }
    @ApiOperation(value = "上传多个文件")
    @ResponseBody
    @PostMapping(value="/uploadMethodMulti")
    public Map<String, Object> uploadLocalMutiFiles(@RequestParam(value = "adduploadfiles", required = false) MultipartFile[] uploadfiles, HttpServletRequest request, HttpServletResponse response)throws Exception {
        UserLoginDo userLoginDo=(UserLoginDo) RedisUtil.hmget(request.getParameter("username"),UserLoginDo.class);
        String deptcode=userLoginDo.getDeptlevelcode();
        Map<String, Object> map =new HashMap<String, Object>();
        UploadUtils uploadUtils=new UploadUtils();
        uploadUtils.setUploadFolder(uploadFolder);
        uploadUtils.setAccessurl(Accessurl);
        uploadUtils.setUserdir(deptcode);
        uploadUtils.setDir("file");
        map = uploadUtils.uploadMethodMulti(uploadfiles,request,response);
        return map;
    }

}

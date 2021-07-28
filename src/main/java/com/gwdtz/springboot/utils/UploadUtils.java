package com.gwdtz.springboot.utils;

import com.gwdtz.springboot.entity.MutiFiles;
import com.gwdtz.springboot.entity.layui.WangEditor;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: springboot
 * @Date: 2021-2-23 10:15
 * @Author: Miss.Chenmf
 * @Description:
 */
public class UploadUtils {
    private String uploadFolder;

    private String Accessurl;

    private String urlString;

    private String dir;

    private String userdir;

    public String getUploadFolder() {
        return uploadFolder;
    }

    public void setUploadFolder(String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }

    public String getAccessurl() {
        return Accessurl;
    }

    public void setAccessurl(String accessurl) {
        Accessurl = accessurl;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getUserdir() {
        return userdir;
    }

    public void setUserdir(String userdir) {
        this.userdir = userdir;
    }
    //上传图片文件

    public WangEditor uploadMethod(Integer type, MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        WangEditor wangEditor = null;
        String scheme = request.getScheme();//http
        String serverName = request.getServerName();//localhost
        String serverPort= String.valueOf(request.getServerPort());
        String url = scheme + "://" + serverName + ":"+serverPort+request.getContextPath();//http://127.0.0.1:8080
        //String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + fileHttp + upDiskFileName;

        String referer = request.getHeader("referer");
        Pattern p = Pattern.compile("([a-z]*:(//[^/?#]+/[^/?#]*/)?)", Pattern.CASE_INSENSITIVE);
        Matcher mathcer = p.matcher(referer);
        if (mathcer.find()) {
            String htmlheader = mathcer.group();// 请求来源

            response.setContentType("text/html;charset=UTF-8");
            String savePath = uploadFolder;
            String saveUrl ="";
            //定义允许上传的文件扩展名
            HashMap<String, String> extMap = new HashMap<String, String>();
            extMap.put("image", "gif,jpg,jpeg,png");
            extMap.put("flash", "swf,flv");
            extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
            extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

            //最大文件大小
            long maxSize = 100000000;
//            long maxSize = 5000000;

            response.setContentType("text/html; charset=UTF-8");

            if (!ServletFileUpload.isMultipartContent(request)) {
                map.put("msg", "请选择文件");
                map.put("success", "false");
                return wangEditor;
            }
            //检查目录
            File uploadDir = new File(savePath);
            if (!uploadDir.isDirectory()) {
                map.put("msg", "上传目录不存在");
                map.put("success", "false");
                return wangEditor;
            }
            //检查目录写权限
            if (!uploadDir.canWrite()) {
                map.put("msg", "上传目录没有写权限");
                map.put("success", "false");
                return wangEditor;
            }

            String dirName = request.getParameter("dir");
            if (dirName == null) {
                dirName = dir;
            }
            String userdirName = request.getParameter("username");
            if (userdirName == null) {
                userdirName = userdir;
            }
            if (!extMap.containsKey(dirName)) {
                map.put("msg", "目录名不正确");
                map.put("success", "false");
                return wangEditor;
            }
            //创建文件夹
            savePath += dirName+ "/"+userdirName + "/";
            saveUrl += Accessurl + dirName+ "/"+userdirName + "/";
            File saveDirFile = new File(savePath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());
            savePath += ymd + "/";
            saveUrl += ymd + "/";
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            //检查文件大小
            if (fileSize > maxSize) {
                map.put("msg", "上传文件大小超过限制。");
                map.put("success", "false");
                return wangEditor;
            }
            //检查扩展名
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//            if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
//                map.put("msg", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
//                map.put("success", "false");
//                return wangEditor;
//            }

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            try {
                File uploadedFile = new File(savePath, newFileName);
                OutputStream os = new FileOutputStream(uploadedFile);
                InputStream inputStream = file.getInputStream();
                byte[] buf = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(buf)) != -1) {
                    os.write(buf, 0, length);
                }
                inputStream.close();
                os.close();
//                map.put("newName", newFileName);
//                map.put("oldName",fileName);
//                map.put("fileSize", fileSize);
                String absoluteurl = url+ saveUrl + newFileName;//绝对路径
                String fileUrl = saveUrl + newFileName;//相对路径
                map.put("imgUrl",fileUrl);
//                map.put("relativeurl",fileUrl);
//                map.put("msg", "上传成功。");
                map.put("errno","0");
                urlString = htmlheader;
                if(type == 1){
                    String[] str = {absoluteurl};
                    wangEditor = new WangEditor(str);
                }else {
                    String[] str = {fileUrl};
                    wangEditor = new WangEditor(str);
                }
            } catch (Exception e) {
                map.put("msg", "上传文件失败。");
                map.put("errno", "1");
            }

        }
        return wangEditor;
    }

//多文件上传 上传任意文件
    public Map<String, Object> uploadMethodMulti( MultipartFile[] uploadfiles, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String scheme = request.getScheme();//http
        String serverName = request.getServerName();//localhost
        String serverPort= String.valueOf(request.getServerPort());
        String url = scheme + "://" + serverName + ":"+serverPort+request.getContextPath();

        String referer = request.getHeader("referer");
        Pattern p = Pattern.compile("([a-z]*:(//[^/?#]+/[^/?#]*/)?)", Pattern.CASE_INSENSITIVE);
        Matcher mathcer = p.matcher(referer);
        if (mathcer.find()) {
            String htmlheader = mathcer.group();// 请求来源

            response.setContentType("text/html;charset=UTF-8");
            String savePath = uploadFolder;
            String saveUrl = "";
            //定义允许上传的文件扩展名
            HashMap<String, String> extMap = new HashMap<String, String>();
            extMap.put("image", "gif,jpg,jpeg,png");
            extMap.put("flash", "swf,flv");
            extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
            extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

            //最大文件大小
            long maxSize = 100000000;

            response.setContentType("text/html; charset=UTF-8");

            if (!ServletFileUpload.isMultipartContent(request)) {
                map.put("msg", "请选择文件");
                map.put("success", "false");
                return map;
            }
            //检查目录
            File uploadDir = new File(savePath);
            if (!uploadDir.isDirectory()) {
                map.put("msg", "上传目录不存在");
                map.put("success", "false");
                return map;
            }
            //检查目录写权限
            if (!uploadDir.canWrite()) {
                map.put("msg", "上传目录没有写权限");
                map.put("success", "false");
                return map;
            }

            String dirName = request.getParameter("dir");
            if (dirName == null) {
                dirName = dir;
            }
            String userdirName = request.getParameter("userdir");
            if (userdirName == null) {
                userdirName = userdir;
            }
            if (!extMap.containsKey(dirName)) {
                map.put("msg", "目录名不正确");
                map.put("success", "false");
                return map;
            }
            //创建文件夹
            savePath += dirName+ "/"+userdirName + "/";
            saveUrl += Accessurl + dirName+ "/"+userdirName + "/";
            File saveDirFile = new File(savePath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());
            savePath += ymd + "/";
            saveUrl += ymd + "/";
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            List<MutiFiles> list=new ArrayList<>();
            if (uploadfiles.length > 0) {
                for (int i = 0; i < uploadfiles.length; i++) {
                    MultipartFile uploadfile = uploadfiles[i];
                    String oldname=uploadfile.getOriginalFilename();

                    //获取上传文件的文件名，去除盘符和路径
                    int unixSeq=oldname.lastIndexOf('/');
                    int winSep=oldname.lastIndexOf('\\');
                    int pos=(winSep>unixSeq?winSep:unixSeq);
                    if(pos!=-1)
                    {
                        oldname=oldname.substring(pos+1);
                    }
                    //检查扩展名
                    String fileExt = oldname.substring(oldname.lastIndexOf(".") + 1).toLowerCase();
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
                    try{
                        uploadfile.transferTo(new File(savePath, newFileName));
                        String relativeurl = saveUrl + newFileName;//相对路径
                        String absoluteurl =  url+ saveUrl + newFileName;//绝对路径
                        MutiFiles mutiFiles=new MutiFiles();
                        mutiFiles.setFilename(oldname);
                        mutiFiles.setRelativeurl(relativeurl);
                        mutiFiles.setAbsoluteurl(absoluteurl);
                        mutiFiles.setFilesize(uploadfile.getSize());
                        mutiFiles.setFiletype(fileExt);
                        list.add(mutiFiles);

                    }catch (IOException e){
                        e.printStackTrace();
                        map.put("msg", "上传文件失败。");
                        map.put("success", "false");
                    }
                }
                map.put("msg", "上传文件成功。");
                map.put("success", "true");
                map.put("filelist", list);
            }else{
                map.put("msg", "请选择文件。");
                map.put("success", "false");
            }
        }
        return map;
    }


    public static int indexPosition(String url, String regex, int num){
        Matcher matcher = Pattern.compile(regex).matcher(url);
        int count = 0;
        int start = 0;
        while (matcher.find()){
            count++;
            if(count == num){
                start = matcher.start();
            }
        }
        return start;
    }
}

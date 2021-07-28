package com.gwdtz.springboot.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.service.IContentDeptService;
import com.gwdtz.springboot.service.IContentService;
import com.gwdtz.springboot.service.IDeptService;
import com.gwdtz.springboot.service.IUserService;
import com.gwdtz.springboot.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: springboot
 * @Date: 2021-6-11 08:54
 * @Author: Miss.Yanjc
 * @Description:
 */
@Api(description = "栏目内容管理")
@RequestMapping("/content")
@RestController
public class ContentsController {
    @Autowired
    IContentService contentService;
    @Autowired
    IContentDeptService contentDeptService;
    @Autowired
    IDeptService deptService;

    @Autowired
    IUserService userService;

    @ApiOperation(value = "栏目内容管理", notes = "栏目内容列表")
    @GetMapping(value = "/getContentList")
    @ResponseBody
    public Map<String, Object> getContentList(HttpServletRequest request, HttpServletResponse response, Integer top) {
        UserLoginDo userLoginDo = UserUtils.userGet(request);
        Integer deptid = userLoginDo.getDeptid();
        String strkey = request.getParameter("strkey");
        String modulepid = request.getParameter("modulepid");
        Map<String, Object> mapobj = new HashMap<String, Object>();
        PageInfo pageInfo = null;
        PageHelper.startPage(request);
        List<ContentDO> list = contentService.getContentList(deptid, request.getParameter("title"), request.getParameter("serial"), request.getParameter("createtime"));
        pageInfo = new PageInfo(list);
        mapobj.put("code", 0);
        mapobj.put("msg", "成功！");
        mapobj.put("data", pageInfo.getList());
        mapobj.put("count", pageInfo.getTotal());
        return mapobj;
    }

    /**
     * 栏目内容管理
     *
     * @return
     * @param栏目ID
     */
    @ApiOperation(value = "栏目内容(单个，用于编辑页面的查询)")
    @GetMapping(value = "/selectContentById")
    @ResponseBody
    private Map<String, Object> selectContentById(HttpServletRequest request) {
        Map<String, Object> mapobj = new HashMap<String, Object>();
        try {
            ContentDO contentDO = contentService.selectContentById(Long.valueOf(request.getParameter("id")));
            List<ContentAttach> attachlist = contentService.getContentAttachList(request.getParameter("id"));
            Integer num = contentService.updatevisittimes(Long.valueOf(request.getParameter("id")));
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String strattachlist = "";
            for (ContentAttach l : attachlist) {
                l.setAttachabsoluteurl(filePath + l.getAttachrelativeurl());
                strattachlist += l.getAttachrealname() + "," + l.getAttachrelativeurl() + "," + l.getAttachtype() + "," + l.getAttachsize() + "|";
            }
            mapobj.put("contentDO", contentDO);
            mapobj.put("attachlist", attachlist);
            mapobj.put("strattachlist", strattachlist);
            mapobj.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            mapobj.put("success", false);
        }
        return mapobj;
    }



    @ApiOperation(value = "栏目内容新增")
    @PostMapping(value = "/contentAdd")
    @ResponseBody
    public Result contentAdd(ContentDO contentDO, HttpServletRequest request) throws Exception {
        UserLoginDo userLoginDo = (UserLoginDo) RedisUtil.hmget(request.getParameter("username"), UserLoginDo.class);
        Integer deptid = userLoginDo.getDeptid();
        String username = userLoginDo.getUsername();
        contentDO.setDeptid(deptid);
        contentDO.setPublishuser(username);
        contentDO.setPubliship(InetAddress.getLocalHost().getHostAddress());
        contentDO.setYyyymm(DateUtils.sdfDate20.format(new Date()));
        contentDO.setVisittimes(0);
//        if (contentDO.getImageurl().length() == 0) {
//            contentDO.setImageurl(null);
//        }
        try {
            contentDO.setVisittimes(0);
            Date now = new Date();
            contentDO.setYyyymm(DateUtils.sdfDate20.format(now));
            if (contentService.getMaxId() != null) {
                contentDO.setId(contentService.getMaxId());
            } else {
                contentDO.setId(Long.valueOf(1));
            }
            Integer num = contentService.insert(contentDO);
            contentDO.setUndo(0);
            Integer num3 = contentService.update(contentDO);
            //附件列表
            List<ContentAttach> contentAttachlist = new ArrayList<ContentAttach>();
            String jsonArray = contentDO.getJsonArray();
            if (!jsonArray.equals("")) {
                jsonArray = jsonArray.substring(0, jsonArray.length() - 1);
                String[] jsonArrays = jsonArray.split("\\|");
                for (int i = 0; i < jsonArrays.length; i++) {
                    ContentAttach contentAttach = new ContentAttach();
                    String[] objs = jsonArrays[i].split(",");
                    contentAttach.setContentid(contentDO.getId());
                    contentAttach.setAttachrealname(objs[0]);
                    contentAttach.setAttachrelativeurl(objs[1]);
//                    contentAttach.setAttachtype(objs[2]);
//                    contentAttach.setAttachsize(objs[3]);
                    contentAttachlist.add(contentAttach);
                }
                ContentAttachDO contentAttachDO = new ContentAttachDO();
                contentAttachDO.setContentAttaches(contentAttachlist);
                //添加附件
                Integer num1 = contentService.addBatchContentAttach(contentAttachDO);
            }
            LogUtils.insert("新增内容:" + contentDO.getTitle(), "contentAdd", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "contentDO");
            return new Result(CODE.SUCCESS, num, "新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "新增失败！");
        }
    }

    @ApiOperation(value = "栏目内容编辑")
    @PutMapping(value = "/contentEdit")
    @ResponseBody
    public Result contentEdit(ContentDO contentDO, HttpServletRequest request) throws Exception {
        long contentId = contentDO.getId();
        UserLoginDo userLoginDo = (UserLoginDo) RedisUtil.hmget(request.getParameter("username"), UserLoginDo.class);
        Integer deptid = userLoginDo.getDeptid();
        String username = userLoginDo.getUsername();
        contentDO.setDeptid(deptid);
        contentDO.setPublishuser(username);
        contentDO.setPubliship(InetAddress.getLocalHost().getHostAddress());
        contentDO.setYyyymm(DateUtils.sdfDate20.format(new Date()));
        contentDO.setVisittimes(0);
        if (contentDO.getImageurl().length() == 0) {
            contentDO.setImageurl(null);
        }
        try {
            Integer num = contentService.update(contentDO);
            //更新时先删除某contentid对应的附件
            Integer num0 = contentService.deleteAttachByContentId(String.valueOf(contentDO.getId()));

            //附件列表
            List<ContentAttach> contentAttachlist = new ArrayList<ContentAttach>();
            String jsonArray = contentDO.getJsonArray();
            if (!jsonArray.equals("")) {
                jsonArray = jsonArray.substring(0, jsonArray.length() - 1);
                String[] jsonArrays = jsonArray.split("\\|");
                for (int i = 0; i < jsonArrays.length; i++) {
                    ContentAttach contentAttach = new ContentAttach();
                    String[] objs = jsonArrays[i].split(",");
                    contentAttach.setContentid(contentDO.getId());
                    contentAttach.setAttachrealname(objs[0]);
                    contentAttach.setAttachrelativeurl(objs[1]);
                    contentAttach.setAttachtype(objs[2]);
                    contentAttach.setAttachsize(objs[3]);
                    contentAttachlist.add(contentAttach);
                }
                ContentAttachDO contentAttachDO = new ContentAttachDO();
                contentAttachDO.setContentAttaches(contentAttachlist);
                //添加附件
                Integer num1 = contentService.addBatchContentAttach(contentAttachDO);
            }
            LogUtils.insert("编辑内容:" + contentDO.getTitle(), "contentEdit", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "contentDO");
            return new Result(CODE.SUCCESS, num, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "更新失败！");
        }
    }

    //内容删除
    @ApiOperation(value = "内容删除")
    @DeleteMapping(value = "/deleteByContentId")
    public Result deleteByContentId(long id, HttpServletRequest request) {
        try {
            ContentDO contentDO = contentService.selectContentById(id);
            Integer num = contentService.deleteById(id);
            Integer num1 = contentDeptService.deleteByContentid(id);
            LogUtils.insert("删除资料:" + contentDO.getTitle(), "deleteByContentId", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "id");
            return new Result(CODE.SUCCESS, num & num1, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "删除失败！");
        }
    }

    //删除附件
    @ApiOperation(value = "附件删除")
    @DeleteMapping(value = "/deleteByAttachId")
    public Result deleteByAttachId(String attachid) {
        try {
            Integer num = contentService.deleteByAttachId(attachid);
            return new Result(CODE.SUCCESS, num, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "删除失败！");
        }
    }

    //更新时先删除某contentid对应的附件
    @ApiOperation(value = "附件批量删除")
    @DeleteMapping(value = "/deleteAttachByContentId")
    public Result deleteByContentId(String contentid) {
        try {
            Integer num = contentService.deleteAttachByContentId(contentid);
            return new Result(CODE.SUCCESS, num, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "删除失败！");
        }
    }


    @ApiOperation(value = "获取选择栏目列表")
    @GetMapping(value = "/selectDeptById")
    @ResponseBody
    public Result selectDeptById(Long contentid) {

        try {
            Integer[] num = contentDeptService.selectDeptById(contentid);
            return new Result(CODE.SUCCESS, num, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "查询失败！");
        }
    }


}

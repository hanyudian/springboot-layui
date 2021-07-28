package com.gwdtz.springboot.controller.manager;

import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.entity.layui.DeptOneSel;
import com.gwdtz.springboot.service.IContentDeptService;
import com.gwdtz.springboot.service.IContentService;
import com.gwdtz.springboot.service.IDeptService;
import com.gwdtz.springboot.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @param
 * @Author hanshuai(the developing of Four)
 * @Description
 * @return
 * @Date 2021/2/24 14:29
 **/
@Api(description = "部门管理")
@RequestMapping(value = "/dept")
@RestController
public class DeptController {

    @Autowired
    IDeptService deptService;
    @Autowired
    IContentDeptService contentDeptService;
    @Autowired
    IContentService contentService;

    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping(value = "/deptTree")
    @ResponseBody
    public Result deptAllList(HttpServletRequest request, DeptTree deptTree) {
        try {
            DeptDO deptDO = UserUtils.deptGet(request);
            if (deptDO != null) {
                deptTree.setDeptid(deptDO.getDeptid());
                List<DeptTree> list = deptService.getAllListCheckJson(deptTree);
                return new Result(CODE.SUCCESS, list, "ok");
            } else {
                return new Result(CODE.ERROR, null, "服务器缓存读取错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "服务器内部错误！");
        }
    }

    @ApiOperation(value = "部门展示（根据登录用户的deptid单个回调）")
    @GetMapping(value = "/{username}")
    @ResponseBody
    private Result selectDeptById(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        try {
            DeptDO deptDO = UserUtils.deptGet(username);
            if (deptDO != null) {
                deptDO = deptService.selectDeptById(deptDO.getDeptid());
                return new Result(CODE.SUCCESS, deptDO, "查询成功！");
            } else {
                return new Result(CODE.ERROR, null, "服务器缓存读取错误！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "查询失败！");
        }
    }

    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping(value = "/deptAllList")
    @ResponseBody
    public Result deptAllList(HttpServletRequest request, DeptTreeDO deptTreeDO) {
        try {
            DeptDO deptDO = UserUtils.deptGet(request);
            String deptid = deptDO.getDeptid().toString();
            if (deptDO != null) {
                deptTreeDO.setId(Integer.parseInt(deptid));
                List<DeptTreeDO> list = deptService.getAllListCheckJsongy(deptTreeDO);
                return new Result(CODE.SUCCESS, list, "ok");
            } else {
                return new Result(CODE.ERROR, null, "服务器缓存读取错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "服务器内部错误！");
        }
    }


    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping(value = "/deptAllListCode")
    @ResponseBody
    public Result deptAllListCode(HttpServletRequest request, DeptTreeDO deptTreeDO) {
        try {
            DeptDO deptDO = UserUtils.deptGet(request);
            String deptid = deptDO.getDeptid().toString();
            if (deptDO != null) {
                deptTreeDO.setId(Integer.parseInt(deptid));
                List<DeptTreeDO> list = deptService.getAllListCheckJsongy(deptTreeDO);
                return new Result(CODE.SUCCESS, list, "ok");
            } else {
                return new Result(CODE.ERROR, null, "服务器缓存读取错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "服务器内部错误！");
        }
    }

    @ApiOperation(value = "部门添加")
    @PostMapping(value = "/deptAdd/{username}")
    @ResponseBody
    public Result deptAdd(DeptDO DeptDO,@PathVariable("username") String username, HttpServletRequest request) {
        try {
            //字段Sortno存储
            String MaxSortno = deptService.getMaxSortno(Integer.parseInt(DeptDO.getPid().toString()));
            if (MaxSortno == null || MaxSortno == "") {
                DeptDO.setSortno(1);
            } else {
                DeptDO.setSortno(Integer.parseInt(MaxSortno) + 1);
            }
            //字段ischild存储
            Integer parentIschild = deptService.getIschild(Integer.parseInt(DeptDO.getPid().toString()));//取得父节点的ischild
            DeptDO.setIschild(0);
            //字段deptlevelcode和deptcode存储
            DeptDO parentDO = deptService.selectDeptById(DeptDO.getPid());//取得父节点的model
            String code = parentDO.getDeptlevelcode();
            if (DeptDO.getSortno() >= 10) {
                DeptDO.setDeptcode(code + DeptDO.getSortno());
                DeptDO.setDeptlevelcode(code + DeptDO.getSortno());
            } else {
                DeptDO.setDeptcode(code + "0" + DeptDO.getSortno());
                DeptDO.setDeptlevelcode(code + "0" + DeptDO.getSortno());
            }
            //字段deptfullname存储
            StringBuilder sb = new StringBuilder();
            String deptnames = checkAll(Integer.parseInt(DeptDO.getPid().toString()), sb);
            String fullName = deptnames + "-" + DeptDO.getDeptname();
            //字段Createtime存储
            SimpleDateFormat time = new SimpleDateFormat();
            time.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            DeptDO.setCreatetime(time.format(date));
            //存储另外两个控制显示的字段
            DeptDO.setStatus(1);
            DeptDO.setDeleted(1);
            //执行添加操作，存储日志信息，返回操作成功提示；
            Integer num = deptService.insertSelective(DeptDO);
            //修改父节点的ischild字段
            if (parentIschild == 0) {
                DeptDO parentDept = new DeptDO();
                parentDept.setDeptid(DeptDO.getPid());
                parentDept.setIschild(1);
                deptService.updateIschild(parentDept);
            }
            LogUtils.insert("新增部门:" + DeptDO.getDeptname(), "DeptAdd", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "DeptDO");
            return new Result(CODE.SUCCESS, num, "插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "插入失败！");
        }
    }

    @ApiOperation(value = "部门编辑")
    @PutMapping(value = "/deptEdit/{username}")
    @ResponseBody
    public Result deptEdit(DeptDO DeptDO,@PathVariable("username") String username, HttpServletRequest request) {
        try {
            //修改字段Updatetime进行存储
            SimpleDateFormat time = new SimpleDateFormat();
            time.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            DeptDO.setUpdatetime(time.format(date));
            //修改字段Deptfullname进行存储
            StringBuilder sb = new StringBuilder();
            Integer pids = deptService.getPid(Integer.parseInt(DeptDO.getDeptid().toString()));
            String deptnames = checkAll(pids, sb);
            String fullName = deptnames + "-" + DeptDO.getDeptname();
            //执行添加操作，存储日志信息，返回操作成功提示；
            Integer num = deptService.updateByPrimaryKeySelective(DeptDO);
            LogUtils.insert("部门编辑:" + DeptDO.getDeptname(), "DeptEdit", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "DeptDO");
            return new Result(CODE.SUCCESS, num, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "更新失败！");
        }
    }

    public String checkAll(Integer deptid, StringBuilder sb) {
        if (deptid != -1) {
            String deptname = deptService.getDeptName(deptid);
            //sb.append(deptname).append("-");
            sb.insert(0, deptname + "-");
            Integer pid = deptService.getPid(deptid);
            checkAll(pid, sb);
        }
        String substring = sb.toString().substring(0, sb.toString().length() - 1);
        return substring;
    }


    @ApiOperation(value = "部门查询(单个)")
    @GetMapping(value = "/selectDeptById")
    @ResponseBody
    private Result selectDeptById(long deptid) {
        try {
            DeptDO deptDO = deptService.selectDeptById(deptid);
            return new Result(CODE.SUCCESS, deptDO, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(CODE.ERROR, null, "查询失败！");
        }
    }

    @ApiOperation(value = "删除子节点deleted=0")
    @ResponseBody
    @DeleteMapping(value = "/deleteChildNode/{username}/{deptId}")
    public String deleteChildNode(@PathVariable String deptId, @PathVariable String username, HttpServletRequest request) {
        try {
            deptService.updateDeleted(Integer.parseInt(deptId));
            //deptService.deleteByPrimaryKey(Long.valueOf(deptid));//真正删除方法
            //以下方法实现：删除部门子节点，查看上级父节点是否还有子节点，如果没有则将上级父节点的ischild字段改为0
            Integer pid = deptService.getPid(Integer.parseInt(deptId));//取得pid
            List<DeptTreeDO> list = deptService.getByPidTreeCheckgy(pid.longValue());
            if (list.size() == 0) {
                DeptDO record = new DeptDO();
                record.setDeptid(pid.longValue());
                record.setIschild(0);
                deptService.updateIschild(record);
            }
            LogUtils.insert("删除部门子节点:" + deptId, "deleteChildNode", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "deptid");
            return "0";
        } catch (Exception e) {
            return "1";
        }
    }

    @ApiOperation(value = "删除父节点和子节点")
    @ResponseBody
    @DeleteMapping(value = "/deleteParentNodes/{username}/{id}")
    public String deleteParentNodes(@PathVariable String id, @PathVariable String username, HttpServletRequest request) {
        //根据父节点id递归得到所有下级的id
        StringBuilder sb = new StringBuilder();
        String allParentAndChildIds = deptService.getAllParentAndChildIds(id, sb);
        //System.out.println(id + "----------" + allParentAndChildIds);
        try {
            //deptService.deleteParentNodesByAllIds(allParentAndChildIds.split(","));//真正删除父节点和所有子节点
            deptService.updateParentNodesByAllIds(allParentAndChildIds.split(","));
            //以下方法实现：删除部门子节点，查看上级父节点是否还有子节点，如果没有则将上级父节点的ischild字段改为0
            Integer pid = deptService.getPid(Integer.parseInt(id));//取得pid
            List<DeptTreeDO> list = deptService.getByPidTreeCheckgy(pid.longValue());
            if (list.size() == 0) {
                DeptDO record = new DeptDO();
                record.setDeptid(pid.longValue());
                record.setIschild(0);
                deptService.updateIschild(record);
            }
            LogUtils.insert("删除部门父节点和所有子节点:" + allParentAndChildIds, "deleteParentNodes", UserUtils.userGet(username).getUserid(), UserUtils.userGet(username).getUsername(), "id");
            return "0";
        } catch (Exception e) {
            return "1";
        }
    }

    //用户模块编辑
    @ApiOperation(value = "设置权限")
    @PostMapping(value = "/contentDeptEdit")
    @ResponseBody
    public Result contentdeptedit(Long contentid, String deptids, HttpServletRequest request) {
        try {
            ContentDO contentDO = contentService.selectContentById(contentid);
            Integer num = contentDeptService.deleteById(contentid, deptids);
            String[] split = deptids.split(",");
            boolean contains = Arrays.asList(split).contains("22");
            Integer undo = split.length;
            if(contains){
                undo--;
            }
            contentService.updateUndo1(contentid, undo);
            LogUtils.insert("设置责任车间:" + contentDO.getTitle(), "contentDeptEdit", UserUtils.userGet(request).getUserid(), UserUtils.userGet(request).getUsername(), "contentid,deptids");
            return new Result(CODE.SUCCESS, num, "更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "更新失败！");
        }
    }

    @ApiOperation(value = "责任车间", notes = "责任车间")
    @GetMapping(value = "/deptList")
    @ResponseBody
    public Result deptList(DeptTreeDO deptTreeDO) {
        deptTreeDO.setId(22);
        List<DeptTreeDO> list = deptService.getAllListCheckJsongy(deptTreeDO);
        return new Result(CODE.SUCCESS, list, "ok");
    }




    @ApiOperation(value = "网站模块", notes = "网站模块列表")
    @GetMapping(value = "/deptOneSel")
    @ResponseBody
    public Result deptOneSel(HttpServletRequest request, DeptOneSel deptOneSel) {
        try {
            DeptDO deptDO = UserUtils.deptGet(request);
            String deptid = deptDO.getDeptid().toString();
            if (deptDO != null) {
                deptOneSel.setId(Integer.parseInt(deptid));
                List<DeptOneSel> list = deptService.getDeptOneSel(deptOneSel);
                return new Result(CODE.SUCCESS, list, "ok");
            } else {
                return new Result(CODE.ERROR, null, "服务器缓存读取错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CODE.ERROR, null, "服务器内部错误！");
        }
    }
}

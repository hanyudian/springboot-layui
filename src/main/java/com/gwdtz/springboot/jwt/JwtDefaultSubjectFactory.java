package com.gwdtz.springboot.jwt;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @program: springboot
 * @Date: 2020-12-31 8:43
 * @Author: Mr.Wu
 * @Description:
 */
public class JwtDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context){
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}

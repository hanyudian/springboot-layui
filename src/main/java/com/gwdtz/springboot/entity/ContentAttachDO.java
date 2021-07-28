package com.gwdtz.springboot.entity;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-4-6 17:23
 * @Author: Miss.Chenmf
 * @Description:
 */
public class ContentAttachDO extends ContentAttach {
    private List<ContentAttach> contentAttaches;

    public List<ContentAttach> getContentAttaches() {
        return contentAttaches;
    }

    public void setContentAttaches(List<ContentAttach> contentAttaches) {
        this.contentAttaches = contentAttaches;
    }
}

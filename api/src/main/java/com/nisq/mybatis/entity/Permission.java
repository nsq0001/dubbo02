package com.nisq.mybatis.entity;

import java.io.Serializable;

/**
 * @author 
 * 
 */
public class Permission implements Serializable {
    private Integer id;

    private String uri;

    private String name;

    private boolean c;

    private boolean r;

    private boolean u;

    private boolean d;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public boolean getR() {
        return r;
    }

    public void setR(boolean r) {
        this.r = r;
    }

    public boolean getU() {
        return u;
    }

    public void setU(boolean u) {
        this.u = u;
    }

    public boolean getD() {
        return d;
    }
    public void setD(boolean d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", c=" + c +
                ", r=" + r +
                ", u=" + u +
                ", d=" + d +
                '}';
    }
}
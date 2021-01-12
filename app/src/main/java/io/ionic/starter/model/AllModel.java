package io.ionic.starter.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-07-17.
 */

public class AllModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

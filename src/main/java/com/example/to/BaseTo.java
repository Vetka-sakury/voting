package com.example.to;

import com.example.entity.HasId;
import io.swagger.annotations.ApiModelProperty;


public abstract class BaseTo implements HasId {
    @ApiModelProperty(hidden = true)
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}

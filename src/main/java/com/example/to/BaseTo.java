package com.example.to;

import com.example.model.HasId;
import io.swagger.v3.oas.annotations.media.Schema;

public abstract class BaseTo implements HasId {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
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

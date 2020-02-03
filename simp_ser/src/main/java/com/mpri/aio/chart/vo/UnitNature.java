package com.mpri.aio.chart.vo;

public class UnitNature {

    private String key;
    private Long value;
    private String type;
    private Double valuePercent;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValuePercent() {
        return valuePercent;
    }

    public void setValuePercent(Double valuePercent) {
        this.valuePercent = valuePercent;
    }
}

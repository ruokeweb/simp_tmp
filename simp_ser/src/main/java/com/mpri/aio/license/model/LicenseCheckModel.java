package com.mpri.aio.license.model;

public class LicenseCheckModel {

    private String shoolCode;
    private String macAddress;
    private String CPUSerial;

    public String getShoolCode() {
        return shoolCode;
    }

    public void setShoolCode(String shoolCode) {
        this.shoolCode = shoolCode;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getCPUSerial() {
        return CPUSerial;
    }

    public void setCPUSerial(String CPUSerial) {
        this.CPUSerial = CPUSerial;
    }
}

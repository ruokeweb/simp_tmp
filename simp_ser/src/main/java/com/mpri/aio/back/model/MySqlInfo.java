package com.mpri.aio.back.model;

public class MySqlInfo {
    private String jdbcUrl="jdbc:mysql://192.168.120.31:3306/smmp_dev?nullCatalogMeansCurrent=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Hongkong&useSSL=false";
    private String user="root";
    private String password="Root123.";
    private String exportPath= "dbbak";
    public String getExportPath() {
        return exportPath;
    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }

    public MySqlInfo(String jdbcUrl,  String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

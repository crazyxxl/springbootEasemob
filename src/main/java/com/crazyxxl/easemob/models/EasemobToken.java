package com.crazyxxl.easemob.models;

public class EasemobToken {
    private String token;

    /**
     * 过期时间
     */
    private Long expiresIn;

    /**
     * 当前 APP 的 UUID 值
     */
    private String application;

    /**
     * 获取环信token
     *
     * @return token - 环信token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置环信token
     *
     * @param token 环信token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取过期时间
     *
     * @return expires_in - 过期时间
     */
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     * 设置过期时间
     *
     * @param expiresIn 过期时间
     */
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * 获取当前 APP 的 UUID 值
     *
     * @return application - 当前 APP 的 UUID 值
     */
    public String getApplication() {
        return application;
    }

    /**
     * 设置当前 APP 的 UUID 值
     *
     * @param application 当前 APP 的 UUID 值
     */
    public void setApplication(String application) {
        this.application = application == null ? null : application.trim();
    }

    public EasemobToken(String token, Long expiresIn, String application) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.application = application;
    }
}
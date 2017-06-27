package com.hz.school.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 令牌表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"access_token")
public class AccessToken extends BaseEntity {
    /**
     * 令牌编码，64位
     */
    private String accessToken;
    /**
     * 有效期
     */
    private Long expiresIn;
    /**
     * 第三方应用唯一凭证
     */
    private String appId;
    /**
     * 第三方应用唯一凭证密钥
     */
    private String appSecret;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}

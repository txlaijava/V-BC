package com.shopping.framework.druid;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Druid配置
 *
 * @author xubin.
 * @create 2017-04-06
 */

@ConfigurationProperties(prefix = "druid.monitor")
public class DruidMonitorProperties {

    private String DruidStatView;
    private String DruidWebStatFilter;

    private String allow;
    private String deny;
    private String loginUsername;
    private String loginPassword;

    private String exclusions;
    private String resetEnable;

    public String getDruidStatView() {
        return DruidStatView;
    }

    public void setDruidStatView(String druidStatView) {
        DruidStatView = druidStatView;
    }

    public String getDruidWebStatFilter() {
        return DruidWebStatFilter;
    }

    public void setDruidWebStatFilter(String druidWebStatFilter) {
        DruidWebStatFilter = druidWebStatFilter;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getDeny() {
        return deny;
    }

    public void setDeny(String deny) {
        this.deny = deny;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public String getResetEnable() {
        return resetEnable;
    }

    public void setResetEnable(String resetEnable) {
        this.resetEnable = resetEnable;
    }
}
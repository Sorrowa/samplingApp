package com.example.core.Entity;

public class User {
    private String account;
    private String orgName;

    public User(String account,String orgName){
        this.account=account;
        this.orgName=orgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

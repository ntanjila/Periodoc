package com.example.periodoc;

public class UserInfo {
    private String name;

    public UserInfo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "Saved name : '" + name + "'"
                ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

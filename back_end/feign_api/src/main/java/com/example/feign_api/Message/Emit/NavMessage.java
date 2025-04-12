package com.example.feign_api.Message.Emit;

public class NavMessage
{
    String name, sex, levelID, level;

    public NavMessage(String name, String sex, String levelID, String level) {
        this.name = name;
        this.sex = sex;
        this.levelID = levelID;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

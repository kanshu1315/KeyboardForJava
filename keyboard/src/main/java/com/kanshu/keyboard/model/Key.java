package com.kanshu.keyboard.model;

/**
 * @author yuquanmao
 *
 * key
 */
public class Key {
    public int id;
    public String text; //显式
    //功能，如果时字符键（0~9、.、B、C等）为0，功能键(删除、清空、确定 等)为1
    public int type;


    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type=" + type +
                '}';
    }
}

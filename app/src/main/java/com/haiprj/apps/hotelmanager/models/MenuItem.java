package com.haiprj.apps.hotelmanager.models;

public class MenuItem {
    private int resId;
    private String text;

    private MenuType menuType;

    public MenuItem() {
    }

    public MenuItem(int resId, String text, MenuType menuType) {
        this.resId = resId;
        this.text = text;
        this.menuType = menuType;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }
}

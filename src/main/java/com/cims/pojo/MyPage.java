package com.cims.pojo;

public class MyPage {
    //定义页码
    private int page = 1;
    private int  row = 5;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}

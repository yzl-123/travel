package com.it.travel.entity;

import java.util.List;

public class PageBean<T> {
    //从数据库中查询
    private List<T> data;//一页的数据
    private int count;

    //用户提供
    private int current;//当前页
    private int size;//每页大小

    //通过其他属性计算出来
    private int first;//首页
    private int previous; //上一页
    private int next; //下一页
    private int total; //总页数

    //所有的计算都写在get方法中
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //首页
    public int getFirst() {
        return 1;
    }
    public void setFirst(int first) {
        this.first = first;
    }
    /*
    上一页,如果当前大于1,上一页等于当前页-1
     */
    public int getPrevious() {
        if(getCurrent()>1){
            return getCurrent()-1;
        }
        else{
            return 1;
        }
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }
    /*
    下一页,如果当前页小于总页数,下一页=当前页+1
     */
    public int getNext() {
        if(getCurrent()<getTotal()){
            return getCurrent()+1;
        }
        else{
            return getTotal();
        }
    }

    public void setNext(int next) {
        this.next = next;
    }

    /*
    1.如果能整除就正好是这么多页
    2.如果不能整除就加一
     */
    public int getTotal() {
        if (getCount()%getSize()==0){
            return getCount()/ getSize();
        }
        else{
            return getCount()/getSize()+1;
        }
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "data=" + data +
                ", count=" + count +
                ", current=" + current +
                ", size=" + size +
                ", first=" + first +
                ", previous=" + previous +
                ", next=" + next +
                ", total=" + total +
                '}';
    }
}

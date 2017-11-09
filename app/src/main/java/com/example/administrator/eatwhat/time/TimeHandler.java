package com.example.administrator.eatwhat.time;

public interface TimeHandler<T extends Task> {
    void exeTask(T mTask);//当前执行
    void overdueTask(T mTask);//已过期
    void futureTask(T mTask);//未来预约
}
package com.esiran.greenpay.actuator.entity;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Flow<T> {
    private List<Task<T>> tasks;
    private T data;
    public Flow(T data) {
        this.data = data;
        this.tasks = new ArrayList<>();
    }
    public void execDependent(String taskName){
        for (Task<T> task : tasks){
            if (task.dependent().equals(taskName)){
                task.action(this);
                execDependent(task.taskName());
            }
        }
    }
    public void add(Task<T> task){
        tasks.add(task);
    }
    public T getData(){
        return data;
    }


    public abstract void setData(T data);

    public abstract void update(T t);

}

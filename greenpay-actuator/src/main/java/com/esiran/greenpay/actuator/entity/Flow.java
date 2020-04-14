package com.esiran.greenpay.actuator.entity;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Flow<T> {
    private List<Task<T>> tasks;
    private T data;
    private Map<String,Object> context;
    public Flow(T data) {
        this.data = data;
        this.tasks = new ArrayList<>();
        this.context = loadContext();
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

    public <D> D getContext(Class<D> dClass){
        Object obj = context.get(dClass.getName());
        return dClass.cast(obj);
    }


    public abstract void setData(T data);

    public abstract void update(T t);

    public abstract Map<String,Object> loadContext();
}

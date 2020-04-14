package com.esiran.greenpay.actuator.entity;

import java.util.List;

public class Flow {
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    public Flow add(Task task){
        tasks.add(task);
        return this;
    }
}

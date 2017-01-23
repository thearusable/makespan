/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arus
 */
public class Job {

    List<Task> Tasks;
    int id;
    int remainingTime;
    int duration;
    
    /*
    public Job(List<Task> Tasks, int id, int remainingTime, int duration) {
        this.Tasks = Tasks;
        this.id = id;
        this.remainingTime = remainingTime;
        this.duration = duration;
    }
    */
    public Job(int id){
        this.Tasks = new ArrayList<>();
        this.id = id;
        this.remainingTime = 0;
        this.duration = 0;
    }
    
    public void print(){
        System.out.println("Duration: " + duration + " RemainingTime: " + remainingTime);
        for(int i = 0; i < Tasks.size(); i++){
            Tasks.get(i).print();
        }
        System.out.println();
    }
    
    public void addTask(Task task){
        Tasks.add(task);
    }
    
    public Task popTask(){
        Task temp = Tasks.get(0);
        Tasks.remove(0);
        return temp;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    
    public void setRemainingTime(){
        if(Tasks.size() > 0){
            for(int i =0; i < Tasks.size(); i++){
                this.remainingTime += Tasks.get(i).getDuration();
            }
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getDuration() {
        return duration;
    }  
}

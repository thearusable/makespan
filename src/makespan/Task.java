/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

/**
 *
 * @author arus
 */
public class Task {
   
    int machineID;
    int gap;
    int duration;
    int jobID;
    int startTime;
    
    public Task(int machineID, int duration, int jobID, int startTime) {
        this.machineID = machineID;
        this.duration = duration;
        this.jobID = jobID;
        this.startTime = startTime;
        this.gap = 0;
    }
    
    public void print(){
        System.out.println("machineID: " + machineID + " gap:" + gap + " duration: " + duration + " JobID: " + jobID + " startTime: " + startTime + " endTime: " + (startTime + duration));
    }
    
} 

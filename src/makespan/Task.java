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
    int duration;
    int jobID;
    int startTime;
    
    public Task(int machineID, int duration, int jobID, int startTime) {
        this.machineID = machineID;
        this.duration = duration;
        this.jobID = jobID;
        this.startTime = startTime;
    }
    
    public void print(){
        System.out.println("machineID: " + machineID + " duration: " + duration + " JobID: " + jobID + " startTime: " + startTime);
    }
    
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getMachineID() {
        return machineID;
    }

    public int getDuration() {
        return duration;
    }

    public int getJobID() {
        return jobID;
    }

    public int getStartTime() {
        return startTime;
    }
}



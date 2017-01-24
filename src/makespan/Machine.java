/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

import java.util.LinkedList;

/**
 *
 * @author arus2
 */
public class Machine {

    public Machine(int ID) {
        Tasks = new LinkedList<>();
        machineID = ID;
        duration = 0;
    }
    
    public void print(){
        System.out.println("MachineID: " + machineID + " duration: " + duration);
        for(int i = 0; i < Tasks.size(); i++){
            Tasks.get(i).print();
        }
    }
    
    LinkedList<Task> Tasks;
    int machineID;
    int duration;
}

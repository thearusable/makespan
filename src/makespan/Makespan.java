/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author arus
 */
public class Makespan {

    public Makespan() {
        MACHINES_NUMBER = 0;
        JOBS_NUMBER = 0;
        MAKESPAN_ORIGINAL = 0;
        Jobs = new ArrayList<>();
        Sequence = new ArrayList<>();
        Machines = new ArrayList<>();   
    }

    int MACHINES_NUMBER;
    int JOBS_NUMBER;
    int MAKESPAN_ORIGINAL;
    
    List<Job> Jobs;
    List<List<Integer>> Sequence;
    List<Machine> Machines;
    
    public int calcMakespan(){       

        for(int i = 0; i < MACHINES_NUMBER; i++){
            Machines.add(new Machine(i + 1));
        }
        
        for(int j =0; j < JOBS_NUMBER; j++){
            for(int m=0; m < MACHINES_NUMBER; m++){
                int jobID = Sequence.get(m).get(j);
                Task task = Jobs.get(jobID).popFrontTask();
                task.startTime = Machines.get(task.machineID - 1).duration;
                
                Machines.get(task.machineID - 1).Tasks.addLast(task);
                Machines.get(task.machineID - 1).duration = task.startTime + task.duration;
            }
        }
        
        int makespan = 0;
        int jobIdToShow = 0;
        for(int i =0; i< MACHINES_NUMBER; i++){
            if(Machines.get(i).duration > makespan)
            {
                makespan = Machines.get(i).duration;
                jobIdToShow = i;
            }
        }
        
        System.out.println();
        System.out.println("Longest working machine:");
        Machines.get(jobIdToShow).print();
        
        return makespan;
    }
    
    public void calc(String[] args) throws IOException {
        boolean canBeCalculated = true;
        if(args == null || args.length == 0){
            System.out.println("Missing params!");
            canBeCalculated = false;
        }
        // load data file
        if(args.length > 0){
            loadDataFile(args[0]);
        } else {
            System.out.println("Path for Data file is not present!");
            canBeCalculated = false;
        }
        
        //load reasults file
        if(args.length > 1){
            loadResultsFile(args[1]);
        } else {
            System.out.println("Path for Results files is not present!");
            canBeCalculated = false;
        }
        
        if(canBeCalculated){
            //calc makespan
            int calculatedMakespan = calcMakespan();
            //display results
            System.out.println();
            System.out.println("////////////////////////////////////////");
            System.out.println("JOBS: " + JOBS_NUMBER + " MACHINES: " + MACHINES_NUMBER);
            System.out.println("Original makespan: " + MAKESPAN_ORIGINAL 
                + "  calculated makespan: " + calculatedMakespan);
        }else{
            System.out.println("Program cannot continue - missing required params!");
            System.out.println("Example Params: file1.txt file2.txt");
            System.out.println("You can enable debug mode by adding -d as third parameter.");
        }
    }
    
    public boolean isComment(String line){
        return line.length() > 0 && line.charAt(0) == '#';
    }
    
    public String[] split(String text) {
        if(text.length() == 0) return null;
        
        StringTokenizer st = new StringTokenizer(text);
        String result = "";
        
        while(st.hasMoreTokens()){
            result += st.nextToken() + " ";
        }
        
        return result.split(" ");
    }
    
    public void loadDataFile(String filename) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        String line;
        String[] readedData;
        int stage = 1;
        int currentReadedJob = 0;
        
        String[][] times = null;
        String[][] machines = null;
        
        while((line = reader.readLine()) != null){        
            //ignore comments
            if(isComment(line) || line.length() == 0) continue;
            
            //metadata
            if(stage == 1){
                if(line.contains("Times")){
                    stage = 2;
                    continue;
                }
                readedData = split(line);
                JOBS_NUMBER = Integer.parseInt(readedData[0]);
                MACHINES_NUMBER = Integer.parseInt(readedData[1]);
                      
                times = new String[JOBS_NUMBER][MACHINES_NUMBER];
                machines = new String[JOBS_NUMBER][MACHINES_NUMBER];
            }
            //times
            if(stage == 2){
                if(line.contains("Machines")){
                    currentReadedJob = 0;
                    stage = 3;
                    continue;
                }
                readedData = split(line);
                times[currentReadedJob] = readedData;
                currentReadedJob++;
            }
            //machines
            if(stage == 3){
                readedData = split(line);
                machines[currentReadedJob] = readedData;
                currentReadedJob++;
            }
        }
        if(true){
            
            System.out.println("Readed from Data file:");
            System.out.println("JOBS: " + JOBS_NUMBER + " MACHINES: " + MACHINES_NUMBER);
            System.out.println("Times:");
            for(int i =0; i<times.length; i++){
                for(int j = 0; j < times[i].length; j++){
                    System.out.print(times[i][j] + " ");
                }
                System.out.println();
            }
            
            System.out.println("Machines:");
            for(int i =0; i<machines.length; i++){
                for(int j = 0; j < machines[i].length; j++){
                    System.out.print(machines[i][j] + " ");
                }
                System.out.println();
            }
        }
        
        //Build Data
        for(int i = 0; i < JOBS_NUMBER; i++){
            Jobs.add(new Job(i));
        }
        for(int i = 0; i < JOBS_NUMBER; i++){
            for(int j=0; j < MACHINES_NUMBER; j++){
                Jobs.get(i).addTask(new Task(
                        Integer.parseInt(machines[i][j]), //machineID
                        Integer.parseInt(times[i][j]),  //duration
                        i, //jobID
                        0)); //startTime
            }
            Jobs.get(i).setRemainingTime();
        }
    }
    
    public void loadResultsFile(String filename) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        String line;
        String[] readedData;
        int stage = 1;
        int currentReadedJob = 0;
        
        String[][] sequence = null;
        
        while((line = reader.readLine()) != null){        
            //ignore comments
            if(isComment(line) || line.length() == 0) continue;
            
            //metadata
            if(stage == 1){
                readedData = split(line);
                JOBS_NUMBER = Integer.parseInt(readedData[0]);
                MACHINES_NUMBER = Integer.parseInt(readedData[1]);
                MAKESPAN_ORIGINAL = Integer.parseInt(readedData[2]);
                
                sequence = new String[MACHINES_NUMBER][JOBS_NUMBER];
                
                stage = 2;
                continue;
            }
            //sequence
            if(stage == 2){
                readedData = split(line);
                sequence[currentReadedJob] = readedData;
                currentReadedJob++;
            }
        }

        System.out.println("Readed from Results file:");
        System.out.println("Sequence:");
        for(int i =0; i<sequence.length; i++){
            for(int j = 0; j < sequence[i].length; j++){
                System.out.print(sequence[i][j] + " ");
            }
            System.out.println();
        }
        
        for(int i = 0; i < MACHINES_NUMBER; i++){
            Sequence.add(new ArrayList<Integer>(20));
        }
        
        for(int i = 0; i < MACHINES_NUMBER; i++){
            for(int j=0; j< JOBS_NUMBER; j++){
                Sequence.get(i).add(
                        Integer.parseInt(sequence[i][j]));
            }
        }
    }
    

}

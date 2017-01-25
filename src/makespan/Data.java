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
import java.util.StringTokenizer;

/**
 *
 * @author arus2
 */
public class Data {
    public Data() {
        times = null;
        machines = null;
        sequence = null;
        JOBS_NUMBER = 0;
        MACHINES_NUMBER = 0;
        MAKESPAN_ORIGINAL = 0;
    }
    public int[][] times;
    public int[][] machines;
    public int[][] sequence;
    
    public int JOBS_NUMBER;
    public int MACHINES_NUMBER;
    public int MAKESPAN_ORIGINAL;
    
    public void print(){
        System.out.println("JOBS: " + JOBS_NUMBER + " MACHINES: " + MACHINES_NUMBER);
        
        if(times != null){
            System.out.println("Times:");
            for(int i =0; i<times.length; i++){
                for(int j = 0; j < times[i].length; j++){
                    System.out.print(times[i][j] + " ");
                }
                System.out.println();
            }
        }
        
        if(machines != null){
            System.out.println("Machines:");
            for(int i =0; i<machines.length; i++){
                for(int j = 0; j < machines[i].length; j++){
                    System.out.print(machines[i][j] + " ");
                }   
                System.out.println();
            }
        }
            
        if(sequence != null){
            System.out.println("Sequence:");
            for(int i =0; i<sequence.length; i++){
                for(int j = 0; j < sequence[i].length; j++){
                    System.out.print(sequence[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
    
    public void LoadFiles(String DataFileName, String SequenceFileName, boolean debug) throws IOException{
        LoadDataFile(DataFileName);
        LoadSequenceFile(SequenceFileName);
        
        if(debug == true){
            print();
        }
    }
    
    private boolean isComment(String line){
        return line.length() > 0 && line.charAt(0) == '#';
    }
    
    private int[] split(String text) {
        if(text.length() == 0) return null;
        
        StringTokenizer tokenizer = new StringTokenizer(text);
        String line = "";
        
        while(tokenizer.hasMoreTokens()){
            line += tokenizer.nextToken() + " ";
        }
        
        String[] stringData = line.split(" ");
        int[] intData = new int[stringData.length];
        
        for(int i = 0; i < stringData.length; i++){
            intData[i] = Integer.parseInt(stringData[i]);
        }
        
        return intData;
    }
    
    private void LoadDataFile(String DataFileName) throws FileNotFoundException, IOException{
        try ( // DataFile
            BufferedReader reader = new BufferedReader(new FileReader(DataFileName))) {
            String line;
            int[] readedData;
            int stage = 1;
            int currentReadedJob = 0;
            
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
                    JOBS_NUMBER = readedData[0];
                    MACHINES_NUMBER = readedData[1];
                    times = new int[JOBS_NUMBER][MACHINES_NUMBER];
                    machines = new int[JOBS_NUMBER][MACHINES_NUMBER];
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
        }
    }
    
    private void LoadSequenceFile(String SequenceFileNamme) throws FileNotFoundException, IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(SequenceFileNamme))) {
            String line;
            int[] readedData;
            int stage = 1;
            int currentReadedJob = 0;
            
            while((line = reader.readLine()) != null){
                //ignore comments
                if(isComment(line) || line.length() == 0) continue;
                
                //metadata
                if(stage == 1){
                    readedData = split(line);
                    JOBS_NUMBER = readedData[0];
                    MACHINES_NUMBER = readedData[1];
                    MAKESPAN_ORIGINAL = readedData[2];
                    
                    sequence = new int[MACHINES_NUMBER][JOBS_NUMBER];
                    
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
        }
    }
    
}

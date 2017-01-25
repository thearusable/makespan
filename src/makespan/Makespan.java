/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
    Nie trzeba nic rysować, ale w kodzie powinna być tworzona reprezentacja grafowa rozwiązania
    i makespan powinien być określany jako długość najdłuższej ścieżki między źródłem i ujściem 
    - dla bardziej złożonych problemów takie uporządkowane podejście pomaga.
    Można się wspomóc biblioteką algs4, ale zawiera ona reprezentację z obciążonymi łukami (klasa EdgeWeightedDigraph), 
    a graf rozwiązania job shop ma obciążone wierzchołki, trzeba zatem wagi przenieść z każdego wierzchołka na wszystkie wychodzące z niego łuki.
*/

/**
 *
 * @author arus
 */
public class Makespan {

    public Makespan() {
        Jobs = new ArrayList<>();
        Sequence = new ArrayList<>();
        DATA = new Data();
        VERTEX_NUMBER = 0;
    }

    
    List<Job> Jobs;
    List<List<Integer>> Sequence;
    
    /////////////////////////////////////////////////////
    
    private Data DATA;
    
    public EdgeWeightedDigraph Digraph;
    
    public int CALCULATED_MAKESPAN;
    private int VERTEX_NUMBER;
    
    private void calcMakespan(){       
        int max;
        CALCULATED_MAKESPAN = 0;
        for(DirectedEdge e: Digraph.adj(0)){
            max = 0;
            max += e.weight();
            int nextVertex = e.to();
            System.out.println(nextVertex);
            while(nextVertex != VERTEX_NUMBER - 1){
                for(DirectedEdge temp: Digraph.adj(nextVertex)){
                    System.out.println("vertex " + nextVertex + " weight " + temp.weight());
                    max += temp.weight();
                    nextVertex = temp.to();
                    break;
                }
            }
            
            if(max > CALCULATED_MAKESPAN) CALCULATED_MAKESPAN = max;
        }
    }
    
    public void calc(String[] args) throws IOException {
        boolean canBeCalculated = true;
        
        //load reasults file
        if(args.length <= 1){
            canBeCalculated = false;
        }
        
        if(canBeCalculated){
            //Load Files
            DATA.LoadFiles(args[0], args[1], false);
        
            //Build Digraph
            BuildDigraph();
        
            //Print Digraph
            System.out.println(Digraph.toString());
            
            //calc makespan
            //calcMakespan();
            
            //display results
            System.out.println();
            System.out.println("////////////////////////////////////////");
            System.out.println("JOBS: " + DATA.JOBS_NUMBER + " MACHINES: " + DATA.MACHINES_NUMBER + " DATA FILE: " + args[0]);
            System.out.println("Original makespan: " + DATA.MAKESPAN_ORIGINAL 
                + "  calculated makespan: " + CALCULATED_MAKESPAN);
        }else{
            System.out.println("Program cannot continue - missing required params!");
            System.out.println("Example Command: java -jar 'path/to/Makespan.jar' 'Data.txt' 'Sequence.txt'");
        }
    }
    
    private void BuildDigraph(){

        VERTEX_NUMBER = DATA.JOBS_NUMBER * DATA.MACHINES_NUMBER + 2;// - (DATA.JOBS_NUMBER - 2);
        
        Digraph = new EdgeWeightedDigraph(VERTEX_NUMBER);
        
        int[] lastIndexPerMachine = new int[DATA.MACHINES_NUMBER];
        int[] TaskInJob = new int[DATA.JOBS_NUMBER];
        int last_index = 1;
        
        System.out.println("sequence length: " + DATA.sequence.length + " seq1 length " + DATA.sequence[1].length);
        
        for(int j = 0; j < DATA.JOBS_NUMBER; j++){
            for(int i = 0; i < DATA.MACHINES_NUMBER; i++){
            
                int jobID = DATA.sequence[i][j];
                int machineID =  i;
                int duration = DATA.times[jobID][TaskInJob[jobID]];
                
                DirectedEdge edge;
                
                System.out.println("jobID: " + jobID + " mID: " + machineID + " d: " + duration + " TASKINJOB: " + TaskInJob[jobID]);
                
                //polaczenie z poczatkiem 
                if(lastIndexPerMachine[machineID] == 0){
                    edge = new DirectedEdge(0, last_index, duration);
                    Digraph.addEdge(edge);
                    lastIndexPerMachine[machineID] = last_index;
                    last_index++;
                    TaskInJob[jobID]++;
                    continue;
                }
                
                //polaczenie z koncem
                /*
                if(TaskInJob[jobID] == DATA.JOBS_NUMBER - 1){
                    edge = new DirectedEdge(lastIndexPerMachine[machineID], VERTEX_NUMBER - 1, duration);
                    Digraph.addEdge(edge);
                    last_index++;
                    TaskInJob[jobID]++;
                    continue;
                }*/
                
                edge = new DirectedEdge(lastIndexPerMachine[machineID], last_index , duration);
                Digraph.addEdge(edge);
                lastIndexPerMachine[machineID] = last_index;
                last_index++;
                TaskInJob[jobID]++;

            }
        }
        for(int i = 0; i < lastIndexPerMachine.length; i++){
            int jobID = DATA.sequence[i][DATA.JOBS_NUMBER - 1];
            DirectedEdge edge;
            edge = new DirectedEdge(lastIndexPerMachine[i], VERTEX_NUMBER - 1, DATA.times[jobID][DATA.MACHINES_NUMBER -1]);
            Digraph.addEdge(edge);
        }
        
    }
    
}

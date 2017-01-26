/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import java.io.IOException;

/**
 *
 * @author arus
 */
public class Makespan {

    public Makespan() {
        DATA = new Data();
        VERTEX_NUMBER = 0;
    }
    
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
            while(nextVertex != VERTEX_NUMBER - 1){
                for(DirectedEdge temp: Digraph.adj(nextVertex)){
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
            calcMakespan();
            
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

        VERTEX_NUMBER = (DATA.JOBS_NUMBER * DATA.MACHINES_NUMBER) - (DATA.MACHINES_NUMBER - 2) ;
        
        Digraph = new EdgeWeightedDigraph(VERTEX_NUMBER);
        
        int[] lastIndexPerMachine = new int[DATA.MACHINES_NUMBER];
        int[] TaskInJob = new int[DATA.JOBS_NUMBER];
        int last_index = 1;
        
        for(int j = 0; j < DATA.JOBS_NUMBER; j++){
            for(int m = 0; m < DATA.MACHINES_NUMBER; m++){    
                int jobID = DATA.sequence[m][j];
                
                int machineID = DATA.machines[jobID][TaskInJob[jobID]];// j;
                int duration = DATA.times[jobID][TaskInJob[jobID]];
                
                DirectedEdge edge;
                
                if(lastIndexPerMachine[machineID - 1] == 0){ //polaczenie z poczatkiem
                    edge = new DirectedEdge(0, last_index, duration);
                }else if (j == DATA.JOBS_NUMBER - 1){ //polaczenie z koncem
                    edge = new DirectedEdge(lastIndexPerMachine[machineID - 1], VERTEX_NUMBER - 1, duration);
                }else{ //reszta
                    edge = new DirectedEdge(lastIndexPerMachine[machineID - 1], last_index , duration);
                }
                
                Digraph.addEdge(edge);
                lastIndexPerMachine[machineID - 1] = last_index;
                last_index++;
                TaskInJob[jobID]++;
            }
        }
    }
    
}

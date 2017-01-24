/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author arus
 */
public class Main {
    public static void main(String[] args) throws IOException{
        Makespan makespan = new Makespan();
        
        if(args.length < 1){
            System.out.println("Empty params - using default ones");
            
            String[] tempParams = {"ta80.txt", "ta80_seq.txt"};
        
            System.out.println("Arguments:" + Arrays.toString(tempParams));
            makespan.calc(tempParams);
        }else{
            System.out.println("Arguments:" + Arrays.toString(args));
            makespan.calc(args);
        }
    }
}

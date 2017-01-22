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
        String[] tempParams = {"ta11.txt", "ta11_seq.txt", "-d"};
        
        System.out.println("Arguments:" + Arrays.toString(tempParams));
        makespan.calc(tempParams);
        //System.out.println("Arguments:" + Arrays.toString(args));
        //makespan.calc(args);
    }
}

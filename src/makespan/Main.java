/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makespan;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author arus
 */
public class Main {
    public static void main(String[] args) throws IOException{
        Makespan makespan = new Makespan();
        
        String path = new java.io.File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
        path = path.substring(0, path.lastIndexOf(File.separator) + 1);
        System.out.println("path3: " + path);
        
        if(args.length < 1){
            System.out.println("Empty params - using default ones");

            String[] tempParams = {path + "ta60.txt", path + "ta60_seq.txt"};
        
            System.out.println("Arguments:" + Arrays.toString(tempParams));
            makespan.calc(tempParams);
        }else{
            
            for(int i = 0; i < args.length; i++){
                args[i] = path + args[i];
            }
            
            System.out.println("Arguments:" + Arrays.toString(args));
            makespan.calc(args);
        }
    }
}

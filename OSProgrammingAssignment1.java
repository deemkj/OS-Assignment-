/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package osprogrammingassignment1;

/**
 *
 * @author deemkj
 */
   import java.util.*;

public class OSProgrammingAssignment1 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner read=new Scanner(System.in);
        int p=0;
        System.out.println("Enter Number of Processes");
        p=read.nextInt();
         Process [] Processes =new Process[p];
        for(int i =0 ; i<p ; i++){
          System.out.println("Enter Arrival Time and Burst Time For Process"+(i+1));
        double  A=read.nextDouble();
        double  B=read.nextDouble();
        Processes[i]=(new Process(A,B,i));
        }
                for(int i =0 ; i<p ; i++)
                    System.out.println("Process"+(i+1)+"---> "+Processes[i].toString() );

    }
    
}

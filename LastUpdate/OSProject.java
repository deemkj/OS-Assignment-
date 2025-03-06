/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package osproject;

/**
 *
 * @author deemkj
 */
import java.util.*;

public class OSProject {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = input.nextInt();
         // List to store the process objects
        List<Process> processes = new ArrayList<>();
      // Loop to take arrival time and burst time for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for P" + (i + 1) + ": "+"\n");
            int arrival = input.nextInt();
            int burst = input.nextInt();
            // Create a new process and add it to the list
            processes.add(new Process(i + 1, arrival, burst));
        }

        input.close();
// Create a scheduling object and execute the scheduling algorithm
        Scheduling scheduler = new Scheduling(processes);
        scheduler.execute();
    }
}

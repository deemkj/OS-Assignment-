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
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for P" + (i + 1) + ": "+"\n");
            int arrival = input.nextInt();
            int burst = input.nextInt();
            processes.add(new Process(i + 1, arrival, burst));
        }

        input.close();

        Scheduling scheduler = new Scheduling(processes);
        scheduler.execute();
    }
}

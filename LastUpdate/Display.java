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

public class Display {
    public static void printResults(List<Process> processes, double totalWaitingTime, double totalTurnaroundTime, int totalTime, int totalIdleTime, List<String> ganttChart) {
        System.out.println("------------------------------------------");
        for (Process p : processes) {
            System.out.printf("P%-2d: Arrival time = %-2d, Burst time = %-2d ms%n", p.id, p.ArrivalTime, p.BurstTime);
        }

        System.out.println("\nScheduling Algorithm: Shortest Remaining Time First");
        System.out.println("Context Switch: 1 ms\n");

        System.out.println("Time    Process/CS");
        int time = 0;
        while (time < ganttChart.size()) {
            String currentEvent = ganttChart.get(time);
            int startTime = time;
            while (time < ganttChart.size() && ganttChart.get(time).equals(currentEvent)) {
                time++;
            }
            System.out.printf("%-5s  %-10s%n", startTime + "-" + time, currentEvent);
        }

        System.out.println("\nPerformance Metrics");
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();
        double avgWaitingTime = totalWaitingTime / processes.size();
        double cpuUtilization = ((double) (totalTime - totalIdleTime) / totalTime) * 100;

        System.out.printf("Average Turnaround Time: %.2f%n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f%n", avgWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%%n", cpuUtilization);
    }
}

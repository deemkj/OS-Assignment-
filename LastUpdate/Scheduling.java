/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package osproject;

import java.util.*;

public class Scheduling{
    private List<Process> processes;  // List of processes to be scheduled
    private List<String> ganttChart;
    private double totalWaitingTime = 0, totalTurnaroundTime = 0;
    private int totalIdleTime = 0;

    public Scheduling(List<Process> processes) {
        this.processes = processes;
        this.ganttChart = new ArrayList<>();
    }

    public void execute() {
        int time = 0, completed = 0;
        Process lastExecuted = null;
        int lastExecutionEnd = 0;
        
       
        // Priority queue to store processes based on shortest remaining time
        // If two processes have the same remaining time, the one that arrived first is given priority.
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
                (p1, p2) -> (p1.remainingTime == p2.remainingTime) ? Integer.compare(p1.ArrivalTime, p2.ArrivalTime)
                        : Integer.compare(p1.remainingTime, p2.remainingTime)
        );
      // Continue scheduling until all processes are completed
        while (completed < processes.size()) {
              // Add newly arrived processes to the ready queue
            for (Process p : processes) {
                if (p.ArrivalTime == time && !readyQueue.contains(p) && p.remainingTime > 0) {
                    readyQueue.add(p);
                }
            }
           // Check if there are processes ready to execute
            if (!readyQueue.isEmpty()) {
                Process current = readyQueue.poll(); // Get the process with shortest remaining time
            // If a new process is selected, context switch occurs (CS)
                if (lastExecuted != null && lastExecuted != current) {
                    ganttChart.add("CS");
                    time++;
                    readyQueue.add(current); // Readd the current process to the queue
                    lastExecuted = null;
                    continue;
                }
           // If there was idle time before this process started, count it
                if (time > lastExecutionEnd) {
                    totalIdleTime += (time - lastExecutionEnd);
                }
          // Add process execution to Gantt Chart
                ganttChart.add("P" + current.id);
                current.remainingTime--;// Decrease remaining execution time
                lastExecutionEnd = ++time;
         // Add any new arriving processes to the queue at the current time
                for (Process p : processes) {
                    if (p.ArrivalTime == time && !readyQueue.contains(p) && p.remainingTime > 0) {
                        readyQueue.add(p);
                    }
                }
         // If the process has finished execution
                if (current.remainingTime == 0) {
                    completed++;
                    current.completionTime = time;// Store completion time
                    current.turnaroundTime = current.completionTime - current.ArrivalTime;
                    current.waitingTime = current.turnaroundTime - current.BurstTime;
                    totalWaitingTime += current.waitingTime;
                    totalTurnaroundTime += current.turnaroundTime;
                } else {
                     // If process is not finished re-add it to the ready queue
                    readyQueue.add(current);
                }

                lastExecuted = current;
            } else {
                // If no process is available CPU is idle
                ganttChart.add("IDLE");
                totalIdleTime++;
                time++;
            }
        }
      // Call method to print results after scheduling is complete
        Display.printResults(processes, totalWaitingTime, totalTurnaroundTime, time, totalIdleTime, ganttChart);
    }
}

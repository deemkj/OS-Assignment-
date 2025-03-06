/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package osproject;

import java.util.*;

public class Scheduling{
    private List<Process> processes;
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
        
        // Priority queue for process scheduling based on remaining time
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
                (p1, p2) -> (p1.remainingTime == p2.remainingTime) ? Integer.compare(p1.ArrivalTime, p2.ArrivalTime)
                        : Integer.compare(p1.remainingTime, p2.remainingTime)
        );

        while (completed < processes.size()) {
            for (Process p : processes) {
                if (p.ArrivalTime == time && !readyQueue.contains(p) && p.remainingTime > 0) {
                    readyQueue.add(p);
                }
            }

            if (!readyQueue.isEmpty()) {
                Process current = readyQueue.poll();

                if (lastExecuted != null && lastExecuted != current) {
                    ganttChart.add("CS");
                    time++;
                    readyQueue.add(current);
                    lastExecuted = null;
                    continue;
                }

                if (time > lastExecutionEnd) {
                    totalIdleTime += (time - lastExecutionEnd);
                }

                ganttChart.add("P" + current.id);
                current.remainingTime--;
                lastExecutionEnd = ++time;

                for (Process p : processes) {
                    if (p.ArrivalTime == time && !readyQueue.contains(p) && p.remainingTime > 0) {
                        readyQueue.add(p);
                    }
                }

                if (current.remainingTime == 0) {
                    completed++;
                    current.completionTime = time;
                    current.turnaroundTime = current.completionTime - current.ArrivalTime;
                    current.waitingTime = current.turnaroundTime - current.BurstTime;
                    totalWaitingTime += current.waitingTime;
                    totalTurnaroundTime += current.turnaroundTime;
                } else {
                    readyQueue.add(current);
                }

                lastExecuted = current;
            } else {
                ganttChart.add("IDLE");
                totalIdleTime++;
                time++;
            }
        }

        Display.printResults(processes, totalWaitingTime, totalTurnaroundTime, time, totalIdleTime, ganttChart);
    }
}

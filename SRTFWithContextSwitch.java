import java.util.*;


class Process {
    int id, arrivalTime, burstTime, remainingTime, completionTime, waitingTime, turnaroundTime;
    
    Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SRTFWithContextSwitch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];
        
        for (int i = 0; i < n; i++) {
            System.out.println("Enter Arrival Time and Burst Time for Process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }
        
        executeSRTF(processes);
    }

    public static void executeSRTF(Process[] processes) {
        int time = 0, completed = 0, n = processes.length;
        boolean[] isCompleted = new boolean[n];
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        List<String> ganttChart = new ArrayList<>();
        int lastExecutedProcess = -1;
        final int CONTEXT_SWITCH_TIME = 1;
        int totalContextSwitchTime = 0;
        
        while (completed < n) {
            int minIndex = -1;
            int minRemainingTime = Integer.MAX_VALUE;
            
            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && processes[i].arrivalTime <= time && processes[i].remainingTime < minRemainingTime) {
                    minRemainingTime = processes[i].remainingTime;
                    minIndex = i;
                }
            }
            
            if (minIndex == -1) {
                ganttChart.add("IDLE");
                time++;
                continue;
            }
            
            if (lastExecutedProcess != -1 && lastExecutedProcess != minIndex) {
                ganttChart.add("CS"); // Add context switch
                time += CONTEXT_SWITCH_TIME;
                totalContextSwitchTime += CONTEXT_SWITCH_TIME;
            }
            
            ganttChart.add("P" + processes[minIndex].id);
            processes[minIndex].remainingTime--;
            lastExecutedProcess = minIndex;
            
            if (processes[minIndex].remainingTime == 0) {
                processes[minIndex].completionTime = time + 1;
                processes[minIndex].turnaroundTime = processes[minIndex].completionTime - processes[minIndex].arrivalTime;
                processes[minIndex].waitingTime = processes[minIndex].turnaroundTime - processes[minIndex].burstTime;
                totalWaitingTime += processes[minIndex].waitingTime;
                totalTurnaroundTime += processes[minIndex].turnaroundTime;
                isCompleted[minIndex] = true;
                completed++;
            }
            time++;
        }
        
        printResults(processes, totalWaitingTime, totalTurnaroundTime, time, totalContextSwitchTime, ganttChart);
    }

    public static void printResults(Process[] processes, int totalWaitingTime, int totalTurnaroundTime, int totalTime, int totalContextSwitchTime, List<String> ganttChart) {
        System.out.println("\nExample Output:\n");

        System.out.println("Number of processes= " + processes.length + " (P1, P2, P3, P4)");
        System.out.println("Arrival times and burst times as follows:");
        for (Process p : processes) {
            System.out.println("P" + p.id + ": Arrival time = " + p.arrivalTime + ", Burst time = " + p.burstTime + " ms");
        }
        
        System.out.println("Scheduling Algorithm: Shortest remaining time first");
        System.out.println("Context Switch: 1 ms\n");

        System.out.println("Time\tProcess/CS");
        int elapsedTime = 0;
        for (int i = 0; i < ganttChart.size(); i++) {
            String event = ganttChart.get(i);
            System.out.println(elapsedTime + "-" + (elapsedTime + 1) + "\t" + event);
            elapsedTime++;
        }

        System.out.println("\nPerformance Metrics");

        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.length;
        double avgWaitingTime = (double) totalWaitingTime / processes.length;

        int idleTime = Collections.frequency(ganttChart, "IDLE");
        double cpuUtilization = ((double) (totalTime - idleTime - totalContextSwitchTime) / totalTime) * 100;

        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f\n", avgWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }
}
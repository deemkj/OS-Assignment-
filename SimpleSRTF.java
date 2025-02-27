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

public class SimpleSRTF {
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
            
            ganttChart.add("P" + processes[minIndex].id);
            processes[minIndex].remainingTime--;
            
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
        
        printResults(processes, totalWaitingTime, totalTurnaroundTime, time, ganttChart);
    }

    public static void printResults(Process[] processes, int totalWaitingTime, int totalTurnaroundTime, int totalTime, List<String> ganttChart) {
        System.out.println("\nProcess Execution Order (Gantt Chart):");
        System.out.println(String.join(" -> ", ganttChart));
        
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.id + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.completionTime + "\t" + p.turnaroundTime + "\t" + p.waitingTime);
        }
        
        System.out.printf("\nAverage Turnaround Time: %.2f\n", (double) totalTurnaroundTime / processes.length);
        System.out.printf("Average Waiting Time: %.2f\n", (double) totalWaitingTime / processes.length);
        System.out.printf("CPU Utilization: %.2f%%\n", ((double) (totalTime - Collections.frequency(ganttChart, "IDLE")) / totalTime) * 100);
    }
}
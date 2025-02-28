package os.project;
 import java.util.*;

public class Scheduling {

    private Process[] processes; 
    private final int ContexSwitch = 1; // Context switch time in milliseconds

    public Scheduling(Process[] Processes) {
        processes = Processes;
    }

    public void execute() {
        int time = 0; // Current time in scheduling
        int completed = 0; //// Number of completed processes
        
        int numberOfProcesses = processes.length; //// Total number of processes
        
        boolean[] isCompleted = new boolean[numberOfProcesses]; // To know completed processes
        
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        
        // A Gantt Chart is a timeline that represents the execution of processes and shows how long each process runs on the CPU over time
        List<String> ganttChart = new ArrayList<>();
        
        int lastExecutedProcess = -1; // Stores the last executed process
        int totalContextSwitchTime = 0; // Total context switch time

        
        while (completed < numberOfProcesses) { // Continue execution until all processes are finished
            
            int min = -1; // process with the shortest remaining time
            int minRemaining = Integer.MAX_VALUE; // initialize it with the maximum integer value and then updates it with the minimum found

            
            //Find the process with the shortest remaining time
            for (int i = 0; i < numberOfProcesses; i++) {
             
            // check if the process is incomplete + if the process access time is less than or equal to the current time    
            if (!isCompleted[i] && processes[i].ArrivalTime <= time) {
                
            // select the process with the shortest remaining time (SRTF principle) 
            // if two processes have the same remaining time, choose the one that arrived first (FCFS principle)
            if (processes[i].remainingTime < minRemaining || (processes[i].remainingTime == minRemaining && processes[i].ArrivalTime < processes[min].ArrivalTime)) {
            minRemaining = processes[i].remainingTime; // it is being updated to the min time it is detected
            min = i; // it is updated to save the index of the process with the min time
            }
            }
            }


            if (min == -1) { // no process has been found ready to execute at this moment
                ganttChart.add("IDLE"); //it means that the CPU is in idle state because there is no process that can be running at this time
                time++; // move to the next time point
                continue; // to skip the rest of the code in the current iteration
            }

            
            // handle context switching
            if (lastExecutedProcess != -1 && lastExecutedProcess != min) { // there is a previous process that has been implemented + the new process different
                ganttChart.add("CS"); // there is contex switching
                time += ContexSwitch; // switching between processes requires additional time 
                totalContextSwitchTime += ContexSwitch; // to calculate total time for context switch
            }
            
            // Execute the process
            ganttChart.add("P" + processes[min].ID);
            processes[min].remainingTime--; // decrease the remaining time for the process 
            lastExecutedProcess = min; // update the last executed process to the current process


            // Now we need to calculate the necessary attributes for the equations
            
            
            if (processes[min].remainingTime == 0) { // if the process is completed
                
                // we add 1 here because the current time unit has already been spent on the process
                processes[min].completionTime = time + 1; 
                
                //  total time spent from the process arrive the system until it is completed
                processes[min].turnaroundTime = processes[min].completionTime - processes[min].ArrivalTime; 
                
                // time before the process begin execute
                processes[min].waitingTime = processes[min].turnaroundTime - processes[min].BurstTime;
                
                totalWaitingTime += processes[min].waitingTime; //  updated by adding the waiting time for the current process
                totalTurnaroundTime += processes[min].turnaroundTime; // updated by adding the turn around time for the current process
                isCompleted[min] = true; //completed process in the system
                
                // counters are incremented for the number of operations that have been fully executed
                completed++; 
            }
            
            
            time++; // move time forward after execution
        }

        printResults(totalWaitingTime, totalTurnaroundTime, time, totalContextSwitchTime, ganttChart);
    }

    // Print final results
    private void printResults(int totalWaitingTime, int totalTurnaroundTime, int totalTime, int totalContextSwitchTime, List<String> ganttChart) {

        System.out.println("Number of processes= "+processes.length );
        
        System.out.println("Arrival times and burst times as follows:");
        for (Process p : processes) {
            System.out.println("P" + p.ID + ": Arrival time = " + p.ArrivalTime + ", Burst time = " + p.BurstTime + " ms");
        }

        System.out.println("Scheduling Algorithm: Shortest Remaining Time First");
        System.out.println("Context Switch Time: 1 ms");

        
        
       // Print Gantt chart
      System.out.println("Time\tProcess/CS");
      int time = 0; // current time while tracking the timeline
      
      // goes through all events up to the last event recorded in the timeline
      while (time < ganttChart.size()) { 
      String currentEvent = ganttChart.get(time); // it takes the event at this time.
      int startTime = time; 
  
    // count until the process changes or the list finished
    while (time < ganttChart.size() && ganttChart.get(time).equals(currentEvent)) {
        time++;
    }

    System.out.println(startTime + "-" + time + "\t" + currentEvent);
}


        System.out.println("Performance Metrics");
        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.length;
        double avgWaitingTime = (double) totalWaitingTime / processes.length;
        int idles = Collections.frequency(ganttChart, "IDLE"); // fnds the number of times "IDLE" appears in the list
        double cpuUtilization = ((double) (totalTime - idles - totalContextSwitchTime) / totalTime) * 100; // determines the percentage of time the CPU was actively executing processes

        
        // print the results!
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: "+ avgWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%", cpuUtilization);
    }
   
}

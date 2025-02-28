package os.project;
import java.util.Scanner;


public class OSProject {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int numberOfProcesses = input.nextInt();
        Process[] processes = new Process[numberOfProcesses];

        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("Enter Arrival Time and Burst Time for Process " + (i + 1) + ":");
            int arrivalTime = input.nextInt();
            int burstTime = input.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        Scheduling scheduler = new Scheduling(processes);
        scheduler.execute();
    }
}
    
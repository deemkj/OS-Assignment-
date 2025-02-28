package os.project;

public class Process {
  
    public int ID;
    public int ArrivalTime;
    public int BurstTime;  // total time required to perform the operation
    public int remainingTime;
    
    public int completionTime;
    public int waitingTime; 
    public int turnaroundTime; // time for process from ArrivalTime until completionTim
    
    Process(int id, int arrivalTime, int burstTime) {
        ID = id;
        ArrivalTime = arrivalTime;
        BurstTime = burstTime;
        remainingTime = burstTime; // at beginning of the process because there is no time consumed
    }
}
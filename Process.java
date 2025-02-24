package osprogrammingassignment1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author deemkj
 */
public class Process {
    double ArrivalTime ;
     double  BurstTime;
 int  ProcessID;
    

    public Process(double ArrivalTime, double BurstTime, int ProcessID) {
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.ProcessID = ProcessID;
    }

    @Override
    public String toString() {
        return  "ArrivalTime=" + ArrivalTime + ", BurstTime=" + BurstTime +", ProcessID="+ProcessID;
    }
     
    
}

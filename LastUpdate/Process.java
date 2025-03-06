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

public class Process {
    public int id, 	ArrivalTime, 	BurstTime, remainingTime,completionTime, waitingTime, turnaroundTime;

    public Process(int id, int 	ArrivalTime, int BurstTime) {
        this.id = id;
        this.ArrivalTime = 	ArrivalTime;
        this.BurstTime = BurstTime;
        this.remainingTime = BurstTime;
    }
}

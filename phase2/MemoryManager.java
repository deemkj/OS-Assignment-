package csc272project;

import java.util.*;

public class MemoryManager {
    List<MemoryInitialization> memory = new ArrayList<>();
    int strategy;

    public void createMemory(int[] sizes, int strategy) {
        this.strategy = strategy;
        int start = 0;
        for (int i = 0; i < sizes.length; i++) {
            memory.add(new MemoryInitialization(i, sizes[i], start));
            start += sizes[i];
        }
    }

    public boolean allocate(String processID, int size) {
        MemoryInitialization selected = null;

        if (strategy == 1) { // First-Fit
            for (MemoryInitialization block : memory) {
                if (!block.isAllocated && block.size >= size) {
                    selected = block;
                    break;
                }
            }
        } else if (strategy == 2) { // Best-Fit
            int min = Integer.MAX_VALUE;
            for (MemoryInitialization block : memory) {
                if (!block.isAllocated && block.size >= size && block.size < min) {
                    min = block.size;
                    selected = block;
                }
            }
        } else if (strategy == 3) { // Worst-Fit
            int max = -1;
            for (MemoryInitialization block : memory) {
                if (!block.isAllocated && block.size >= size && block.size > max) {
                    max = block.size;
                    selected = block;
                }
            }
        }

        if (selected != null) {
            selected.allocate(processID, size);
            System.out.println(processID + " Allocated at address " + selected.startAddress +
                    ", and the internal fragmentation is " + selected.internalFragmentation);
            return true;
        } else {
            System.out.println("Error: No suitable block available for allocation.");
            return false;
        }
    }

    public boolean deallocate(String processID) {
        for (MemoryInitialization block : memory) {
            if (block.isAllocated && block.processID.equals(processID)) {
                block.deallocate();
                System.out.println("Process " + processID + " deallocated successfully.");
                return true;
            }
        }
        System.out.println("Error: Process ID not found.");
        return false;
    }

    public void printBasicInfo() {
        System.out.println("Memory blocks:");
        System.out.println("============================================");
        System.out.println("Block#      size                 start-end     status");
        System.out.println("============================================");
        for (MemoryInitialization block : memory) {
            System.out.printf("Block%d      %d                 %d-%d      %s\n",
                    block.blockNumber, block.size, block.startAddress, block.endAddress,
                    block.isAllocated ? "allocated" : "free");
        }
        System.out.println("============================================");
    }

    public void printDetailedInfo() {
        System.out.println("Memory blocks:");
        System.out.println("==========================================================");
        System.out.println("Block#     size          start-end     status         ProcessID  InternalFragmentation");
        System.out.println("==========================================================");
        for (MemoryInitialization block : memory) {
            System.out.printf("Block%d   %-10d%-15s%-15s%-10s%d\n",
                    block.blockNumber,
                    block.size,
                    block.startAddress + "-" + block.endAddress,
                    block.isAllocated ? "allocated" : "free",
                    block.processID,
                    block.internalFragmentation);
        }
        System.out.println("==========================================================");
    }    
}

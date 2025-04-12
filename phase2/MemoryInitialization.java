package csc272project;

public class MemoryInitialization {

    int blockNumber;
    int size;
    int startAddress;
    int endAddress;
    boolean isAllocated;
    String processID;
    int internalFragmentation;

    public MemoryInitialization (int blockNumber, int size, int startAddress) {
        this.blockNumber = blockNumber;
        this.size = size;
        this.startAddress = startAddress;
        this.endAddress = startAddress + size - 1;
        this.isAllocated = false;
        this.processID = "Null";
        this.internalFragmentation = 0;
    }

    public void allocate(String processID, int processSize) {
        this.isAllocated = true;
        this.processID = processID;
        this.internalFragmentation = size - processSize;
    }

    public void deallocate() {
        this.isAllocated = false;
        this.processID = "Null";
        this.internalFragmentation = 0;
    }  
}

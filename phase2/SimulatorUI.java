package csc272project;

import java.util.Scanner;

public class SimulatorUI {
    Scanner scanner = new Scanner(System.in);
    MemoryManager memoryManager = new MemoryManager();

    public void start() {
        System.out.print("Enter the total number of blocks: ");
        int numBlocks = scanner.nextInt();
        int[] sizes = new int[numBlocks];
        System.out.print("Enter the size of each block in KB: ");
        for (int i = 0; i < numBlocks; i++) {
            sizes[i] = scanner.nextInt();
        }

        System.out.print("Enter allocation strategy (1 for first-fit, 2 for best-fit, 3 for worst-fit): ");
        int strategy = scanner.nextInt();

        memoryManager.createMemory(sizes, strategy);
        System.out.println("Memory blocks are created…");
        memoryManager.printBasicInfo();

        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> allocate();
                case 2 -> deallocate();
                case 3 -> memoryManager.printDetailedInfo();
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void showMenu() {
        System.out.println("============================================");
        System.out.println("1) Allocates memory blocks");
        System.out.println("2) De-allocates memory blocks");
        System.out.println("3) Print report about the current state of memory and internal Fragmentation");
        System.out.println("4) Exit");
        System.out.println("============================================");
        System.out.print("Enter your choice: ");
    }

    private void allocate() {
        System.out.print("Enter the process ID and size of process: ");
        String processID = scanner.next();
        int size = scanner.nextInt();
        memoryManager.allocate(processID, size);
    }

    private void deallocate() {
        System.out.print("Enter the process ID to be released from the memory: ");
        String processID = scanner.next();
        memoryManager.deallocate(processID);
    }
}

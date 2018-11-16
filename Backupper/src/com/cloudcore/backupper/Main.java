package com.cloudcore.backupper;

import com.cloudcore.backupper.core.FileSystem;


public class Main {

    /* Methods */
    /**
     * Creates an Backupper instance and runs it.
     * @param args
     */
    public static void main(String[] args) {
        try {
            setup();
            Backuper exporter = new Backuper();

            exporter.backupCoins();
        } catch (Exception e) {
            System.out.println("Uncaught exception - " + e.getLocalizedMessage());
        }
    }

    /**
     * Sets up the FileSystem instance in the defined rootFolder.
     */
    private static void setup() {
        FileSystem.createDirectories();

    }
}

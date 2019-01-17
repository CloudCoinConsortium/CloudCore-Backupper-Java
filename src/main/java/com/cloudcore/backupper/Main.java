package com.cloudcore.backupper;

import com.cloudcore.backupper.core.FileSystem;
import com.cloudcore.backupper.server.Command;
import com.cloudcore.backupper.server.FolderWatcher;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;

public class Main {



    /* Methods */
    /**
     * Creates an Backupper instance and runs it.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 0 && Files.exists(Paths.get(args[0]))) {
            System.out.println("New root path: " + args[0]);
            FileSystem.changeRootPath(args[0]);
        }

        ArrayList<Command> commands = FileSystem.getCommands();
        if (commands.size() > 0)
            for (Command command : commands) {
                new Backupper().backupCoins(command);
                FileSystem.archiveCommand(command);
            }

        FolderWatcher watcher = new FolderWatcher(FileSystem.CommandFolder);
        System.out.println("Watching for commands at " + FileSystem.CommandFolder);
        while (true) {
            try {
                Thread.sleep(1000);

                if (watcher.newFileDetected()) {
                    System.out.println(Instant.now().toString() + ": Exporting coins...");
                    commands = FileSystem.getCommands();
                    for (Command command : commands) {
                        new Backupper().backupCoins(command);
                        FileSystem.archiveCommand(command);
                    }
                }
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
            }
        }
    }

    /**
     * Sets up the FileSystem instance in the defined rootFolder.
     */

}
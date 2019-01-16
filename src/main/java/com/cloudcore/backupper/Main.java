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

        ArrayList<Command> commands;

        FolderWatcher watcher = new FolderWatcher(FileSystem.CommandFolder);
        System.out.println("Watching for commands at " + FileSystem.CommandFolder);
        commands = FileSystem.getCommands();
        if (commands.size() > 0)
            for (Command command : commands) {
                new Backupper().backupCoins(command);
                FileSystem.archiveCommand(command);
            }

        while (true) {
            if (watcher.newFileDetected()) {
                System.out.println(Instant.now().toString() + ": Exporting coins...");
                commands = FileSystem.getCommands();
                if (commands.size() > 0)
                    for (Command command : commands) {
                        new Backupper().backupCoins(command);
                        FileSystem.archiveCommand(command);
                    }
            }
        }
    }

    /**
     * Sets up the FileSystem instance in the defined rootFolder.
     */

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudcore.backupper.utils;

import com.cloudcore.backupper.core.FileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.cloudcore.backupper.core.FileSystem.CommandFolder;


/**
 *
 * @author neeraj
 */
public class CommandUtil {


    public static void saveCommand(byte[] command) throws IOException {
        FileSystem.copyFiles(new File(FileSystem.CommandFolder), new File(FileSystem.CommandHistory),true);
        String filename = "Backupper.backup.txt";
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n"
                + "      \"command\": \"backup\",\n"
                + "      \"account\": \"default\",\n"
                + "      \"toPath\": \"default" + "\"\n"
                + "}").getBytes();
    }

}

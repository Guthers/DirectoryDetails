package com.company;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {
        if (System.console() == null) {
            return;
        }
        if (args.length < 1) {
            System.console().writer().println("Usage: java -jar [PATH]directorydetails.jar [head directory]");
            return;
        }
        Directory mainDir;
        String dir = processIncomingDir(args);

        try {
            mainDir = new Directory(dir);
        } catch (Exception e) {
            System.console().writer().println("Exception message: " + e.getMessage());
            return;
        }

        try {
            File f = File.createTempFile("directory-" + mainDir.getName() + "-output", ".txt");
            FileWriter fw = new FileWriter(f);
            fw.write("\\" + mainDir.getName() + '\n');
            fw.write(mainDir.getLinerage(1));
            fw.close();

            if (Desktop.isDesktopSupported()) {
                Desktop d = Desktop.getDesktop();
                d.open(f);
            } else {
                System.console().writer().println("Could not open file, it is located: " + f.getPath());
            }

        } catch (Exception e) {
            System.console().writer().println("Could not output to file.\nException message: " + e.getMessage());
        }


    }

    private static String processIncomingDir(String[] args) {
        String dir = args[0];
        if (args.length > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append(dir);
            for (int i = 1; i < args.length; i++) {
                sb.append(' ');
                sb.append(args[i]);
            }
            dir = sb.toString();
        }

        dir = dir.replaceAll("\\\\","\\\\\\\\");
        return dir;
    }
}

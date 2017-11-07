package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

class Directory {
    private HashMap<String,Directory> subDirs = new HashMap<String, Directory>();
    private ArrayList<String> files = new ArrayList<String>();
    private File dir;

    Directory (String dp) throws FileNotFoundException {
        if (dp.substring(dp.length() - 1, dp.length()).equals("/")) {
            //remove leading /
            dp = dp.substring(0,dp.length() - 1);
        }
        dir = new File(dp);

        if (!dir.exists()) {
            throw new FileNotFoundException();
        }

        //Add all the subdirs
        for (File child: dir.listFiles()) {
            if (child.isDirectory()) {
                subDirs.put(child.getName(),new Directory(child.getPath()));
            } else if (child.isFile()) {
                files.add(child.getName());
            }
        }
    }

    String getChildren() {
        StringBuilder sb = new StringBuilder();
        if (!files.isEmpty()) {
            sb.append("Files: ");
            sb.append('\n');
        }
        for (String f: files) {
            sb.append(f);
            sb.append('\n');
        }
        if (subDirs.size() > 0) {
            sb.append("Directories: ");
            sb.append('\n');
        }
        for (String s: subDirs.keySet()){
            sb.append(s);
            sb.append('\n');
        }
        return sb.toString().trim();
    }

    String getLinerage(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String lvlIndent = "";
        if (indentLevel > 0) {
            lvlIndent = new String(new char[indentLevel]).replace("\0", "   ");
        }
        for (String f: files) {
            // indent for level
            sb.append(lvlIndent);
            sb.append(f);
            sb.append('\n');
        }
        for (String s: subDirs.keySet()){
            sb.append(lvlIndent); // indent for level
            sb.append('\\'); //Used to identify it is a directory
            sb.append(s);
            sb.append('\n');
            // get sub files and dirs
            sb.append(subDirs.get(s).getLinerage(indentLevel + 1));
        }
        return sb.toString();
    }

    String getName() {
        return this.dir.getName();
    }
}

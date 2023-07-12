package service;

import java.io.File;

public class FileSystem {
    public File makeParentFolder(String name) {
        File parentFolder = new File(System.getProperty("java.io.tmpdir"), name);
        if (parentFolder.exists()) {
            return parentFolder;
        }

        if (parentFolder.mkdir()) {
            return parentFolder;
        } else {
            throw new RuntimeException("Folder can not be made.");
        }
    }

    public File makeChildFolder(File parentFolder, String name) {
        File childFolder = new File(parentFolder, name);
        if (childFolder.exists()) {
            return childFolder;
        }

        if (childFolder.mkdir()) {
            return childFolder;
        } else {
            throw new RuntimeException("Folder can not be made.");
        }
    }
}

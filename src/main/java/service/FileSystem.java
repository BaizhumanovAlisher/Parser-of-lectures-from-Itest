package service;

import java.io.File;

public class FileSystem {
    private final String path;
    private final Translator translator;
    private File parentFolder;

    public FileSystem(String path, Translator translator) {
        this.path = path;
        this.translator = translator;
    }
    public void makeParentFolder(String name) {
        parentFolder = new File("%s%s%s".formatted(path, File.separator, translator.translate(name)));
        if (parentFolder.exists()) {
            return;
        }

        if (!parentFolder.mkdir()) {
            throw new RuntimeException("Folder can not be made.");
        }
    }

    public void makeChildFolder(String name) {
        String fileName = "%s%s%s".formatted(parentFolder.getPath(), File.separator, translator.translate(name));

        File childFolder = new File(fileName);
        if (childFolder.exists()) {
            return;
        }

        if (!childFolder.mkdir()) {
            throw new RuntimeException("Folder can not be made. Name:" + name);
        }
    }
}

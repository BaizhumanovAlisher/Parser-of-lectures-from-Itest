package service;

import java.io.*;

public class FileSystem {
    private final File folderLecture;
    private final File folderAudio;
    private final File outline;
    private FileWriter fileWriter;

    public FileSystem(String path, String nameFolderLecture, String nameFolderAudio) {
        this.folderLecture = makeFile(path, nameFolderLecture);
        this.folderAudio = makeFile(path, nameFolderAudio);
        outline = new File(folderLecture.getPath(),"0} OUTLINE.md");
    }
    private File makeFile(String path, String name) {
        File folder = new File("%s%s%s".formatted(path, File.separator, name));
        folder.mkdirs();
        return folder;
    }

    public void saveAudio(String fileName, byte[] audio) {
        File file = new File(folderAudio, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(audio);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveLecture(String title, String fileName, String lecture) {
        try {
            File file = new File(folderLecture, fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write("# %s\n\n%s".formatted(title, lecture));

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startWriting()  {
        try {
            fileWriter = new FileWriter(outline);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeText(String text) {
        try {
            fileWriter.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endWriting() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
                fileWriter = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

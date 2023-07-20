package service;

import model.Lecture;

import java.io.File;

public class FileSystem {
    private final File folderLecture;
    private final File folderAudio;
    private final File outline;

    public FileSystem(String path, String nameFolderLecture, String nameFolderAudio) {
        this.folderLecture = makeFile(path, nameFolderLecture);
        this.folderAudio = makeFile(path, nameFolderAudio);
        outline = new File("%s%s%s".formatted(folderLecture.getAbsolutePath(), File.separator, "OUTLINE.md"));
    }
    private File makeFile(String path, String name) {
        return new File("%s%s%s".formatted(path, File.separator, name));
    }

    public void saveLecture(Lecture lecture){

        writeLectureNameInOutline();
    }

    private void writeLectureNameInOutline() {
    }

    public void startWriteOutline() {

    }

    public void writeChapterNameInOutline() {

    }

    public void endWriteOutline() {
    }
}

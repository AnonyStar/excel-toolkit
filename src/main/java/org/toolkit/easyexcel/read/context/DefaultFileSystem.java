package org.toolkit.easyexcel.read.context;

import javax.persistence.Transient;
import java.io.*;

/**
 * @author: zhoucx
 * @time: 2021-06-22
 */
public class DefaultFileSystem implements FileSystem<File>{

    private File file;
    private transient InputStream inputStream;
    private transient OutputStream outputStream;

    private File resultFileSystem;

    public DefaultFileSystem(File file) throws FileNotFoundException {
        setFileSystem(file);
    }

    @Override
    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void setFileSystem(File fileSystem) throws FileNotFoundException {
        this.file = fileSystem;
        this.inputStream = new FileInputStream(file);
        String dirPath = fileSystem.getParentFile().getPath();
        String name = fileSystem.getName().substring(0, fileSystem.getName().lastIndexOf(".")) + "-result";
        String substring = fileSystem.getName().substring(fileSystem.getName().lastIndexOf("."));
        resultFileSystem = new File(dirPath + File.separator + name + substring);
        this.outputStream = new FileOutputStream(resultFileSystem);
    }

    @Override
    public File getFileSystem() {
        return this.file;
    }

    @Override
    public void removeSourcess() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public File getResultFileSystem() {
        return resultFileSystem;
    }
}

package org.toolkit.easyexcel.read.context;

import java.io.*;

/**
 * @author: zhoucx
 * @time: 2021-06-22
 */
public class DefaultFileSystem implements FileSystem<File>{

    private File file;
    private InputStream inputStream;
    private OutputStream outputStream;

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
        this.outputStream = new FileOutputStream(dirPath + File.separator + name + substring);
    }

    @Override
    public File getFileSystem() {
        return this.file;
    }
}

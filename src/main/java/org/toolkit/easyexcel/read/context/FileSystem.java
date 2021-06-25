package org.toolkit.easyexcel.read.context;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: zhoucx
 * @time: 2021-06-22
 */
public interface FileSystem<T> {


    OutputStream getOutputStream();

    void setOutputStream(OutputStream outputStream);

    InputStream getInputStream();

    void setInputStream(InputStream inputStream);

    void setFileSystem(T fileSystem) throws FileNotFoundException;

    T getFileSystem();

}

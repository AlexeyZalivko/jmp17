package jmp.services;

import com.sun.jersey.core.header.FormDataContentDisposition;
import jmp.database.data.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alex on 20.03.17.
 */
public interface FileSystemService {
    File getLogoByUser(final User user) throws Exception;

    void saveImage(final InputStream imageStream, final FormDataContentDisposition form, final String id) throws IOException;
}

package jmp.services;

import com.sun.jersey.core.header.FormDataContentDisposition;
import jmp.database.dao.UserDAO;
import jmp.database.dao.UserDAOImpl;
import jmp.database.data.User;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by alex on 20.03.17.
 */
public class FileSystemServiceImpl implements FileSystemService {
    private static Logger log = Logger.getLogger(FileSystemServiceImpl.class.getName());

    public static final String ERROR_USER_HASN_T_LOGO = "That user hasn't logo";
    public static final String USER_HOME = "/home/alex";
    public static final String FS_SEPARATOR = "/";
    public static final String UNDERLINE = "_";

    @Override
    public File getLogoByUser(final User user) throws Exception {

        if (user == null || StringUtils.isEmpty(user.getLogo())) {
            throw new Exception(ERROR_USER_HASN_T_LOGO);
        }

        return new File(getBasePath(user.getLogo(), user.getId().toString()));
    }

    @Override
    public void saveImage(final InputStream imageStream, final FormDataContentDisposition form, final String id) throws IOException {
        OutputStream ostream = null;
        final String resultPath = getBasePath(form.getFileName(), id);
        final File file = new File(resultPath);
        ostream = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int length = 0;
        while ((length = imageStream.read(b)) != -1) {
            ostream.write(b, 0, length);
        }
        ostream.flush();
        ostream.close();
    }

    private String getBasePath(final String fileName, final String id) {
        final String name = USER_HOME + FS_SEPARATOR + id + UNDERLINE + fileName;
        log.info("Image name: " + name);
        return name;
    }
}

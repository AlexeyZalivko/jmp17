import java.io.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Created by alex on 04.02.17.
 */
public class MyClassloader extends ClassLoader {

    private Logger log = Logger.getLogger(MyClassloader.class);

    private static final String CLASS_EXTENSION = ".class";

    private String pathToFolder;

    public MyClassloader(final String pathToFolder, final ClassLoader parent) {
        super(parent);
        this.pathToFolder = pathToFolder;
    }

    @Override
    protected Class<?> findClass(final String className) throws ClassNotFoundException {
        try {
            byte[] loaded = fetchFileSystem(pathToFolder + className + CLASS_EXTENSION);
            return defineClass(className, loaded, 0, loaded.length);
        } catch (final FileNotFoundException e) {
	    log.error(e);
            //System.out.println(e);
            return super.findClass(className);
        } catch (final IOException e) {
	    log.error(e);
            //System.out.println(e);
            return super.loadClass(className);
        }
    }

    private byte[] fetchFileSystem(final String fullPath) throws FileNotFoundException, IOException {
        final File classFile = new File(fullPath);
        final InputStream is = new FileInputStream(classFile);

        final long lenght = classFile.length();
        if (lenght > Integer.MAX_VALUE) {
            throw new IOException("File is too large");
        }

        final byte[] loaded = new byte[Long.valueOf(lenght).intValue()];

        int readCount = 0;
        int offset = 0;

        while (offset < loaded.length && (readCount = is.read(loaded, offset, loaded.length - offset)) >= 0) {
            offset += readCount;
        }

        if (offset < loaded.length) {
            throw new IOException("File is not completely loaded");
        }

        is.close();
        return loaded;
    }
}

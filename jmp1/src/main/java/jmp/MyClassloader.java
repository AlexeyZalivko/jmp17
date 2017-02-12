package jmp;

/**
 * Created by alex on 04.02.17.
 */
public class MyClassloader extends ClassLoader {

    private Class parentClass;

    public MyClassloader(final Class parentClass, final ClassLoader parent) {
        super(parent);

        this.parentClass = parentClass;
    }

    @Override
    protected Class<?> findClass(final String className) throws ClassNotFoundException {
        final String fullClassName = parentClass.getPackage().getName() + "." + className;
        return Class.forName(fullClassName);
    }
}

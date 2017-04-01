package jmp;

import jmp.database.DBManager;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, SQLException {
        System.out.println( "Hello World!" );
        DBManager.getConnection();
    }
}

package com.example.main;

// Importovanje neophodnih paketa za rad sa MySQL bazom podataka
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {

    //* private static boolean isDriverLoaded = false;
    private static boolean isDriverLoaded = true;

 /*   static{
        try{
           // Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            isDriverLoaded = true;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private final static String url="jdbc:oracle:thin:@localhost:1521:XE";
    private final static String user="SYSTEM";
    private final static String password="system";
 */
// URL baze podataka, podaci o korisniku baze podataka
    static final String url ="jdbc:mysql://localhost:3306/employee?useSSL=false&allowPublicKeyRetrieval=true";
    //static final String DB_DRV = "com.mysql.cj.jdbc.Driver";
    static final String user = "vonk";
    static final String password = "pw";

// Učitavanje drajvera, Otvaranje konekcije, Kreiranje Connection objekta
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        if(isDriverLoaded) {
            con  = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established");
        }
        return con;
    }

// Zatvaranje konekcije
    public static void closeConnection(Connection con) throws SQLException {
        if(con != null) {
            con.close();
            System.out.println("Connection closed");
        }
    }
}

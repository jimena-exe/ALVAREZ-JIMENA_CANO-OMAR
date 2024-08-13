package db;

import model.Odontologo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OdontologosLog {
    public static void main(String[] args) throws Exception{
        Class.forName("org.h2.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:h2:"+
                "./Database/my", "root", "myPassword");
        Statement stmt = con.createStatement();

        String crearSql = "DROP TABLE IF EXISTS ODONTOLOGOS; \n" +
                "CREATE TABLE ODONTOLOGOS(ID INT AUTO_INCREMENT PRIMARY KEY, NUMERO_MATRICULA VARCHAR(50), NOMBRE VARCHAR(50), APELLIDO VARCHAR(50)); \n" +
                "INSERT INTO ODONTOLOGOS VALUES(DEFAULT, 'MATRICULA1','ALBERTO','GOMEZ');\n" +
                "INSERT INTO ODONTOLOGOS VALUES(DEFAULT, 'MATRICULA2','KATERINE','CORREA');\n"+
                "INSERT INTO ODONTOLOGOS VALUES(DEFAULT, 'MATRICULA3','SARA','VANEGAS');";
        stmt.execute(crearSql);

        //Consultamos
        String sql = "SELECT * FROM ODONTOLOGOS";
        ResultSet resultSet = stmt.executeQuery(sql);

        while(resultSet.next()) {
            System.out.println(resultSet.getInt(1) +
                    resultSet.getString(2) +
                    resultSet.getString(3) +
                    resultSet.getString(4)
            );

        }


    }
}

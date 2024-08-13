package test;

import dao.impl.DaoH2Odontologo;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    public static final Logger logger = Logger.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new DaoH2Odontologo());

    @BeforeAll
    static void crearTabla(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./examen1;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error: "+ e.getMessage());
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                logger.error("Error: "+ e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Testear creación de los odontologos ")
    void caso1(){
        //Dado
        Odontologo odontologo1 = new Odontologo("MATRICULA4","ALEJANDRO","OCHOA");
        Odontologo odontologo2 = new Odontologo("MATRICULA5","JULIAN","PEREZ");
        //Cuando
        Odontologo odontologoDesdeLaDB = odontologoService.guardarOdontologo(odontologo1);
        //Entonces
        assertNotNull(odontologoDesdeLaDB.getId());
    }

    @Test
    @DisplayName("Testear que un odontologo fue cargado correctamente con su domicilio")
    void caso2(){
        //Dado
        Odontologo odontologo = new Odontologo("Matricula6","Maria", "Rojas");
        //cuando
        Odontologo odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
        // entonces
        assertNotNull(odontologoDesdeDb.getId());
    }




}
package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoH2Odontologo implements IDao<Odontologo> {
    //Logger
    public static final Logger logger = Logger.getLogger(DaoH2Odontologo.class);

    //BD
    public static final String INSERT = "INSERT INTO ODONTOLOGOS VALUES(DEFAULT,?,?,?)"; //guardar
    public static final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";


    @Override
    public Odontologo guardar(Odontologo odontologo) {
        //conexion
        Connection connection = null;
        Odontologo odontologoRespuesta = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, odontologo.getNumeroMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();
            connection.commit();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                odontologoRespuesta = new Odontologo(id, odontologo.getNumeroMatricula(),
                        odontologo.getNombre(), odontologo.getApellido());
            }
            logger.info("Odontologo: " + odontologoRespuesta);


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoRespuesta;
    }

    @Override
    public List<Odontologo> listarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologoDesdeLaDB = null;
        try {
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String NumeroMatricula = resultSet.getString(2);
                String Nombre = resultSet.getString(3);
                String Apellido = resultSet.getString(4);

                odontologoDesdeLaDB = new Odontologo(id, NumeroMatricula, Nombre, Apellido);

                // vamos cargando la lista de odontologos
                odontologos.add(odontologoDesdeLaDB);
                logger.info("Odontologo " + odontologoDesdeLaDB);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }

        }
        return odontologos;
    }
}



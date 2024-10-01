package Ejercicio1_SQL.Persistencia;
import java.sql.*;

public class Conexion implements SentenciasSQL{
    private Connection conexion = null;
    private String user = "root";
    private String password = "";
    private String puerto = "3306";
    private String base = "computadoras";
    private String host = "localhost";


    public Connection estableceConexion() {
        if (conexion != null) {
            return conexion;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urlConexion = "jdbc:mysql://" + host + ":" + puerto + "/" + base;
            conexion = DriverManager.getConnection(urlConexion, user, password);
            conexion.setAutoCommit(false);
            crearTablas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }

    private void crearTablas() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE_COMPUTADORA);
            stmt.executeUpdate(CREATE_TABLE_COMPONENTE);
        } catch (SQLException e) {
            throw new SQLException("Error al crear la base de datos o tablas: " + e.getMessage());
        }
    }

    public void cierraConexion() {
        try {
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

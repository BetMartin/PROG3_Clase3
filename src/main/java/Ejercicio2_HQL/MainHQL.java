package Ejercicio2_HQL;

import Ejercicio1_SQL.Persistencia.Conexion;
import Ejercicio1_SQL.Vista.UIComputadora;

import javax.swing.*;
import java.sql.SQLException;

public class MainHQL {
    public static void main(String[] args) throws SQLException {
        Conexion conexion = new Conexion();
        try{
            conexion.estableceConexion();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIComputadora.createAndShowUI();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            conexion.cierraConexion();
        }
    }
}

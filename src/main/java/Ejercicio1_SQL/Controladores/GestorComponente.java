package Ejercicio1_SQL.Controladores;

import Ejercicio1_SQL.Modelo.Componente;
import Ejercicio1_SQL.Modelo.Computadora;
import Ejercicio1_SQL.Persistencia.Conexion;
import Ejercicio1_SQL.Persistencia.SentenciasSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GestorComponente extends Conexion implements SentenciasSQL {
    private Componente componente;
    private PreparedStatement sentencia;
    private ResultSet datos;
    GestorComputadora gestorComputadora =new GestorComputadora();

    public void agregarComponente(Componente componenteNuevo) throws Exception {
        try {
            String codigoComputadora = componenteNuevo.getComputadora().getCodigo();
            Computadora computadoraAsociada = gestorComputadora.obtenerComputadora(codigoComputadora);
            if (computadoraAsociada == null) {
                computadoraAsociada = componenteNuevo.getComputadora();
                gestorComputadora.agregarComputadora(computadoraAsociada);
            } else {
                Object[] params = {componenteNuevo.getId(), componenteNuevo.getNombre(), componenteNuevo.getNroSerie(), componenteNuevo.getComputadora().getId()};
                sentencia = estableceConexion().prepareStatement(INSERT_COMPONENTE);
                for (int i = 0; i < params.length; i++) {
                    sentencia.setObject(i + 1, params[i]);
                }
                sentencia.executeUpdate();
                estableceConexion().commit();
            }
        }catch (SQLException ex) {
            estableceConexion().rollback();
            throw ex;
        }finally {
            if (sentencia != null) {
                sentencia.close();
            }
        }
    }
    public void borrarComponente(String id) throws SQLException {
        try {
            Object[] params = {id};
            sentencia = estableceConexion().prepareStatement(DELETE_COMPONENTE);
            sentencia.setObject(1, params[0]);
            sentencia.executeUpdate();
            estableceConexion().commit();
        }catch (SQLException ex) {
            estableceConexion().rollback();
            throw ex;
        } finally {
            if (sentencia != null) sentencia.close();
        }
    }
    public Componente obtenerComponente(String nroSerie) throws SQLException {
        datos=null;
        try {
            Object[] params = {nroSerie};
            Componente componenteBuscado = new Componente();
            sentencia = estableceConexion().prepareStatement(SELECT_COMPONENTE);
            sentencia.setObject(1, params[0]);
            datos=sentencia.executeQuery();
            while (datos.next()) {
                componenteBuscado.setId(datos.getLong("id"));
                componenteBuscado.setNombre(datos.getString("nombre"));
                componenteBuscado.setNroSerie(datos.getString("nroSerie"));
            }
            return componenteBuscado;
        } catch (SQLException ex) {
            throw ex;
        }finally {
            if (datos != null) datos.close();
            if (sentencia != null) sentencia.close();  // Cerrar la sentencia y resultset
        }

    }
}

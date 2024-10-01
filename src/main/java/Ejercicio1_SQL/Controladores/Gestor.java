package Ejercicio1_SQL.Controladores;
import Ejercicio1_SQL.Modelo.Componente;
import Ejercicio1_SQL.Modelo.Computadora;
import Ejercicio1_SQL.Persistencia.Conexion;
import Ejercicio1_SQL.Persistencia.SentenciasSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Gestor extends Conexion implements SentenciasSQL {
    private Computadora computadora;
    private PreparedStatement sentencia;
    private ResultSet datos;


    public void agregarComputadora(Computadora computadora, List<Componente> listaComponentes) throws Exception {
        Connection cx = null;
        PreparedStatement sentencia = null;
        try {
            List<Computadora> listaComputadoras = obtenerComputadoras();
            boolean codigoExistente = false;
            for (Computadora com : listaComputadoras) {
                if (com.getCodigo().equals(computadora.getCodigo())) {
                    codigoExistente = true;
                    break;
                }
            }
            if (codigoExistente) {
                throw new Exception("La computadora ya existe en el sistema");
            }
            cx = estableceConexion();
            cx.setAutoCommit(false);

            sentencia = cx.prepareStatement(INSERT_COMPUTADORA, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, computadora.getCodigo());
            sentencia.setString(2, computadora.getMarca());
            sentencia.setString(3, computadora.getModelo());
            sentencia.executeUpdate();

            ResultSet rs = sentencia.getGeneratedKeys();
            int computadoraId = 0;
            if (rs.next()) {
                computadoraId = rs.getInt(1);
            }
            rs.close();
            sentencia.close();

            for (Componente componenteNuevo : listaComponentes) {
                sentencia = cx.prepareStatement(INSERT_COMPONENTE);
                sentencia.setString(1, componenteNuevo.getNombre());
                sentencia.setString(2, componenteNuevo.getNroSerie());
                sentencia.setInt(3, computadoraId);
                sentencia.executeUpdate();
                sentencia.close();
            }
            cx.commit();

        } catch (SQLException ex) {
            if (cx != null)
                cx.rollback();
            throw ex;
        } finally {
            if (sentencia != null) sentencia.close();
            if (cx != null) cx.close();
        }
    }

    public List<Computadora> obtenerComputadoras() throws SQLException {
        datos = null;
        try {
            List<Computadora> listaComputadoras = new ArrayList<Computadora>();
            sentencia = estableceConexion().prepareStatement(SELECT_ALL_COMPUTADORAS);
            datos = sentencia.executeQuery();
            while (datos.next()) {
                Computadora computadoraAgregado = new Computadora();
                computadoraAgregado.setId(datos.getLong("id"));
                computadoraAgregado.setCodigo(datos.getString("codigo"));
                computadoraAgregado.setMarca(datos.getString("marca"));
                computadoraAgregado.setModelo(datos.getString("modelo"));
                listaComputadoras.add(computadoraAgregado);
            }
            return listaComputadoras;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (datos != null) datos.close();
            if (sentencia != null) sentencia.close();
        }
    }
}



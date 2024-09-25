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


public class GestorComputadora extends Conexion implements SentenciasSQL {
    private Computadora computadora;
    private PreparedStatement sentencia;
    private ResultSet datos;

    public Computadora obtenerComputadora(String codigo) throws SQLException {
        try {
            Computadora computadoraBuscada = null;
            sentencia = estableceConexion().prepareStatement(SELECT_COMPUTADORA);
            sentencia.setString(1,codigo);
            datos= sentencia.executeQuery();
            while (datos.next()) {
                computadoraBuscada=new Computadora();
                computadoraBuscada.setId(datos.getLong("id"));
                computadoraBuscada.setCodigo(datos.getString("codigo"));
                computadoraBuscada.setMarca(datos.getString("marca"));
                computadoraBuscada.setModelo(datos.getString("modelo"));
            }
            return computadoraBuscada;
        } catch (SQLException ex) {
            throw ex;
        }finally {
            if (datos != null) datos.close();
            if (sentencia != null) sentencia.close();
        }
    }

    public List<Computadora> obtenerComputadoras() throws SQLException {
        datos=null;
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
        }catch (SQLException ex) {
            throw ex;
        }finally {
            if (datos != null) datos.close();
            if (sentencia != null) sentencia.close();
        }
    }

    public List<Componente> obtenerComponentes(long idComputadora) throws SQLException {
        datos=null;
        try {
            List<Componente> componentes = new ArrayList<Componente>();
            sentencia = estableceConexion().prepareStatement(SELECT_COMPONENTES);
            sentencia.setLong(1, idComputadora);
            datos=sentencia.executeQuery();
            while (datos.next()) {
                Componente componenteAgregado = new Componente();
                componenteAgregado.setNombre(datos.getString("nombre"));
                componenteAgregado.setNroSerie(datos.getString("nroSerie"));
                componentes.add(componenteAgregado);
            }
            return componentes;
        } catch (SQLException ex) {
            throw ex;
        }finally {
            if (datos != null) datos.close();
            if (sentencia != null) sentencia.close();
        }
    }

    public void agregarComputadora(Computadora computadora) throws Exception {
        try{
            List<Computadora> listaComputadoras = obtenerComputadoras();
            boolean codigoExistente = false;
            for(Computadora com : listaComputadoras){
                if(com.getCodigo().equals(computadora.getCodigo())){
                    codigoExistente = true;
                    break;
                }
            }
            if(codigoExistente) {
                throw new Exception("La computadora ya existe en el sistema");
            }else {
                sentencia = estableceConexion().prepareStatement(INSERT_COMPUTADORA);
                Object[] params = {computadora.getId(), computadora.getCodigo(), computadora.getMarca(), computadora.getModelo()};
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
            if (sentencia != null) sentencia.close();
        }
    }
    public void borrarComputadora(String codigo) throws SQLException {
        try {
            Computadora computadora = obtenerComputadora(codigo);
            if (computadora != null) {
                long idComputadora = computadora.getId();
                if (!obtenerComponentes(idComputadora).isEmpty()) {
                    sentencia = estableceConexion().prepareStatement(DELETE_COMPONENTE_COMPUTADORA);
                    sentencia.setObject(1, idComputadora);
                    sentencia.executeUpdate();
                }
                sentencia = estableceConexion().prepareStatement(DELETE_COMPUTADORA);
                sentencia.setObject(1, idComputadora);
                sentencia.executeUpdate();
                estableceConexion().commit();
            }
        }catch (SQLException ex) {
            estableceConexion().rollback();
            throw ex;
        }finally {
            if (sentencia != null) sentencia.close();
        }
    }
}

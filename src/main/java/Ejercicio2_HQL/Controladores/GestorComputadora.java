package Ejercicio2_HQL.Controladores;

import Ejercicio2_HQL.Modelo.*;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class GestorComputadora extends Gestor{
    private Componente componente;
    private Computadora computadora;
    public Computadora obtenerComputadora(String codigo) throws Exception {
        Computadora computadoraBuscada = null;
        try{
            computadoraBuscada=(Computadora) buscarObjetoPorAtributo(getSesion(), Computadora.class,"codigo",codigo);
        }catch(SQLException ex){
            throw new Exception("Error al obtener la computadora: " + ex.getMessage());
        }
        return computadoraBuscada;
    }

    public List<Computadora> obtenerComputadoras() throws Exception {
        try {
            return listar(getSesion(), Computadora.class);
        } catch (Exception e) {
            throw new Exception("Error al listar las computadoras: " + e.getMessage());
        }
    }

    public List<Componente> obtenerComponentes(long idComputadora) throws Exception {
        try {
            List<Componente> componentes = new ArrayList<>();
            List<Object> objetos =buscarPorAtributo(getSesion(), Componente.class, "idComputadora", idComputadora);
            for (Object obj : objetos) {
                if (obj instanceof Componente) {
                    componentes.add((Componente) obj);
                }
            }
            return componentes;
        } catch (Exception e) {
            throw new Exception("Error al obtener componentes: " + e.getMessage());
        }
    }

    public void agregarComputadora(Computadora computadora) throws Exception {
        try {
            guardar(computadora);
        } catch (Exception e) {
            throw new Exception("Error al agregar la computadora: " + e.getMessage());
        }
    }

    public void borrarComputadora(String codigo) throws Exception {
        try {
            guardar(obtenerComputadora(codigo));
        } catch (Exception e) {
            throw new Exception("Error al agregar la computadora: " + e.getMessage());
        }
    }
}

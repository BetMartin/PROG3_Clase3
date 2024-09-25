package Ejercicio2_HQL.Controladores;

import Ejercicio2_HQL.Modelo.*;
import java.sql.SQLException;

public class GestorComponente extends Gestor{
    private GestorComputadora gestorComputadora;

    public void agregarComponente(Componente componenteNuevo) throws Exception {
        try {
            String codigoComputadora = componenteNuevo.getComputadora().getCodigo();
            Computadora computadoraAsociada = (Computadora) gestorComputadora.obtenerComputadora(codigoComputadora);
            if (computadoraAsociada == null) {
                computadoraAsociada = componenteNuevo.getComputadora();
                gestorComputadora.agregarComputadora(computadoraAsociada);
            }
            componenteNuevo.setComputadora(computadoraAsociada);
            guardar(componenteNuevo);
        }catch (SQLException ex) {
            throw new Exception("Error al agregar el componente: " + ex.getMessage());
        }
    }
    public void borrarComponente(String id) throws Exception {
        try {
            Componente componente = (Componente) buscarPorId(Componente.class, Long.parseLong(id));
            if (componente != null) {
                eliminar(componente);
            }
        }catch (SQLException ex) {
            throw new Exception("Error al borrar el componente: " + ex.getMessage());
        }
    }
    public Componente obtenerComponente(String nroSerie) throws Exception {
        try {
            return (Componente) buscarObjetoPorAtributo(getSesion(), Componente.class, "nroSerie", nroSerie);
        } catch (SQLException ex) {
            throw new Exception("Error al obtener el componente: " + ex.getMessage());
        }
    }
}

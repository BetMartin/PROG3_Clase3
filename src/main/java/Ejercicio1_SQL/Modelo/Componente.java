package Ejercicio1_SQL.Modelo;

public class Componente extends EntityBeans{
    private String nombre;
    private String nroSerie;
    private Computadora computadora;
    //Constructores
    public Componente() {}

    public Componente(String nombre, String nroSerie, Computadora computadora) {
        this.nombre = nombre;
        this.nroSerie = nroSerie;
        this.computadora = computadora;
    }
    public Componente(String nombre, String nroSerie) {
        this.nombre = nombre;
        this.nroSerie = nroSerie;
    }
    //Getters and setters
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getNroSerie() {return nroSerie;}
    public void setNroSerie(String nroSerie) {this.nroSerie = nroSerie;}
    public Computadora getComputadora() {return computadora;}
    public void setComputadora(Computadora computadora) {this.computadora = computadora;}
}

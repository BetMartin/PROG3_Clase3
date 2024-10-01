package Ejercicio2_HQL.Modelo;

import javax.persistence.*;
import java.util.List;
@Entity
public class Computadora extends EntityBeans implements java.io.Serializable{
    @Column(unique = true)
    private String codigo;
    private String marca;
    private String modelo;
    @OneToMany(mappedBy = "computadora", cascade = CascadeType.ALL)
    private List<Componente> componentes;

    //Constructor
    public Computadora() {}

    public Computadora(String codigo, String marca, String modelo, List<Componente> componentes) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.componentes = componentes;
    }
    public Computadora(String codigo, String marca, String modelo) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
    }

    //Getters y setters
    public String getCodigo () {return codigo;}
    public void setCodigo (String codigo){this.codigo = codigo;}
    public String getMarca () {return marca;}
    public void setMarca (String marca){this.marca = marca;}
    public String getModelo () {return modelo;}
    public void setModelo (String modelo){this.modelo = modelo;}
    public List<Componente> getComponentes() {return componentes;}
    public void setComponentes(List<Componente> componentes) {this.componentes = componentes;}
}

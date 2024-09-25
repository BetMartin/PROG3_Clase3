package Ejercicio2_HQL.Modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class EntityBeans implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    //Metdoos setter y getter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public static String getIdentityPropery() {
        return "id";
    }
}


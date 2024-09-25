package Ejercicio1_SQL.Modelo;

public class EntityBeans {
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


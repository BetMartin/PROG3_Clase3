package Ejercicio1_SQL.Persistencia;

public interface SentenciasSQL {
    //Sentencias Para Crear Tablas si no existen
    String CREATE_TABLE_COMPUTADORA = "CREATE TABLE IF NOT EXISTS Computadora (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "codigo VARCHAR(255) NOT NULL, " +
            "marca VARCHAR(255) NOT NULL, " +
            "modelo VARCHAR(255) NOT NULL" +
            ")";
    String CREATE_TABLE_COMPONENTE = "CREATE TABLE IF NOT EXISTS Componente (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "nombre VARCHAR(255) NOT NULL, " +
            "nroSerie VARCHAR(255) NOT NULL, " +
            "idComputadora BIGINT, " +
            "FOREIGN KEY (idComputadora) REFERENCES Computadora(id)" +
            ")";

    //Sentencias Parar Computadora
    String INSERT_COMPUTADORA = "INSERT INTO `Computadoras`.`Computadora` (id, codigo, marca, modelo) VALUES (?, ?, ?, ?)";
    String DELETE_COMPUTADORA = "DELETE FROM `Computadoras`.`Computadora` WHERE id=?";
    String SELECT_COMPUTADORA = "SELECT * FROM `Computadoras`.`Computadora` WHERE codigo=?";
    String SELECT_ALL_COMPUTADORAS = "SELECT * FROM `Computadoras`.`Computadora`";

    //Sentencias Para Componente
    String INSERT_COMPONENTE = "INSERT INTO `Computadoras`.`Componente` (id, nombre, nroSerie, idComputadora) VALUES (?, ?, ?, ?)";
    String DELETE_COMPONENTE = "DELETE FROM `Computadoras`.`Componente` WHERE id=?";
    String DELETE_COMPONENTE_COMPUTADORA = "DELETE FROM `Computadoras`.`Componente` WHERE idComputadora=?";
    String SELECT_COMPONENTES = "SELECT * FROM `Computadoras`.`Componente`WHERE idComputadora=?";
    String SELECT_COMPONENTE = "SELECT * FROM `Computadoras`.`Componente`WHERE nroSerie=?";
}

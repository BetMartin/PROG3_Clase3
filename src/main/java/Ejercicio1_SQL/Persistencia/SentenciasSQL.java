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

    //Sentencias Para ingresar registros
    String INSERT_COMPUTADORA = "INSERT INTO `Computadoras`.`Computadora` (codigo, marca, modelo) VALUES (?, ?, ?)";
    String SELECT_ALL_COMPUTADORAS = "SELECT * FROM `Computadoras`.`Computadora`";

    String INSERT_COMPONENTE = "INSERT INTO `Computadoras`.`Componente` (nombre, nroSerie, idComputadora) VALUES (?, ?, ?)";

}

package co.edu.poli.parcial2.servicios;

/**
 * Contrato de persistencia para guardar y leer producciones desde archivo.
 * La implementacion usa serializacion binaria.
 */
public interface OperacionArchivo {

    /**
     * Guarda el inventario en el archivo indicado.
     * @param ruta ruta de archivo, por ejemplo "./data/producciones.dat"
     * @return mensaje de resultado
     */
    String serializar(String ruta);

    /**
     * Lee el archivo indicado y reemplaza el inventario en memoria.
     * @param ruta ruta de archivo
     * @return mensaje de resultado
     */
    String deserializar(String ruta);
}
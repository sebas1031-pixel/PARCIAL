package co.edu.poli.parcial2.servicios;

import co.edu.poli.parcial2.model.ProduccionAudiovisual;

/**
 * Contrato de operaciones CRUD para producciones audiovisuales.
 */
public interface OperacionCRUD {

    /**
     * Crea una produccion si el serial no existe.
     * @param o produccion a registrar
     * @return mensaje de resultado
     */
    String create(ProduccionAudiovisual o);

    /**
     * Retorna todas las producciones.
     * @return arreglo con todas las producciones
     */
    ProduccionAudiovisual[] readAll();

    /**
     * Busca una produccion por serial.
     * @param serial identificador
     * @return produccion encontrada o null
     */
    ProduccionAudiovisual readId(int serial);

    /**
     * Actualiza la produccion con el serial indicado.
     * @param serial identificador a actualizar
     * @param o datos nuevos (se mantiene el mismo serial)
     * @return mensaje de resultado
     */
    String update(int serial, ProduccionAudiovisual o);

    /**
     * Elimina la produccion por serial.
     * @param serial identificador
     * @return objeto eliminado o null si no existe
     */
    ProduccionAudiovisual delete(int serial);
}
package co.edu.poli.parcial2.model;

import java.io.Serializable;

/**
 * Representa un director de cine asociado a una produccion audiovisual.
 * Clase simple de dominio, usada por agregacion en ProduccionAudiovisual.
 */
public class DirectorDeCine implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Identificador interno del director. */
    private int id;
    /** Nombre del director. */
    private String nombre;
    /** Nacionalidad del director. */
    private String nacionalidad;

    /** Constructor vacio requerido para serializacion. */
    public DirectorDeCine() {}

    /**
     * Constructor completo.
     * @param id identificador
     * @param nombre nombre del director
     * @param nacionalidad nacionalidad del director
     */
    public DirectorDeCine(int id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    /** Obtiene el identificador del director.
     *  @return identificador del director
     */
    public int getId() { return id; }

    /** Establece el identificador del director.
     *  @param id nuevo identificador
     */
    public void setId(int id) { this.id = id; }

    /** Obtiene el nombre del director.
     *  @return nombre del director
     */
    public String getNombre() { return nombre; }

    /** Establece el nombre del director.
     *  @param nombre nuevo nombre
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** Obtiene la nacionalidad del director.
     *  @return nacionalidad del director
     */
    public String getNacionalidad() { return nacionalidad; }

    /** Establece la nacionalidad del director.
     *  @param nacionalidad nueva nacionalidad
     */
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ") id=" + id;
    }
}
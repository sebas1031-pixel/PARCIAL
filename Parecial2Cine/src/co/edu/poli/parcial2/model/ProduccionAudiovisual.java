package co.edu.poli.parcial2.model;

import java.io.Serializable;

/**
 * Superclase abstracta para Pelicula y Serie.
 * Contiene atributos y utilidades comunes para el dominio de streaming.
 */
public abstract class ProduccionAudiovisual implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Identificador de la produccion (clave de negocio). */
    private int serial;
    /** Titulo de la produccion. */
    private String titulo;
    /** Duracion en minutos. En Serie representa minutos por episodio. */
    private int duracionMinutos;
    /** Fecha de estreno en formato ISO simple "YYYY-MM-DD" o "YYYY". */
    private String fechaEstreno;
    /** Director asociado (agregacion debil). */
    private DirectorDeCine directorDeCine;

    /** Constructor vacio requerido para serializacion. */
    public ProduccionAudiovisual() {}

    /**
     * Constructor completo.
     * @param serial identificador de la produccion
     * @param titulo titulo de la produccion
     * @param fechaEstreno fecha de estreno "YYYY-MM-DD" o "YYYY"
     * @param duracionMinutos duracion en minutos
     * @param directorDeCine director asociado
     */
    public ProduccionAudiovisual(int serial, String titulo, String fechaEstreno,
                                 int duracionMinutos, DirectorDeCine directorDeCine) {
        this.serial = serial;
        this.titulo = titulo;
        this.fechaEstreno = fechaEstreno;
        this.duracionMinutos = duracionMinutos;
        this.directorDeCine = directorDeCine;
    }

    /**
     * Retorna una linea resumida con los datos clave de la produccion.
     * @return texto legible para listados
     */
    public abstract String listar();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {serial=" + serial + ", titulo=\"" + titulo
                + "\", fechaEstreno=" + fechaEstreno + ", dur=" + duracionMinutos
                + " min, director=" + (directorDeCine != null ? directorDeCine.getNombre() : "N/A") + "}";
    }

    /** Devuelve el serial de la produccion.
     *  @return identificador serial de la produccion
     */
    public int getSerial() { return serial; }

    /**
     * Verifica reglas basicas del contenido.
     * @param visualizacion minutos vistos (puede ser 0)
     * @return 0 si todo es valido;
     *         1 si el titulo esta vacio;
     *         2 si la duracion {@code <= 0};
     *         3 si la visualizacion {@code < 0}
     */
    public int verificacionContenido(int visualizacion) {
        if (titulo == null || titulo.isBlank()) return 1;
        if (duracionMinutos <= 0) return 2;
        if (visualizacion < 0) return 3;
        return 0;
    }

    /**
     * Intenta obtener el anio de estreno a partir de la cadena fechaEstreno.
     * @return anio entero o -1 si no es posible parsear
     */
    public int determinarAnioEstreno() {
        if (fechaEstreno == null || fechaEstreno.isBlank()) return -1;
        String y = fechaEstreno.trim();
        if (y.length() >= 4) {
            try { return Integer.parseInt(y.substring(0, 4)); }
            catch (NumberFormatException e) { return -1; }
        }
        return -1;
    }

    /**
     * Devuelve una etiqueta uniforme tipo: "Titulo" — Dir. Nombre (anio)
     * @return texto de etiqueta
     */
    public final String etiquetaStreaming() {
        return "\"" + titulo + "\" — Dir. " +
               (directorDeCine != null ? directorDeCine.getNombre() : "N/A") +
               " (" + determinarAnioEstreno() + ")";
    }

    /** Establece el serial de la produccion.
     *  @param serial nuevo identificador
     */
    public void setSerial(int serial) { this.serial = serial; }

    /** Obtiene el titulo actual.
     *  @return titulo de la produccion
     */
    public String getTitulo() { return titulo; }

    /** Establece el titulo.
     *  @param titulo nuevo titulo
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /** Obtiene la duracion en minutos.
     *  @return duracion en minutos
     */
    public int getDuracionMinutos() { return duracionMinutos; }

    /** Establece la duracion en minutos.
     *  @param duracionMinutos nueva duracion
     */
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    /** Obtiene la fecha de estreno.
     *  @return fecha de estreno
     */
    public String getFechaEstreno() { return fechaEstreno; }

    /** Establece la fecha de estreno.
     *  @param fechaEstreno nueva fecha de estreno
     */
    public void setFechaEstreno(String fechaEstreno) { this.fechaEstreno = fechaEstreno; }

    /** Obtiene el director asociado.
     *  @return director de cine
     */
    public DirectorDeCine getDirectorDeCine() { return directorDeCine; }

    /** Establece el director asociado.
     *  @param directorDeCine nuevo director asociado
     */
    public void setDirectorDeCine(DirectorDeCine directorDeCine) { this.directorDeCine = directorDeCine; }
}
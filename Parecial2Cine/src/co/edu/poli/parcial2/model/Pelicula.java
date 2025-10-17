package co.edu.poli.parcial2.model;

/**
 * Entidad concreta para representar una pelicula dentro del catalogo.
 * Extiende ProduccionAudiovisual e incorpora el atributo genero.
 */
public class Pelicula extends ProduccionAudiovisual {
    private static final long serialVersionUID = 1L;

    /** Genero cinematografico en texto libre. */
    private String genero;

    /** Constructor vacio requerido para serializacion. */
    public Pelicula() {}

    /**
     * Constructor completo.
     * @param serial identificador
     * @param titulo titulo de la pelicula
     * @param fechaEstreno fecha de estreno "YYYY-MM-DD" o "YYYY"
     * @param duracionMinutos duracion total en minutos
     * @param director director asociado
     * @param genero genero cinematografico en texto
     */
    public Pelicula(int serial, String titulo, String fechaEstreno,
                    int duracionMinutos, DirectorDeCine director, String genero) {
        super(serial, titulo, fechaEstreno, duracionMinutos, director);
        this.genero = genero;
    }

    /**
     * Indica si la pelicula es largometraje segun su duracion.
     * @return true si duracionMinutos {@code >= 60}; false en caso contrario
     */
    public boolean esLargometraje() {
        return getDuracionMinutos() >= 60;
    }

    /**
     * Retorna una linea legible con los datos de la pelicula.
     * @return cadena con serial, titulo, genero, fecha, duracion y director
     */
    @Override
    public String listar() {
        return "[Pelicula] serial=" + getSerial() + " | \"" + getTitulo() + "\" | genero=" + genero +
               " | fecha=" + getFechaEstreno() + " | dur=" + getDuracionMinutos() + " min | dir=" +
               (getDirectorDeCine() != null ? getDirectorDeCine().getNombre() : "N/A");
    }

    /** Obtiene el genero actual.
     *  @return genero de la pelicula
     */
    public String getGenero() { return genero; }

    /** Establece el genero.
     *  @param genero nuevo genero
     */
    public void setGenero(String genero) { this.genero = genero; }
}
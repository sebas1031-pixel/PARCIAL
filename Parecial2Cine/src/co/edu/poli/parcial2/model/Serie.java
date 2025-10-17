package co.edu.poli.parcial2.model;

/**
 * Entidad concreta para representar una serie dentro del catalogo.
 * Extiende ProduccionAudiovisual e incorpora el numero de temporadas.
 */
public class Serie extends ProduccionAudiovisual {
    private static final long serialVersionUID = 1L;

    /** Numero total de temporadas. */
    private int numeroTemporadas;

    /** Constructor vacio requerido para serializacion. */
    public Serie() {}

    /**
     * Constructor completo.
     * @param serial identificador
     * @param titulo titulo de la serie
     * @param fechaEstreno fecha de estreno "YYYY-MM-DD" o "YYYY"
     * @param duracionPorEpisodio minutos por episodio
     * @param director director asociado
     * @param numeroTemporadas cantidad de temporadas
     */
    public Serie(int serial, String titulo, String fechaEstreno,
                 int duracionPorEpisodio, DirectorDeCine director, int numeroTemporadas) {
        super(serial, titulo, fechaEstreno, duracionPorEpisodio, director);
        this.numeroTemporadas = numeroTemporadas;
    }

    /**
     * Indica si la serie puede considerarse miniserie.
     * @return true si numeroTemporadas {@code <= 2}; false en caso contrario
     */
    public boolean esMiniSerie() {
        return numeroTemporadas <= 2;
    }

    /**
     * Calcula una duracion total estimada para toda la serie.
     * @param episodiosPorTemporada cantidad estimada de episodios por temporada
     * @return minutos estimados = temporadas * episodios * minutos por episodio
     */
    public int duracionTotalEstimada(int episodiosPorTemporada) {
        if (episodiosPorTemporada <= 0) return 0;
        return numeroTemporadas * episodiosPorTemporada * getDuracionMinutos();
    }

    /**
     * Retorna una linea legible con los datos de la serie.
     * @return cadena con serial, titulo, temporadas, fecha, minutos por episodio y director
     */
    @Override
    public String listar() {
        return "[Serie] serial=" + getSerial() + " | \"" + getTitulo() + "\" | temporadas=" + numeroTemporadas +
               " | fecha=" + getFechaEstreno() + " | dur/ep=" + getDuracionMinutos() + " min | dir=" +
               (getDirectorDeCine() != null ? getDirectorDeCine().getNombre() : "N/A");
    }

    /** Obtiene el numero total de temporadas.
     *  @return numero de temporadas
     */
    public int getNumeroTemporadas() { return numeroTemporadas; }

    /** Establece el numero total de temporadas.
     *  @param numeroTemporadas nuevo numero de temporadas
     */
    public void setNumeroTemporadas(int numeroTemporadas) { this.numeroTemporadas = numeroTemporadas; }
}
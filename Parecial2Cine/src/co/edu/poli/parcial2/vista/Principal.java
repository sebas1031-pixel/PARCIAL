package co.edu.poli.parcial2.vista;


import java.util.Arrays;
import java.util.Scanner;
import co.edu.poli.parcial2.model.*;
import co.edu.poli.parcial2.servicios.ImplementacionOperacionCRUD;
import co.edu.poli.parcial2.model.*;
/**
 * Clase principal de consola para ejecutar el menu de la aplicacion de cine.
 * Implementa 8 opciones: 5 CRUD + serializar + deserializar + salir.
 * El director se deja predefinido desde el codigo (no se captura por menu).
 */
public final class Principal {

    /** Ruta del archivo binario para persistencia. */
    private static final String RUTA = "./data/producciones.dat";

    /** Servicio CRUD y archivo. */
    private static final ImplementacionOperacionCRUD svc = new ImplementacionOperacionCRUD();

    /** Director predefinido (requisito del enunciado). */
    private static final DirectorDeCine DIRECTOR_DEF =
            new DirectorDeCine(1, "Christopher Nolan", "Britanico");

    /**
     * Evita la instanciacion de la clase principal.
     * Se usa solo como contenedor de main y metodos estaticos.
     */
    private Principal() { throw new AssertionError("No instanciable"); }

    /**
     * Punto de entrada de la aplicacion de consola.
     * @param args argumentos de linea de comandos (no usados)
     */
    public static void main(String[] args) {
        // Asegura que exista la carpeta ./data para guardar/leer archivos
        new java.io.File("./data").mkdirs();

        Scanner sc = new Scanner(System.in);
        int op;
        do {
            op = menu(sc);
            switch (op) {
                case 1 -> crear(sc);              // sub menu: Pelicula o Serie
                case 2 -> listarTodas();
                case 3 -> listarUna(sc);          // por serial
                case 4 -> modificarSerie(sc);     // Update Serie (director permanece igual)
                case 5 -> eliminarPelicula(sc);   // Delete Pelicula
                case 6 -> System.out.println(svc.serializar(RUTA));
                case 7 -> System.out.println(svc.deserializar(RUTA));
                case 8 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida");
            }
            if (op != 8) pause(sc);
        } while (op != 8);
        sc.close();
    }

    // -------------------- MENU --------------------

    private static int menu(Scanner sc) {
        System.out.println("\n=== Streaming (CRUD + Archivo) ===");
        System.out.println("1. Crear (Pelicula o Serie)");
        System.out.println("2. Listar todas");
        System.out.println("3. Listar una (por serial)");
        System.out.println("4. Modificar una Serie");
        System.out.println("5. Eliminar una Pelicula");
        System.out.println("6. Guardar en archivo");
        System.out.println("7. Leer archivo");
        System.out.println("8. Salir");
        System.out.print("Opcion: ");
        return leerEntero(sc);
    }

    // -------------------- CREATE --------------------

    private static void crear(Scanner sc) {
        System.out.println("-- Crear -- 1) Pelicula  2) Serie");
        int tipo = leerEntero(sc);
        if (tipo == 1) crearPelicula(sc);
        else if (tipo == 2) crearSerie(sc);
        else System.out.println("Tipo invalido.");
    }

    /** Crea Pelicula usando el director predefinido (no se pide por menu). */
    private static void crearPelicula(Scanner sc) {
        int serial = leerEnteroConLabel(sc, "serial");
        String titulo = leerTexto(sc, "titulo");
        String fecha = leerTexto(sc, "fecha (YYYY-MM-DD)");
        int dur = leerEnteroConLabel(sc, "duracion (min)");
        String genero = leerTexto(sc, "genero");

        Pelicula p = new Pelicula(serial, titulo, fecha, dur, DIRECTOR_DEF, genero);
        System.out.println(svc.create(p));
    }

    /** Crea Serie usando el director predefinido (no se pide por menu). */
    private static void crearSerie(Scanner sc) {
        int serial = leerEnteroConLabel(sc, "serial");
        String titulo = leerTexto(sc, "titulo");
        String fecha = leerTexto(sc, "fecha (YYYY-MM-DD)");
        int durEpisodio = leerEnteroConLabel(sc, "duracion por episodio (min)");
        int temporadas = leerEnteroConLabel(sc, "numero de temporadas");

        Serie s = new Serie(serial, titulo, fecha, durEpisodio, DIRECTOR_DEF, temporadas);
        System.out.println(svc.create(s));
    }

    // -------------------- READ --------------------

    private static void listarTodas() {
        ProduccionAudiovisual[] arr = svc.readAll();
        if (arr.length == 0) { System.out.println("(sin producciones)"); return; }
        Arrays.stream(arr).forEach(p -> System.out.println(p.listar()));
    }

    private static void listarUna(Scanner sc) {
        int serial = leerEnteroConLabel(sc, "serial a buscar");
        ProduccionAudiovisual p = svc.readId(serial);
        System.out.println(p == null ? "No existe" : p.listar());
    }

    // -------------------- UPDATE (Serie) --------------------

    /**
     * Modifica una Serie conservando el mismo serial.
     * El director permanece siendo el predefinido (no editable por menu).
     */
    private static void modificarSerie(Scanner sc) {
        int serial = leerEnteroConLabel(sc, "serial de la serie");
        ProduccionAudiovisual p = svc.readId(serial);
        if (p == null) { System.out.println("No existe."); return; }
        if (!(p instanceof Serie)) { System.out.println("El serial no corresponde a Serie."); return; }
        Serie orig = (Serie) p;

        String nuevoTitulo  = leerTextoOpc(sc, "nuevo titulo");
        String nuevaFecha   = leerTextoOpc(sc, "nueva fecha (YYYY-MM-DD)");
        Integer nuevaDur    = leerEnteroOpc(sc, "nueva duracion por episodio (min)");
        Integer nuevasTemps = leerEnteroOpc(sc, "nuevo numero de temporadas");

        Serie mod = new Serie(
            orig.getSerial(),
            noNull(nuevoTitulo,  orig.getTitulo()),
            noNull(nuevaFecha,   orig.getFechaEstreno()),
            (nuevaDur    != null ? nuevaDur    : orig.getDuracionMinutos()),
            DIRECTOR_DEF, // se mantiene el director predefinido
            (nuevasTemps != null ? nuevasTemps : orig.getNumeroTemporadas())
        );
        System.out.println(svc.update(serial, mod));
    }

    // -------------------- DELETE (Pelicula) --------------------

    private static void eliminarPelicula(Scanner sc) {
        int serial = leerEnteroConLabel(sc, "serial de la pelicula");
        ProduccionAudiovisual p = svc.readId(serial);
        if (p == null) { System.out.println("No existe."); return; }
        if (!(p instanceof Pelicula)) { System.out.println("El serial no corresponde a Pelicula."); return; }
        System.out.println(svc.delete(serial) != null ? "Eliminada." : "No se elimino.");
    }

    // -------------------- UTILIDADES --------------------

    private static void pause(Scanner sc) { System.out.print("(Enter) "); sc.nextLine(); }

    private static int leerEntero(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.print("Invalido: "); }
        }
    }

    private static int leerEnteroConLabel(Scanner sc, String label) {
        System.out.print(label + ": ");
        return leerEntero(sc);
    }

    private static Integer leerEnteroOpc(Scanner sc, String label) {
        System.out.print(label + " (opcional): ");
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return null;
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
    }

    private static String leerTexto(Scanner sc, String label) {
        System.out.print(label + ": ");
        String s = sc.nextLine().trim();
        while (s.isEmpty()) {
            System.out.print("No vacio. " + label + ": ");
            s = sc.nextLine().trim();
        }
        return s;
    }

    private static String leerTextoOpc(Scanner sc, String label) {
        System.out.print(label + " (opcional): ");
        String s = sc.nextLine().trim();
        return s.isEmpty() ? null : s;
    }

    private static String noNull(String v, String def) { return v != null ? v : def; }
}
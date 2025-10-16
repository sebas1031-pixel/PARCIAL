package co.edu.poli.parcial2.servicios;

import co.edu.poli.parcial2.model.ProduccionAudiovisual;
import java.io.*; 
import java.util.*;
/**
 * Implementacion en memoria de CRUD con soporte de archivos.
 * Usa serializacion binaria para guardar y cargar la lista completa.
 */
@SuppressWarnings("unchecked")
public class ImplementacionOperacionCRUD implements OperacionCRUD, OperacionArchivo, Serializable {
    private static final long serialVersionUID = 1L;

    /** Lista interna que almacena las producciones. */
    private final List<ProduccionAudiovisual> inventario = new ArrayList<>();

    /** {@inheritDoc} */
    @Override
    public String create(ProduccionAudiovisual o) {
        if (o == null) return "ERROR: objeto null";
        if (readId(o.getSerial()) != null) return "ERROR: serial duplicado";
        inventario.add(o);
        return "OK: creado serial=" + o.getSerial();
    }

    /** {@inheritDoc} */
    @Override
    public ProduccionAudiovisual[] readAll() {
        return inventario.toArray(new ProduccionAudiovisual[0]);
    }

    /** {@inheritDoc} */
    @Override
    public ProduccionAudiovisual readId(int serial) {
        for (ProduccionAudiovisual p : inventario) {
            if (p.getSerial() == serial) return p;
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String update(int serial, ProduccionAudiovisual o) {
        if (o == null) return "ERROR: objeto null";
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getSerial() == serial) {
                o.setSerial(serial); // mantener clave de negocio
                inventario.set(i, o);
                return "OK: actualizado serial=" + serial;
            }
        }
        return "ERROR: no existe serial=" + serial;
    }

    /** {@inheritDoc} */
    @Override
    public ProduccionAudiovisual delete(int serial) {
        Iterator<ProduccionAudiovisual> it = inventario.iterator();
        while (it.hasNext()) {
            ProduccionAudiovisual p = it.next();
            if (p.getSerial() == serial) {
                it.remove();
                return p;
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String serializar(String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(inventario);
            return "OK: archivo guardado en " + ruta + " | objetos=" + inventario.size();
        } catch (NotSerializableException nse) {
            return "Error al guardar: NotSerializableException - " + nse.getMessage();
        } catch (IOException e) {
            return "Error al guardar: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }

    /** {@inheritDoc} */
    @Override
    public String deserializar(String ruta) {
        File f = new File(ruta);
        if (!f.exists()) return "ERROR: archivo no existe -> " + ruta;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                inventario.clear();
                inventario.addAll((List<ProduccionAudiovisual>) obj);
                return "OK: archivo cargado desde " + ruta + " | objetos=" + inventario.size();
            } else {
                return "ERROR: contenido inesperado en " + ruta;
            }
        } catch (Exception e) {
            return "Error al leer: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }

    /** Exposicion de solo lectura del inventario (referencia viva). */
    public List<ProduccionAudiovisual> getInventario() { return inventario; }
}
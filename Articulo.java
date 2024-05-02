/**
 *  Clase Articulo de la Aplicación Tienda
 * 
 *  @author José Antonio Pozo González IWC70842@educastur.es
 *          Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */
import java.io.Serializable;

public class Articulo implements Serializable, Comparable<Articulo> {
  private String idArticulo;
  private String descripcion;
  private int existencias;
  private double pvp;

  public Articulo(String idArticulo, String descripcion, int existencias, double pvp) {
    this.idArticulo = idArticulo;
    this.descripcion = descripcion;
    this.existencias = existencias;
    this.pvp = pvp;
  }

  public String getIdArticulo() {
    return idArticulo;
  }

  public void setIdArticulo(String idArticulo) {
    this.idArticulo = idArticulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getExistencias() {
    return existencias;
  }

  public void setExistencias(int existencias) {
    this.existencias = existencias;
  }

  public double getPvp() {
    return pvp;
  }

  public void setPvp(double pvp) {
    this.pvp = pvp;
  }

  @Override
  public String toString() {
    return "\t\tID: " + idArticulo + " - DESCRIPCION: " + descripcion + " - UNIDADES DISPONIBLES: " + existencias
        + " - PRECIO: " + pvp;
  }

  @Override
  public int compareTo(Articulo a) {
    return this.idArticulo.compareTo(a.idArticulo);
  }

}

/**
 *  Clase LineaPedido de la Aplicación Tienda
 * 
 *  @author José Antonio Pozo González IWC70842@educastur.es
 *          Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */
import java.io.Serializable;

public class LineaPedido implements Serializable {
  private String idArticulo;
  private int unidades;

  public LineaPedido(String idArticulo, int unidades) {
    this.idArticulo = idArticulo;
    this.unidades = unidades;
  }

  public String getIdArticulo() {
    return idArticulo;
  }

  public void setIdArticulo(String idArticulo) {
    this.idArticulo = idArticulo;
  }

  public int getUnidades() {
    return unidades;
  }

  public void setUnidades(int unidades) {
    this.unidades = unidades;
  }

  @Override
  public String toString() {
    return "LineaPedido [idArticulo=" + idArticulo + ", unidades=" + unidades + "]";
  }

}
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class LineaPedido implements Serializable{
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



}
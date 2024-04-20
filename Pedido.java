import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido implements Serializable, Comparable<Pedido>{

  private String idPedido;
  private Cliente clientePedido;
  private LocalDate fechaPedido;
  private ArrayList <LineaPedido> cestaCompra;
  
  public Pedido(String idPedido, Cliente clientePedido, LocalDate fechaPedido, ArrayList<LineaPedido> cestaCompra) {
    this.idPedido = idPedido;
    this.clientePedido = clientePedido;
    this.fechaPedido = fechaPedido;
    this.cestaCompra = cestaCompra;
  }

  @Override
  public int compareTo(Pedido o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
  }
  
  


  
}

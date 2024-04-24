// EXCEPCION PERSONALIZADA PARA CUANDO NO HAY STOCK SUFICIENTE

public class StockInsuficiente extends Exception{
  public StockInsuficiente(String cadena){
    super(cadena);
  }
}

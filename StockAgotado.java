// EXCEPCION PERSONALIZADA PARA CUANDO EL STOCK ES 0

public class StockAgotado extends Exception {
  public StockAgotado (String cadena){
    super(cadena);
  }

}

/**
 *  Clase StockAgotado de la Aplicación Tienda
 * 
 *  @author José Antonio Pozo González IWC70842@educastur.es
 *          Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */

// EXCEPCION PERSONALIZADA PARA CUANDO EL STOCK ES 0

public class StockAgotado extends Exception {
  public StockAgotado (String cadena){
    super(cadena);
  }

}

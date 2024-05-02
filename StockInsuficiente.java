/**
 *  Clase StockInsuficiente de la Aplicación Tienda
 * 
 *  @author José Antonio Pozo González IWC70842@educastur.es
 *          Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */

// EXCEPCION PERSONALIZADA PARA CUANDO NO HAY STOCK SUFICIENTE

public class StockInsuficiente extends Exception{
  public StockInsuficiente(String cadena){
    super(cadena);
  }
}

// EXCEPCION PERSONALIZADA PARA CUANDO NO EXISTA EL DNI

public class DniNoExiste extends Exception{
  public DniNoExiste (String cadena){
    super(cadena);
  }
  
}

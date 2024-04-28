import java.util.Comparator;

public class ComparaArticuloPorPrecio implements Comparator<Articulo> {

  // METODO PARA COMPARAR DOS ARTICULOS Y COLOCARLOS POR PRECIO DE MENOR A MAYOR

  @Override
  public int compare(Articulo a1, Articulo a2) {
    return Double.compare(a1.getPvp(), a2.getPvp());
  }

}

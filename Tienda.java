
/**
 *  Clase Principal de la Tienda
 * 
 *  @author José Antonio Pozo González IWC70842@educastur.es
 *          Módulo de Programación de 1º de Desarrollo de Aplicaciones Web 2024
 */
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Tienda {

  Scanner sc = new Scanner(System.in);
  private ArrayList<Pedido> pedidos;
  private HashMap<String, Articulo> articulos;
  private HashMap<String, Cliente> clientes;
  private HashMap<String, Double> totales;

  public Tienda() {
    pedidos = new ArrayList();
    articulos = new HashMap();
    clientes = new HashMap();
    totales = new HashMap();
  }

  public static void main(String[] args) {
    Tienda t = new Tienda();
    //t.leerArchivos(); // DESCOMENTAR DESPUES DESPUES DE CORRER POR PRIMERA VEZ EL
    // PROGRAMA Y CARGAR LOS DATOS DE PRUEBA
    t.cargaDatos(); // COMENTAR DESPUES DE REALIZAR LA COPIA DE SEGURIDAD DE LOS DATOS
    t.menu();
  }

  // MENU PRINCIPAL APLICACION

  public void menu() {
    String opcionStr;
    int opcion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\t***************************");
      System.out.println("\t\t\t\t*   GESTION DE TIENDA     *");
      System.out.println("\t\t\t\t*   MENU DE OPCIONES      *");
      System.out.println("\t\t\t\t***************************");
      System.out.println("\t\t\t\t*  1 - ARTICULOS          *");
      System.out.println("\t\t\t\t*  2 - CLIENTES           *");
      System.out.println("\t\t\t\t*  3 - PEDIDOS            *");
      System.out.println("\t\t\t\t*  4 - COPIA DE SEGURIDAD *");
      System.out.println("\t\t\t\t*                         *");
      System.out.println("\t\t\t\t*  9 - SALIR              *");
      System.out.println("\t\t\t\t***************************");
      opcionStr = sc.next();
      if (opcionStr.matches("^[1-4]|9$")) {
        opcion = Integer.parseInt(opcionStr);
        switch (opcion) {
          case 1: {
            menuArticulos();
            break;
          }
          case 2: {
            menuClientes();
            break;
          }
          case 3: {
            menuPedidos();
            break;
          }
          case 4: {
            backup();
            clientesTxtBackup();
            break;
          }
        }
      } else {
        System.out
            .println("\n\t\t\tOPCION INVALIDA. POR FAVOR INTRODUCE UN NUMERO \n\t\t\tDEL 1 AL 4, O UN 9 PARA SALIR.");
      }
    } while (opcion != 9);
  }

  // SUBMENU ARTICULOS

  public void menuArticulos() {
    String opcionStr;
    int opcion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\t***************************");
      System.out.println("\t\t\t\t*         ARTICULOS       *");
      System.out.println("\t\t\t\t***************************");
      System.out.println("\t\t\t\t*  1 - ALTA               *");
      System.out.println("\t\t\t\t*  2 - BAJA               *");
      System.out.println("\t\t\t\t*  3 - REPOSICIÓN         *");
      System.out.println("\t\t\t\t*  4 - LISTADOS           *");
      System.out.println("\t\t\t\t*                         *");
      System.out.println("\t\t\t\t*  9 - SALIR              *");
      System.out.println("\t\t\t\t***************************");
      opcionStr = sc.next();
      if (opcionStr.matches("^[1-4]|9$")) {
        opcion = Integer.parseInt(opcionStr);
        switch (opcion) {
          case 1: {
            altaArticulos();
            break;
          }
          case 2: {
            bajaArticulos();
            break;
          }
          case 3: {
            reposicionArticulos();
            break;
          }
          case 4: {
            listarArticulos();
            break;
          }
        }
      } else {
        System.out
            .println("\n\t\t\tOPCION INVALIDA. POR FAVOR INTRODUCE UN NUMERO \n\t\t\tDEL 1 AL 4, O UN 9 PARA SALIR.");
      }
    } while (opcion != 9);
  }

  // METODO PARA DAR DE ALTA UN ARTICULO NUEVO

  public void altaArticulos() {
    String idT, descT, exT, pvpT;
    sc.nextLine();
    System.out.println("\t\t\tALTA NUEVO ARTICULO");

    // VALIDAMOS CON REGEX LA ID

    do {
      System.out.println("\n\t\t\tINTRODUCE ID ARTICULO:");
      idT = sc.nextLine();
    } while (!idT.matches("[1-5][-][0-9][0-9]") || articulos.containsKey(idT));

    // DESCRIPCION DEL ARTICULO

    System.out.println("\n\t\t\tINTRODUZCA LA DESCRIPCION DEL ARTICULO:");
    descT = sc.nextLine().toUpperCase();

    // VALIDACION DE LAS EXISTENCIAS MEDIANTE METODO AUXILIAR ESINT

    do {
      System.out.println("\n\t\t\tINTRODUZCA EL NUMERO DE EXISTENCIAS:");
      exT = sc.next();
    } while (!esInt(exT));

    // VALIDACION DE PVP MEDIANTE EL METODO AUXILIAR ESDOUBLE

    do {
      System.out.println("\n\t\t\tINTRODUZCA PVP DEL ARTICULO:");
      pvpT = sc.next();
    } while (!esDouble(pvpT));

    // AÑADIMOS EL ARTICULO A LA COLECCION HACIENDO PARSING DE LOS DATOS
    articulos.put(idT, new Articulo(idT, descT, Integer.parseInt(exT), Double.parseDouble(pvpT)));
    System.out.println("\n\t\t\tEL ARTICULO " + descT + " SE HA AÑADIDO CON EXITO.");
  }

  // METODO PARA ELIMINAR UN ARTICULO

  public void bajaArticulos() {
    String idT;

    sc.nextLine();
    System.out.println("\t\t\tBAJA DE UN ARTICULO");

    // VALIDAMOS EL ARTICULO MEDIANTE EXPRESION REGULAR
    do {
      System.out.println("\n\t\t\tIdArticulo a ELIMINAR (IDENTIFICADOR):");
      idT = sc.nextLine();
    } while (!idT.matches("[1-5][-][0-9][0-9]"));

    // COMPROBAMOS QUE EL ARTICULO EXISTE
    if (articulos.containsKey(idT)) {
      System.out.println("\n\t\t\t" + articulos.get(idT).getDescripcion());
      // VERIFICAMOS QUE NO EXITE NINGUN PEDIDO EN EL QUE ARTICULO ESTE IMPLICADO
      // ANTES DE ELIMINARLO
      if (existePedidoConArticulo(idT)) {
        System.out.println("\t\t\tNO SE PUEDE ELIMINAR EL ARTICULO PORQUE HAY PEDIDOS QUE LO CONTIENEN");
      } else {
        articulos.remove(idT);
        System.out.println("\n\t\t\tARTICULO ELIMINADO");
      }
    } else {
      System.out.println("\n\t\t\tNO EXISTE ARTICULO CON ESE IDENTIFICADOR. NO SE PUEDE BORRAR");
    }
  }

  // METODO REPONER ARTICULOS

  public void reposicionArticulos() {
    String stockMinimo, idT, stockMas;
    sc.nextLine();
    do {
      // PEDIMOS EL NUMERO DE UNIDADES QUE CONSIDERAMOS ROTURA DE STOCK
      System.out.println("\n\t\tINTRODUCE EL NUMERO DE UNIDADES PARA RUPTURA DE STOCK:");
      stockMinimo = sc.nextLine();
    } while (!esInt(stockMinimo) && stockMinimo.length() > 0);
    if (stockMinimo.length() == 0)
      return;

    System.out.println("\n\t\tLISTADO DE ARTICULOS CON " + Integer.parseInt(stockMinimo) + " UNIDADES O MENOS");
    for (Articulo a : articulos.values()) {
      if (a.getExistencias() <= Integer.parseInt(stockMinimo)) {// MOSTRAMOS LAS UNIDADES CON ESE NUMERO MINIMO O MENOS
        System.out.println(a);
      }
    }

    do {
      // PEDIMOS EL IDARTICULO DEL ARTICULO QUE SE DESEA REPONER
      System.out.println("\n\t\tINTRODUCE EL IdArticulo a REPONER (IDENTIFICADOR) O PULSA ENTER PARA SALIR:");
      idT = sc.nextLine();
    } while (!idT.matches("[1-5][-][0-9][0-9]") && idT.length() > 0);
    if (idT.length() == 0)
      return;
    if (articulos.containsKey(idT)) {// SI EL ARTICULO EXISTE PREGUNTAMOS LAS UNIDADES A REPONER
      do {
        System.out.println("\n\t\tINTRODUCE EL NUMERO DE UNIDADES A REPONER O PULSA ENTER PARA SALIR:");
        stockMas = sc.nextLine();
      } while (!esInt(stockMas) && stockMas.length() > 0);
      if (stockMas.length() == 0)
        return;
      articulos.get(idT).setExistencias(articulos.get(idT).getExistencias() + Integer.parseInt(stockMas));
      System.out.println(
          "\n\t\tSE HA REPUESTO " + stockMas + " UNIDADES DEL ARTICULO " + articulos.get(idT).getDescripcion());
    } else {// SI EL ARTICULO NO EXISTE DAMOS FEEDBACK Y REGRESAMOS AL MENU
      System.out.println("\n\t\tEL idArticulo INTRODUCIDO NO EXISTE");
      return;
    }

  }

  // SUBMENU LISTADOS ARTICULOS

  public void listarArticulos() {
    String opcion;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\t****************************");
      System.out.println("\t\t\t\t*     LISTAR ARTICULOS     *");
      System.out.println("\t\t\t\t****************************");
      System.out.println("\t\t\t\t*  0 - TODOS LOS ARTICULOS *");
      System.out.println("\t\t\t\t*  1 - PERIFERICOS         *");
      System.out.println("\t\t\t\t*  2 - ALMACENAMIENTO      *");
      System.out.println("\t\t\t\t*  3 - IMPRESORAS          *");
      System.out.println("\t\t\t\t*  4 - MONITORES           *");
      System.out.println("\t\t\t\t*  5 - COMPONENETES        *");
      System.out.println("\t\t\t\t*                          *");
      System.out.println("\t\t\t\t*  6 - SALIR               *");
      System.out.println("\t\t\t\t****************************");
      do {
        System.out.println("\n\t\t\t\tSELECCIONA TU OPCION:");
        opcion = sc.next();
      } while (!opcion.matches("[0-6]"));
      if (!opcion.equals("6")) {
        listados(opcion);
      }

    } while (!opcion.equals("6"));

  }

  // METODO LISTADOS

  public void listados(String seccion) {

    String[] secciones = { "TODAS", "PERIFERICOS", "ALMACENAMIENTO", "IMPRESORAS", "MONITORES", "COMPONENTES" };
    sc.nextLine();
    System.out.println("\n\t\t[RETURN]ORDEN NORMAL POR idArticulo - PARA ORDENAR POR PRECIO < a >(-) > a <(+)");
    String opcion = sc.nextLine();
    if (seccion.equals("0")) {
      if (opcion.isBlank()) {
        articulos.values().stream().sorted().forEach(System.out::println);
      } else if (opcion.equals("-")) {
        articulos.values().stream().sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
      } else if (opcion.equals("+")) {
        articulos.values().stream().sorted(new ComparaArticuloPorPrecio().reversed()).forEach(System.out::println);
      }
    } else {
      System.out
          .println("\n\t\tLISTADO DE LOS ARTICULOS DE LA SECCION (" + secciones[Integer.parseInt(seccion)] + ")");
      if (opcion.isBlank()) {
        articulos.values().stream().filter(a -> a.getIdArticulo().startsWith(seccion)).sorted()
            .forEach(System.out::println);
      } else if (opcion.equals("-")) {
        articulos.values().stream().filter(a -> a.getIdArticulo().startsWith(seccion))
            .sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
      } else if (opcion.equals("+")) {
        articulos.values().stream().filter(a -> a.getIdArticulo().startsWith(seccion))
            .sorted(new ComparaArticuloPorPrecio().reversed()).forEach(System.out::println);
      }

    }

  }

  // SUBMENU CLIENTES

  public void menuClientes() {
    String opcionStr;
    int opcion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\t***************************");
      System.out.println("\t\t\t\t*      MENU CLIENTES      *");
      System.out.println("\t\t\t\t***************************");
      System.out.println("\t\t\t\t*  1 - AÑADIR CLIENTE     *");
      System.out.println("\t\t\t\t*  2 - LISTAR CLIENTES    *");
      System.out.println("\t\t\t\t*  3 - MODIFICAR DATOS    *");
      System.out.println("\t\t\t\t*      DE CONTACTO        *");
      System.out.println("\t\t\t\t*  4 - ELIMINAR CLIENTE   *");
      System.out.println("\t\t\t\t*                         *");
      System.out.println("\t\t\t\t*  9 - SALIR              *");
      System.out.println("\t\t\t\t***************************");
      opcionStr = sc.next();
      if (opcionStr.matches("^[1-4]|9$")) {
        opcion = Integer.parseInt(opcionStr);
        switch (opcion) {
          case 1:
            nuevoCliente();
            break;
          case 2:
            listaClientes();
            break;
          case 3:
            modificaCliente();
            break;
          case 4:
            bajaClientes();
            break;
        }
      } else {
        System.out
            .println("\t\t\tOPCION INVALIDA. POR FAVOR INTRODUCE UN NUMERO \n\t\t\tDEL 1 AL 4, O UN 9 PARA SALIR.");
      }
    } while (opcion != 9);
  }

  // METODO PARA DAR DE ALTA UN NUEVO CLIENTE

  public void nuevoCliente() {
    String dniT, nombreT, telT, emailT;
    sc.nextLine();
    System.out.println("\t\t\tALTA NUEVO CLIENTE");

    // VALIDACION DNI CON METODO VALIDA DNI
    do {
      System.out.println("\t\t\tINTRODUZCA DNI CLIENTE:");
      dniT = sc.nextLine().toUpperCase();
    } while (!validarDNI(dniT) || (clientes.containsKey(dniT)));

    // NOMBRE SIN VALIDACION GUARDADO EN MAYUSCULAS
    System.out.println("\t\t\tINTRODUZCA NOMBRE:");
    nombreT = sc.nextLine().toUpperCase();

    // VALIDACIÓN TELEFONO CON REGEX
    do {
      System.out.println("\t\t\tINTRODUZCA NUMERO DE TELEFONO:");
      telT = sc.next();
    } while (!telT.matches("^(?:(?:\\+|00)34)?[6789]\\d{8}$"));

    // VALIDACION EMAIL CON REGEX
    do {
      System.out.println("\t\t\tINTRODUZCA EMAIL:");
      emailT = sc.next();
    } while (!emailT.matches(
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"));

    // AÑADIMOS EL CLIENTE A LA COLECCION DE CLIENTES
    clientes.put(dniT, new Cliente(dniT, nombreT, telT, emailT));
  }

  // METODO PARA LISTAR TODOS LOS CLIENTES EXISTENTES ORDENADOS POR NOMBRE
  // (COMPARABLE)

  public void listaClientes() {
    System.out.println("\t\tCLIENTES DADOS DE ALTA EN LA APLICACION:\n");
    clientes.values().stream().sorted().forEach(System.out::println);
  }

  // METODO PARA MODIFICAR LOS DATOS DE CONTACTO DE UN CLIENTE

  public void modificaCliente() {
    String dniT, telefonoT, emailT;
    sc.nextLine();
    System.out.println("\t\t\tMODIFICAR UN CLIENTE");
    do {
      System.out.println("\t\t\tINTRODUZCA EL DNI DEL CLIENTE A MODIFICAR:");
      dniT = sc.nextLine().toUpperCase();
    } while (!validarDNI(dniT));

    if (clientes.containsKey(dniT)) {
      int opcion = 0;
      do {
        System.out.println("\n\t\t\tSE VA A MODIFICAR EL CONTACTO DE:\n" + "\t\t\t" + clientes.get(dniT).getNombre());
        System.out.println("\n\t\t\t1 - MODIFICAR EL TELEFONO");
        System.out.println("\t\t\t2 - MODIFICAR EL EMAIL");
        System.out.println("\n\t\t\t9 - SALIR");
        opcion = sc.nextInt();
        switch (opcion) {
          case 1: {
            do {
              System.out.println("\n\t\t\tINTRODUZCA NUMERO DE TELEFONO:");
              telefonoT = sc.next();
            } while (!telefonoT.matches("^(?:(?:\\+|00)34)?[6789]\\d{8}$"));
            clientes.get(dniT).setTelefono(telefonoT);
            System.out.println("\n\t\t\tSE HA MODIFICADO EL TELEFONO, EL NUEVO NUMERO PARA EL CONTACTO ES:");
            System.out.println("\t\t\t" + clientes.get(dniT).getNombre() + " - " + clientes.get(dniT).getTelefono());
            return;
          }
          case 2: {
            do {
              System.out.println("\n\t\t\tINTRODUZCA NUEVO EMAIL:");
              emailT = sc.next();
            } while (!emailT.matches(
                "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"));
            clientes.get(dniT).setEmail(emailT);
            System.out.println("\n\t\t\tSE HA MODIFICADO EL EMAIL, EL NUEVO EMAIL PARA EL CONTACTO ES:");
            System.out.println("\t\t\t" + clientes.get(dniT).getNombre() + " - " + clientes.get(dniT).getEmail());
            return;
          }

        }
      } while (opcion != 9);
    } else {
      System.out.println("\t\t\tNO EXISTE NINGUN CLIENTE CON ESE DNI.");
    }

  }

  // METODO PARA DAR DE BAJA UN CLIENTE

  public void bajaClientes() {
    String dniT;
    sc.nextLine();
    System.out.println("\t\t\tBAJA DE UN CLIENTE");
    do {
      System.out.println("\t\t\tINTRODUZCA EL DNI DEL CLIENTE A ELIMNAR:");
      dniT = sc.nextLine().toUpperCase();
    } while (!validarDNI(dniT));

    if (clientes.containsKey(dniT)) {
      Iterator<Pedido> it = pedidos.iterator();
      while (it.hasNext()) {
        if (it.next().getClientePedido().getDni().equalsIgnoreCase(dniT)) {
          it.remove();
        }
      }
      clientes.remove(dniT);
      System.out.println("\t\t\tSE HA ELIMINADO EL CLIENTE Y SUS PEDIDOS");

    } else {
      System.out.println("\t\t\tNO EXISTE NINGUN CLIENTE CON ESE DNI.");
    }
  }

  // SUBMENU PEDIDOS

  public void menuPedidos() {
    String opcionStr;
    int opcion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\t******************************");
      System.out.println("\t\t\t\t*       MENU PEDIDOS         *");
      System.out.println("\t\t\t\t******************************");
      System.out.println("\t\t\t\t*  1 - NUEVO PEDIDO          *");
      System.out.println("\t\t\t\t*  2 - LISTADOS              *");
      System.out.println("\t\t\t\t*  3 - TOTAL PEDIDO CONCRETO *");
      System.out.println("\t\t\t\t*  4 - ELIMINAR PEDIDO       *");
      System.out.println("\t\t\t\t*                            *");
      System.out.println("\t\t\t\t*  9 - SALIR                 *");
      System.out.println("\t\t\t\t******************************");
      opcionStr = sc.next();
      if (opcionStr.matches("^[1-4]|9$")) {
        opcion = Integer.parseInt(opcionStr);
        switch (opcion) {
          case 1: {
            nuevoPedido();
            break;
          }
          case 2: {
            listarPedidos();
            break;
          }
          case 3: {
            totalPedido();
            break;
          }
          case 4: {
            eliminarPedido();
            break;
          }
        }
      } else {
        System.out
            .println("\n\t\t\tOPCION INVALIDA. POR FAVOR INTRODUCE UN NUMERO \n\t\t\tDEL 1 AL 4, O UN 9 PARA SALIR.");
      }
    } while (opcion != 9);
  }

  // METODO PARA REALIZAR LISTADOS DE PEDIDOS SEGÚN ELECCION

  public void listarPedidos() {
    String opcion;
    sc.nextLine();
    do {
      System.out.println("\n\n\n\n\n\t\t\tLISTAR PEDIDOS\n");
      System.out.println("\t\t\t1 - TODOS LOS PEDIDOS ORDENADOS POR IMPORTE TOTAL SIN DESGLOSAR");
      System.out.println("\t\t\t2 - PEDIDOS ORDENADOS POR FECHA DESGLOSADOS");
      System.out.println("\t\t\t3 - PEDIDOS QUE SUPERAN UN IMPORTE");
      System.out.println("\n\t\t\t4 - SALIR");
      do {
        System.out.println("\n\t\t\tSELECCIONA TU OPCION:");
        opcion = sc.nextLine();
      } while (!opcion.matches("[1-4]"));
      if (!opcion.equals("4")) {
        listadosPedidos(opcion);
      }
    } while (!opcion.equals("4"));
  }

  public void listadosPedidos(String opcion) {
    for (Pedido p : pedidos) {
      totales.put(p.getIdPedido(), totalPedido(p));
    }

    if (opcion.equals("1")) {
      System.out.println("\n\t\tPEDIDOS ORDENADOS DE MENOR A MAYOR IMPORTE TOTAL:\n");
      pedidos.stream()
          .sorted((p1, p2) -> Double.compare(totales.get(p1.getIdPedido()), totales.get(p2.getIdPedido())))
          .forEach(p -> {
            System.out.println("\t\t" + p.getIdPedido() + " - " + p.getClientePedido().getDni()
                + " - " + p.getClientePedido().getNombre() + " - TOTAL: "
                + String.format("%.2f", totales.get(p.getIdPedido()))
                + " EUROS -> TOTAL IVA INCLUIDO: " + String.format("%.2f", totales.get(p.getIdPedido()) * 1.21)
                + " EUROS.");
          });
    }
    if (opcion.equals("2")) {
      System.out.println("\n\t\tPEDIDOS ORDENADOS POR FECHA DESGLOSADOS:");
      pedidos.stream().sorted(Comparator.comparing(Pedido::getFechaPedido)).forEach(p -> {
        System.out.println("\n\t\tPEDIDO: " + p.getIdPedido() + " - " + p.getClientePedido().getDni() + " - "
            + p.getClientePedido().getNombre() + " - " + p.getFechaPedido());
        System.out.println("\t\tARTICULOS:");
        for (LineaPedido lp : p.getCestaCompra()) {
          System.out
              .println(
                  "\t\t" + articulos.get(lp.getIdArticulo()).getDescripcion() + " - " + lp.getUnidades() + " UNIDADES");
        }
        System.out.println(
            "\t\tTOTAL: " + String.format("%.2f", totales.get(p.getIdPedido())) + " EUROS -> TOTAL IVA INCLUIDO: "
                + String.format("%.2f", totales.get(p.getIdPedido()) * 1.21) + " EUROS.");
      });

    }
    if (opcion.equals("3")) {
      String importe;
      System.out.println("\n\t\tPEDIDOS QUE SUPERAN UN IMPORTE DETERMINADO (SIN IVA)");
      do {
        System.out.println("\n\t\tPOR FAVOR INTRODUZCA EL IMPORTE:");
        importe = sc.nextLine();
      } while (!esDouble(importe));
      double importeD = Double.parseDouble(importe);
      System.out.println("\n\t\tPEDIDOS SUPERIORES A " + importe + ":\n");
      pedidos.stream()
          .filter(p -> totales.get(p.getIdPedido()) > importeD)
          .sorted(Comparator.comparingDouble(p -> totales.get(p.getIdPedido())))
          .forEach(p -> {
            System.out.println("\t\t" + p.getIdPedido() + " - " + p.getClientePedido().getDni()
                + " - " + p.getClientePedido().getNombre() + " - "
                + String.format("%.2f", totales.get(p.getIdPedido()))
                + " EUROS -> TOTAL IVA INCLUIDO: " + String.format("%.2f", totales.get(p.getIdPedido()) * 1.21)
                + " EUROS.");
          });

    }

  }

  // METODO PARA CALCULAR EL TOTAL DE UN PEDIDO PASADO COMO PARAMETRO

  public double totalPedido(Pedido p) {
    double total = 0;
    for (LineaPedido lp : p.getCestaCompra()) {
      total += articulos.get(lp.getIdArticulo()).getPvp() * (lp.getUnidades());
    }
    return total;
  }

  // METODO PARA CALCULAR EL TOTAL DE UN PEDIDO SOLICITANDO LOS DATOS DEL MISMO
  // POR TECLADO

  public void totalPedido() {
    String id;
    sc.nextLine();
    System.out.println("\t\tPEDIDOS ACTUALES EN LA APLICACION:\n ");
    for (Pedido p : pedidos) {
      System.out.println("\t\tIDPEDIDO: " + p.getIdPedido() + " - CLIENTE: " + p.getClientePedido().getNombre());
      totales.put(p.getIdPedido(), totalPedido(p));
    }
    do {
      System.out.println("\n\t\tINTRODUCE EL ID DEL PEDIDO PARA CALCULAR TOTAL: ");
      id = sc.nextLine().toUpperCase();
    } while (!totales.containsKey(id));

    System.out.println("\n\t\tLISTADO DE ARTICULOS PEDIDO " + id + ":\n");
    for (Pedido p : pedidos) {
      if (p.getIdPedido().equals(id)) {
        for (LineaPedido lp : p.getCestaCompra()) {
          System.out.println("\t\t" + articulos.get(lp.getIdArticulo()).getDescripcion() + " - "
              + lp.getUnidades() + " UNIDADES");
        }
      }
    }

    System.out.println(
        "\n\t\tEL TOTAL DEL PEDIDO " + id + " ES:\n\n\t\tTOTAL NETO: " + totales.get(id) + "Euros." + "\n\t\tIVA 21%: "
            + String.format("%.2f", totales.get(id) * 0.21)
            + " Euros.\n\t\tTOTAL IVA INCLUIDO: " + String.format("%.2f", totales.get(id) * 1.21)
            + " Euros.");
  }

  // METODO PARA CREAR UN NUEVO PEDIDO

  public void nuevoPedido() {
    // ARRAYLIST AUXILIAR PARA CREAR EL PEDIDO
    ArrayList<LineaPedido> CestaCompraAux = new ArrayList();
    String dniT, idT, opc, pedidasS;
    int pedidas = 0;

    sc.nextLine();
    do {
      System.out.println("\n\t\tINTRODUCE CLIENTE PEDIDO (DNI) O PULSA ENTER PARA REGRESAR:");
      dniT = sc.nextLine().toUpperCase();
      if (dniT.isBlank())
        break;
    } while (!clientes.containsKey(dniT));

    if (!dniT.isBlank()) {
      System.out.println("\t\tINTRODUZCA LOS ARTICULOS DEL PEDIDO UNO A UNO: ");
      do {
        System.out.println("\n\t\tINTRODUCE CODIGO ARTICULO (99 PARA TERMINAR): ");
        idT = sc.next();
        if (!idT.equals("99") && articulos.containsKey(idT)) {
          do {
            System.out.print("\t\t(" + articulos.get(idT).getDescripcion() + ") - UNIDADES? ");
            pedidasS = sc.next();
          } while (!esInt(pedidasS));

          pedidas = Integer.parseInt(pedidasS);

          try {
            stock(pedidas, idT); // LLAMO AL METODO STOCK, PUEDEN SALTAR 2 EXCEPCIONES
            CestaCompraAux.add(new LineaPedido(idT, pedidas));
          } catch (StockAgotado e) {
            System.out.println(e.getMessage());
          } catch (StockInsuficiente e) {
            System.out.println("\t\t" + e.getMessage());
            int disponibles = articulos.get(idT).getExistencias();
            System.out.print("\t\tQUIERES LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N) ");
            opc = sc.next();
            if (opc.equalsIgnoreCase("S")) {
              CestaCompraAux.add(new LineaPedido(idT, disponibles));
            } else {
              System.out.println("\n\t\tNO SE HAN AÑADIDO LAS UNIDADES DISPONIBLES DEL ARTICULO AL PEDIDO");
            }
          }

        }
      } while (!idT.equals("99"));

      // IMPRIMO EL PEDIDO Y SOLICITO ACEPTACION DEFINITIVA DEL MISMO
      for (LineaPedido l : CestaCompraAux) {
        System.out
            .println("\t\t" + articulos.get(l.getIdArticulo()).getDescripcion() + " - (" + l.getUnidades() + ")");
      }
      System.out.println("\t\tESTE ES EL PEDIDO, PROCEDEMOS? (S/N)   ");
      opc = sc.next();
      if (opc.equalsIgnoreCase("S")) {
        // ESCRIBO EL PEDIDO DEFINITIVAMENTE Y DESCUENTO LAS EXISTENCIAS PEDIDAS DE CADA
        // ARTICULO
        LocalDate hoy = LocalDate.now();
        pedidos.add(new Pedido(generaIdPedido(dniT), clientes.get(dniT), hoy, CestaCompraAux));

        for (LineaPedido l : CestaCompraAux) {
          articulos.get(l.getIdArticulo())
              .setExistencias(articulos.get(l.getIdArticulo()).getExistencias() - l.getUnidades());
        }
      } else {
        System.out.println("\t\tSE HA CANCELADO EL PEDIDO PORQUE NO HAS ACEPTADO LA CONFIRMACIÓN FINAL.");
      }
    }
  }

  // METODO PARA ELIMINAR UN PEDIDO

  public void eliminarPedido() {
    String id;
    sc.nextLine();
    System.out.println("\t\t\tPEDIDOS ACTUALES EN EL SISTEMA:\n ");
    for (Pedido p : pedidos) {
      System.out.println("\t\t\t" + p.getIdPedido() + " " + p.getClientePedido().getNombre());
      totales.put(p.getIdPedido(), totalPedido(p));
    }
    do {
      System.out.println("\n\t\t\tINTRODUCE EL ID DEL PEDIDO A ELIMINAR: ");
      id = sc.next().toUpperCase();
    } while (!totales.containsKey(id));
    // DECLARO LA VARIABLE PERIODO ID PORQUE DEBER SER UNA FINAL O NO MODIFICADA
    // PARA PODER USARLA EN LA LAMBDA
    String pedidoid = id;
    System.out.println("\n\t\t\tSE HA ELIMINADO EL PEDIDO " + id);
    pedidos.removeIf(p -> p.getIdPedido().equals(pedidoid));
  }

  // METODO AUXILIAR PARA GENERAR IDS PEDIDOS

  private String generaIdPedido(String dni) {
    int contador = 0;
    String nuevoId;
    for (Pedido p : pedidos) {
      if (p.getClientePedido().getDni().equalsIgnoreCase(dni)) {
        contador++;
      }
    }
    contador++;
    nuevoId = dni + "-" + String.format("%03d", contador) + "/" + LocalDate.now().getYear();
    return nuevoId;
  }

  // METODO AUXILIAR PARA VALIDAR SI UN DNI INTRODUCIDO ES VALIDO

  public static boolean validarDNI(String dni) {
    // Verificar que el DNI tiene un formato válido
    if (dni.isBlank() || !dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
      return false;
    }

    // Extraer el número y la letra del DNI
    String numeroStr = dni.substring(0, 8);
    char letra = dni.charAt(8);

    // Calcular la letra correspondiente al número del DNI
    char letraCalculada = calcularLetraDNI(Integer.parseInt(numeroStr));

    // Comparar la letra calculada con la letra proporcionada y devolver
    // el resultado de la comparación TRUE/FALSE

    return letra == letraCalculada;
  }

  private static char calcularLetraDNI(int numero) {
    String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    return letras.charAt(numero % 23);
  }

  // METODO AUXILIAR VALIDAR SI UN STRING ES UN ENTERO

  public boolean esInt(String s) {
    int n;
    try {
      n = Integer.parseInt(s);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }

  // METODO AUXILIAR PARA VALIDAR SI UN STRING ES UN DOUBLE

  public boolean esDouble(String s) {
    double n;
    try {
      n = Double.parseDouble(s);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }

  /*
   * METODO TEMPORAL PARA LA CARGA DE DATOS HASTA IMPLEMENTAR LA PERSISTENCIA O EN
   * LA PRIMERA EJECUCION. SE CAMBIARON LOS DNI FICTICIOS POR DNI REALES PARA QUE
   * FUNCIONARAN LOS METODOS QUE USAN validarDNI()
   * 
   * DNI DE LOS CLIENTES DE PRUEBA:
   * 
   * 70733332Y
   * 82284426V
   * 22541447K
   * 
   */

  public void cargaDatos() {
    clientes.put("70733332Y", new Cliente("70733332Y", "ANA", "658111111", "ana@gmail.com"));
    clientes.put("82284426V", new Cliente("82284426V", "LOLA", "649222222", "lola@gmail.com"));
    clientes.put("22541447K", new Cliente("22541447K", "JUAN", "652333333", "juan@gmail.com"));

    articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH", 14, 15));
    articulos.put("1-22", new Articulo("1-22", "TECLADO STANDARD", 9, 18));
    articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1TB", 16, 80));
    articulos.put("2-22", new Articulo("2-22", "SSD KINGSTOM 256GB", 9, 70));
    articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 15, 120));
    articulos.put("3-11", new Articulo("3-11", "EPSON ET2800", 0, 200));
    articulos.put("3-22", new Articulo("3-22", "EPSON XP200", 5, 80));
    articulos.put("4-11", new Articulo("4-11", "ASUS LED 22", 5, 100));
    articulos.put("4-22", new Articulo("4-22", "HP LED 28", 5, 180));
    articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY", 12, 580));

    LocalDate hoy = LocalDate.now();

    pedidos.add(new Pedido("70733332Y-001/2024", clientes.get("70733332Y"), hoy.minusDays(1),
        new ArrayList<>(List.of(new LineaPedido("1-11", 1), new LineaPedido("2-11", 1)))));
    pedidos.add(new Pedido("70733332Y-002/2024", clientes.get("70733332Y"), hoy.minusDays(2),
        new ArrayList<>(List.of(new LineaPedido("4-11", 14), new LineaPedido("4-22", 4), new LineaPedido("4-33", 4)))));
    pedidos.add(new Pedido("22541447K-001/2024", clientes.get("22541447K"), hoy.minusDays(3),
        new ArrayList<>(List.of(new LineaPedido("3-22", 3), new LineaPedido("2-22", 3)))));
    pedidos.add(new Pedido("22541447K-002/2024", clientes.get("22541447K"), hoy.minusDays(5),
        new ArrayList<>(List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3)))));
    pedidos.add(new Pedido("82284426V-001/2024", clientes.get("82284426V"), hoy.minusDays(4),
        new ArrayList<>(List.of(new LineaPedido("2-11", 2), new LineaPedido("2-33", 2), new LineaPedido("4-33", 2)))));
  }

  // METODO AUXILIAR CALCULAR EL STOCK

  public void stock(int unidadesPed, String id) throws StockAgotado, StockInsuficiente {
    int n = articulos.get(id).getExistencias();
    if (n == 0) {
      throw new StockAgotado("\t\tStock AGOTADO para el articulo: " + articulos.get(id).getDescripcion());
    } else if (n < unidadesPed) {
      throw new StockInsuficiente("\t\tNo hay Stock suficiente. Solicita " + unidadesPed + " de "
          + articulos.get(id).getDescripcion()
          + " y sólo se dispone de: " + n);
    }
  }

  // METODO PARA DETERMINAR SI UN ARTICULO ESTA IMPLICADO EN ALGUN PEDIDO

  public boolean existePedidoConArticulo(String idArticulo) {
    for (Pedido p : pedidos) {
      for (LineaPedido lp : p.getCestaCompra()) {
        if (lp.getIdArticulo().equals(idArticulo)) {
          return true;
        }
      }
    }
    return false;
  }

  // METODOS DE BACKUP Y CARGA DE DATOS (PERSISTENCIA)

  // METODO PARA REALIZAR LA COPIA DE DATOS A ARCHIVOS .DAT

  public void backup() {
    try (
        ObjectOutputStream oosArticulos = new ObjectOutputStream(new FileOutputStream("articulos.dat"));
        ObjectOutputStream oosClientes = new ObjectOutputStream(new FileOutputStream("clientes.dat"));
        ObjectOutputStream oosPedidos = new ObjectOutputStream(new FileOutputStream("pedidos.dat"))) {

      // COLECCIONES ENTERAS
      oosArticulos.writeObject(articulos);
      oosClientes.writeObject(clientes);
      // PEDIDOS
      for (Pedido p : pedidos) {
        oosPedidos.writeObject(p);
      }

      System.out.println("\t\t\t\t***********************");
      System.out.println("\t\t\t\t*   |      |  |  |    *");
      System.out.println("\t\t\t\t*   |      |__|  |    *");
      System.out.println("\t\t\t\t*   |____________|    *");
      System.out.println("\t\t\t\t*   _______________   *");
      System.out.println("\t\t\t\t*  |               |  *");
      System.out.println("\t\t\t\t*  |    TIENDA     |  *");
      System.out.println("\t\t\t\t*  |    BACKUP     |  *");
      System.out.println("\t\t\t\t*  |               |  *");
      System.out.println("\t\t\t\t***********************");
      System.out.println("\n\t\t\tCOPIA DE SEGURIDAD REALIZADA CON EXITO.");

    } catch (FileNotFoundException e) {
      System.out.println(e.toString());
    } catch (IOException e) {
      System.out.println(e.toString());
    }

  }

  // METODO PARA CARGAR ELEMENTOS DESDE LOS ARCHIVOS .DAT

  public void leerArchivos() {
    try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream("articulos.dat"));
        ObjectInputStream oisClientes = new ObjectInputStream(new FileInputStream("clientes.dat"));
        ObjectInputStream oisPedidos = new ObjectInputStream(new FileInputStream("pedidos.dat"))) {

      // IMPORTAMOS LAS COLECCIONES
      articulos = (HashMap<String, Articulo>) oisArticulos.readObject();
      clientes = (HashMap<String, Cliente>) oisClientes.readObject();

      // IMPORTAMOS LOS PEDIDOS
      Pedido p = null;
      while ((p = (Pedido) oisPedidos.readObject()) != null) {
        pedidos.add(p);
      }
      System.out.println("\n\t\tDATOS IMPORTADOS CON EXITO.");

    } catch (FileNotFoundException e) {
      System.out.println(
          "\n\n\n\t\tERROR AL CARGAR LOS DATOS DE LOS ARCHIVOS DE BACKUP. NO EXISTEN UNO O VARIOS ARCHIVOS.\n\t\t\t"
              + e.toString());
    } catch (EOFException e) {

    } catch (ClassNotFoundException | IOException e) {
      System.out.println(e.toString());
    }
    // RESTABLECER LA CORRESPONDENCIA ENTRE PEDIDOS Y CLIENTES
    for (Pedido p : pedidos) {
      p.setClientePedido(clientes.get(p.getClientePedido().getDni()));
    }
  }

  // METODO BACKUP A ARCHIVOS CSV

  public void clientesTxtBackup() {
    String ruta = "clientes/clientes.csv";
    try {
      File carpeta = new File("clientes");
      if (!carpeta.exists()) {
        carpeta.mkdirs();
      }
      try (BufferedWriter bfwClientes = new BufferedWriter(new FileWriter(ruta))) {
        for (Cliente c : clientes.values()) {
          bfwClientes.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail());
        }
        System.out.println("\n\t\tCOPIA DE CLIENTES EN FORMATO CSV REALIZADA CON EXITO.");
      } catch (IOException e) {
        System.out.println("\t\tERROR ENTRADA/SALIDA:" + e);
      }
    } catch (Exception e) {
      System.out.println("\t\tERROR AL CREAR LA CARPETA: " + e);
    }
  }

}

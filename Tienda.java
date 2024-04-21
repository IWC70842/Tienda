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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Tienda {

  Scanner sc = new Scanner(System.in);
  private ArrayList<Pedido> pedidos;
  private HashMap<String, Articulo> articulos;
  private HashMap<String, Cliente> clientes;

  public Tienda() {
    pedidos = new ArrayList();
    articulos = new HashMap();
    clientes = new HashMap();
  }

  public static void main(String[] args) {
    Tienda t = new Tienda();
    // t.leerArchivos();
    // t.cargadatos();
    t.menu();
  }

  public int buscarDni(String dni) {
    int posicion = -1;
    int i = 0;
    for (Cliente c : clientes) {
      if (c.getDni().equals(dni)) {
        posicion = i;
        break;
      }
      i++;
    }
    return posicion;
  }

  public int buscarId(String id) {
    int posicion = -1;
    int i = 0;
    for (Articulo a : articulos) {
      if (a.getIdArticulo().equals(id)) {
        posicion = i;
        break;
      }
      i++;
    }
    return posicion;
  }

  public void listaPedidos() {
    for (Pedido p : pedidos) {
      System.out.println("\nLISTADO PEDIDO " + p.getIdPedido() + " - CLIENTE: " + p.getClientePedido().getNombre());
      for (LineaPedido lp : p.getCestaCompra()) {
        System.out.println(articulos.get(buscarId(lp.getIdArticulo())).getDescripcion() + "\t" + lp.getUnidades());
      }
    }

  }

  public void listaArticulos() {
    System.out.println("\n\n");
    for (Articulo a : articulos) {
      System.out.println(a);
    }
  }

  public void listaClientes() {
    System.out.println("\n\n");
    for (Cliente c : clientes) {
      System.out.println(c);
    }
  }

  // MENU PRINCIPAL APLICACION
  public void menu(){
    int opcion=0;
    do{
      System.out.println("\n\n\n\n\n\t\t\t\tMENU DE OPCIONES\n");
      System.out.println("\t\t\t\t1 - ARTICULOS");
      System.out.println("\t\t\t\t2 - CLIENTES");
      System.out.println("\t\t\t\t3 - PEDIDOS");
      System.out.println("\t\t\t\t4 - COPIA DE SEGURIDAD");
      System.out.println("\t\t\t\t1 - SALIR");
      opcion=sc.nextInt();
      switch(opcion){
        case 1:{
          menuArticulos();
          break;
        }
        case 2:{
          menuClientes();
          break;
        }
        case 3:{
          menuPedidos();
          break;
        }
        case 4:{
          menuTxtBackup();
          break;
        }
      }
    }while (opcion !=9);
  }

  // SUBMENU PEDIDOS
  public void menuPedidos(){
    int opcion=0;
    do{
      System.out.println("\n\n\n\n\n\t\t\t\tMENU PEDIDOS\n");
      System.out.println("\t\t\t\t1 - NUEVO PEDIDO");
      System.out.println("\t\t\t\t2 - MODIFICAR ESTADO");
      System.out.println("\t\t\t\t3 - LISTADOS");
      System.out.println("\t\t\t\t4 - TOTAL PEDIDO");
      System.out.println("\t\t\t\t1 - SALIR");
      opcion=sc.nextInt();
      switch(opcion){
        case 1:{
          nuevoPedido();
          break;
        }
        case 2:{
          modificarEstado();
          break;
        }
        case 3:{
          listarPedidos();
          break;
        }
        case 4:{
          totalPedido();
          break;
        }
      }
    }while (opcion !=9);
  }
  
  /*Se sustituyó por nuevoPedido()

    public void altaPedido() {

    ArrayList<LineaPedido> pedidoAux = new ArrayList();
    LocalDate hoy;

    System.out.println("INTRODUCE EL DNI CLIENTE PARA EL PEDIDO: ");
    String dni = sc.next();

    System.out.println("SE VAN A INTRODUCIR LOS ARTÍCULOS 1 A 1: ");
    System.out.println("INTRODUCE CODIGO ARTICULO (99 PARA TERMINAR): ");
    String id = sc.next();
    while (!id.equals("99")) {
      System.out.println("CUANTAS UNIDADES QUIERES ?:");
      int unidades = sc.nextInt();
      pedidoAux.add(new LineaPedido(id, unidades));
      articulos.get(buscarId(id)).setExistencias(articulos.get(buscarId(id)).getExistencias() - unidades);
      System.out.println("INTRODUCE CODIGO ARTICULO (99 PARA TERMINAR): ");
      id = sc.next();
    }
    hoy = LocalDate.now();
    //
    // LLAMADA AL MÉTODO generaIdPedido() - ALTA DEL NUEVO PEDIDO EN EL ARRAYLIST
    //
    pedidos.add(new Pedido(generaIdPedido(dni), clientes.get(buscarDni(dni)), hoy, pedidoAux));
  }*/

  public void totalPedido() {
    System.out.println("INTRODUCE EL ID DEL PEDIDO PARA CALCULAR TOTAL: ");
    String id = sc.next();
    int i = 0;
    double total = 0;
    while (!pedidos.get(i).getIdPedido().equals(id)) {
      i++;
    }

    System.out.println("LISTADO DE ARTICULOS PEDIDO " + id + ":\n");
    for (LineaPedido lp : pedidos.get(i).getCestaCompra()) {
      System.out.println("\t" + articulos.get(buscarId(lp.getIdArticulo())).getDescripcion() +
          "\t" + articulos.get(buscarId(lp.getIdArticulo())).getPvp() +
          " * " + lp.getUnidades() + " Unidades");

      total += articulos.get(buscarId(lp.getIdArticulo())).getPvp() * lp.getUnidades();
    }
    System.out.println("\nEL TOTAL DEL PEDIDO " + id + " ES: " + total + "€");
  }

  public void nuevoPedido() {
    // ARRAYLIST AUXILIAR PARA CREAR EL PEDIDO
    ArrayList<LineaPedido> CestaCompraAux = new ArrayList();
    String dniT, idT, opc, pedidasS;
    int pedidas = 0;

    sc.nextLine();
    do {
      System.out.println("CLIENTE PEDIDO (DNI):");
      dniT = sc.nextLine().toUpperCase();
      if (dniT.isBlank())
        break;
    } while (!clientes.containsKey(dniT));

    if (!dniT.isBlank()) {
      System.out.println("INTRODUZCA LOS ARTICULOS DEL PEDIDO UNO A UNO: ");
      do {
        System.out.println("INTRODUCE CODIGO ARTICULO (99 PARA TERMINAR): ");
        idT = sc.next();
        if (!idT.equals("99") && articulos.containsKey(idT)) {
          System.out.print("(" + articulos.get(idT).getDescripcion() + ") - UNIDADES? ");
          do {
            pedidasS = sc.next();
          } while (!esInt(pedidasS));

          pedidas = Integer.parseInt(pedidasS);

          try {
            stock(pedidas, idT); // LLAMO AL METODO STOCK, PUEDEN SALTAR 2 EXCEPCIONES
            CestaCompraAux.add(new LineaPedido(idT, pedidas));
          } catch (StockAgotado e) {
            System.out.println(e.getMessage());
          } catch (StockInsuficiente e) {
            System.out.println(e.getMessage());
            int disponibles = articulos.get(idT).getExistencias();
            System.out.print("QUIERES LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N) ");
            opc = sc.next();
            if (opc.equalsIgnoreCase("S")) {
              CestaCompraAux.add(new LineaPedido(idT, disponibles));
            }
          }

        }
      } while (!idT.equals("99"));

      // IMPRIMO EL PEDIDO Y SOLICITO ACEPTACION DEFINITIVA DEL MISMO
      for (LineaPedido l : CestaCompraAux) {
        System.out.println(articulos.get(l.getIdArticulo()).getDescripcion() + " - (" + l.getUnidades() + ")");
      }
      System.out.println("ESTE ES TU PEDIDO. PROCEDEMOS? (S/N)   ");
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
      }
    }
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

  // FALTA SUBMENU CLIENTES

  public void altaClientes() {
    String dniT, nombreT, telT, emailT;
    sc.nextLine();
    System.out.println("ALTA NUEVO CLIENTE");

    // VALIDACION DNI
    do {
      System.out.println("INTRODUZCA DNI CLIENTE:");
      dniT = sc.nextLine().toUpperCase();
    } while (!validarDNI(dniT) || !clientes.containsKey(dniT));

    // NOMBRE SIN VALIDACION
    System.out.println("INTRODUZCA NOMBRE:");
    nombreT = sc.nextLine().toUpperCase();

    // VALIDACIÓN TELEFONO CON REGEX
    do {
      System.out.println("INTRODUZCA NUMERO DE TELEFONO:");
      telT = sc.next();
    } while (!telT.matches("[0-9]{8,11}"));

    // VALIDACION EMAIL CON REGEX
    do {
      System.out.println("INTRODUZCA EMAIL:");
      emailT = sc.next();
    } while (!emailT.matches(
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"));

    // AÑADIMOS EL CLIENTE A LA COLECCION DE CLIENTES
    clientes.put(dniT, new Cliente(dniT, nombreT, telT, emailT));
  }

  public void bajaClientes() {
    String dniT;
    sc.nextLine();
    System.out.println("BAJA DE UN CLIENTE");
    do {
      System.out.println("INTRODUZCA EL DNI DEL CLIENTE A ELIMNAR:");
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
      System.out.println("SE HA ELIMINADO EL CLIENTE Y SUS PEDIDOS");

    } else {
      System.out.println("NO EXISTE NINGUN CLIENTE CON ESE DNI.");
    }
  }

  // SUBMENU ARTICULOS

  public void menuArticulos() {
    int opcion = 0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\tARTICULOS\n");
      System.out.println("\t\t\t\t1 - ALTA");
      System.out.println("\t\t\t\t2 - BAJA");
      System.out.println("\t\t\t\t3 - REPOSICIÓN");
      System.out.println("\t\t\t\t4 - LISTADOS");
      System.out.println("\t\t\t\t9 - SALIR");
      opcion = sc.nextInt();
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
    } while (opcion != 9);
  }

  public void altaArticulos() {
    String idT, descT, exT, pvpT;
    sc.nextLine();
    System.out.println("ALTA NUEVO ARTICULO");

    // VALIDAMOS CON REGEX LA ID

    do {
      System.out.println("INTRODUCE ID ARTICULO:");
      idT = sc.nextLine();
    } while (!idT.matches("[1-5][-][0-9][0-9]") || articulos.containsKey(idT));

    // DESCRIPCION DEL ARTICULO

    System.out.println("INTRODUZCA LA DESCRIPCION DEL ARTICULO:");
    descT = sc.nextLine();

    // VALIDACION DE LAS EXISTENCIAS

    do {
      System.out.println("INTRODUZCA EL NUMERO DE EXISTENCIAS:");
      exT = sc.next();
    } while (!esInt(exT));

    // VALIDACION DE PVP

    do {
      System.out.println("INTRODUZCA PVP DEL ARTICULO:");
      pvpT = sc.next();
    } while (!esDouble(pvpT));

    // AÑADIMOS EL ARTICULO A LA COLECCION HACIENDO PARSING DE LOS DATOS
    articulos.put(idT, new Articulo(idT, descT, Integer.parseInt(exT), Double.parseDouble(pvpT)));
  }

  public void bajaArticulos() {
    String idT;

    sc.nextLine();
    System.out.println("BAJA DE UN ARTICULO");
    // idArticulo VALIDADO CON EXPRESION REGULAR SENCILLA
    do {
      System.out.println("IdArticulo a ELIMINAR (IDENTIFICADOR):");
      idT = sc.nextLine();
    } while (!idT.matches("[1-5][-][0-9][0-9]"));
    if (articulos.containsKey(idT)) {
      articulos.remove(idT);
      System.out.println("ARTICULO ELIMINADO");
    } else {
      System.out.println("NO EXISTE ARTICULO CON ESE IDENTIFICADOR. NO SE PUEDE BORRAR");
    }
  }

  public void reposicionArticulos() {
    String stockMinimo, idT, stockMas;
    sc.nextLine();
    do {
      System.out.println("NUMERO DE UNIDADES PARA RUPTURA DE STOCK:");
      stockMinimo = sc.nextLine(); // Se lee la entrada de EXISTENCIAS como un String
    } while (!esInt(stockMinimo) && stockMinimo.length() > 0);
    if (stockMinimo.length() == 0)
      return;

    System.out.println("\t\tLISTADO DE ARTICULOS CON " + Integer.parseInt(stockMinimo) + " UNIDADES O MENOS");
    for (Articulo a : articulos.values()) {
      if (a.getExistencias() <= Integer.parseInt(stockMinimo)) {
        System.out.println(a);
      }
    }

    do {
      System.out.println("IdArticulo a REPONER (IDENTIFICADOR):");
      idT = sc.nextLine();
    } while (!idT.matches("[1-5][-][0-9][0-9]") && idT.length() > 0);
    if (idT.length() == 0)
      return;

    do {
      System.out.println("NUMERO DE UNIDADES A REPONER:");
      stockMas = sc.nextLine(); // Se lee la entrada de EXISTENCIAS como un String
    } while (!esInt(stockMas) && stockMas.length() > 0);
    if (stockMas.length() == 0)
      return;
    articulos.get(idT).setExistencias(articulos.get(idT).getExistencias() + Integer.parseInt(stockMas));
  }

  // LISTADOS ARTICULOS

  public void listarArticulos() {
    String opcion;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\tLISTAR ARTICULOS\n");
      System.out.println("\t\t\t\t0 - TODOS LOS ARTICULOS");
      System.out.println("\t\t\t\t1 - PERIFERICOS");
      System.out.println("\t\t\t\t2 - ALMACENAMIENTO");
      System.out.println("\t\t\t\t3 - IMPRESORAS");
      System.out.println("\t\t\t\t4 - MONITORES");
      System.out.println("\t\t\t\t5 - COMPONENETES");
      System.out.println("\t\t\t\t6 - SALIR");
      do
        opcion = sc.next();
      while (!opcion.matches("[0-6]"));
      if (!opcion.equals("6")) {
        listados(opcion);

      }
    } while (!opcion.equals("6"));

  }

  public void listados(String seccion) {

    String[] secciones = { "TODAS", "PERIFERICOS", "ALMACENAMIENTO", "IMPRESORAS", "MONITORES", "COMPONENTES" };
    sc.nextLine();
    System.out.println("[RETURN]ORDEN NORMAL POR idArticulo - PARA ORDENAR POR PRECIO < a >(-) > a <(+)");
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
      System.out.println("LISTADO DE LOS ARTICULOS DE LA SECCION (" + secciones[Integer.parseInt(seccion)] + ")");
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

  // FALTA SUBMENU BACKUP
  
  
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

  // METODO AUXILIAR PARA VALIDAR UN DNI

  public static boolean validarDNI(String dni) {
    // VERIFICAMOS EL FORMATO CON REGEX
    if (dni == null || !dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
      return false;
    }

    // COMPROBACION DE LETRA CORRECTA

    String numeroStr = dni.substring(0, 8);
    char letra = dni.charAt(8);

    char letraCalculada = calcularLetraDNI(Integer.parseInt(numeroStr));

    return letra == letraCalculada;
  }

  // METODO AUXILIAR CALCULAR LA LETRA DEL DNI

  private static char calcularLetraDNI(int numero) {
    String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    return letras.charAt(numero % 23);
  }

  // METODO TEMPORAL PARA LA CARGA DE DATOS HASTA IMPLEMENTAR LA PERSISTENCIA
  public void cargaDatos() {
    clientes.put("11111111R", new Cliente("11111111R", "ANA", "658111111", "ana@gmail.com"));
    clientes.put("22222222H", new Cliente("22222222H", "LOLA", "649222222", "lola@gmail.com"));
    clientes.put("33333333F", new Cliente("33333333F", "JUAN", "652333333", "juan@gmail.com"));

    articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH", 14, 15));
    articulos.put("1-22", new Articulo("1-22", "TECLADO STANDAR", 9, 18));
    articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1TB", 16, 80));
    articulos.put("2-22", new Articulo("2-22", "SSD KINGSTIM 256GB", 9, 70));
    articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 15, 120));
    articulos.put("3-11", new Articulo("3-11", "EPSON ET2800", 0, 200));
    articulos.put("3-22", new Articulo("3-22", "EPSON XP200", 5, 80));
    articulos.put("4-11", new Articulo("4-11", "ASUS LED 22", 5, 100));
    articulos.put("4-22", new Articulo("4-22", "HP LED 28", 5, 180));
    articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY", 12, 580));

    LocalDate hoy = LocalDate.now();

    pedidos.add(new Pedido("11111111R-001/2024", clientes.get("11111111R"), hoy.minusDays(1),
        new ArrayList<>(List.of(new LineaPedido("1-11", 1), new LineaPedido("2-11", 1)))));
    pedidos.add(new Pedido("11111111R-002/2024", clientes.get("11111111R"), hoy.minusDays(2),
        new ArrayList<>(List.of(new LineaPedido("4-11", 14), new LineaPedido("4-22", 4), new LineaPedido("4-33", 4)))));
    pedidos.add(new Pedido("33333333F-001/2024", clientes.get("33333333F"), hoy.minusDays(3),
        new ArrayList<>(List.of(new LineaPedido("3-22", 3), new LineaPedido("2-22", 3)))));
    pedidos.add(new Pedido("33333333f-002/2024", clientes.get("33333333F"), hoy.minusDays(5),
        new ArrayList<>(List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3)))));
    pedidos.add(new Pedido("22222222H-001/2024", clientes.get("22222222H"), hoy.minusDays(4),
        new ArrayList<>(List.of(new LineaPedido("2-11", 2), new LineaPedido("2-33", 2), new LineaPedido("4-33", 2)))));
  }

  // METODO AUXILIAR CALCULAR EL STOCK

  public void stock(int unidadesPed, String id) throws StockAgotado, StockInsuficiente {
    int n = articulos.get(id).getExistencias();
    if (n == 0) {
      throw new StockAgotado("Stock AGOTADO para el articulo: " + articulos.get(id).getDescripcion());
    } else if (n < unidadesPed) {
      throw new StockInsuficiente("No hay Stock suficiente. Solicita " + unidadesPed + " de "
          + articulos.get(id).getDescripcion()
          + " y sólo se dispone de: " + n);
    }
  }

  // METODOS DE BACKUP Y CARGA DE DATOS (PERSISTENCIA)

  public void backup(){
    try(
      ObjectOutputStream oosArticulos = new ObjectOutputStream(new FileOutputStream("articulos.dat"));
      ObjectOutputStream oosClientes = new ObjectOutputStream(new FileOutputStream("clientes.dat"));
      ObjectOutputStream oosPedidos = new ObjectOutputStream(new FileOutputStream("pedidos.dat"))){

      // COLECCIONES ENTERAS
      oosArticulos.writeObject(articulos);
      oosClientes.writeObject(clientes);
      // PEDIDOS
      for (Pedido p: pedidos){
        oosPedidos.writeObject(p);
      }

      System.out.println("COPIA DE SEGURIDAD REALIZADA CON EXITO.");    

    }catch (FileNotFoundException e){
      System.out.println(e.toString());
    }catch (IOException e){
      System.out.println(e.toString());
    }
    
  }

  public void leerArchivos(){
    try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream("articulos.dat"));
          ObjectInputStream oisClientes = new ObjectInputStream(new FileInputStream("clientes.dat"));
          ObjectInputStream oisPedidos = new ObjectInputStream(new FileInputStream("pedidos.dat"))){

            // IMPORTAMOS LAS COLECCIONES
            articulos= (HashMap<String,Articulo>) oisArticulos.readObject();
            clientes= (HashMap<String,Cliente>) oisClientes.readObject();

            // IMPORTAMOS LOS PEDIDOS
            Pedido p=null;
            while ((p=(Pedido)oisPedidos.readObject()) !=null){
              pedidos.add(p);
            }
            System.out.println("DATOS IMPORTADOS CON EXITO.");

    }catch (FileNotFoundException e){
      System.out.println(e.toString());
    }catch (EOFException e){
      
    }catch (ClassNotFoundException | IOException e){
      System.out.println(e.toString());
    }
    // RESTABLECER LA CORRESPONDENCIA ENTRE PEDIDOS Y CLIENTES
    for (Pedido p:pedidos){
      p.setClientePedido(clientes.get(p.getClientePedido().getDni()));
    }
  }
  
  // METODOS BACKUP A ARCHIVOS CSV

  public void clientesTxtBackup(){
    try(BufferedWriter bfwClientes=new BufferedWriter(new FileWriter("clientes.csv"))){
      for (Cliente c:clientes.values()){
        bfwClientes.write(c.getDni()+","+c.getNombre()+","+c.getTelefono()+","+c.getEmail());
      }
    }catch(IOException e){
      System.out.println("ERROR ENTRADA/SALIDA:"+e);
    }
  }

  public void clientesTxtLeer(){
    File fClientes=new File("clientes.csv");
    try (Scanner scClientes=new Scanner(fClientes)){
      while (scClientes.hasNextLine()){
        String[] atributos =scClientes.nextLine().split("[,]");
        for (String s:atributos){
          System.out.println(s + "-");
        }
        System.out.println("\n");
      }

    }catch (IOException e){
      System.out.println("ERROR ENTRADA/SALIDA:"+e );
    }
  }

}

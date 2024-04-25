/*public class DescartesCodigo {
  public void menuClientes() {
    int opcion =0;
    do {
      System.out.println("\n\n\n\n\n\t\t\t\tMENU CLIENTES\n");
      System.out.println("\t\t\t\t1 - AÑADIR CLIENTE");
      System.out.println("\t\t\t\t2 - LISTAR CLIENTES");
      System.out.println("\t\t\t\t3 - MODIFICAR DATOS CONTACTO");
      System.out.println("\t\t\t\t4 - ELIMINAR CLIENTE");
      System.out.println("\n\t\t\t\t9 - SALIR");
      try {
        opcion = sc.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("NO ES UNA SELECCION VALIDA. POR FAVOR INTRODUCE UNA DE LAS OPCIONES INDICADAS");
        sc.next(); // Limpiar el buffer del scanner
        continue; // Continuar con la siguiente iteración del bucle
    }     
      
      switch (opcion) {
        case 1: {
          nuevoCliente();
          break;
        }
        case 2: {
          listaClientes();
          break;
        }
        case 3: {
          modificaCliente();
          break;
        }
        case 4: {
          bajaClientes();
          break;
        }
      }
    } while (opcion != 9);
  } */
  
  /*
   * Se sustituyó por nuevoPedido()
   * 
   * public void altaPedido() {
   * 
   * ArrayList<LineaPedido> pedidoAux = new ArrayList();
   * LocalDate hoy;
   * 
   * System.out.println("INTRODUCE EL DNI CLIENTE PARA EL PEDIDO: ");
   * String dni = sc.next();
   * 
   * System.out.println("SE VAN A INTRODUCIR LOS ARTÍCULOS 1 A 1: ");
   * System.out.println("INTRODUCE CODIGO ARTICULO (99 PARA TERMINAR): ");
   * String id = sc.next();
   * while (!id.equals("99")) {
   * System.out.println("CUANTAS UNIDADES QUIERES ?:");
   * int unidades = sc.nextInt();
   * pedidoAux.add(new LineaPedido(id, unidades));
   * articulos.get(buscarId(id)).setExistencias(articulos.get(buscarId(id)).
   * getExistencias() - unidades);
   * System.out.println("INTRODUCE CODIGO ARTICULO (99 PARA TERMINAR): ");
   * id = sc.next();
   * }
   * hoy = LocalDate.now();
   * //
   * // LLAMADA AL MÉTODO generaIdPedido() - ALTA DEL NUEVO PEDIDO EN EL ARRAYLIST
   * //
   * pedidos.add(new Pedido(generaIdPedido(dni), clientes.get(buscarDni(dni)),
   * hoy, pedidoAux));
   * }
   */

   /*public int buscarId(String id) {
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
  } */

   // DEPRECATED METODO AUXILIAR PARA VALIDAR UN DNI

  /*public static boolean validarDNI(String dni) {
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
  }*/

  /*  public void clientesTxtLeer() {
    File fClientes = new File("/clientes/clientes.csv");
    try (Scanner scClientes = new Scanner(fClientes)) {
      while (scClientes.hasNextLine()) {
        String[] atributos = scClientes.nextLine().split("[,]");
        for (String s : atributos) {
          System.out.println(s + "-");
        }
        System.out.println("\n");
      }

    } catch (IOException e) {
      System.out.println("ERROR ENTRADA/SALIDA:" + e);
    }
  } */


import javax.swing.*;
import java.util.*;
import java.io.*;

public class ArticulosAD
{
	private ArticulosDP primero, ultimo, actual, anterior, primerVenta, actualVenta, ultimaVenta;
	private PrintWriter archivoSalida;
	private BufferedReader archivoEntrada;
	
	public ArticulosAD()
	{
		String datos = "";
		try
		{
			//1) Abrir Archivo
			archivoEntrada = new BufferedReader(new FileReader("Articulos.txt"));
			
			//2) Procesar datos del archivo
			while(archivoEntrada.ready())
			{
				datos = archivoEntrada.readLine();
				crearNodo(datos);
			}
			
			//3) Cerrar Archivo
			archivoEntrada.close();
		}
		catch(IOException ioe)
		{
			System.out.println("Error: " + ioe);
		}
		
	}
	
	public String crearNodo(String datos)
	{
		String respuesta = "Nuevo nodo creado: ";
		if(primero == null)
		{
			primero = new ArticulosDP(datos);
			ultimo = primero;
			ultimo.setNext(null);
			return respuesta + datos;
		}
		else
		{
			actual = new ArticulosDP(datos);
			ultimo.setNext(actual); //Link
			ultimo = actual;
			ultimo.setNext(null);
			return respuesta + datos;
		}
	}
	
	public String crearNodoInicio(String datos)
	{
		String respuesta = "Nuevo nodo creado: ";
		if(primero == null)
		{
			primero = new ArticulosDP(datos);
			ultimo = primero;
			ultimo.setNext(null);
			return respuesta + datos;
		}
		else
		{
			actual = primero;
			primero = new ArticulosDP(datos);
			primero.setNext(actual);
			return respuesta + datos;
		}
	}
	
	public boolean vacia()
	{
		boolean vacia = false;
		
		if(primero == null)
			vacia = true;
		
		return vacia;
	}
	
	public String consultarNodos()
	{
		String datos = "";
		
		if(primero == null)
			datos = "LISTA_VACIA";
		else
		{
			actual = primero;
			
			while(actual != null)
			{
				datos = datos + actual.toString() + "\n";
				actual = actual.getNext();
			}
		}
		return datos;
	}
	
	public String consultarNodosVentas()
	{
		String datos = "";
		
		if(primerVenta == null)
			datos = "LISTA_VACIA";
		else
		{
			actualVenta = primerVenta;
			
			while(actualVenta != null)
			{
				datos = datos + actualVenta.toString() + "\n";
				actualVenta = actualVenta.getNext();
			}
		}
		return datos;
	}

	public String consultarClave(String clave)
	{
		String datos = "";
		boolean encontrado = false;
		
		if(primero == null)
			datos = "LISTA_VACIA"; 
		else
		{
			actual = primero;
			while((actual != null)&&(encontrado == false))
			{
				String id = actual.getClave();
				if(clave.equals(id))
				{
					datos = actual.toString();
					encontrado = true;
				}
				else
				{
					anterior = actual;
					actual = actual.getNext();
				}
			}
			if(encontrado == false)
					datos = "CLAVE_NO_ENCONTRADA";
		}
		return datos;
	}
	
	public String consultarVentas(String clave)
	{
		String datos = "";
		boolean encontrado = false;
		
		actualVenta = primerVenta;
		while((actualVenta != null)&&(encontrado == false))
		{
			String id = actualVenta.getClave();
			if(clave.equals(id))
			{
				datos = actualVenta.toString();
				encontrado = true;
			}
			else
				actualVenta = actualVenta.getNext();
		}
			if(encontrado == false)
					datos = "CLAVE_NO_ENCONTRADA";
		return datos;
	}
	
	public String consultarMarca(String marca)
	{
		String datos = "";
		boolean encontrado = false;
		
		if(primero == null)
			datos = "LISTA_VACIA";
		else
		{
			actual = primero;
			while(actual != null)
			{
				String brand = actual.getMarca();
				if(brand.equals(marca))
				{
					datos = datos + actual.toString() + "\n";
					encontrado = true;
				}
				anterior = actual;
				actual = actual.getNext();
			}
			if(encontrado == false)
					datos = "MARCA_NO_ENCONTRADA";
		}
		return datos;
	}
	
	public void crearNodoVentas(String datos, int venta)
	{
		String clave, nombre, marca, precio, datosVenta, resultado, strExistencia, strNuevaVenta;
		StringTokenizer st = new StringTokenizer(datos, "_");
		int existencia, nuevaVenta;
		clave = st.nextToken();
		nombre = st.nextToken();
		marca = st.nextToken();
		st.nextToken(); //Existencia
		precio = st.nextToken();
		
	    datosVenta = clave+"_"+nombre+"_"+marca+"_"+venta+"_"+precio;
	    
	    if(primerVenta == null)
		{
			primerVenta = new ArticulosDP(datosVenta);
			ultimaVenta = primerVenta;
			ultimaVenta.setNext(null);
		}
		else
		{
			//Se comprobar� que no exista una venta de ese art�culo
			resultado = consultarVentas(clave);
			
			if(resultado.equals("CLAVE_NO_ENCONTRADA"))
			{
				actualVenta = new ArticulosDP(datosVenta);
				ultimaVenta.setNext(actualVenta); //Link
				ultimaVenta = actualVenta;
				ultimaVenta.setNext(null);
			}
			else //En caso de que el nodo S� exista, se actualizar� la informaci�n de la venta.
			{
				strExistencia = actualVenta.getExistencia();
				existencia = Integer.parseInt(strExistencia);
				nuevaVenta = existencia + venta;
				strNuevaVenta = Integer.toString(nuevaVenta);
				
				actualVenta.setExistencia(strNuevaVenta);
			}
		}
	}
	
	public String venderArticulos(int cantidad, int existencia, String clave)
	{
		int nuevaCantidad = 0;
		String nuevaExistencia = "", datos = "", datosVenta = "";
		StringTokenizer st;
	
		nuevaCantidad = existencia - cantidad;
		nuevaExistencia = Integer.toString(nuevaCantidad);
		
		actual.setExistencia(nuevaExistencia);
		datos = actual.toString();
		
		crearNodoVentas(datos, cantidad);
		
		return datos;
	}
	
	public String borrarNodo()
	{ 
		if(actual == primero)
			primero = actual.getNext(); // Nodo siguiente a "Actual"; Segundo nodo
		else 
		{
			if(actual == ultimo)
			{
				ultimo = anterior; 
				ultimo.setNext(null); //Apuntar a un "null" en la siguiente direcci�n del �ltimo nodo.
			}
			else
				anterior.setNext(actual.getNext()); //Unir el nodo anterior con el siguiente del que se borra.
		}
		
		return "Nodo Borrado Exit�samente.";
	}
	
	public String datosListaArchivo()
	{
		String resultado = "";
		
		if(primero == null)
			resultado = "LISTA_VACIA";
		else
		{
			try
			{
				//1) Abrir archivo
				archivoSalida = new PrintWriter(new FileWriter("Articulos.txt"));
				
				//2) Procesar datos 
				actual = primero;
			
				while(actual != null)
				{
					archivoSalida.println(actual.toString());
					actual = actual.getNext();
				}
				
				//3) Cerrar Archivo
				archivoSalida.close();
			}
			catch(IOException ioe)
			{
				System.out.println("Error: " + ioe);
			}
		}
		return resultado;
	}
}
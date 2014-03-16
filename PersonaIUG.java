/*
 * PersonaIUG.java
 *
 *  Created on: 24/02/2014
 *      Author: Mario Jacob Garc�a Navarro. All Rights Reserved 2014.
 *		IN THIS PROGRAM WE WILL BE CREATING A LINKED LIST, ADDING AND DELETING ELEMENTS FROM IT. OTHER TASKS WILL BE DONE THROUGH.
 *		IT IS MAIN PURPOSE IS USING THE METHODS CONTAINED IN THE CLASS "LINKEDLIST".
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PersonaIUG extends Frame implements ActionListener
{
	private JTextField tfNombre, tfTelefono;
	private JButton    bCapturar, bConsultar, bConsultarNombre, bModificar, bActualizar, bCancelar, bBorrar, bCapturarInicio, bSalir;
	private JTextArea  taDatos;
	private JPanel 	   p1, p2;
	
	private String datos, nombre, telefono, resultado;
	
	//private PersonaAD lista = new PersonaAD(); 
	private PersonaADLL lista = new PersonaADLL(); //Utilizaremos una nueva clase AD, la cual hace uso de los m�todos proporcionados en LinkedList
	
	public PersonaIUG()
	{
		super("Directorio Telef�nico (Linked List)");
		
		//Inicializar los atributos
		tfNombre   = new JTextField();
		tfTelefono = new JTextField();
		taDatos    = new JTextArea(13, 40);
		p1  	   = new JPanel();
		p2  	   = new JPanel();
		datos      = "";
		nombre     = "";
		telefono   = "";
		resultado  = "";
		
		//Agregar los atributos a los paneles
		p1.setLayout(new GridLayout(7,2));
		
		p1.add(new Label("Nombre"));
		p1.add(tfNombre);
		
		p1.add(new Label("Tel�fono"));
		p1.add(tfTelefono);
			
		bCapturar = new JButton("Crear Nuevo Nodo de Persona");
		bCapturar.addActionListener(this);
		p1.add(bCapturar);
		
		bConsultar = new JButton("Consultar Nodos de Personas");
		bConsultar.addActionListener(this);
		p1.add(bConsultar);
		
		bConsultarNombre = new JButton("Consultar Personas por Nombre");
		bConsultarNombre.addActionListener(this);
		p1.add(bConsultarNombre);
		
		bModificar = new JButton("Modificar Datos");
		bModificar.addActionListener(this);
		p1.add(bModificar);
		
		bBorrar = new JButton("Borrar Nodo");
		bBorrar.addActionListener(this);
		p1.add(bBorrar);
		
		bCapturarInicio = new JButton("Crear Nuevo Nodo al Inicio");
		bCapturarInicio.addActionListener(this);
		p1.add(bCapturarInicio);
		
		bActualizar = new JButton("Actualizar Datos");
		bActualizar.addActionListener(this);
		p1.add(bActualizar);
		
		bCancelar = new JButton("Cancelar Modificaci�n");
		bCancelar.addActionListener(this);
		p1.add(bCancelar);
		
		bSalir = new JButton("Salir");
		bSalir.addActionListener(this);
		p1.add(bSalir);
				
		p2.setLayout(new FlowLayout());
		
		p2.add(p1);
		p2.add(new JScrollPane(taDatos));
		
		add(p2);
		setSize(500,500);
		setVisible(true);
		
		//Deshabilitar botones pertenecientes a "Modificar Datos"
		bActualizar.setEnabled(false);
		bCancelar.setEnabled(false);
	}
	
	public void clrFields()
	{
		tfNombre.setText("");
		tfTelefono.setText("");
	}
	
	private String obtenerDatos()
	{
		nombre = tfNombre.getText();
        telefono = tfTelefono.getText();
		
		if((nombre.equals(""))||(telefono.equals("")))
			datos = "CAMPO_VACIO";
        else
        	datos = nombre+"_"+telefono;

        return datos;
	}
	
	public void habilitarBotones(boolean value)
	{
		bCapturar.setEnabled(value); 
		bConsultar.setEnabled(value);
		bConsultarNombre.setEnabled(value);
		bModificar.setEnabled(value);
		bBorrar.setEnabled(value);
		bCapturarInicio.setEnabled(value);
		
		bActualizar.setEnabled(!value);
		bCancelar.setEnabled(!value);
	}
	
	private String consultarNombre()
	{
		boolean vacia = false;
		
		nombre = tfNombre.getText();
		
		vacia = lista.vacia();
		if(vacia == true)
			resultado = "LISTA_VACIA";
		else
		{
			if(nombre.equals(""))
				resultado = "NOMBRE_VACIO";
			else
				resultado = lista.consultarNombre(nombre);
		}
			
		return resultado;
	}
	
	private void print(String str)
	{
		if(str.equals("LISTA_VACIA")||(str.equals("NOMBRE_VACIO"))||(str.equals("NO_ENCONTRADO"))||(str.equals("CAMPO_VACIO")))
		{
			if(str.equals("LISTA_VACIA"))
				taDatos.setText("Message Log\n--------------------------------------------------------------------------------------------------------------\nLa lista no tiene nodos.");
				
			if(str.equals("NOMBRE_VACIO"))
				taDatos.setText("Message Log\n--------------------------------------------------------------------------------------------------------------\nEl campo 'Nombre' se encuentra vac�o.");
				
			if(str.equals("NO_ENCONTRADO"))
				taDatos.setText("Message Log\n--------------------------------------------------------------------------------------------------------------\nEl nombre '" + tfNombre.getText() + "' no se encontr� en la lista.");
			
			if(str.equals("CAMPO_VACIO"))
				taDatos.setText("Message Log\n--------------------------------------------------------------------------------------------------------------\nTodos los campos deben contener datos.");
		}
		else
			taDatos.setText("Message Log\n--------------------------------------------------------------------------------------------------------------\n" + str);
			
	}
			
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bCapturar)
		{
			//1) Obtener datos de los TextFields
            datos = obtenerDatos();
            
            //2) Comprobar que ninguno de los campos est� vac�o, y en caso de que lo anterior no se cumpla, evitar enviar los datos en ese estado a los nodos
			if(datos.equals("CAMPO_VACIO"))
				print("CAMPO_VACIO");
			else
			{
				//3) Enviar los datos a la clase AD a trav�s del metodo crearNodo
		        resultado = lista.crearNodo(datos);
		
		        //4) Desplegar el resultado de la operaci�n
		        print(resultado);
		        
		        //5) Quitar la informaci�n de los TextFields
		        clrFields();
			}
		}
		
		if (e.getSource() == bConsultar)
		{	
			resultado = lista.consultarNodo();
			print(resultado);
		}
		
		if (e.getSource() == bConsultarNombre)
		{	
			resultado = consultarNombre();
			print(resultado);
		}
		
		if(e.getSource() == bModificar)
		{
			StringTokenizer st;
			//1) Hacer una consulta de los datos para comprobar que exista el "Nombre" o "Registro"
			resultado = consultarNombre();

			//2) Hacer las validaciones correspondientes
			if((resultado.equals("LISTA_VACIA"))||(resultado.equals("NO_ENCONTRADO"))||(resultado.equals("NOMBRE_VACIO")))
				print(resultado);
			else
			{			
				//3) Colocar los datos del nodo en los TextFields
				st = new StringTokenizer(resultado, "_");
				
				nombre = st.nextToken();
				telefono = st.nextToken();
				tfNombre.setText(nombre);
				tfTelefono.setText(telefono);
				habilitarBotones(false);
			}
		}
		
		if(e.getSource() == bBorrar)
		{
			//1) Hacer una consulta de los datos para comprobar que exista el "Nombre" o "Registro"
			resultado = consultarNombre();

			//2) Hacer las validaciones correspondientes
			if((resultado.equals("LISTA_VACIA"))||(resultado.equals("NO_ENCONTRADO"))||(resultado.equals("NOMBRE_VACIO")))
				print(resultado);
			else
			{
				//3) Llamar al m�todo que borra a los nodos
				resultado = lista.borrarNodo();
				print(resultado);
				
				//Quitar la informaci�n de los TextFields
			    clrFields();
			}
		}
		
		if(e.getSource() == bActualizar)
		{
			datos = obtenerDatos();
			
			//Comprobar que ninguno de los campos est� vac�o, y en caso de que lo anterior no se cumpla, evitar enviar los datos en ese estado a los nodos
			if(datos.equals("CAMPO_VACIO"))
				print("CAMPO_VACIO");
			else
			{
				resultado = lista.modificarNodo(datos);
				
				//Mostrarle los cambios al usuario 
				print("Cambios Realizados: " + resultado);
				
				//Devolver los botones a su estado original
				habilitarBotones(true);
				
				//Quitar la informaci�n de los TextFields
			    clrFields();
			}
		}
		
		if(e.getSource() == bCancelar)
		{
			habilitarBotones(true);
			clrFields();
		}
		
		if (e.getSource() == bCapturarInicio)
		{
			//1) Obtener datos de los TextFields
            datos = obtenerDatos();
            
            //2) Comprobar que ninguno de los campos est� vac�o, y en caso de que lo anterior no se cumpla, evitar enviar los datos en ese estado a los nodos
			if(datos.equals("CAMPO_VACIO"))
				print("CAMPO_VACIO");
			else
			{
				//3) Enviar los datos a la clase AD a trav�s del metodo crearNodo
		        resultado = lista.crearNodoInicio(datos);
		
		        //4) Desplegar el resultado de la operaci�n
		        print(resultado);
		        
		        //5) Quitar la informaci�n de los TextFields
		        clrFields();
			}
		}
				
		if (e.getSource() == bSalir)
		{
			String resultado = lista.datosListaArchivo();
			System.out.println(resultado);
			System.exit(0);
		}
	}
	
	public static void main(String args[])
	{
		new PersonaIUG();
	}
}
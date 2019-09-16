package presentacion;

import accesoBD.AccesoBD;
import accesoBD.Examen;
import accesoBD.Resultado;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.event.*;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VentanaResultados {

	private JFrame frmResultados;
	private JTextField txtCedula;
	private JTextField txtCalificacion;
	private Connection conexion;
	private AccesoBD abd;
	private String codigoExamen;


	/**
	 * Create the application.
	 */
	public VentanaResultados(Connection con) 
	{
		this.conexion=con;
		abd= new AccesoBD();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmResultados = new JFrame();
		frmResultados.setTitle("Ingresar resultado de examen");
		frmResultados.setBounds(100, 100, 553, 279);
		frmResultados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmResultados.getContentPane().setLayout(null);
		
		JLabel lblSeleccioneExamen = new JLabel("Por favor, seleccione examen:");
		lblSeleccioneExamen.setBounds(10, 11, 222, 14);
		frmResultados.getContentPane().add(lblSeleccioneExamen);
		
		JList listaExamenes = new JList();
		listaExamenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaExamenes.setBounds(10, 35, 255, 194);
		frmResultados.getContentPane().add(listaExamenes);
		
		JLabel lblCedula = new JLabel("Cedula:");
		lblCedula.setBounds(294, 11, 79, 14);
		frmResultados.getContentPane().add(lblCedula);
		
		JLabel lblCalificacion = new JLabel("Calificacion:");
		lblCalificacion.setBounds(275, 36, 98, 14);
		frmResultados.getContentPane().add(lblCalificacion);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(383, 8, 106, 20);
		frmResultados.getContentPane().add(txtCedula);
		txtCedula.setColumns(10);
		
		txtCalificacion = new JTextField();
		txtCalificacion.setBounds(383, 33, 106, 20);
		frmResultados.getContentPane().add(txtCalificacion);
		txtCalificacion.setColumns(10);
		
		JButton btnIngresarResultado = new JButton("Ingresar Resultado");
		btnIngresarResultado.setBounds(294, 84, 171, 40);
		frmResultados.getContentPane().add(btnIngresarResultado);
		
		// Cierro la conexion a la DB cuando cierro la ventana
		frmResultados.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						try
						{
							abd.CerrarConexion(conexion);
						}
						catch(SQLException sE)
						{
							JOptionPane.showMessageDialog(frmResultados, sE.toString(), "Error", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
			);
		
		
		List<Examen> listaExamenesDevuelta=null;
		try
		{
			listaExamenesDevuelta = abd.ListarExamenes(conexion);
			if(!listaExamenesDevuelta.isEmpty())
			{
				// Rellenar tabla
				DefaultListModel modeloListaExamenes = new DefaultListModel();
				Object examen = new Object();
				for(Examen ex : listaExamenesDevuelta)
				{
					examen = ex.GetCodigo() + " - " + ex.GetCodigo() + " - " + ex.GetPeriodo();
					modeloListaExamenes.addElement(examen);
				}		
				listaExamenes.setModel(modeloListaExamenes);
			}
		}
		catch(SQLException sExc)
		{
			JOptionPane.showMessageDialog(frmResultados, sExc.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		btnIngresarResultado.addActionListener 
		(
			new ActionListener() 
			{
					public void actionPerformed (ActionEvent e) 
					{
						int ced = Integer.parseInt(txtCedula.getText());
						int calif = Integer.parseInt(txtCalificacion.getText());
						
						Resultado resu = new Resultado(ced, codigoExamen, calif);
						try
						{
							abd.IngresarResultado(conexion, resu);
							JOptionPane.showMessageDialog(frmResultados, "Resultado ingresado correctamente", "Ingreso correcto!", JOptionPane.INFORMATION_MESSAGE);
						}
						catch(SQLException sExc)
						{
							JOptionPane.showMessageDialog(frmResultados, sExc.toString(), "Error", JOptionPane.ERROR_MESSAGE);
						}
						
					};
			}
		);
		
		listaExamenes.addListSelectionListener
		(
			new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					if(!e.getValueIsAdjusting())
					{
						int indice = e.getFirstIndex();
						ListModel modeloLista = listaExamenes.getModel();
						String obj = modeloLista.getElementAt(indice).toString();
						String[] objArray = obj.split("-");
						codigoExamen=objArray[0];
					}
				}
			}
				
				
		);
		
	}
	
	public void setVisible (boolean b)
	{
		// setea si la ventana estará visible u oculta
		frmResultados.setVisible(b);
	}
	
	public void setConexion(Connection con)
	{
		this.conexion=con;
	}
	
}

package grafica;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import grafica.ControladorPersonas;
import logica.valueObjects.VOPersona;

public class VentanaPersonas
{

	private JFrame frame;
	private JTable tblListarPersonas;
	private JTextField txtCedObtenerPersona;
	private JTextField txtObtenerPNombre;
	private JTextField txtObtenerPEdad;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtEdad;
	private JTextField txtMayorEdad;
	
	private ControladorPersonas cont;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					VentanaPersonas window = new VentanaPersonas();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPersonas()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		cont = new ControladorPersonas(this);
		frame = new JFrame();
		frame.setBounds(100, 100, 908, 551);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 280, 490);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNuevaPersona = new JButton("Ingresar Persona");
		btnNuevaPersona.setBounds(68, 281, 124, 36);
		panel.add(btnNuevaPersona);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(86, 110, 86, 20);
		panel.add(txtCedula);
		txtCedula.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(86, 163, 86, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtEdad = new JTextField();
		txtEdad.setBounds(86, 214, 86, 20);
		panel.add(txtEdad);
		txtEdad.setColumns(10);
		
		JLabel label = new JLabel("Cedula");
		label.setBounds(30, 113, 46, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Nombre");
		label_1.setBounds(30, 166, 46, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Edad");
		label_2.setBounds(30, 217, 46, 14);
		panel.add(label_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(300, 11, 280, 490);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnObtenerPersona = new JButton("Obtener Persona");
		btnObtenerPersona.setBounds(80, 122, 115, 38);
		panel_1.add(btnObtenerPersona);
		
		txtCedObtenerPersona = new JTextField();
		txtCedObtenerPersona.setBounds(80, 69, 86, 20);
		panel_1.add(txtCedObtenerPersona);
		txtCedObtenerPersona.setColumns(10);
		
		JLabel lblCedula = new JLabel("Cedula");
		lblCedula.setBounds(90, 44, 46, 14);
		panel_1.add(lblCedula);
		
		txtObtenerPNombre = new JTextField();
		txtObtenerPNombre.setEnabled(false);
		txtObtenerPNombre.setBounds(90, 185, 86, 20);
		panel_1.add(txtObtenerPNombre);
		txtObtenerPNombre.setColumns(10);
		
		txtObtenerPEdad = new JTextField();
		txtObtenerPEdad.setEnabled(false);
		txtObtenerPEdad.setBounds(90, 228, 86, 20);
		panel_1.add(txtObtenerPEdad);
		txtObtenerPEdad.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(34, 188, 46, 14);
		panel_1.add(lblNombre);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setBounds(34, 231, 46, 14);
		panel_1.add(lblEdad);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(590, 11, 292, 490);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 272, 418);
		panel_2.add(scrollPane);
		
		tblListarPersonas = new JTable();
		scrollPane.setViewportView(tblListarPersonas);
		
		JLabel lblPersonasMayoresA = new JLabel("Personas mayores a :");
		lblPersonasMayoresA.setBounds(10, 23, 119, 14);
		panel_2.add(lblPersonasMayoresA);
		
		txtMayorEdad = new JTextField();
		txtMayorEdad.setBounds(119, 20, 34, 20);
		panel_2.add(txtMayorEdad);
		txtMayorEdad.setColumns(10);
		
		JButton btnListar = new JButton("Listar");
		btnListar.setBounds(177, 19, 89, 23);
		panel_2.add(btnListar);
		
		// Ingresar persona
		btnNuevaPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String nom = txtNombre.getText();
				int ced = Integer.parseInt(txtCedula.getText());
				int edad = Integer.parseInt(txtEdad.getText());
				VOPersona vop = new VOPersona(ced, nom, edad);
				cont.IngresarPersona(vop);
			}
		});
		
		// Obtener persona
		btnObtenerPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int ced = Integer.parseInt(txtCedObtenerPersona.getText());
				VOPersona vop = cont.ObtenerPersona(Integer.parseInt(txtCedObtenerPersona.getText()));
				txtObtenerPNombre.setText(vop.getNombre());
				txtObtenerPEdad.setText(Integer.toString(vop.getEdad()));
			}
		});
		
		
		
		// Listar mayores
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int edad = Integer.parseInt(txtMayorEdad.getText());
				List<VOPersona> lista = cont.ListarMayores(edad);
				
				DefaultTableModel model = new DefaultTableModel();
				model.addColumn("Cedula");
				model.addColumn("Nombre");
				model.addColumn("Edad");
				Object rowData[] = new Object[3];
				
				//Cargo los datos en la tabla.
				for(VOPersona vop : lista) 
				{
		            rowData[0] = vop.getCedula();
		            rowData[1] = vop.getNombre();
		            rowData[2] = vop.getEdad();
		            model.addRow(rowData);
		        }
				tblListarPersonas.setModel(model);				
			}
		});		
	}
	
	public void setVisible(boolean valor)
	{
		frame.setVisible(valor);
	}
	
	public void mostrarError(String res)
	{
		JOptionPane.showMessageDialog(frame, res, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarResultado(String res)
	{
		JOptionPane.showMessageDialog(frame, res, "Resultado", JOptionPane.INFORMATION_MESSAGE);
	}
}

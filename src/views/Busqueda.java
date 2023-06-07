package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.InquilinoController;
import controller.ReservaController;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.Optional;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservaController reservaController;
	private InquilinoController inquilinoController;

	/**
	 * Launch the application.s
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public Busqueda() throws SQLException {

		super("Reservas");

		this.reservaController = new ReservaController();
		this.inquilinoController = new InquilinoController();

		// apariencia JOprionPanel
		UIManager.put("Button.background", new Color(12, 138, 199));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		UIManager.put("Button.foreground", Color.white);

		JLabel lblBuscar = new JLabel("BUSCAR POR ID");

		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int numPanel= panel.getSelectedIndex();				

				switch (numPanel) {
				case 0:
					lblBuscar.setText("BUSCAR POR ID");
					limpiarTabla(modelo);
					try {
						cargarTablaReserva("");
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					break;
				case 1:					
					lblBuscar.setText("BUSCAR POR APELLIDO");
					limpiarTabla(modeloHuesped);	
					try {
						cargarTablaHuesped("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}		
				
			}
		});
		
		
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		
		// Método cargar datos de reserva
		cargarTablaReserva("");
		
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		
		// Método cargar datos de huesped
		cargarTablaHuesped("");
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int numPanel = panel.getSelectedIndex();

				switch (numPanel) {
				case 0:

					try {
						Long.valueOf(txtBuscar.getText());
					} catch (NumberFormatException e1) {
						txtBuscar.setText("");
					}

					if (!txtBuscar.getText().isEmpty()) {
						limpiarTabla(modelo);
						try {
							cargarTablaReserva(txtBuscar.getText());
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						txtBuscar.setText("");

					} else {
						JOptionPane.showMessageDialog(null, "RESERVAS - Ingresar el ID a buscar");
						txtBuscar.requestFocus();
						limpiarTabla(modelo);
						try {
							cargarTablaReserva("");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					break;
				case 1:

					if (!txtBuscar.getText().isEmpty()) {
						limpiarTabla(modeloHuesped);
						try {
							cargarTablaHuesped(txtBuscar.getText());
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						txtBuscar.setText("");

					} else {
						JOptionPane.showMessageDialog(null, "HUÉSPEDES - Ingresa el APELLIDO a buscar");
						txtBuscar.requestFocus();
						limpiarTabla(modeloHuesped);
						try {
							cargarTablaHuesped("");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					break;
				}

			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		//JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
				int numTab = panel.getSelectedIndex();
				switch (numTab) {
				case 0:
					modificarReserva();
					tbReservas.clearSelection();
					limpiarTabla(modelo);					
					try {
						cargarTablaReserva("");
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				break;					
				case 1:					
					modificarHuesped();
					tbHuespedes.clearSelection();
					limpiarTabla(modeloHuesped);	
					try {
						cargarTablaHuesped("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				break;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btnEditar.setBackground(new Color(10,91,132));				
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				btnEditar.setBackground(new Color(12, 138, 199));			    
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
				int numTab = panel.getSelectedIndex();
				switch (numTab) {
				case 0:
					eliminarReserva();
					tbReservas.clearSelection();
					limpiarTabla(modelo);					
					try {
						cargarTablaReserva("");
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				break;					
				case 1:
					eliminarHuesped();
					tbHuespedes.clearSelection();
					limpiarTabla(modeloHuesped);	
					try {
						cargarTablaHuesped("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				break;
				}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
	
		private void cargarTablaReserva(String campo) throws SQLException {
					
			var reservas = this.reservaController.listar(campo);
			
			reservas.forEach(reserva -> modelo.addRow(new Object[] {
					reserva.getId(),
					reserva.getFechaEntrada(),
					reserva.getFechaSalida(),
					reserva.getValor(),
					reserva.getFormaPago()
			}));		
		}
		
		private void cargarTablaHuesped(String campo) throws SQLException {
					
			var huespedes = this.inquilinoController.listar(campo);
						
			huespedes.forEach(huesped -> modeloHuesped.addRow(new Object[] {
					huesped.getId(),
					huesped.getNombre(),
					huesped.getApellido(),
					huesped.getFechaNacimiento(),
					huesped.getNacionalidad(),
					huesped.getTelefono(),
					huesped.getReservaId()
			}));		
		}	
		
		// limpiar tabla
		private void limpiarTabla(DefaultTableModel modelo) {
			modelo.getDataVector().clear();
		}
		
		// Seleccion de item tabla
		  private boolean tieneFilaElegida(int numTab) {		  	
			  if(numTab == 0) {
		  		    return tbReservas.getSelectedRowCount() == 0 || tbReservas.getSelectedColumnCount() == 0;			  
			  } else {
					return tbHuespedes.getSelectedRowCount() == 0 || tbHuespedes.getSelectedColumnCount() == 0;
			  }        
		  }
		  
		  // Modificar reseva
		  private void modificarReserva() {
			  if (tieneFilaElegida(0)) {
		            JOptionPane.showMessageDialog(this, "RESERVAS - Por favor, elije un item");		
		            return;
		        } 
			  
				Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
	            .ifPresentOrElse(fila -> {
	                Long id = Long.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
	                String fecha_entrada = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 1);
	                String fecha_salida = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 2);
	                Double valor = Double.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 3).toString());
	                String forma_pago =  (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);

	                var	filasModificadas = this.reservaController.modificar(fecha_entrada, fecha_salida, valor, forma_pago, id);
	                
	                JOptionPane.showMessageDialog(this, String.format("%d RESERVA - modificado con éxito!", filasModificadas));
	                
	            }, () -> JOptionPane.showMessageDialog(this, "RESERVAS - Por favor, elije un item"));
		  }
		  
		// Modificar huesped
			  private void modificarHuesped() {
				  if (tieneFilaElegida(1)) {
			            JOptionPane.showMessageDialog(this, "HUÉSPEDES - Por favor, elije un item");		
			            return;
			        } 
				  	
					Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
		            .ifPresentOrElse(fila -> {
		                Long id = Long.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
		                String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
		                String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
		                String fecha_nacimiento = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3);	                
		                String nacionalidad =  (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
		                String telefono =  (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);

		                var	filasModificadas = this.inquilinoController.modificar(nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id);
		                
		                JOptionPane.showMessageDialog(this, String.format("%d HUÉSPED - modificado con éxito!", filasModificadas));
		                
		            }, () -> JOptionPane.showMessageDialog(this, "HUÉSPEDES - Por favor, elije un item"));
			  }
		
		// Eliminar reserva
			  
			  private void eliminarReserva() {
			        if (tieneFilaElegida(0)) {
			            JOptionPane.showMessageDialog(this, "RESERVAS - Por favor, elije un item");
			            return;
			        }

			        Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
			                .ifPresentOrElse(fila -> {
			                	
			                    Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());

			                    var	cantidadEliminadaHuesped = this.inquilinoController.eliminarPorIDReserva(id);
			                    var	cantidadEliminada = this.reservaController.eliminar(id);
							
			                    modelo.removeRow(tbReservas.getSelectedRow());

			                    JOptionPane.showMessageDialog(this, cantidadEliminada + " RESERVA - eliminado con éxito! HUÉSPED " + cantidadEliminadaHuesped);
			                }, () -> JOptionPane.showMessageDialog(this, "RESERVAS - Por favor, elije un item"));
			    }
			  
	// Eliminar huésped
			  
			  private void eliminarHuesped() {
			        if (tieneFilaElegida(1)) {
			            JOptionPane.showMessageDialog(this, "HUÉSPEDES - Por favor, elije un item");
			            return;
			        }

			        Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
			                .ifPresentOrElse(fila -> {
			                    Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
			                    Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());

			                    var	cantidadEliminada = this.inquilinoController.eliminar(id);
			                    var	cantidadEliminadaReserva = this.reservaController.eliminar(idReserva);
							
			                    modeloHuesped.removeRow(tbHuespedes.getSelectedRow());

			                    JOptionPane.showMessageDialog(this, cantidadEliminada + " HUÉSPED - eliminado con éxito! RESERVA " + cantidadEliminadaReserva);
			                }, () -> JOptionPane.showMessageDialog(this, "HUÉSPEDES - Por favor, elije un item"));
			    }
}

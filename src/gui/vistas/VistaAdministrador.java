package gui.vistas;


import javax.swing.*;

import gui.MyTreeCellRenderer;
import gui.elements.TextArea;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.tree.DefaultTreeModel;
import conexionBD.ConexionVuelos;

import javax.swing.tree.DefaultMutableTreeNode;

public class VistaAdministrador extends Vista {

	private JTextArea txtrConsulta;
	private JScrollPane scrollPaneTree, scrollPaneTextArea;
	private JTree treeDB;
	private JButton btnEjecutar;
	private ConexionVuelos conn;
	private JPanel panelQuery;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaAdministrador window = new VistaAdministrador(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public VistaAdministrador(ConexionVuelos conexion) {
		this.conn = conexion;
		initialize();
		frame.setVisible(true);
	}

	@Override
	public void finalizarVista() {
		super.finalizarVista();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 772, 371);
//		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		frame.setLayout(new BorderLayout(0, 0));
//		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
//
		panelQuery = new JPanel();
		panelQuery.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelQuery.setLayout(new BorderLayout(0, 15));

		scrollPaneTextArea = new JScrollPane();
		scrollPaneTextArea.setBounds(189, 13, 553, 264);
		
		txtrConsulta = new TextArea("Ingrese su consulta", new Font("Monospaced", Font.PLAIN, 16));
		scrollPaneTextArea.setViewportView(txtrConsulta);

		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(645, 290, 97, 25);
		btnEjecutar.addActionListener(e -> ejecutarSqlCode());

		panelQuery.add(scrollPaneTextArea, BorderLayout.CENTER);
		panelQuery.add(btnEjecutar, BorderLayout.SOUTH);
		
		
		scrollPaneTree = new JScrollPane();
		scrollPaneTree.setBounds(12, 13, 145, 264);
		scrollPaneTree.setPreferredSize(new Dimension(200, 400));
		scrollPaneTree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

/*		treeDB = new JTree();
		treeDB.setRootVisible(false);
		scrollPaneTree.setViewportView(treeDB);*/
		crearTreeDB();


		frame.add(scrollPaneTree, BorderLayout.WEST);
		frame.add(panelQuery, BorderLayout.CENTER);
	}
	
	private void crearTreeDB() {
		treeDB = new JTree();
		treeDB.setRootVisible(false);
		treeDB.setCellRenderer(new MyTreeCellRenderer());
		try {
			treeDB.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("DB") {
					{ //utilizamos un inicializador anónimo.
						if(conn.getConnection().isValid(10)) {
							Statement stmtAtribs, stmtTables;
							
							stmtTables = conn.getConnection().createStatement();
							stmtAtribs = conn.getConnection().createStatement();
							
							ResultSet rsAtribs, rsTables = stmtTables.executeQuery("SHOW TABLES");
							
							String table;
							DefaultMutableTreeNode tableNode;
							
							while(rsTables.next()) {
								table = rsTables.getString(1);
								rsAtribs = stmtAtribs.executeQuery("DESCRIBE " + table);
								
								tableNode = new DefaultMutableTreeNode(table);
								while(rsAtribs.next()) {
									tableNode.add(new DefaultMutableTreeNode(rsAtribs.getString(1)));
								}
								
								add(tableNode);
								rsAtribs.close();
								
							}
							
							rsTables.close();
							stmtAtribs.close();
							stmtTables.close();
						}
					}
				}
			));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scrollPaneTree.setViewportView(treeDB);
	}
	
	private void ejecutarSqlCode() {
		try {
			if(conn.getConnection().isValid(10)){
				if(conn.isSelect(txtrConsulta.getText().trim())){
					conn.setSelectSql(txtrConsulta.getText().trim());

					conn.refresh();

					conn.setPreferredSize(new Dimension(conn.getPreferredSize().width, 200));
					SwingUtilities.updateComponentTreeUI(frame);
					frame.add(conn, BorderLayout.SOUTH);
				} else {
					Statement st = conn.getConnection().createStatement();
					st.execute(txtrConsulta.getText().trim());
					st.close();
					frame.getContentPane().remove(conn);
					SwingUtilities.updateComponentTreeUI(frame);
				}

			}
		} catch (SQLException e) {
			//acá poner diálogo con lo que me dice el servidor
			e.printStackTrace();
		}
	}
}

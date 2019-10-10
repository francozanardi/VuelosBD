package gui.vistas;


import javax.swing.JTree;
import gui.MyTreeCellRenderer;
import gui.elements.TextArea;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import conexionBD.Conexion;
import quick.dbtable.DBTable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;

public class VistaAdministrador extends Vista {

	private JTextArea txtrConsulta;
	private JScrollPane scrollPane;
	private JTree treeDB;
	private JButton btnEjecutar;
	private Conexion conn;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VistaAdministrador window = new VistaAdministrador(null);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public VistaAdministrador(Conexion conexion) {
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
		frame.setBounds(100, 100, 772, 371);
		frame.setLayout(null);
		
		retrocederAlCerrarVista();
		
		txtrConsulta = new TextArea("Ingrese su consulta", new Font("Monospaced", Font.PLAIN, 16));
		
		txtrConsulta.setBounds(189, 13, 553, 264);
		frame.add(txtrConsulta);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 145, 264);
		frame.add(scrollPane);
		
		crearTreeDB();

		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(645, 290, 97, 25);
		btnEjecutar.addActionListener(e -> ejecutarSqlCode());
		
		frame.add(btnEjecutar);
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
		
		scrollPane.setViewportView(treeDB);
	}
	
	private void ejecutarSqlCode() {
		try {
			if(conn.getConnection().isValid(10)){
				if(conn.isQuery()){
					conn.setSelectSql(txtrConsulta.getText().trim());

					conn.refresh();

					conn.setLocation(12, btnEjecutar.getY()+btnEjecutar.getHeight()+30);
					conn.setAutomaticSize(txtrConsulta.getLocation().x+txtrConsulta.getWidth()-conn.getX(), 200);
					frame.add(conn);

					frame.setSize(Math.max(frame.getWidth(),conn.getWidth()+12), conn.getY()+conn.getHeight()+80);
				} else {
					Statement st = conn.getConnection().createStatement();
					st.execute(txtrConsulta.getText().trim());
					st.close();

					frame.setSize(txtrConsulta.getWidth()+txtrConsulta.getX()+70, btnEjecutar.getY()+btnEjecutar.getHeight()+70);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

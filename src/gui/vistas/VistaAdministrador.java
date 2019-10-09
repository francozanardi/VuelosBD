package gui.vistas;

import java.awt.EventQueue;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import gui.MyTreeCellRenderer;
import gui.elements.TextArea;

import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class VistaAdministrador {

	private JFrame frame;
	private JTextArea txtrConsulta;
	private JScrollPane scrollPane;
	private JTree treeDB;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaAdministrador window = new VistaAdministrador();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VistaAdministrador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 772, 346);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtrConsulta = new TextArea("Ingrese su consulta", new Font("Monospaced", Font.PLAIN, 16));
		
		txtrConsulta.setBounds(189, 13, 553, 264);
		frame.getContentPane().add(txtrConsulta);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 145, 264);
		frame.getContentPane().add(scrollPane);
		
		treeDB = new JTree();
		treeDB.setRootVisible(false);
		treeDB.setCellRenderer(new MyTreeCellRenderer());
		treeDB.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("DBNombre") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("Tabla1");
						node_1.add(new DefaultMutableTreeNode("atrib1"));
						node_1.add(new DefaultMutableTreeNode("atrib2"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tabla2");
						node_1.add(new DefaultMutableTreeNode("atrib3"));
					add(node_1);
				}
			}
		));
		scrollPane.setViewportView(treeDB);
	}
}

package gui;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	
	public MyTreeCellRenderer() {
		super();
		super.setOpenIcon(new ImageIcon("icons/table-grid.png"));
		super.setClosedIcon(new ImageIcon("icons/table-grid.png"));
		super.setLeafIcon(new ImageIcon("icons/grid_1.png"));
	}

}

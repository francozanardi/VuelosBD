package gui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	
	public MyTreeCellRenderer() {
		super();
		try {
			super.setOpenIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/table-grid.png"))));
			super.setClosedIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/table-grid.png"))));
			super.setLeafIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/grid_1.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

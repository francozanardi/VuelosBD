package conexionBD;

import java.sql.SQLException;

import javax.swing.JPanel;

import quick.dbtable.Column;
import quick.dbtable.DBTable;

public class Conexion {
	protected DBTable table;
	private static final String SERVIDOR = "localhost";
	private static final int PUERTO = 3306;
	private static final String DB_NAME = "vuelos";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String SERVER_TIME_ZONE = "America/Argentina/Buenos_Aires";
	
	public Conexion(String user, String password) throws ClassNotFoundException, SQLException {
		table = new DBTable();
		table.connectDatabase(DRIVER, "dbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + DB_NAME + "?serverTimeZone=" + SERVER_TIME_ZONE, user, password);
		table.setEditable(false);
	}
	
	public JPanel getTablePanel() {
		return table;
	}
	
//	public void setEditable(boolean val) {
//		table.setEditable(val);
//	}

	public void close() throws SQLException {
		table.close();
	}
	
	public void refresh() throws SQLException {
		table.refresh();
	}
	
	public void setSelectSql(String input) {
		table.setSelectSql(input);
	}
	
	public void createColumnModelFromQuery() throws SQLException {
		table.createColumnModelFromQuery();
	}
	
	public Column getColumn(int i) {
		return table.getColumn(i);
	}
	
	public int getColumnCount() {
		return table.getColumnCount();
	}
}

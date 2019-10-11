package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import quick.dbtable.DBTable;

public class ConexionVuelos extends DBTable {
	protected Connection conn;
	private static final String SERVIDOR = "localhost";
	private static final int PUERTO = 3306;
	private static final String DB_NAME = "vuelos";
	private static final String SERVER_TIME_ZONE = "America/Argentina/Buenos_Aires";

	public ConexionVuelos(DBTable db) {
		setConnection(db.getConnection());
	}
	
	public ConexionVuelos(String user, String password) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + DB_NAME + "?serverTimezone=" + SERVER_TIME_ZONE, user, password);
		super.setConnection(conn);
		super.setEditable(false);
	}


	public boolean isSelect(String sqlCode){
		return sqlCode.trim().toLowerCase().startsWith("select");
	}

	public boolean isConnectionValid() throws SQLException {
		return conn.isValid(15);
	}

}

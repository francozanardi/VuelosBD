package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JPanel;

import com.mysql.cj.Messages;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.util.StringUtils;
import quick.dbtable.Column;
import quick.dbtable.DBTable;

/*
 * Si tenemos tiempo, lo que más me gustaría acá sería usar una JTable y crear nosotros la tabla cuando el usuario envíe el mensaje.
 * Siento que quedaría mucho mejor. Además de nunca devolver la conexión actual, sino que hacer una especie de adapter solo con aquellos 
 * métodos necesarios de connection.
 */

public class ConexionVuelos extends DBTable {
//	private String sqlCode;
	protected Connection conn;
	private static final String SERVIDOR = "localhost";
	private static final int PUERTO = 3306;
	private static final String DB_NAME = "vuelos";
//	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String SERVER_TIME_ZONE = "America/Argentina/Buenos_Aires";
	
	public ConexionVuelos(String user, String password) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + DB_NAME + "?serverTimezone=" + SERVER_TIME_ZONE, user, password);
		super.setConnection(conn);
		super.setEditable(false);
	}


	public boolean isSelect(String sqlCode){
		return sqlCode.trim().toLowerCase().startsWith("select");
	}


}

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

public class Conexion extends DBTable {
	private String sqlCode;
	protected Connection conn;
	private static final String SERVIDOR = "localhost";
	private static final int PUERTO = 3306;
	private static final String DB_NAME = "vuelos";
//	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String SERVER_TIME_ZONE = "America/Argentina/Buenos_Aires";
	
	public Conexion(String user, String password) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + DB_NAME + "?serverTimezone=" + SERVER_TIME_ZONE, user, password);
		super.setConnection(conn);
		super.setEditable(false);
	}

	@Override
	public void setSelectSql(String input) {
		super.setSelectSql(input);
		sqlCode = input.trim().toLowerCase();
	}

	public void setAutomaticSize(int w, int h){
		for(int i = 0; i < getColumnCount(); i++){
			getColumn(i).setPreferredWidth(w/getColumnCount());
		}

		h = Math.min(h, (getRowCount()+2)*getTable().getRowHeight(0));

		setSize(w, h);
	}

	public boolean generateTable(){
		return sqlCode.startsWith("select");
	}

	protected static int findStartOfStatement(String sql) {
		int statementStartPos = 0;
		if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
			statementStartPos = sql.indexOf("*/");
			if (statementStartPos == -1) {
				statementStartPos = 0;
			} else {
				statementStartPos += 2;
			}
		} else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) {
			statementStartPos = sql.indexOf(10);
			if (statementStartPos == -1) {
				statementStartPos = sql.indexOf(13);
				if (statementStartPos == -1) {
					statementStartPos = 0;
				}
			}
		}

		return statementStartPos;
	}

	protected boolean checkForDml(String sql, char firstStatementChar) {
		if (firstStatementChar == 'I' || firstStatementChar == 'U' || firstStatementChar == 'D' || firstStatementChar == 'A' || firstStatementChar == 'C' || firstStatementChar == 'T' || firstStatementChar == 'R') {
			String noCommentSql = StringUtils.stripComments(sql, "'\"", "'\"", true, false, true, true);
			if (StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "INSERT") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DELETE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DROP") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "CREATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "ALTER") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "TRUNCATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "RENAME")) {
				return false;
			}
		}

		return true;

	}

	public boolean isQuery(){
		char firstStatementChar = StringUtils.firstAlphaCharUc(sqlCode, findStartOfStatement(sqlCode));
		return checkForDml(sqlCode, firstStatementChar);
	}

}

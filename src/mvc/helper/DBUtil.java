package mvc.helper;

import java.sql.Connection;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DBUtil {
	
	public static String createTable="(SELECT attname,format_type(atttypid, atttypmod) AS data_type,'~~pk' ispk\n" + 
			"FROM   pg_attribute\n" + 
			"WHERE  attrelid = '?'::regclass\n" + 
			"AND    attnum > 0\n" + 
			"AND    NOT attisdropped\n" + 
			"ORDER  BY attnum)\n" + 
			"\n" + 
			"UNION\n" + 
			"select '-1' as attname,'-1' as data_type, '~pk' as ispk\n" + 
			"UNION\n" + 
			"\n" + 
			"(SELECT a.attname, format_type(a.atttypid, a.atttypmod) AS data_type,'pk' ispk\n" + 
			"FROM   pg_index i\n" + 
			"JOIN   pg_attribute a ON a.attrelid = i.indrelid\n" + 
			"                     AND a.attnum = ANY(i.indkey)\n" + 
			"WHERE  i.indrelid = '?'::regclass\n" + 
			"AND    i.indisprimary\n" + 
			"ORDER  BY attnum)\n" + 
			"ORDER  BY ispk DESC;";
	
	public static Logger log = Logger.getLogger(DBUtil.class.getName());
	private static DataSource dataSource;
	
	static {
		try {
			InitialContext cxt = new InitialContext();
			if (cxt == null) {
				throw new Exception("Bad context!");
			}

			dataSource = (DataSource) cxt
					.lookup("java:/comp/env/jdbc/postgres");

			if (dataSource == null) {
				throw new Exception("Data source not found!");
			}
		} catch (Exception e) {
			log.info("error:" + e.toString());
			throw new ExceptionInInitializerError(
					"Not found in JNDI:" + e.toString());
		}
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
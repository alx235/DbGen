package mvc.helper;

public class SqlUtil {

	public static String metaTable = "(SELECT attname,format_type(atttypid, atttypmod) AS data_type,'~~pk' ispk\n"
			+ "FROM   pg_attribute\n"
			+ "WHERE  attrelid = regexp_replace(?, '''', '', 'g')::regclass\n"
			+ "AND    attnum > 0\n"
			+ "AND    NOT attisdropped\n"
			+ "ORDER  BY attnum)\n"
			+ "\n"
			+ "UNION\n"
			+ "select '-1' as attname,'-1' as data_type, '~pk' as ispk\n"
			+ "UNION\n"
			+ "\n"
			+ "(SELECT a.attname, 'pk' AS data_type,'pk' ispk\n"
			+ "FROM   pg_index i\n"
			+ "JOIN   pg_attribute a ON a.attrelid = i.indrelid\n"
			+ "                     AND a.attnum = ANY(i.indkey)\n"
			+ "WHERE  i.indrelid = regexp_replace(?, '''', '', 'g')::regclass\n"
			+ "AND    i.indisprimary\n"
			+ "ORDER  BY attnum)\n" + "ORDER  BY ispk DESC;";
	
	public static String metaTableWithOutAtr = "(SELECT attname,'~~pk' ispk\n"
			+ "FROM   pg_attribute\n"
			+ "WHERE  attrelid = regexp_replace(?, '''', '', 'g')::regclass\n"
			+ "AND    attnum > 0\n"
			+ "AND    NOT attisdropped\n"
			+ "ORDER  BY attnum)\n"
			+ "\n"
			+ "UNION\n"
			+ "select '-1' as attname, '~pk' as ispk\n"
			+ "UNION\n"
			+ "\n"
			+ "(SELECT a.attname,'pk' ispk\n"
			+ "FROM   pg_index i\n"
			+ "JOIN   pg_attribute a ON a.attrelid = i.indrelid\n"
			+ "                     AND a.attnum = ANY(i.indkey)\n"
			+ "WHERE  i.indrelid = regexp_replace(?, '''', '', 'g')::regclass\n"
			+ "AND    i.indisprimary\n"
			+ "ORDER  BY attnum)\n" + "ORDER  BY ispk DESC;";
}

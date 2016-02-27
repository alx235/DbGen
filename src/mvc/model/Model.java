package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mvc.helper.DBUtil;
import mvc.helper.SqlUtil;

public class Model {

	public static List<String> Query(/* String query, */String strObj,
			boolean addType) throws SQLException {

		List<String> result = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String tmp_;
		try {
			connection = DBUtil.getConnection();

			if (addType)
				statement = connection.prepareStatement(SqlUtil.metaTable);
			else
				statement = connection.prepareStatement(SqlUtil.metaTableWithOutAtr);
			statement.setString(1, strObj);
			statement.setString(2, strObj);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tmp_ = resultSet.getString(1);
				if (tmp_.equalsIgnoreCase("-1")) {//add delimiter
					result.add(tmp_);
					addType = false;//next val only pk fields, so we skip type
					continue;
				}
				if (addType)
					result.add(tmp_ + " " + resultSet.getString(2));
				else
					result.add(tmp_);
			}
		} catch (SQLException e) {
			DBUtil.log.info("error:" + e.toString());
		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}
		return result;
	}
}

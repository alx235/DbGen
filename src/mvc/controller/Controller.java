package mvc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.Model;
import mvc.helper.DBUtil;

@WebServlet(urlPatterns = { "/Gen/*" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			response.getWriter().println(
					GenQuery(request.getPathInfo(),
							request.getParameter("sch_name"),
							request.getParameter("tbl_name")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String GenQuery(String path, String sch_name, String tbl_name)
			throws SQLException {
		String query = "", fullname = sch_name + "." + tbl_name;
		String tmp_ = "";// for indexed store
		boolean isnt_pk = true;// fill query standard fields before delimiter
		List<String> result = null;
		int count_ = 0;// result count
		boolean addtype_ = true; // Model.Query 2d par.
		String pr_key_ = ""; // query's pk part
		
		boolean isselect = false, isupdate = false, iscreate = false;// flags...

		if (path.equalsIgnoreCase("/Create")) {//Parse
			pr_key_ = "PRIMARY KEY (";
			query = "Create table " + fullname + "(\n";
			iscreate = true;
		} else if (path.equalsIgnoreCase("/Select")) {
			query = "select ";
			addtype_ = false;
			isselect = true;
		} else if (path.equalsIgnoreCase("/Update")) {
			query = "update " + fullname + " set ";
			addtype_ = false;
			isupdate = true;
		} else
			return "Url parser NotFound";

		try {//make query
			result = Model.Query("\'" + fullname + "\'", addtype_);
		} catch (SQLException ignore) {
		}
		count_ = result.size();
		if (count_ == 0)
			return "Not Found";

		for (int i = 0, n = count_; i < n; ++i) {
			tmp_ = result.get(i);
			if ((isnt_pk) && (tmp_.contains("-1"))) {// ignore delimiter,
														// check before
														// false
				isnt_pk = false;
				continue;
			}
			if (isnt_pk) {
				if (iscreate)
					query += tmp_ + ",\n";
				if (isselect)
					query += tmp_ + " ";
				if (isupdate)
					query += tmp_ + "=? ";				
			}
			else// case false: add pk fields to query
				if (isselect||isupdate)//add pk_f=? and ...
					pr_key_ += tmp_ + "=? and ";
				else
					pr_key_ += tmp_ + ",";// PK(pk_f,...)
		}
		//merge ~pk and pk query's parts
		if (iscreate)
			query += pr_key_.substring(0, pr_key_.length() - 1) + ")\n);";
		if (isselect||isupdate)
			query = 
				query.substring(0, query.length() - 1)
					+ ((isselect)? "\nfrom " + fullname: "")
					 + "\nwhere "
					 	+ pr_key_.substring(0, pr_key_.length() - 5);

		return query;
	}

}

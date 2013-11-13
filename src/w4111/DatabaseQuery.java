package w4111;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import java.sql.*;

import oracle.jdbc.*;
import oracle.jdbc.pool.*;

public class DatabaseQuery {
	private static Statement s = null;
	private static Connection conn = null; 
	protected DatabaseQuery() {}
	public static ResultSet getResultSet(String query) throws SQLException {
		if (s == null) {
			String dbUser = "cz2276"; // enter your username here
			String dbPassword = "coms4111"; // enter your password here
			try {
				OracleDataSource ods = new oracle.jdbc.pool.OracleDataSource();
				ods.setURL("jdbc:oracle:thin:@//w4111b.cs.columbia.edu:1521/ADB");
				ods.setUser(dbUser);
				ods.setPassword(dbPassword);
				conn = ods.getConnection();
				s = conn.createStatement();
			} catch(Exception e) {
				System.out.println("The database could not be accessed.<br>");
				System.out.println("More information is available as follows:<br>");
			}
		}
		return s.executeQuery(query);
	}
	public static void shutDown() throws SQLException {
		s.close();
		conn.close();
	}
}

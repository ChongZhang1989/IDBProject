package w4111;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("username") == null) {
			out.println("You haven't signed in yet!");
		} else {
			out.println("Welcome " + session.getAttribute("username"));
			out.println("<a href = \"SignOut\">Sign Out</a>");
		}
		String searchQuery = request.getParameter("SearchContent");
		boolean book = request.getParameter("book") != null;
		boolean audio = request.getParameter("audio") != null;
		boolean title = request.getParameter("title") != null;
		boolean author = request.getParameter("author") != null;
		boolean publication = request.getParameter("publication") != null;
		if (searchQuery == null) searchQuery = "";
		if (!book && !audio) book = audio = true;
		ResultSet r = null;
		if (book) {
			StringBuffer sb = new StringBuffer();
			if (title)
				sb.append("title like '%" + searchQuery + "%'");
			if (author) {
				if (sb.length() > 0) sb.append("and ");
				sb.append("author like '%" + searchQuery + "%'");
			}
			if (publication) {
				if (sb.length() > 0) sb.append("and ");
				sb.append("publication like '%" + searchQuery + "%'");
			}
			if (!title && !author && !publication)
				sb.append("title like '%%'");
			String query = "select * from books where " + sb.toString();
			
			try {
				r = DatabaseQuery.getResultSet(query);
			} catch(Exception e) {
				out.println(e);
			}
		} else if (audio) {
			StringBuffer sb = new StringBuffer();
			if (title)
				sb.append("title like '%" + searchQuery + "%'");
			if (author) {
				if (sb.length() > 0) sb.append("and ");
				sb.append("author like '%" + searchQuery + "%'");
			}
			if (publication) {
				if (sb.length() > 0) sb.append("and ");
				sb.append("publication like '%" + searchQuery + "%'");
			}
			if (!title && !author && !publication)
				sb.append("title like '%%'");
			String query = "select * from audios where " + sb.toString();
			
			try {
				r = DatabaseQuery.getResultSet(query);
			} catch(Exception e) {
				out.println(e);
			}
		}
		
		//example of how to use ResultSet r
		try {
			while (r.next()) {
				out.println(r.getString("bid"));
				out.println(r.getString("title"));
				out.println(r.getString("author"));
				out.println(r.getString("publication"));
			}
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

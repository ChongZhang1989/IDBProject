package w4111;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
		
//		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//		Date date = new Date();
//		out.println(new Date(df.format(date)));
//		
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("username") == null) {
			out.println("You haven't signed in yet!");
		} else {
			out.println("Welcome " + session.getAttribute("name"));
			out.println("<a href = \"PersonalInfo\">Personal Information</a>");
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
			out.println("<form action=BorrowBook method=get>");
			out.println("<table>");
			try {
				while (r.next()) {
					out.println("<tr>");
					out.println("<td>");
					out.println(r.getString("bid"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("title"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("author"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("publication"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("quantity"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("remaining"));
					out.println("</td>");
					out.println("<td>");
					if (Integer.parseInt(r.getString("remaining")) > 0)
						out.println("<input type=\"checkbox\" name=\"bid\"" + "value=\"" + r.getString("bid") + "\">");
					out.println("</td>");
					out.println("</tr>");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("</table>");
		}
		if (audio) {
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
			out.println("<table>");
			try {
				while (r.next()) {
					out.println("<tr>");
					out.println("<td>");
					out.println(r.getString("aid"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("title"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("author"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("publication"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("quantity"));
					out.println("</td>");
					out.println("<td>");
					out.println(r.getString("remaining"));
					out.println("</td>");
					out.println("<td>");
					if (Integer.parseInt(r.getString("remaining")) > 0)
						out.println("<input type=\"checkbox\" name=\"aid\"" + "value=\"" + r.getString("aid") + "\">");
					out.println("</td>");
					out.println("</tr>");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("</table>");
		}
		out.println("Please input the ID of the librarian who serves you:<br>");
		out.println("<input type='text' name='lid'><br>");
		out.println("<input type=submit name=Borrow value=Borrow>");
		out.println("</form>");
		try {
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

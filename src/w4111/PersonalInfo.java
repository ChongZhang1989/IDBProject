package w4111;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PersonalInfo
 */
public class PersonalInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonalInfo() {
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
			response.sendRedirect("start.html");
		}
		ResultSet r = null;
		out.println("<form action = 'Return' method = 'post'>");
		out.println("<table>");
		try {
			r = DatabaseQuery.getResultSet("select * from borrowbook t1, books t2 where t1.bid = t2.bid and t1.id = " + session.getAttribute("username"));
			while (r.next()) {
				out.println("<tr>");
				out.println("<td>");
				out.println(r.getString("title"));
				out.println("</td>");
				out.println("<td>");
				out.println("<input type = 'checkbox' name = 'bid' value = '" + r.getString("bid") + "'>");
				out.println("</td>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</table>");
		out.println("<table>");
		try {
			r = DatabaseQuery.getResultSet("select * from borrowaudio t1, audios t2 where t1.aid = t2.aid and t1.id = " + session.getAttribute("username"));
			while (r.next()) {
				out.println("<tr>");
				out.println("<td>");
				out.println(r.getString("title"));
				out.println("</td>");
				out.println("<td>");
				out.println("<input type = 'checkbox' name = 'aid' value = '" + r.getString("aid") + "'>");
				out.println("</td>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</table>");
		out.println("<input type = 'submit' value = 'Return'>");
		out.println("</form>");
		out.println("<form action = 'PayFees' method = 'post'>");
		out.println("<table>");
		try {
			r = DatabaseQuery.getResultSet("select * from fees where id = " + session.getAttribute("username"));
			while (r.next()) {
				out.println("<tr>");
				out.println("<td>");
				out.println(r.getString("title"));
				out.println("</td>");
				out.println("<td>");
				out.println(r.getString("amount"));
				out.println("</td>");
				out.println("<td>");
				out.println("<input type = 'checkbox' name = 'fid' value = '" + r.getString("fid") + "'>");
				out.println("</td>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</table>");
		out.println("<input type = 'submit' value = 'Pay'>");
		out.println("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

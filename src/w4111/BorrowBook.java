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
 * Servlet implementation class BorrowBook
 */
public class BorrowBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ResultSet r = null;
		String bid = request.getParameter("bid");
		if (session != null && session.getAttribute("username") != null) {
			try {
				r = DatabaseQuery.getResultSet("select * from books where bid = " + bid);
				if (r.next()) {
					//DatabaseQuery.getResultSet("insert * ");
				} else {
					out.println("Cannot find this book!");
					out.println("<a href = \"start.html\">Start Page</a>");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			out.println("You haven't signed in yet!");
			out.println("<a href = \"signin.html\">Sign In</a>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

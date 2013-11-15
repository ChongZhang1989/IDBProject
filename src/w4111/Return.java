package w4111;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Return
 */
public class Return extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Return() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("username") == null) {
			out.println("You haven't signed in yet!");
			response.sendRedirect("start.html");
		}
		String [] bid = request.getParameterValues("bid");
		String [] aid = request.getParameterValues("aid");
		if (bid == null) bid = new String [0];
		if (aid == null) aid = new String [0];
		ResultSet r = null;
		for (int i = 0; i < bid.length; ++i) {
			try {
				r = DatabaseQuery.getResultSet("select * from borrowbook where bid = " + bid[i] + " and id = " + session.getAttribute("username"));
				r.next();
				Date today = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				today = new Date(df.format(today));
				df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date expire = df.parse(r.getString("expire"));
				df = new SimpleDateFormat("dd-MMM-yyyy");
				expire = new Date(df.format(expire));
				if (today.after(expire)) {
					int diff = Math.abs(today.compareTo(expire));
					r = DatabaseQuery.getResultSet("select max(id) as max_id from fees");
					Integer maxid = 1;
					if (r.next()) {
						maxid = Integer.parseInt(r.getString("max_id")) + 1;
					}
					
					DatabaseQuery.getResultSet("insert fees values("+ maxid + "," +
																	session.getAttribute("username") + "," +
																	"Fine," +
																	diff + ")");
				}
				DatabaseQuery.getResultSet("delete from borrowbook where id = " + session.getAttribute("username") +
											"and bid = " + bid[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < aid.length; ++i) {
			try {
				r = DatabaseQuery.getResultSet("select * from borrowaudio where aid = " + aid[i] + " and id = " + session.getAttribute("username"));
				r.next();
				Date today = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				today = new Date(df.format(today));
				df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date expire = df.parse(r.getString("expire"));
				df = new SimpleDateFormat("dd-MMM-yyyy");
				expire = new Date(df.format(expire));
				if (today.after(expire)) {
					int diff = Math.abs(today.compareTo(expire));
					r = DatabaseQuery.getResultSet("select max(id) as max_id from fees");
					Integer maxid = 1;
					if (r.next()) {
						maxid = Integer.parseInt(r.getString("max_id")) + 1;
					}
					
					DatabaseQuery.getResultSet("insert fees values("+ maxid + "," +
																	session.getAttribute("username") + "," +
																	"Fine," +
																	diff + ")");
				}
				DatabaseQuery.getResultSet("delete from borrowaudio where id = " + session.getAttribute("username") +
											"and aid = " + aid[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("start.html");
		}
	}

}

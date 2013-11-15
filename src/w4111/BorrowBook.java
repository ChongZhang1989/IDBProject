package w4111;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		String [] bid = request.getParameterValues("bid");
		String [] aid = request.getParameterValues("aid");
		if (bid == null) bid = new String [0];
		if (aid == null) aid = new String [0];
		String lid = request.getParameter("lid");
		//test whether the request is legal
		try {
			r = DatabaseQuery.getResultSet("select * from librarians where lid = " + lid);
			if (!r.next()) {
				response.sendRedirect("start.html");
			}
			r = DatabaseQuery.getResultSet("select count(*) as cnt from borrowbook where id = " + session.getAttribute("username"));
			r.next();
			int bookcnt = Integer.parseInt(r.getString("cnt")) + (bid == null ? 0 : bid.length) + (aid == null ? 0 : aid.length);
			r = DatabaseQuery.getResultSet("select limit from membership m, users u where m.mid = u.mid and u.id = " +
										session.getAttribute("username"));
			r.next();
			int limit = Integer.parseInt(r.getString("limit"));
			if (bookcnt > limit) {
				response.sendRedirect("start.html");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date today = new Date();
		Date expire = new Date();
		expire.setMonth(today.getMonth() + 1);
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		df.applyPattern("dd-MMM-yyyy");
		
		if (session != null && session.getAttribute("username") != null) {
			try {
				for (int i = 0; i < bid.length; ++i) {
					r = DatabaseQuery.getResultSet("select remaining from books b2 where b2.bid = "+bid[i]);
					r.next();
					String rm = r.getString("remaining");
					Integer tmp = (Integer.parseInt(rm) - 1);
					rm = tmp.toString();
					DatabaseQuery.getResultSet("update books b1 set remaining=" + rm
									+ "where b1.bid="+bid[i]);
//					r = DatabaseQuery.getResultSet("select max(id) as max_id from borrowbook");
//					r.next();
//					String maxid = r.getString("max_id");
//					tmp = (Integer.parseInt(maxid) + 1);
//					maxid = tmp.toString();
					DatabaseQuery.getResultSet("insert into borrowbook values("
							+ session.getAttribute("username") + "," + bid[i] + "," + lid + ",'" +
							df.format(today) + "','" + df.format(expire) +"')");
				}
				for (int i = 0; i < aid.length; ++i) {
					r = DatabaseQuery.getResultSet("select remaining from audios a2 where a2.aid = "+aid[i]);
					r.next();
					String rm = r.getString("remaining");
					Integer tmp = (Integer.parseInt(rm) - 1);
					rm = tmp.toString();
					DatabaseQuery.getResultSet("update audios a1 set remaining=" + rm
									+ "where a1.aid="+aid[i]);
					DatabaseQuery.getResultSet("insert into borrowaudio values("
							+ session.getAttribute("username") + "," + aid[i] + "," + lid + ",'" +
							df.format(today) + "','" + df.format(expire) +"')");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("<a href = \"start.html\">Back</a>");
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

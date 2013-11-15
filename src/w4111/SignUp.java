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
 * Servlet implementation class SignUp
 */
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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
		HttpSession session = request.getSession(true);
		ResultSet rs = null;
		
		String username = request.getParameter("username");
		String password = request.getParameter("userpass"); 
		String password1 = request.getParameter("userpass1");
		String userssn = request.getParameter("userssn");
		
		if (username.equals("") || password.equals("") || password1.equals("")
			|| userssn.equals("")) {
			Integer registerError = Integer.valueOf(1);		
			session.setAttribute("registerError", registerError);
			out.println("Please fill up the form.");
			response.sendRedirect("start.html");
		} else if (!password.equals(password1)) {
			Integer registerError = Integer.valueOf(2);
			session.setAttribute("registerError", registerError);
			out.println("Your password and confirm password do not match.");
			response.sendRedirect("start.html");
		}
		
		try {
			rs = DatabaseQuery.getResultSet("select * from users");
			while(rs.next()) {
				if(userssn.equals(rs.getString("ssn"))){
					Integer registerError = Integer.valueOf(3);		
					session.setAttribute("registerError", registerError);
					out.println("SSN already existed");
					response.sendRedirect("start.html");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Integer max_id = null;
		try {
			rs = DatabaseQuery.getResultSet("select max(id) from users");
			if (rs.next()) {
				max_id = rs.getInt(1);
			}
			Integer user_ssn = Integer.parseInt(userssn);
			rs = DatabaseQuery.getResultSet("insert into users (id, mid, ssn, username, password)"  +  "values (" + (max_id + 1) + ", " + 1 + ", " + user_ssn + ", '" + username + "', '"+ password + "')");
			
			out.println("You are successfully registered.");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			out.println(e1);
			out.close();
		}
		
	}

}

package sk.michacu.zmenaren;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "indexServlet", urlPatterns = {"/","","/home"})
public class IndexServlet extends HttpServlet {
    private RequestDispatcher jsp;
    private final Utilities utilities = new Utilities();

    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        utilities.fillInitList();
        jsp = context.getRequestDispatcher("/index.jsp");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp.forward(req, resp);
    }
}

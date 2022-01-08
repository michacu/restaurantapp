package sk.michacu.zmenaren;

import sk.michacu.zmenaren.model.MenaObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "zoznamServlet")
public class ZoznamServlet extends HttpServlet {

    List<MenaObject> currencyList = new ArrayList<>();
    private RequestDispatcher jsp;
    private final Utilities utilities = new Utilities();

    public ZoznamServlet() {
    }

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        currencyList = utilities.getCurrencyList();
        context.setAttribute("currencyList", currencyList);
        jsp = context.getRequestDispatcher("/zoznam.jsp");
    }

    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menaOperation = request.getParameter("menaoperation");
        String objectId = request.getParameter("id");
        if (menaOperation != null) {
            if (menaOperation.equals("remove")) {
                utilities.removeMenaObject(Long.valueOf(objectId));
            }
        }
        currencyList = utilities.getCurrencyList();
        request.getServletContext().setAttribute("currencyList", utilities.getCurrencyList());
        jsp.forward(request,response);
    }

    @Override
    public void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        jsp.forward(request,response);
    }

    @Override
    public void destroy() {}
}

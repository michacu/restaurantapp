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
import java.util.Currency;
import java.util.List;

@WebServlet(name = "pridanieMenyServlet")
public class PridanieMenyServlet extends HttpServlet {
    private final Utilities utilities = new Utilities();
    private RequestDispatcher jsp;

    public PridanieMenyServlet() {
    }

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        List<Currency> zoznamMienList = utilities.getAllCurrencies();
        context.setAttribute("zoznamMienList", zoznamMienList);
        jsp = context.getRequestDispatcher("/pridaniemeny.jsp");
    }

    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        jsp.forward(request,response);
    }

    @Override
    public void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menaSelectorForm = request.getParameter("menaSelectorForm");
        String iconValueForm = request.getParameter("iconValueForm");
        String descriptionValueForm = request.getParameter("descriptionValueForm");
        String activeValueForm = request.getParameter("activeValueForm");
        String menaRadio = request.getParameter("flexRadioDefault");
        StringBuilder error = new StringBuilder();
        List<MenaObject> actualList = utilities.getCurrencyList();
        error = utilities.fillErrorList(menaSelectorForm, iconValueForm, descriptionValueForm, error, actualList, menaRadio);
        if (error.length() > 0) {
            request.setAttribute("error", error.toString());
            jsp.forward(request,response);
        }

        if (menaRadio.equals("add")) {
            utilities.addMenaObject(menaSelectorForm,iconValueForm,descriptionValueForm, activeValueForm.equals("on"));
            request.setAttribute("pridanieMenaResponse", menaSelectorForm + " : mena bola uspesne pridana");
        }
        if (menaRadio.equals("modify")) {
            utilities.updateMena(menaSelectorForm,iconValueForm,descriptionValueForm, activeValueForm.equals("on"));
            request.setAttribute("pridanieMenaResponse", menaSelectorForm + " : mena bola uspesne zmenena");
        }
        if (menaRadio.equals("remove")) {
            utilities.removeMenaObject(menaSelectorForm);
            request.setAttribute("pridanieMenaResponse", menaSelectorForm + " : mena bola uspesne vymazana");
        }
        jsp.forward(request,response);
    }

    @Override
    public void destroy() {}
}

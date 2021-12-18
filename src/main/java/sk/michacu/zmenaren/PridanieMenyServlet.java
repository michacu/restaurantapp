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
import javax.xml.bind.annotation.XmlEnum;
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
        String currnameValueFrom = request.getParameter("currnameValueFrom");
        String iconValueForm = request.getParameter("iconValueForm");
        String descriptionValueForm = request.getParameter("descriptionValueForm");
        String activeValueForm = request.getParameter("activeValueForm");
        StringBuilder error = new StringBuilder();
        List<MenaObject> actualList = utilities.getCurrencyList();
        actualList.forEach(menaObject -> {
            if (menaObject.getCurrName().equals(menaSelectorForm)) {
                error.append("Mena allready exist in zoznam please choose diferent value");
            }
        });
        if (currnameValueFrom.isEmpty()) {
            error.append("Currname value Cant be Empty please enter name");
        }
        if (iconValueForm.isEmpty()) {
            error.append("\n").append("Icon Value cant be empty please enter icon symbol");
        }
        if (descriptionValueForm.isEmpty()) {
            error.append("\n").append("Description cant be empty please fill description");
        }
        if (error.length() > 0) {
            request.setAttribute("error", error.toString());
            jsp.forward(request,response);
        }
        utilities.addMenaObject(currnameValueFrom,iconValueForm,descriptionValueForm, Boolean.parseBoolean(activeValueForm));
        request.setAttribute("pridanieMenaResponse", currnameValueFrom + " : mena bola uspesne pridana");
        jsp.forward(request,response);
    }

    @Override
    public void destroy() {}
}

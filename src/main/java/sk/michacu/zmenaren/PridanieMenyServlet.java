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
        String menaOperation = request.getParameter("menaoperation");
        String objectId = request.getParameter("id");
        if (menaOperation != null) {
            if (menaOperation.equals("modify")) {
                MenaObject modifyingMenaObject = utilities.getMena(Long.valueOf(objectId));
                request.setAttribute("modifyingMenaObject", modifyingMenaObject);
            }
        }
        jsp.forward(request,response);
    }

    @Override
    public void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menaSelectorForm = utilities.parameterIsNotNull(request,"menaSelectorForm") ?
                request.getParameter("menaSelectorForm") : request.getParameter("menaUpdateForm");
        String iconValueForm = utilities.parameterIsNotNull(request,"iconValueForm") ?
                request.getParameter("iconValueForm") : request.getParameter("iconModifyForm");
        String descriptionValueForm = utilities.parameterIsNotNull(request,"descriptionValueForm") ?
                request.getParameter("descriptionValueForm") : request.getParameter("descriptionModForm");
        String activeValueForm = utilities.parameterIsNotNull(request,"activeValueForm") ?
                request.getParameter("activeValueForm") : request.getParameter("activeModifyForm");
        String operationType = utilities.parameterIsNotNull(request,"formTypeValue") ?
                request.getParameter("formTypeValue") : request.getParameter("formTypeVal");
        String idValue = request.getParameter("menaIdModValue");
        StringBuilder error = new StringBuilder();
        List<MenaObject> actualList = utilities.getCurrencyList();
        error = utilities.fillErrorList(menaSelectorForm, iconValueForm, descriptionValueForm, error, actualList, operationType);
        if (error.length() > 0) {
            if (operationType.equals("add")) {
                request.setAttribute("modifyingMenaObject", utilities.fillModifiedValues(null,menaSelectorForm, iconValueForm, descriptionValueForm,activeValueForm != null && activeValueForm.equals("on")));
            }
            if (operationType.equals("modify")) {
                request.setAttribute("modifyingMenaObject", utilities.fillModifiedValues(Long.valueOf(idValue),menaSelectorForm, iconValueForm, descriptionValueForm,activeValueForm != null && activeValueForm.equals("on")));
            }
            request.setAttribute("error", error.toString());
            jsp.forward(request,response);
        }

        if (operationType.equals("add")) {
            utilities.addMenaObject(menaSelectorForm,iconValueForm,descriptionValueForm, activeValueForm != null && activeValueForm.equals("on"));
            request.setAttribute("menaResponse", menaSelectorForm + " : mena bola uspesne pridana");
        }
        if (operationType.equals("modify")) {
            utilities.updateMena(menaSelectorForm,iconValueForm,descriptionValueForm, activeValueForm != null && activeValueForm.equals("on"));
            request.setAttribute("modifyingMenaObject", idValue != null && !idValue.isEmpty() ? utilities.getMena(Long.valueOf(idValue)) : utilities.getIdMena(menaSelectorForm));
            request.setAttribute("menaResponse", menaSelectorForm + " : mena bola uspesne zmenena");
        }
        jsp.forward(request,response);
    }

    @Override
    public void destroy() {}
}

package sk.michacu.zmenaren;

import sk.michacu.zmenaren.model.MenaObject;

import javax.money.MonetaryAmount;
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
import java.util.Currency;
import java.util.List;

@WebServlet(name = "zmenarenServlet")

public class ZmenarenServlet extends HttpServlet {
    private final Utilities utilities = new Utilities();
    private RequestDispatcher jsp;
    List<MenaObject> currencyList = new ArrayList<>();

    public ZmenarenServlet() {
    }

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        currencyList = utilities.getCurrencyList();
        context.setAttribute("currencyList", currencyList);
        jsp = context.getRequestDispatcher("/zmenaren.jsp");
    }

    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("initValueFrom", 0);
        jsp.forward(request, response);
    }

    @Override
    public void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fromCurrency = request.getParameter("currencyValueFrom");
        String currencySelectorTo = request.getParameter("currencySelectorTo");
        String currencySelectorFrom = request.getParameter("currencySelectorFrom");
        StringBuilder error = new StringBuilder();
        request.setAttribute("initValueFrom", fromCurrency);
        if (fromCurrency.isEmpty()) {
            error.append("From Currency Cant be Empty please enter number");
        }
        if (utilities.isNotNumeric(fromCurrency)) {
            error.append("\n").append("From Currency is not in numeric format please enter number");
        }
        if (currencySelectorFrom.isEmpty()) {
            error.append("\n").append("Currency Selector From is empty choose please");
        }
        if (currencySelectorTo.isEmpty()) {
            error.append("\n").append("Currency Selector To is empty choose please");
        }
        if (error.length() > 0) {
            request.setAttribute("error", error.toString());
            jsp.forward(request,response);
        } else {
            MonetaryAmount converted = utilities.getMonetaryAmount(fromCurrency, currencySelectorTo, currencySelectorFrom);
            if (converted.toString().isEmpty()) {
                error.append("Technical problem - Something went wrong with conversion try again later");
            } else {
                request.setAttribute("initValueFrom", 0);
                request.setAttribute("currencyResponse", converted.toString().substring(0, converted.toString().indexOf(".") + 4));
            }
            request.getServletContext().setAttribute("currencyList", currencyList);
            jsp.forward(request,response);
        }
    }
}

package si.fri.prpo.skupina3.api.servleti;

import si.fri.prpo.skupina3.storitve.odjemalci.PriceAnalyticsApiOdjemalec;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/find-on-market")
public class PriceAnalysisCheck extends HttpServlet {

    @Inject
    PriceAnalyticsApiOdjemalec priceAnalyticsApiOdjemalec;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        priceAnalyticsApiOdjemalec.isciProdukt("amazon", "de", "iphone 11");
    }
}

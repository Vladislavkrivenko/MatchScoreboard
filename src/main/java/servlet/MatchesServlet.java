package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import util.JSPHelper;

import java.io.IOException;

@Slf4j
@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("GET /matches - Matches page requested");
        req.setAttribute("matches", null);
        req.getRequestDispatcher(JSPHelper.getPath("matches")).forward(req, resp);
    }
}

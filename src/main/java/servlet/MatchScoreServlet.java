package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import service.MatchService;
import util.JSPHelper;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final MatchService service = MatchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        if (uuidParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UUID uuid = UUID.fromString(uuidParam);
        Match match = service.getMatch(uuid);
        if (match == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("player1", match.getPlayer1());
        req.setAttribute("player2", match.getPlayer2());
        req.setAttribute("score", match.getScore().toString());
        req.getRequestDispatcher(JSPHelper.getPath("score")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        String pointWinner = req.getParameter("pointWinner");
        if (uuidParam == null || pointWinner == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UUID uuid = UUID.fromString(uuidParam);
        Match match = service.getMatch(uuid);
        if (match == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
       }
//        Score score = match.getScore();
//        if ("player1".equals(pointWinner)){
//            score.pointTOPlayer();
//        }else if("player2".equals(pointWinner)){
//            score.pointToPlayer2();
//        }
//        if(score.isFinished()){
//            service.removeMatch(uuid);
//        }
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + req.getParameter("uuid"));
    }

}

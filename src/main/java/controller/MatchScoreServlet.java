package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.Match;
import service.FinishedMatchesPersistenceService;
import service.MatchScoreCalculationService;
import service.MatchService;
import util.JSPHelper;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final MatchService service = MatchService.getInstance();
    private final MatchScoreCalculationService calculationService = MatchScoreCalculationService.getInstance();
    private final FinishedMatchesPersistenceService persistenceService = FinishedMatchesPersistenceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        if (uuidParam == null) {
            log.warn("GET /match-score called without uuid parameter");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidParam);
        } catch (IllegalArgumentException e) {
            log.warn("GET /match-score called with invalid UUID: {}", uuidParam);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Match match = service.getMatch(uuid);
        if (match == null) {
            log.warn("Match witch id {} could not be found", uuidParam);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        log.info("Displaying score for match UUID: {}", uuidParam);
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
            log.warn("POST /match-score missing required parameters: uuid='{}', pointWinner='{}'", uuidParam, pointWinner);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidParam);
        } catch (IllegalArgumentException e) {
            log.warn("POST /match-score called with invalid UUID: {}", uuidParam);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Match match = service.getMatch(uuid);
        if (match == null) {
            log.warn("POST /match-score no match found for UUID: {}", uuid);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        log.info("POST /match-score: point won by '{}', match UUID: {}", pointWinner, uuid);
        if ("player1".equals(pointWinner)) {
            calculationService.pointToPlayer(uuid);
        } else if ("player2".equals(pointWinner)) {
            calculationService.pointToPlayer2(uuid);
        } else {
            log.warn("POST /match-score invalid invalid pointWinner value: {}", pointWinner);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (match.getScore().isMatchOver()) {
            log.info("Match with UUID {} is over. Removing from active matches", uuid);
            persistenceService.saveFinishedMatches(match);
            service.removeMatch(uuid);
        }
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + req.getParameter("uuid"));
    }
}

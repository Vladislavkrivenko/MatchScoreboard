package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.Player;
import service.MatchService;
import service.PlayerService;
import util.JSPHelper;

import java.io.IOException;
import java.util.UUID;

import static util.ValidationUtil.isValidPlayerName;

@Slf4j
@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private final MatchService service = MatchService.getInstance();
    private final PlayerService playerService = PlayerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("GET request to /new-match");
        req.getRequestDispatcher(JSPHelper.getPath("match")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");

        log.info("POST request to /new-match with players: '{}' and '{}'", player1Name, player2Name);

        if (!isValidPlayerName(player1Name) || !isValidPlayerName(player2Name)) {
            log.warn("Invalid player names provided: '{}' and '{}'", player1Name, player2Name);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (player1Name.trim().equalsIgnoreCase(player2Name.trim())) {
            log.warn("Player names are the same: '{}'", player1Name);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Players must have different names");
            return;
        }

        Player player1 = playerService.findOrCreatePlayer(player1Name);
        Player player2 = playerService.findOrCreatePlayer(player2Name);

        UUID matchId = service.createMatch(player1, player2);
        log.info("Created new match with id: {}", matchId);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId);
    }
}

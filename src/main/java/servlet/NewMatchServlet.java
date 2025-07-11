package servlet;

import dao.PlayerRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Players;
import service.MatchService;
import util.JSPHelper;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private final MatchService service = MatchService.getInstance();
    private final PlayerRepository playerRepository = PlayerRepository.getInstance();//test

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPHelper.getPath("match")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameFirstPlayer = req.getParameter("nameFirstPlayer");
        String nameSecondPlayer = req.getParameter("nameSecondPlayer");

        if (nameFirstPlayer == null || nameSecondPlayer == null || nameFirstPlayer.equals(nameSecondPlayer)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Players player1 = playerRepository.findOrCreate(nameFirstPlayer);//test
        Players player2 = playerRepository.findOrCreate(nameSecondPlayer);//test
        UUID matchId = service.createMatch(player1, player2);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId);
    }
}

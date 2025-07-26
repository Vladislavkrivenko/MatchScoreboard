package servlet;

import dao.PlayerRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import service.MatchService;
import util.JSPHelper;

import java.io.IOException;

@Slf4j
@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private final MatchService service = MatchService.getInstance();
    private final PlayerRepository playerRepository = PlayerRepository.getInstance();//test

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("GET request to /new-match");
        req.getRequestDispatcher(JSPHelper.getPath("match")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameFirstPlayer = req.getParameter("nameFirstPlayer");
        String nameSecondPlayer = req.getParameter("nameSecondPlayer");

        log.info("POST request to /new-match with players: {} and '{}", nameFirstPlayer, nameSecondPlayer);
        if (nameFirstPlayer == null || nameSecondPlayer == null || nameFirstPlayer.equals(nameSecondPlayer)) {
            log.warn("Invalid player names provided: '{}' and '{}'", nameFirstPlayer, nameSecondPlayer);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

//        Player player1 = playerRepository.findOrCreate(nameFirstPlayer);//test
//        Player player2 = playerRepository.findOrCreate(nameSecondPlayer);//test

//        UUID matchId = service.createMatch(player1, player2);
//        log.info("Created new match with id: {}", matchId);
//        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId);
    }
}

package controller;

import dto.MatchesDto;
import dto.MatchesFilterDto;
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
@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private final MatchService matchService = MatchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("GET /matches - Matches page requested");
        String pageParameter = req.getParameter("page");
        String playerName = req.getParameter("filter_by_player_name");
        int page = 1;
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                log.warn("Invalid page parameter: {}", pageParameter);
            }
        }
        MatchesFilterDto filterDto = new MatchesFilterDto(playerName, page);

        MatchesDto matchesDto = matchService.findMatches(filterDto);


//        req.setAttribute("matchesDto", matchesDto);
        req.setAttribute("matchesDto", filterDto);//test
        req.getRequestDispatcher(JSPHelper.getPath("matches")).forward(req, resp);
    }
}

package api.game;

import api.interfaces.IGameService;
import application.model.MultiPlayerGame;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController implements IGameService {

    private GameRepository repository = new GameRepository(new GameSqlContext());


    @Override
    @RequestMapping(value = "/game/savesingleplayer", method = RequestMethod.POST)
    public void saveSinglePlayer(@RequestParam(value = "score") int score, @RequestParam("userId") int userId) {
        repository.saveSinglePlayerGame(score, userId);
    }

    @Override
    @RequestMapping(value = "/game/savemultiplayer", method = RequestMethod.POST)
    public void saveMultiPlayer(@RequestParam(value = "playerAId")int playerAId,
                                @RequestParam(value = "playerBId")int playerBId,
                                @RequestParam(value = "playerAScore") int playerAscore,
                                @RequestParam(value = "playerBScore") int playerBScore,
                                @RequestParam(value = "winnerId") int winnerId ) {

        repository.saveMultiPlayerGame(playerAId, playerBId, playerAscore, playerBScore, winnerId);
    }
}

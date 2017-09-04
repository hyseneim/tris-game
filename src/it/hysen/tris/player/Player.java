package it.hysen.tris.player;

import it.hysen.tris.board.Pawn;
import it.hysen.tris.game.Game;

/**
 * @author Gabriele Cipolloni
 */
public interface Player {

	Game getGame();

	Pawn getPawn();

}

package it.hysen.tris.player;

import it.hysen.tris.board.Pawn;
import it.hysen.tris.game.Game;

/**
 * @author Gabriele Cipolloni
 */
public abstract class AbstractPlayer implements Player {

	private final Game game;
	private final Pawn pawn;

	public AbstractPlayer(Game game, Pawn pawn) {
		this.game = game;
		this.pawn = pawn;
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public Pawn getPawn() {
		return pawn;
	}

}

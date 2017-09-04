package it.hysen.tris.game;

import java.util.List;

import it.hysen.tris.board.Cell;
import it.hysen.tris.board.Pawn;
import it.hysen.tris.game.util.GameUtil;
import it.hysen.tris.player.AIPlayer;
import it.hysen.tris.player.HumanPlayer;
import it.hysen.tris.player.Player;
import it.hysen.tris.tree.TreeNode;

/**
 * @author Gabriele Cipolloni
 */
public abstract class AbstractGame implements Game {
	
	private int turns;
	
	private Cell[][] board;
	
	private final Player player1;
	
	private final Player player2;
	
	private List<TreeNode> tree;
	
	public AbstractGame() {
		turns = 1;
		board = new Cell[3][3];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = new Cell();
			}
		}
		player1 = new HumanPlayer(this, Pawn.X);
		player2 = new AIPlayer(this, Pawn.O);
		
		GameUtil.initializeTree(this);
	}
	
	@Override
	public Cell[][] getBoard() {
		return board;
	}
	
	@Override
	public Player getCurrentPlayer() {
		return (turns % 2) != 0 ? player1 : player2;
	}
	
	@Override
	public Player getPlayer1() {
		return player1;
	}
	
	@Override
	public Player getPlayer2() {
		return player2;
	}
	
	@Override
	public List<TreeNode> getTree() {
		return tree;
	}
	
	@Override
	public int getTurns() {
		return turns;
	}
	
	@Override
	public void setBoard(Cell[][] board) {
		this.board = board;
	}
	
	@Override
	public void setTree(List<TreeNode> tree) {
		this.tree = tree;
	}
	
	@Override
	public void setTurns(int turns) {
		this.turns = turns;
	}
	
}

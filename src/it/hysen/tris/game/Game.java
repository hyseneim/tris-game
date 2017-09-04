package it.hysen.tris.game;

import java.util.List;

import it.hysen.tris.board.Cell;
import it.hysen.tris.player.Player;
import it.hysen.tris.tree.TreeNode;

/**
 * @author Gabriele Cipolloni
 */
public interface Game {

	public Cell[][] getBoard();

	public Player getCurrentPlayer();

	public Player getPlayer1();

	public Player getPlayer2();

	public List<TreeNode> getTree();

	public int getTurns();

	public void setBoard(Cell[][] board);

	public void setTree(List<TreeNode> tree);

	public void setTurns(int turns);

}

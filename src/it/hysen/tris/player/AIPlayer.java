package it.hysen.tris.player;

import java.util.List;

import it.hysen.tris.board.Pawn;
import it.hysen.tris.game.Game;
import it.hysen.tris.tree.TreeNode;

/**
 * @author Gabriele Cipolloni
 */
public class AIPlayer extends AbstractPlayer {
	
	public AIPlayer(Game game, Pawn pawn) {
		super(game, pawn);
	}
	
	public TreeNode findMaxValueNode(List<TreeNode> currentTree) {
		final TreeNode maxValueNode = currentTree.stream()
				.filter(element -> element.getParent().isEqualBoard(getGame().getBoard())).max((x, y) -> {
					if ((getGame().getTurns() % 2) == 0) {
						return new Double(y.getValue()).compareTo(new Double(x.getValue()));
					}
					return new Double(x.getValue()).compareTo(new Double(y.getValue()));
				}).get();
		return maxValueNode;
	}
	
}

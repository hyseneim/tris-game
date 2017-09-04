package it.hysen.tris.board;

/**
 * @author Gabriele Cipolloni
 */
public enum Pawn {

	EMPTY("-"), X("X"), O("O");

	private final String content;

	private Pawn(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}

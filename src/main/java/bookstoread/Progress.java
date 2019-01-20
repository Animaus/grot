package bookstoread;

// TODO 04 track bookshelf progress - p.074
public class Progress {
	private final int completed;
	private final int toRead;
	private final int inProgress;
	
	public Progress(int completed, int toRead, int inProgress) {
		this.completed = completed;
		this.toRead = toRead;
		this.inProgress = inProgress;
	}

	public int completed() {
		return completed;
	}

	public int toRead() {
		return toRead;
	}

	public int inProgress() {
		return inProgress;
	}
	
	@Override
	public String toString() {
		return completed + "% completed, " + inProgress + "% in progress and " + toRead + "% to read";
	}
}

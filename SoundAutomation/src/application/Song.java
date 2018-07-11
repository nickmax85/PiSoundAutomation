package application;

public class Song {

	private String filename;
	private boolean repeat;

	public Song(String filename, boolean repeat) {

		this.filename = filename;
		this.repeat = repeat;
	}

	public String getFilename() {
		return filename;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	@Override
	public String toString() {
		return "Song [filename=" + filename + ", repeat=" + repeat + "]";
	}

}

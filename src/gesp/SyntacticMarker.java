package gesp;

/**
 * @author silvuss
 */
class SyntacticMarker {
	private String openingMarker;
	private String closingMarker;

	public SyntacticMarker(String openingMarker, String closingMarker) {
		this.setOpeningMarker(openingMarker);
		this.setClosingMarker(closingMarker);
	}

	public String getOpeningMarker() {
		return this.openingMarker;
	}

	public void setOpeningMarker(String openingMarker) {
		this.openingMarker = openingMarker;
	}

	public String getClosingMarker() {
		return this.closingMarker;
	}

	public void setClosingMarker(String closingMarker) {
		this.closingMarker = closingMarker;
	}
}
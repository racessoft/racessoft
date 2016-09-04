package org.races.model;

public class ValidityCheck {
	boolean isValid;
	String message;
	String placeHolderString;

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPlaceHolderString() {
		return placeHolderString;
	}

	public void setPlaceHolderString(String placeHolderString) {
		this.placeHolderString = placeHolderString;
	}
	

}

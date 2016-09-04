package org.races.model;

public class ServiceChart {
	String Duration;
	int pending_count;
	int completed_count;
	public String getDuration() {
		return Duration;
	}
	public void setDuration(String duration) {
		Duration = duration;
	}
	public int getPending_count() {
		return pending_count;
	}
	public void setPending_count(int pending_count) {
		this.pending_count = pending_count;
	}
	public int getCompleted_count() {
		return completed_count;
	}
	public void setCompleted_count(int completed_count) {
		this.completed_count = completed_count;
	}

}

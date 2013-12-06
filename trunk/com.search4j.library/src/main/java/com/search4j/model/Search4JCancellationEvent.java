package com.search4j.model;

public class Search4JCancellationEvent {

	private boolean cancelled;

	public boolean isCancelled() {
		return cancelled;
	}

	public boolean isNotCancelled() {
		return !isCancelled();
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}

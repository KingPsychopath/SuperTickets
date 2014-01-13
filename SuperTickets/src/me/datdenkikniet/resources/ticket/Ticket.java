package me.datdenkikniet.resources.ticket;

import org.bukkit.entity.Player;

public class Ticket {
	public String helper;
	public String question;
	public String sender;
	public int id = 0;
	public Status status;

	public enum Status {
		UNSOLVED, BEING_HELPED, RESOLVED
	}

	/**
	 * 
	 * @param sender
	 *            the sender
	 * @param question
	 *            the question
	 */
	public Ticket(String sender, String question) {
		this.sender = sender;
		this.question = question;
		this.status = Status.UNSOLVED;
		this.helper = null;
		Ticketer.ticketInstances.add(this);
	}

	public void clear() {
		this.sender = null;
		this.question = "";
		this.helper = "";
		this.status = Status.RESOLVED;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setResolved() {
		this.status = Status.RESOLVED;
	}

	public int getId() {
		return this.id;
	}

	public String getSender() {
		return this.sender;
	}

	public String getHelper() {
		return this.helper;
	}

	public String getQuestion() {
		return this.question;
	}

	public boolean ownsTicket(Player player) {
		boolean b = false;
		if (this.sender == player.getName()) {
			b = true;
		}
		return b;
	}
}

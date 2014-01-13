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
	/**
	 * clear the ticket! (mark it completely as solved)
	 */
	public void clear() {
		this.sender = "";
		this.question = "";
		this.helper = "";
		this.status = Status.RESOLVED;
	}
	/**
	 * set the helpers name
	 * @param helper
	 * the name of the helper
	 */
	public void setHelper(String helper) {
		this.helper = helper;
	}
	/**
	 * set the new status of a ticket
	 * @param status
	 * the new status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * set a ticket resolved or not
	 */
	public void setResolved() {
		this.status = Status.RESOLVED;
	}
	/**
	 * get the ticket's id
	 * @return the id of the ticket
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * get the name of the sender of the ticket
	 * @return
	 * the name of the sender
	 */
	public String getSender() {
		return this.sender;
	}
	/**
	 * get the name of the person who is assisting the sender
	 * @return
	 * the name of the helper
	 */
	public String getHelper() {
		return this.helper;
	}
	/**
	 * get the question of the ticket
	 * @return
	 * the question of the ticket
	 */
	public String getQuestion() {
		return this.question;
	}
	/**
	 * returns wheter a player has a ticket or not
	 * @param player 
	 * the player to check
	 * @return
	 * true if the player has a ticket, false if the player doesn't
	 */
	public boolean ownsTicket(Player player) {
		boolean b = false;
		if (this.sender == player.getName()) {
			b = true;
		}
		return b;
	}
}

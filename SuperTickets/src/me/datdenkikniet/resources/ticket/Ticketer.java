package me.datdenkikniet.resources.ticket;

import java.util.ArrayList;

public class Ticketer {
	public static ArrayList<Ticket> ticketInstances = new ArrayList<Ticket>();
	/**
	 * get all of the tickets
	 * @return
	 * the tickets
	 */
	public static ArrayList<Ticket> getTickets() {
		return ticketInstances;
	}
}

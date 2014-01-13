package me.datdenkikniet.resources.ticket;

import java.util.ArrayList;

public class Ticketer {
	public static ArrayList<Ticket> ticketInstances = new ArrayList<Ticket>();

	public static ArrayList<Ticket> getTickets() {
		return ticketInstances;
	}
}

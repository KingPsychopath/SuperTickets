package me.datdenkikniet.SuperTickets.events;

import me.datdenkikniet.SuperTickets.Supertickets;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticket;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticketer;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticket.Status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener{
	Supertickets plugin;
	public LeaveEvent(Supertickets instance){
		plugin = instance;
	}
@EventHandler
public void leave(PlayerQuitEvent e){
	String name = e.getPlayer().getName();
	for (Ticket t: Ticketer.getTickets()){
		if (t.getSender().equalsIgnoreCase(name)){
			if (t.getHelper() != null){
				Player p = Bukkit.getPlayer(t.getHelper());
				if (p != null){
					p.sendMessage(plugin.pr + " " + name + " has just left the server, so his ticket has been marked as resolved!");
				}
				t.clear();
			}
			break;
		} 
		if (t.getHelper().equalsIgnoreCase(name)){
			Player ps = Bukkit.getPlayer(t.getSender());
			if (ps != null){
			t.setStatus(Status.UNSOLVED);
			t.setHelper("");
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.hasPermission("supertickets.notify")
						|| p
								.hasPermission("supertickets.*")) {
					p.sendMessage(plugin.pr
							+ " "
							+ p.getName()
							+ " created a new ticket with the question: \""
							+ t.question
							+ "\", and the id " + t.id
							+ "!");
				}
			}
			t.setHelper("");
			ps.sendMessage(plugin.pr + " " + name + " has left the server, but he was helping you! Your ticket has been reopened!");
			}
			break;
		}
	}
	name = null;
}
}

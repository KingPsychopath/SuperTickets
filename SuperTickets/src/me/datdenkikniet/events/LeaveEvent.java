package me.datdenkikniet.events;

import me.datdenkikniet.Supertickets;
import me.datdenkikniet.resources.ticket.Ticket;
import me.datdenkikniet.resources.ticket.Ticketer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener{
@EventHandler
public void leave(PlayerQuitEvent e){
	for (Ticket t: Ticketer.getTickets()){
		if (t.getSender().equalsIgnoreCase(e.getPlayer().getName())){
			Player helper = Bukkit.getPlayer(t.getHelper());
			helper.sendMessage(Supertickets.pr + " " + e.getPlayer().getName() + " has left the game, and his ticket has been marked as resolved!");
			t.clear();
		}
		if (t.getHelper().equalsIgnoreCase(e.getPlayer().getName())){
			Player sender  = Bukkit.getPlayer(t.getSender());
			sender.sendMessage(Supertickets.pr + " the person who was helping you has left! Your ticket has been reopened to be answered by other people!");
			for (Player p: Bukkit.getOnlinePlayers()){
				if (p.hasPermission("supertickets.notify")){
					p.sendMessage(Supertickets.pr
							+ " "
							+ sender.getName()
							+ "'s helper left,so his ticket with the question: \""
							+ t.question
							+ "\", and the id " + t.id
							+ " has been reopened!");
					t.setHelper("");
				}
			}
		}
	}
}
}

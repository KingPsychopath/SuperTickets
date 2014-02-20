package me.datdenkikniet.SuperTickets.events;

import me.datdenkikniet.SuperTickets.Supertickets;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticket;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticketer;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticket.Status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
	Supertickets plugin;
	public ChatEvent(Supertickets instance){
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void chat(AsyncPlayerChatEvent evt) {
		for (Ticket t : Ticketer.getTickets()) {
			if (t.status == Status.BEING_HELPED) {
				if (t.getSender() == evt.getPlayer().getName()) {
					Player player = Bukkit.getPlayerExact(t.helper);
					evt.setCancelled(true);
					player.sendMessage(plugin.pr + " "
							+ evt.getPlayer().getName() + ": "
							+ evt.getMessage());
					evt.getPlayer().sendMessage(
							plugin.pr + " " + evt.getPlayer().getName()
									+ " ---> " + t.helper + ": "
									+ evt.getMessage());
					break;
				}
				if (t.helper == evt.getPlayer().getName()) {
					Player player = Bukkit.getPlayerExact(t.sender);
					evt.setCancelled(true);
					player.sendMessage(plugin.pr + " "
							+ evt.getPlayer().getName() + ": "
							+ evt.getMessage());
					evt.getPlayer().sendMessage(
							plugin.pr + " " + evt.getPlayer().getName()
									+ " ---> " + t.sender + ": "
									+ evt.getMessage());
					break;
				}
			}
		}
	}

}

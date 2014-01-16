package me.datdenkikniet.resources.schedulers;

import me.datdenkikniet.Supertickets;
import me.datdenkikniet.resources.ticket.Ticket;
import me.datdenkikniet.resources.ticket.Ticket.Status;
import me.datdenkikniet.resources.ticket.Ticketer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NotifyRunnable extends BukkitRunnable {
	Supertickets plugin;
	public NotifyRunnable(Supertickets instance){
		plugin=instance;
}
	String pr = Supertickets.pr;
	public void run(){
		for (Player p: plugin.getServer().getOnlinePlayers()){
			if (p.hasPermission("supertickets.notify") || p.hasPermission("supertickets.*")){
				String f = pr
						+ " these are the current open tickets:\n <id>, <sender>, <question>";
				int count = 0;
				for (Ticket t : Ticketer.getTickets()) {
					if (t.status == Status.UNSOLVED) {
						f = f + "\n" + t.id + ", " + t.sender + ", \""
								+ t.question + "\"";
						count++;
					}
				}
				if (count != 0){
				p.sendMessage(f);
				} else {
					
				}
			}
		}
	}
	}


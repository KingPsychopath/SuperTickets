package me.datdenkikniet;

import me.datdenkikniet.events.ChatEvent;
import me.datdenkikniet.events.LeaveEvent;
import me.datdenkikniet.resources.ticket.Ticket;
import me.datdenkikniet.resources.ticket.Ticket.Status;
import me.datdenkikniet.resources.ticket.Ticketer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/** Created by datdenkikniet
 * If you copy this code, I would be glad if you could mention my name somewhere!!!
 **/

public class Supertickets extends JavaPlugin {
	public void onEnable() {
		System.out.println("We Have Enablisment!");
		Bukkit.getServer().getPluginManager()
				.registerEvents(new ChatEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
	}

	public void onDisable() {
		System.out.println("We Have Disableshment");
	}

	int IdCount = 0;
	public String noTicket = pr + ChatColor.RED + " you dont have a ticket!";
	public static String pr = ChatColor.DARK_AQUA + "[" + ChatColor.YELLOW
			+ "ST" + ChatColor.DARK_AQUA + "]" + ChatColor.YELLOW + ":";
	public String noperm = pr + ChatColor.RED
			+ " You don't have permission to do this!";

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("supertickets")
				|| label.equalsIgnoreCase("st")) {
			if (args.length == 0) {
				if (sender.hasPermission("supertickets.help")
						|| sender.hasPermission("supertickets.*")) {
					sender.sendMessage(Supertickets.pr + ChatColor.GREEN
							+ " The SuperTicket "
							+ getDescription().getVersion().toString()
							+ " Help menu!");
					if (sender.hasPermission("supertickets.help")
							|| sender.hasPermission("supertickets.*")) {
						sender.sendMessage(ChatColor.YELLOW
								+ "/supertickets help " + ChatColor.DARK_AQUA
								+ "displays this help menu!");
						sender.sendMessage(ChatColor.YELLOW + "/supertickets "
								+ ChatColor.DARK_AQUA
								+ "also displays this help menu!");
					}
					if (sender.hasPermission("supertickets.create")
							|| sender.hasPermission("supertickets.*")) {
						sender.sendMessage(ChatColor.YELLOW
								+ "/supertickets create <question>"
								+ ChatColor.DARK_AQUA + "creates a new ticket!");
						sender.sendMessage(ChatColor.YELLOW
								+ "/supertickets leave" + ChatColor.DARK_AQUA
								+ " makes you resolve your own ticket!");
					}
					if (sender.hasPermission("supertickets.accept")
							|| sender.hasPermission("supertickets.*")) {
						sender.sendMessage(ChatColor.YELLOW
								+ "/supertickets accept <id>"
								+ ChatColor.DARK_AQUA + " accepts a ticket!");
					}
					if (sender.hasPermission("supertickets.list")
							|| sender.hasPermission("supertickets.*")) {
						sender.sendMessage(ChatColor.YELLOW
								+ "/supertickets list"
								+ ChatColor.DARK_AQUA
								+ " lists all active tickets with their id's, senders, and questions");
					}
					if (sender.hasPermission("supertickets.resolve")
							|| sender.hasPermission("supertickets.*")) {
						sender.sendMessage(ChatColor.YELLOW
								+ "/supertickets resolve <id>"
								+ ChatColor.DARK_AQUA
								+ " marks a ticket as resolved!");
					}
					if (sender.hasPermission("supertickets.notify")
							|| sender.hasPermission("supertickets.*")) {
						sender.sendMessage(ChatColor.DARK_AQUA
								+ "you also get notified when someone creates a ticket!");
					}
					return true;
				}
				sender.sendMessage(noperm);
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					if (sender.hasPermission("supertickets.list")
							|| sender.hasPermission("supertickets.*")) {
						String f = pr
								+ " all of the running tickets:\n <id>, <sender>, <question>";
						int count = 0;
						for (Ticket t : Ticketer.getTickets()) {
							if (t.status == Status.UNSOLVED) {
								f = f + "\n" + t.id + ", " + t.sender + ", \""
										+ t.question + "\"";
								count++;
							}
						}
						if (count != 0) {
							sender.sendMessage(f);
						} else {
							sender.sendMessage(pr + " There are no tickets!");
							return true;
						}
						return true;
					} else {
						sender.sendMessage(noperm);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("help")) {
					if (sender.hasPermission("supertickets.help")
							|| sender.hasPermission("supertickets.*")) {
						sender.sendMessage(Supertickets.pr + ChatColor.GREEN
								+ " The SuperTicket "
								+ getDescription().getVersion().toString()
								+ " Help menu!");
						if (sender.hasPermission("supertickets.help")
								|| sender.hasPermission("supertickets.*")) {
							sender.sendMessage(ChatColor.YELLOW
									+ "/supertickets help "
									+ ChatColor.DARK_AQUA
									+ "displays this help menu!");
							sender.sendMessage(ChatColor.YELLOW
									+ "/supertickets " + ChatColor.DARK_AQUA
									+ "also displays this help menu!");
						}
						if (sender.hasPermission("supertickets.create")
								|| sender.hasPermission("supertickets.*")) {
							sender.sendMessage(ChatColor.YELLOW
									+ "/supertickets create <question>"
									+ ChatColor.DARK_AQUA
									+ "creates a new ticket!");
							sender.sendMessage(ChatColor.YELLOW
									+ "/supertickets leave"
									+ ChatColor.DARK_AQUA
									+ " makes you resolve your own ticket!");
						}
						if (sender.hasPermission("supertickets.accept")
								|| sender.hasPermission("supertickets.*")) {
							sender.sendMessage(ChatColor.YELLOW
									+ "/supertickets accept <id>"
									+ ChatColor.DARK_AQUA
									+ " accepts a ticket!");
						}
						if (sender.hasPermission("supertickets.list")
								|| sender.hasPermission("supertickets.*")) {
							sender.sendMessage(ChatColor.YELLOW
									+ "/supertickets list"
									+ ChatColor.DARK_AQUA
									+ " lists all active tickets with their id's, senders, and questions");
						}
						if (sender.hasPermission("supertickets.resolve")
								|| sender.hasPermission("supertickets.*")) {
							sender.sendMessage(ChatColor.YELLOW
									+ "/supertickets resolve <id>"
									+ ChatColor.DARK_AQUA
									+ " marks a ticket as resolved!");
						}
						if (sender.hasPermission("supertickets.notify")
								|| sender.hasPermission("supertickets.*")) {
							sender.sendMessage(ChatColor.DARK_AQUA
									+ "you also get notified when someone creates a ticket!");
						}
						return true;
					} else {
						sender.sendMessage(noperm);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("leave")) {
					if (sender.hasPermission("supertickets.create")
							|| sender.hasPermission("supertickets.*")) {
						boolean ownsATicket = false;
						Ticket ticket = null;
						for (Ticket t : Ticketer.getTickets()) {
							if (t.ownsTicket((Player) sender)) {
								ownsATicket = true;
								ticket = t;
								break;
							}
						}
						if (ownsATicket) {
							if (ticket.getHelper() != null) {
								Bukkit.getPlayerExact(ticket.getHelper())
										.sendMessage(
												pr
														+ " "
														+ ticket.sender
														+ " closed his/her ticket, you are done!");
							}
							ticket.clear();
							sender.sendMessage(pr
									+ " You succesfully resolved your own ticket!");
							return true;
						} else {
							sender.sendMessage(noTicket);
							return true;
						}
					} else {
						sender.sendMessage(noperm);
						return true;
					}
				} else {
					sender.sendMessage(pr + ChatColor.RED
							+ " Wrong usage: use /st for help!");
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("accept")) {
					if (sender.hasPermission("supertickets.accept")
							|| sender.hasPermission("supertickets.*")) {
						int d = -1;
						try {
							d = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(pr
									+ ChatColor.RED
									+ " your second argument was no number! (it has to be a number, according to the ticket id)");
							return true;
						}
						Ticket ticket = null;
						for (Ticket t : Ticketer.getTickets()) {
							if (t.getId() == d) {
								ticket = t;
								break;
							}
						}
						try {
							if (ticket.status != Status.RESOLVED) {
								if (ticket.helper == null) {
									ticket.setHelper(sender.getName());
									ticket.setStatus(Status.BEING_HELPED);
									Player p = Bukkit
											.getPlayerExact(ticket.sender);
									sender.sendMessage(pr
											+ " You are now helping "
											+ ticket.sender + "!");
									p.sendMessage(pr + " " + sender.getName()
											+ " is now helping you!");
									return true;
								} else {
									sender.sendMessage(pr
											+ " this ticket is already being handeled by someone else!");
								}
							} else {
								sender.sendMessage(pr
										+ " this ticket has been resolved already!");
								return true;
							}
						} catch (NullPointerException e) {
							sender.sendMessage(pr
									+ ChatColor.RED
									+ " that ticket does not exist/has been resolved!");
							return true;
						}
					} else {
						sender.sendMessage(noperm);
					}
				} else if (args[0].equalsIgnoreCase("resolve")) {
					if (sender.hasPermission("supertickets.resolve")
							|| sender.hasPermission("supertickets.*")) {
						int d = -1;
						try {
							d = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(pr
									+ ChatColor.RED
									+ " your second argument was no number! (it has to be a number, according to the ticket id");
							return true;
						}
						Ticket ticket = null;
						for (Ticket t : Ticketer.getTickets()) {
							if (t.getId() == d) {
								ticket = t;
								break;
							}
						}
						try {
							Player p = Bukkit.getPlayerExact(ticket.sender);
							sender.sendMessage(pr + " you closed "
									+ ticket.sender + "'s ticket");
							p.sendMessage(pr + " " + sender.getName()
									+ " closed your ticket!");
							ticket.clear();
							return true;
						} catch (Exception e) {
							sender.sendMessage(pr
									+ ChatColor.RED
									+ " that ticket does not exist/has been resolved!");
							return true;
						}
					}
				} else {
					sender.sendMessage(pr + ChatColor.RED
							+ " Wrong usage: use /st for help!");
				}
			} else if (args.length >= 2) {
				if (args[0].equalsIgnoreCase("create")) {
					if (sender.hasPermission("supertickets.create")
							|| sender.hasPermission("supertickets.*")) {
						boolean hasTicket = false;
						for (Ticket t : Ticketer.getTickets()) {
							if (t.sender == sender.getName()) {
								hasTicket = true;
							}
						}
						if (!hasTicket) {
							String ARGS = "";
							for (int i = 1; i < args.length; i++) {
								if (ARGS.length() == 0) {
									ARGS = args[i];
								} else {
									ARGS = ARGS + " " + args[i];
								}
							}
							Ticket ticket = new Ticket(sender.getName(), ARGS);
							ticket.id = IdCount;
							IdCount++;
							sender.sendMessage(pr
									+ " You created a new Ticket with the question: \""
									+ ticket.question + "\", and the id of "
									+ ticket.id + "!");
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p.hasPermission("supertickets.notify")
										|| sender
												.hasPermission("supertickets.*")) {
									p.sendMessage(pr
											+ " "
											+ sender.getName()
											+ " created a new ticket with the question: \""
											+ ticket.question
											+ "\", and the id " + ticket.id
											+ "!");
								}
								return true;
							}
						} else {
							sender.sendMessage(pr + ChatColor.RED
									+ " You already have a ticket!");
							return true;
						}
					} else {
						sender.sendMessage(noperm);
						return true;
					}
				} else {
					sender.sendMessage(pr + ChatColor.RED
							+ " Wrong usage: use /st for help!");
				}
			} else {
				sender.sendMessage(pr + ChatColor.RED
						+ " Wrong usage: use /st for help!");
			}
		}
		return false;
	}
}

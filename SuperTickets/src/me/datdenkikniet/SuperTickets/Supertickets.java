package me.datdenkikniet.SuperTickets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import me.datdenkikniet.SuperTickets.events.ChatEvent;
import me.datdenkikniet.SuperTickets.events.LeaveEvent;
import me.datdenkikniet.SuperTickets.resources.config.Config;
import me.datdenkikniet.SuperTickets.resources.schedulers.NotifyRunnable;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticket;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticketer;
import me.datdenkikniet.SuperTickets.resources.ticket.Ticket.Status;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/** Created by datdenkikniet
 * If you copy this code, I would be glad if you could mention my name somewhere!!!
 **/

public class Supertickets extends JavaPlugin{
	int idCount = 0;
	Supertickets plugin = this;
	public Config configYAML = new Config("config");
	public CustomConfig cfg = new CustomConfig();
	public String pr = "";
	public void onEnable() {
		System.out.println("We Have Enablisment!");
		Bukkit.getServer().getPluginManager()
				.registerEvents(new ChatEvent(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
		if (cfg.getCustomConfig1(configYAML) == null){
			cfg.saveDefaultConfig(configYAML);
		}
		if (cfg.getCustomConfig1(configYAML).getLong("delay between notifiy messages") != 0){
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new NotifyRunnable(this), 1, cfg.getCustomConfig1(configYAML).getLong("delay between notifiy messages")*20);
		}
		pr = ChatColor.translateAlternateColorCodes('&', cfg.getCustomConfig1(configYAML).getString("supertickets prefix"));
	}
	public void onDisable() {
		System.out.println("We Have Disableshment");
	}

			//ChatColor.DARK_AQUA + "[" + ChatColor.YELLOW + "ST" + ChatColor.DARK_AQUA + "]" + ChatColor.YELLOW + ":";
	public String noTicket = pr + ChatColor.RED + " you dont have a ticket!";
	public String noperm = pr + ChatColor.RED
			+ " You don't have permission to do this!";

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("supertickets")
				|| label.equalsIgnoreCase("st")) {
			if (args.length == 0) {
				if (sender.hasPermission("supertickets.help")
						|| sender.hasPermission("supertickets.*")) {
					sender.sendMessage(pr + ChatColor.GREEN
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
								+ "/supertickets leave (optional reason)" + ChatColor.DARK_AQUA
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
								+ "/supertickets resolve <id> (optional reason)"
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
						sender.sendMessage(pr + ChatColor.GREEN
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
									+ "/supertickets leave (optional reason)"
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
									+ "/supertickets resolve <id> (optional reason)"
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
							ticket.id = idCount;
							idCount++;
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
				} else if (args[0].equalsIgnoreCase("leave")){
					if (sender.hasPermission("supertickets.create") || sender.hasPermission("supertickets.*")){
					boolean ownsATicket = false;
					Ticket ticket = null;
					for (Ticket t : Ticketer.getTickets()) {
						if (t.ownsTicket((Player) sender)) {
							ownsATicket = true;
							ticket = t;
							break;
						}
					}
					String ticketReason = "";
					for (int i =1; i< args.length; i++){
						if (ticketReason.equalsIgnoreCase("")){
							ticketReason = args[i];
						} else {
						ticketReason = ticketReason + " " + args[i];
						}
					}
					if (ownsATicket) {
						if (ticket.getHelper() != null) {
							Bukkit.getPlayerExact(ticket.getHelper())
									.sendMessage(
											pr
													+ " "
													+ ticket.sender
													+ " closed his/her ticket with the reason :\""+ ticketReason + "\", you are done!");
						}
						ticket.clear();
						sender.sendMessage(pr
								+ " You succesfully resolved your own ticket with the reason: \" " + ticketReason + "\"");
						return true;
					} else {
						sender.sendMessage(noTicket);
						return true;
					}
				} else {
					sender.sendMessage(noperm);
					return true;
				}
				} else if (args[0].equalsIgnoreCase("resolve")){
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
							String closeReason = "";
							for (int i =2; i < args.length; i++){
								if (closeReason.equalsIgnoreCase("")){
									closeReason = args[i];
								} else {
									closeReason = closeReason + " " + args[i];
								}
							}
							Player p = Bukkit.getPlayerExact(ticket.sender);
							sender.sendMessage(pr + " you closed "
									+ ticket.sender + "'s ticket with the reason: \"" + closeReason + "\".");
							p.sendMessage(pr + " " + sender.getName()
									+ " closed your ticket with the reason: \"" + closeReason + "\"");
							ticket.clear();
							return true;
						} catch (Exception e) {
							sender.sendMessage(pr
									+ ChatColor.RED
									+ " that ticket does not exist/has been resolved!");
							return true;
						}
					}
				} 
				else {
					sender.sendMessage(pr + ChatColor.RED
							+ " Wrong usage: use /st for help!");
					return true;
				}
			} else {
				sender.sendMessage(pr + ChatColor.RED
						+ " Wrong usage: use /st for help!");
				return true;
			}
		}
		return false;
	}
	public class CustomConfig{
	public FileConfiguration getCustomConfig1(Config config) {
		if (config.fileConfig == null){
		reloadCustomConfig2(config);
		}
		return config.fileConfig;
		}
		public void reloadCustomConfig2(Config config) {
		if (config.fileConfig == null) {
		config.file = new File(plugin.getDataFolder(), config.name + ".yml");
		}
		config.fileConfig = YamlConfiguration.loadConfiguration(config.file);
		 
		InputStream defConfigStream = plugin.getResource(config.name + ".yml");
		if (defConfigStream != null) {
		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		config.fileConfig.setDefaults(defConfig);
		}
		}
		public void saveCustomConfig3(Config config) {
		if (config.fileConfig == null || config.file == null) {
		return;
		}
		try {
		getCustomConfig1(config).save(config.file);
		} catch (IOException ex) {
		plugin.getLogger().log(Level.SEVERE, "Could not save config to " + config.file, ex);
		}
		}
		public void saveDefaultConfig(Config config) {
		if (config.file == null) {
		config.file = new File(plugin.getDataFolder(), config.name + ".yml");
		}
		if (!config.file.exists()) {
		plugin.saveResource(config.name + ".yml", false);
		}
		}
	}
}

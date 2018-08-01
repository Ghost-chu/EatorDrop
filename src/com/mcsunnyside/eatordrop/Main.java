package com.mcsunnyside.eatordrop;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		reloadConfig();
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		int food = e.getEntity().getFoodLevel();
		getConfig().set("hunger." + e.getEntity().getName(), food);
		e.getEntity().getActivePotionEffects();
		if (food == 0) {
			e.getEntity().sendMessage("§c您因为饥饿而死失去了背包内的物品与...");
			e.setKeepInventory(false);
		} else {
			e.setKeepInventory(true);
		}
	}
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		new BukkitRunnable() {

			@Override
			public void run() {
				int beforeDiedfood = getConfig().getInt("hunger." + e.getPlayer().getName());
				if (beforeDiedfood > 0) {
					e.getPlayer().setFoodLevel(beforeDiedfood);
				}
			}
		}.runTaskLaterAsynchronously(this, 5);
	}
}

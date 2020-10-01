package me.IcanR.GodCombat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CombatEvents implements Listener {
	
	static Main plugin;
	public CombatEvents(Main instance) {
		plugin = instance;
	}
	
	@EventHandler()
	public void onClick(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW))
			if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = (Player) event.getPlayer();
				//Right click
				if (event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (!plugin.list.contains(player.getName()))
						plugin.list.add(player.getName());
					return;
				}
			}
		if (plugin.list.contains(event.getPlayer().getName())) {
			plugin.list.remove(event.getPlayer().getName());
		}
	}
	
	@EventHandler()
	public void onLand(ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.ARROW) {
			if (event.getEntity().getShooter() instanceof Player) {
				Player player = (Player) event.getEntity().getShooter();
				if (plugin.list.contains(player.getName())) {
					// spawn lightning
					Location loc = event.getEntity().getLocation();
					loc.setY(loc.getY() + 1);
					for (int i = 1; i < 4 ; i++) {
						loc.getWorld().spawnEntity(loc, EntityType.LIGHTNING);
						loc.setX(loc.getX() + i);
					}
				}
			}
		}
	}
}


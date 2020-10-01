package me.IcanR.GodCombat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	
	public List<String> list = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(new CombatEvents(this), this);
	}
	
	@Override
	public void onDisable( ) {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("combatcrossbow")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "Sorry console masuk server untuk melakukanya.");
				return true;
			}
			Player player = (Player) sender;
			if (player.getInventory().firstEmpty() == -1) {
				// inventory is full
				Location loc = player.getLocation();
				World world = player.getWorld();
				
				world.dropItemNaturally(loc, getItem());
				player.sendMessage(ChatColor.GOLD + "Equipment for fighting has been provided");
				return true;
			}
			player.getInventory().addItem(getItem());
			player.sendMessage(ChatColor.GOLD + "Equipment for fighting has been provided");
			return true;
		}
		return false;
	}
	
	
	public ItemStack getItem() {
		
		ItemStack item = new ItemStack(Material.CROSSBOW);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Epic Crossbow");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "This crossbow has special powers");
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Spawn a lightning"));
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.MULTISHOT, 2, true);
		meta.addEnchant(Enchantment.ARROW_FIRE, 2, true);
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		
		item.setItemMeta(meta);
		
		
		return item;
	}

	@EventHandler()
	public void onClick(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW))
			if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = (Player) event.getPlayer();
				//Right click
				if (event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (!list.contains(player.getName()))
						list.add(player.getName());
					return;
				}
			}
		if (list.contains(event.getPlayer().getName())) {
			list.remove(event.getPlayer().getName());
		}
	}
	
	@EventHandler()
	public void onLand(ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.ARROW) {
			if (event.getEntity().getShooter() instanceof Player) {
				Player player = (Player) event.getEntity().getShooter();
				if (list.contains(player.getName())) {
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













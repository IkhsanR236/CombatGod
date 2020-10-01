package me.IcanR.GodSword;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable( ) {
		
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// /pahlawansword
		if (label.equalsIgnoreCase("godsword")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console please join server to run this command.");
				return true;
			}
			Player player = (Player) sender;
			if (player.getInventory().firstEmpty() == -1) {
				// inventory is full
				Location loc = player.getLocation();
				World world = player.getWorld();
				
				world.dropItemNaturally(loc, getitem());
				player.sendMessage(ChatColor.GOLD + "Karena Inventorymu penuh pedang pahlawan di drop di sebelah mu");
			}
			player.getInventory().addItem(getitem());
			player.sendMessage(ChatColor.GOLD + "ini pedang sang pahlawan,Thanks for buying pahlawan rank.");
			return true;
		}
		return false;
	}
	
	
	public ItemStack getitem() {
		
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = sword.getItemMeta();
		
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Epic Sword");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.RED + "" + ChatColor.ITALIC + "Pedang Khusus untuk pahlawan sejati");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
		meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		meta.addEnchant(Enchantment.DAMAGE_ALL, 50, true);
		meta.addEnchant(Enchantment.FROST_WALKER, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		
		sword.setItemMeta(meta);
		
		return sword;

	}
	
	@EventHandler
	public void onWalk(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();
		if (player.getInventory().getItemInMainHand() !=null)
			if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Epic Sword"))
				if (player.getInventory().getItemInMainHand().getItemMeta().hasLore())
					if (event.getFrom().getY() < event.getTo().getY() &&
							player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
						player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
					}
	}
	
}

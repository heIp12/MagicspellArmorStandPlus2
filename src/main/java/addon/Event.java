package addon;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.nisovin.magicspells.events.MagicSpellsLoadedEvent;

public class Event	implements Listener
{
  
	  @SuppressWarnings("unused")
	  private final Plugin plugin;
		
	  public Event(ArmorStandPlus armorStandPlus) {
		  plugin = armorStandPlus;
	  }

	  @EventHandler
	  public void MagicSpellsLoadedEvent(MagicSpellsLoadedEvent e) {
	      System.out.println("[MSAddon] Clean Armor Stand");
			
	      ArmorStandPlus.Reset();
	  }
	  
}

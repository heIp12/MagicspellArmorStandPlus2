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

import com.nisovin.magicspells.events.MagicSpellsLoadedEvent;
import com.nisovin.magicspells.events.SpellTargetEvent;

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
	  @EventHandler
	  private void TargetSpell(SpellTargetEvent e) {
		  if(e.getTarget() instanceof ArmorStand) {
			  e.setCancelled(true);
		  }
	  }
}

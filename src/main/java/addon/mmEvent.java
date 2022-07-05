package addon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.nisovin.magicspells.events.MagicSpellsLoadedEvent;
import com.nisovin.magicspells.events.SpellTargetEvent;

import addon.util.NpcPlayer;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import mm.MagicSpellCast;
import mm.SignalCast;

public class mmEvent implements Listener
{
  
	@SuppressWarnings("unused")
	private final Plugin plugin;
	
	public mmEvent(ArmorStandPlus armorStandPlus) {
		plugin = armorStandPlus;
	}

	@EventHandler
	public void onMythicMechanicLoad(MythicMechanicLoadEvent event)	{
		if (event.getMechanicName().equalsIgnoreCase("magicspell")) {
			event.register(new MagicSpellCast("magicspell", event.getConfig()));
		}
		if (event.getMechanicName().equalsIgnoreCase("standsignal")) {
			event.register(new SignalCast("standsignal", event.getConfig()));
		}
	}
	  
	@EventHandler
	public void MagicSpellsLoadedEvent(SpellTargetEvent e) {
		if(e.getCaster().getName().equals(""+e.getTarget().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}

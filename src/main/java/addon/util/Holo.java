package addon.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;



public class Holo {
	public static void create(Location loc, String name, int tick, Vector v) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
			ArmorStand am = create(loc,name);
			new Ar(ArmorStandPlus.plugin,am,tick,v);
		});
	}

	public static ArmorStand create(Location loc, String name)
	  {
	    Location locPatch = loc.add(0,1,0);
	    ArmorStand as = (ArmorStand)locPatch.getWorld().spawnEntity(locPatch, EntityType.ARMOR_STAND);
	    as.setVisible(false);
	    as.setGravity(false);
	    as.setMarker(true);
	    as.setCanPickupItems(false);
	    as.setCustomName(name);
	    as.setCustomNameVisible(true);
	    return as;
	  }
	
	private static class Ar
    implements Runnable {
        int taskId;
        ArmorStand as;
        int tick = 0;
        Vector v;
        
        public Ar(Plugin plugin,ArmorStand as, int tick, Vector v) {
            this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, (Runnable)this, (long)1, 1);
            this.as = as;
            this.tick = tick;
            this.v = v;
        }

        public void stop() {
        	as.remove();
            Bukkit.getServer().getScheduler().cancelTask(this.taskId);
        }

        @Override
        public void run() {
        	if(tick>0) {
        		tick--;
        		as.teleport(as.getLocation().add(v));
        	} else {
        		stop();
        	}
        }
	}
	
}

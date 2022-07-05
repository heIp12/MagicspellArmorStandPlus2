package addon;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;

import addon.types.ASAction;
import addon.util.AsRun;
import armorstand.BaseArmorStand;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;

public class ArmorStandPlus extends JavaPlugin {
	public static Plugin plugin;
	public static AsRun timeSystem;
	public static ASAction asAction;
	public static Plugin MythicMobs;
	public static Plugin MagicSpell;
	
	@Override
	public void onEnable() {
		plugin = this;
		timeSystem = new AsRun(plugin);
		asAction = new ASAction();
		try {
			MagicSpell = getServer().getPluginManager().getPlugin("MagicSpells");
		} catch(ClassCastException e) {
			System.out.println("No MagicSpells");
		}
		try {
			MythicMobs = getServer().getPluginManager().getPlugin("MythicMobs");
			System.out.println("Load MythicMobs");
		} catch(ClassCastException e) {
			System.out.println("No MythicMobs");
		}

        getServer().getPluginManager().registerEvents(new Event(this), this);
        if(MythicMobs != null) getServer().getPluginManager().registerEvents(new mmEvent(this), this);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Client.STEER_VEHICLE){

            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(event.isCancelled()) return;
                Entity vehicle = event.getPlayer().getVehicle();
                if(vehicle == null || !(vehicle instanceof ArmorStand)) return;
                float swSpeed, fwSpeed;
                boolean jumping;
                boolean shift;
                try {
                    swSpeed = event.getPacket().getFloat().read(0)/20;
                    fwSpeed = event.getPacket().getFloat().read(1)/5;
                    jumping = event.getPacket().getBooleans().read(0);
                    shift = event.getPacket().getBooleans().read(1);
                } catch (FieldAccessException ex){
                    ex.printStackTrace();
                    return;
                }
                for(BaseArmorStand a : timeSystem.armorstands) {
                	if(a.castPlayer == event.getPlayer()) {
                		a.sign.put("move:"+swSpeed+","+fwSpeed+","+jumping+","+shift, 1);
                	}
				}
                event.setCancelled(true);
            }
        });
        
	}

	@Override
	public void onDisable() {
        timeSystem.stop();
        killArmorStand();
	}
	
	public static void Reset() {
		killArmorStand();
		timeSystem.remove();
		timeSystem = new AsRun(plugin);
	}
	
    public static void killArmorStand(){
        for (World world : Bukkit.getWorlds()){
        	for(Entity entity : Bukkit.getWorld(world.getName()).getEntities()) {
        		if(entity.getCustomName()!=null) {
	        		if(entity.getCustomName().equals("HEIPASSP")) {
	        			entity.remove();
	        		}
        		}
        		
        	}
        }
    }
}
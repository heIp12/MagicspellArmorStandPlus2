package addon;
//fristloc 수정함


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import addon.event.DamageSpellEvent;
import addon.types.ASAction;
import addon.util.AsRun;
import armorstand.BaseArmorStand;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import sk.SkDamageEvent;
import sk.skECaster;
import sk.skEDamage;
import sk.skEDamageSet;
import sk.skEGetType;
import sk.skETarget;
import sk.skEType;

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
        if (Bukkit.getPluginManager().getPlugin("Skript") != null)
        {
          Skript.registerAddon(this);
          Skript.registerEvent("addon damage spell", SkDamageEvent.class, DamageSpellEvent.class, new String[] { "addon damage spell" });
          Skript.registerExpression(skEDamage.class, Number.class, ExpressionType.PROPERTY, new String[] { "s[pell][-]damage" });
          Skript.registerExpression(skECaster.class, Player.class, ExpressionType.PROPERTY, new String[] { "s[pell][-]caster" });
          Skript.registerExpression(skETarget.class, LivingEntity.class, ExpressionType.PROPERTY, new String[] { "s[pell][-]target" });
          Skript.registerExpression(skEGetType.class, String.class, ExpressionType.PROPERTY, new String[] { "s[pell][-]type" });
          
          Skript.registerEffect(skEDamageSet.class, new String[] { "s[pell][-]damage set %number%" });
          Skript.registerCondition(skEType.class, new String[] { "attack type is %string%" });
        }
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
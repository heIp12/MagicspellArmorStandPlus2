package heIpaddon;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;
import com.nisovin.magicspells.util.MagicConfig;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.BaseArmorStand;

public class ArmorStandSpell extends InstantSpell implements TargetedLocationSpell, TargetedEntitySpell{

	private boolean maker;
	private boolean small;
	
	private String tick;
	private String id;
	private String data;
	private boolean gravity;
	private ItemLoc itemloc;
	private String itemRotate;
	
	private boolean locRelative;
	private String locOffset;
	private List<String> action;
	private boolean rotateRelative;
	private boolean lockpitch;
	private boolean lockyaw;
	private List<String> parts;
	private boolean rotateLoc;
	
	public ArmorStandSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		this.name = spellName;
	    this.maker = getConfigBoolean("maker", false);
	    this.small = getConfigBoolean("small", false);
	    this.gravity = getConfigBoolean("gravity", false);
	    this.lockpitch = getConfigBoolean("lock-pitch", false);
	    this.lockyaw = getConfigBoolean("lock-yaw", false);
	  
	    this.tick = getConfigString("tick", "40");
	    this.id = getConfigString("item-id", "1");
	    this.data = getConfigString("item-data", "0");
	    this.itemRotate = getConfigString("item-rotate", "0,0,0");
	    this.rotateRelative = getConfigBoolean("rotate-relative", true);
	    this.rotateLoc = getConfigBoolean("rotate-loc", false);
	    
	    this.locOffset = getConfigString("loc-offset", "0,0,0");
	    this.locRelative = getConfigBoolean("loc-relative", true);
	    
	    this.itemloc = ItemLoc.valueOf(getConfigString("item-loc", "head").toUpperCase());
	    
	    this.action = getConfigStringList("actions", null);
	    this.parts = getConfigStringList("parts", null);
	}
	
	public BaseArmorStand create(LivingEntity en,LivingEntity caster, Location loc) {
		Location startloc = loc.clone();
		if(locRelative) {
			startloc = StandLoc.getRelativeOffset(startloc, ConfigRep.rep_Vector(locOffset,ArmorStandPlus.timeSystem.getTime()));
		} else {
			startloc.add(ConfigRep.rep_Vector(locOffset,ArmorStandPlus.timeSystem.getTime()));
		}
		Vector rotate = null;
		if(rotateRelative) {
			Vector vtr = new Vector(loc.getPitch(),loc.getYaw(),0);
			if(lockpitch) vtr.setX(0);
			if(lockyaw)  vtr.setY(0);
			vtr.add(ConfigRep.rep_Vector(itemRotate,ArmorStandPlus.timeSystem.getTime()));
			//rotate.add(new Vector(0,vtr.getZ(),-vtr.getZ()))
			rotate = vtr;
		} else {
			rotate = ConfigRep.rep_Vector(itemRotate,ArmorStandPlus.timeSystem.getTime());
		}
		

		startloc.setYaw(0);
		startloc.setPitch(0);

		
		BaseArmorStand bas = new BaseArmorStand(name,en,caster,startloc);
		bas.teleport(new LocAndRotate(startloc,rotate,itemloc));
		bas.startloc = new LocAndRotate(startloc,rotate,itemloc);
		bas.setMaker(maker);
		bas.setSize(small);
		bas.setGravity(gravity);
		bas.locrotate = rotateLoc;
		bas.setTick((int) ConfigRep.rep(tick,ArmorStandPlus.timeSystem.getTime()));
		bas.createItem(itemloc,
				(int) ConfigRep.rep(id,ArmorStandPlus.timeSystem.getTime()),
				(int) ConfigRep.rep(data,ArmorStandPlus.timeSystem.getTime()));
		bas.setAction(action);
		if(!(en instanceof ArmorStand)) {
			ArmorStandPlus.timeSystem.Add(bas);
		}
		if(parts != null) {
			for(String s : parts){
				bas.addParts(s);
			}
		}
		return bas;
	}
	
	@Override
	public boolean castAtEntity(LivingEntity arg0, float arg1) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
			create(arg0,arg0,arg0.getLocation());
		});
		return true;
	}

	@Override
	public boolean castAtEntity(Player arg0, LivingEntity arg1, float arg2) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
			create(arg0,arg0,arg1.getLocation());
		});
		return false;
	}

	@Override
	public boolean castAtLocation(Location arg0, float arg1) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
			create(null,null,arg0);
		});
		return false;
	}

	@Override
	public boolean castAtLocation(Player arg0, Location arg1, float arg2) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
			create(arg0,arg0,arg1);
		});
		return false;
	}

	@Override
	public PostCastAction castSpell(Player arg0, SpellCastState arg1, float arg2, String[] arg3) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
			create(arg0,arg0,arg0.getLocation());
		});
		return null;
	}
}

package heIpaddon;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
	private String locRotate;
	private List<String> action;
	private boolean rotateRelative;
	private boolean lockpitch;
	private boolean lockyaw;
	private List<String> parts;
	
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
	    
	    this.locOffset = getConfigString("loc-offset", "0,0,0");
	    this.locRotate = getConfigString("loc-rotate", "0,0,0");
	    this.locRelative = getConfigBoolean("loc-relative", true);
	    
	    this.itemloc = ItemLoc.valueOf(getConfigString("item-loc", "head").toUpperCase());
	    
	    this.action = getConfigStringList("actions", null);
	    this.parts = getConfigStringList("parts", null);
	}
	
	public BaseArmorStand create(LivingEntity en,LivingEntity caster, Location loc) {
		if(lockpitch) loc.setPitch(0);
		if(lockyaw)  loc.setYaw(0);
		Location startloc = loc.clone();
		if(!locRotate.equals("0,0,0")) {
			Vector locs = ConfigRep.rep_Vector(locRotate,ArmorStandPlus.timeSystem.getTime(),caster);
			startloc.setPitch(startloc.getPitch()+ (float) locs.getX());
			startloc.setYaw(startloc.getYaw()+ (float) locs.getY());
		}
		if(locRelative) {
			startloc = StandLoc.getRelativeOffset(startloc, ConfigRep.rep_Vector(locOffset,ArmorStandPlus.timeSystem.getTime(),caster));
		} else {
			startloc.add(ConfigRep.rep_Vector(locOffset,ArmorStandPlus.timeSystem.getTime(),caster));
		}
		
		Vector rotate = null;
		if(rotateRelative) {
			Vector vtr = new Vector(loc.getPitch(),loc.getYaw(),0);
			vtr.add(ConfigRep.rep_Vector(itemRotate,ArmorStandPlus.timeSystem.getTime(),caster));
			rotate = vtr;
		} else {
			rotate = ConfigRep.rep_Vector(itemRotate,ArmorStandPlus.timeSystem.getTime(),caster);
		}
		startloc.setYaw((float) rotate.getY());
		
		BaseArmorStand bas = new BaseArmorStand(name,en,caster,startloc);
		bas.standLoc = new LocAndRotate(startloc,itemloc);
		bas.teleport(bas.standLoc);
		bas.tp();
		bas.startloc = new LocAndRotate(bas.nextLocation,itemloc);
		bas.setMaker(maker);
		bas.setSize(small);
		bas.setGravity(gravity);
		bas.setTick((int) ConfigRep.rep(tick,ArmorStandPlus.timeSystem.getTime(),caster));
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(ArmorStandPlus.plugin, ()->{
			bas.createItem(itemloc,
					(int) ConfigRep.rep(id,ArmorStandPlus.timeSystem.getTime(),caster),
					(int) ConfigRep.rep(data,ArmorStandPlus.timeSystem.getTime(),caster));
			
			/*
			bas.createItem(itemloc,
					Material.getMaterial(id),
					(int) ConfigRep.rep(data,ArmorStandPlus.timeSystem.getTime(),caster));
			
			*/
		},2);
		bas.setPose(itemloc, rotate.setY(0));
		bas.setAction(action);
		if(!(en instanceof ArmorStand)) ArmorStandPlus.timeSystem.Add(bas);
		
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

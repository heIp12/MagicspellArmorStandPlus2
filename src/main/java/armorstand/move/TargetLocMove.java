package armorstand.move;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ASTarget;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.ASBase;

public class TargetLocMove extends ASBase{
	boolean lockyaw = false;
	boolean lockpitch = false;
	boolean only = false;
	LivingEntity target = null;
	
	@Override
	public void set() {
		super.set();
		lockyaw = getBoolean("lyaw");
		lockpitch = getBoolean("lpitch");
		only = getBoolean("only");
		
	}
	
	@Override
	public boolean run() {
		boolean run = super.run();
		if(run) {
			double size = getDouble("size");
			double vsize = getDouble("vsize");
			if(getValue("size") == null) size = 1.0f;
			if(getValue("vsize") == null) vsize = size;
			
			List<LivingEntity> e = ASTarget.Cbox(armorstand.armorstand, armorstand.caster, new Vector(size,vsize,size));
			if(e == null || e.isEmpty()) run = false;
			if(e.size() > 0) target = e.get(0);
			
			if(run && target !=null) {
				LocAndRotate lar = getLoc();
				Vector vtr = lar.getRotate();
				Location loc = StandLoc.lookAt(lar.getLocation(),target.getLocation(),getDouble("p",1));
				
				if(lockyaw) {
					loc.setYaw((float) getDouble("yaw"));
				} else {
					loc.setYaw((float) (loc.getYaw() + getDouble("yaw")));
				}
				if(lockpitch) {
					loc.setPitch((float) getDouble("pitch"));
				} else {
					loc.setPitch((float) (loc.getPitch() + getDouble("pitch")));
				}
				loc = loc.add(loc.getDirection().multiply(getDouble("speed")));
				vtr = new Vector(loc.getPitch(),loc.getYaw(),vtr.getZ());
				lar.setLoc(loc, vtr, type);
				armorstand.teleport(lar);
				
			}
	
		}
		return run;
	}
}

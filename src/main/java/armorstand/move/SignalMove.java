package armorstand.move;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.ASBase;

public class SignalMove extends ASBase{
	boolean lockyaw = false;
	boolean lockpitch = false;
	
	@Override
	public void set() {
		super.set();
		lockyaw = getBoolean("lyaw");
		lockpitch = getBoolean("lpitch");
	}
	
	@Override
	public boolean run() {
		boolean run = super.run();
		if(run) {
			LocAndRotate lar = getLoc();
			Location loc = armorstand.castPlayer.getLocation();
			String locs = null;
			for(String s : armorstand.sign.keySet()) {
				if(s.contains("[loc]")) locs = s.replace("[loc]", "");
			}
			if(locs == null) return !run;
			
			String[] local = locs.split(",");
			loc.setX(Float.parseFloat(local[0]));
			loc.setY(Float.parseFloat(local[1]));
			loc.setZ(Float.parseFloat(local[2]));
			loc.setPitch(Float.parseFloat(local[3]));
			loc.setYaw(Float.parseFloat(local[4]));
			
			Vector vtr = lar.getRotate();
			
			if(lockyaw) loc.setYaw(0);
			if(lockpitch) loc.setPitch(0);
			
			
			loc = StandLoc.getRelativeOffset(loc, new Vector(getDouble("x"),getDouble("y"),getDouble("z")));
			if(lar.getLocation().distance(loc) > getDouble("range",getDouble("speed"))){
				
				loc = StandLoc.lookAt(lar.getLocation(), loc,getDouble("p",1));
				loc = loc.add(loc.getDirection().multiply(getDouble("speed")));
				vtr = new Vector(loc.getPitch(),loc.getYaw(),vtr.getZ());
				lar.setLoc(loc, vtr, type);
				
				armorstand.teleport(lar);
			} else {
				lar.setLoc(loc, lar.getRotate(), type);
				armorstand.teleport(lar);
			}
		}
		return run;
	}
}

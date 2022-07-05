package armorstand.move;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.ASBase;

public class AddLocMove extends ASBase{

	@Override
	public void set() {
		super.set();
	}
	
	@Override
	public boolean run() {
		boolean run = super.run();
		if(run) {
			LocAndRotate lar = getLoc();
			Location loc = lar.getLocation();
			Vector vtr = lar.getRotate();
			
			loc.setPitch((float) (vtr.getX() +  getDouble("pitch")));
			loc.setYaw((float) (vtr.getY() +  getDouble("yaw")));
			
			loc = StandLoc.getRelativeOffset(loc, new Vector(getDouble("x",1),getDouble("y"),getDouble("z")).multiply(getDouble("speed")));
			
			vtr.add(new Vector(getDouble("pitch"), getDouble("yaw"),getDouble("roll")));
			
			lar.setLoc(loc, vtr, type);
			armorstand.teleport(lar);
		}
		return run;
	}
}

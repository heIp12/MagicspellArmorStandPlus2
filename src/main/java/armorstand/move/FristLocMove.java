package armorstand.move;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.util.ConfigRep;
import addon.util.LocAndRotate;
import armorstand.ASBase;
import armorstand.BaseArmorStand;

public class FristLocMove extends ASBase{

	public FristLocMove() {
		super();
	}
	
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
			
			Vector vtr = armorstand.startloc.getRotate();
			Location sloc = armorstand.startloc.getLocation();

			sloc.setYaw((float) (vtr.getY() + getDouble("yaw")));
			sloc.setPitch((float) (vtr.getX() + getDouble("pitch")));
			
			loc.add(sloc.getDirection().multiply(getDouble("speed")));
			vtr.add(new Vector(getDouble("yaw"),getDouble("pitch"),getDouble("roll")));
			
			
			lar.setLoc(loc, vtr, type);
			armorstand.teleport(lar);

		}
		return run;
	}
}

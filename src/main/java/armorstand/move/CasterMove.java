package armorstand.move;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.ASBase;

public class CasterMove extends ASBase{

	private Boolean lockpitch;
	private Boolean lockyaw;

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
			Location loc = armorstand.caster.getLocation().clone();
			Vector vtr = new Vector(0,0,0);
			if(armorstand.caster_Armorstand) {
				loc = armorstand.owner.getLocation(lar.getItemloc()).getLocation().clone();
				vtr = armorstand.owner.standLoc.getRotate().clone();
				loc.setPitch((float) vtr.getX() + loc.getPitch());
			}
			
			if(lockyaw) {
				vtr.setY(0);
				loc.setYaw(0);
			}
			if(lockpitch) {
				vtr.setX(0);
				loc.setPitch(0);
			}
			
			vtr = new Vector(
					loc.getPitch(),
					loc.getYaw(),
					vtr.getZ());
			
			loc = StandLoc.getRelativeLocation(loc, new Vector(getDouble("z")*-1,getDouble("y"),getDouble("x")),StandLoc.toEulerAngle(vtr.clone()));
			//loc = StandLoc.getRelativeOffset(loc, new Vector(getDouble("x"),getDouble("y"),getDouble("z")));
			loc.setYaw((float) (loc.getYaw() + getDouble("yaw")));
			vtr = new Vector(
					loc.getPitch() + getDouble("pitch"),
					loc.getYaw() + getDouble("yaw"),
					vtr.getZ() + getDouble("roll"));

			lar.setLoc(loc, vtr, type);
			
			armorstand.teleport(lar);
		}
		return run;
	}
}

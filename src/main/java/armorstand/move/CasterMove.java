package armorstand.move;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
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
			
			if(armorstand.caster_Armorstand) {
				loc = armorstand.owner.getLocation(lar.getItemloc()).getLocation();
			}
			
			Vector vtr = lar.getRotate();
			
			if(armorstand.caster_Armorstand) {
				vtr = armorstand.owner.getLocation(lar.getItemloc()).getRotate();
			} else {
				vtr = vtr.zero();
			}
			loc.setPitch((float) (loc.getPitch()+vtr.getX()));
			loc.setYaw((float) (loc.getYaw() + vtr.getY()));
			
			if(lockyaw) loc.setYaw(0);
			if(lockpitch) loc.setPitch(0);
			
			vtr = new Vector(loc.getPitch() + getDouble("pitch"),loc.getYaw() + getDouble("yaw"),vtr.getZ() + getDouble("roll"));
			loc = StandLoc.getRelativeOffset(loc, new Vector(getDouble("x"),getDouble("y"),getDouble("z")));
			lar.setLoc(loc, vtr, type);
			armorstand.teleport(lar);
		}
		return run;
	}
}

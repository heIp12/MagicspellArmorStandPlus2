package armorstand.condition;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import addon.util.StandLoc;
import armorstand.ASBase;

public class CasterRadius extends ASBase{
	boolean not = false;
	private Boolean lockyaw;
	private Boolean lockpitch;
	boolean reset = false;
	
	@Override
	public void set() {
		super.set();
		not = getBoolean("not"); 
		lockyaw = getBoolean("lyaw");
		lockpitch = getBoolean("lpitch");
		reset = getBoolean("tr");
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(run) {
			run = !not;
			Location loc = armorstand.caster.getLocation().clone();
			if(lockyaw) loc.setYaw(0);
			if(lockpitch) loc.setPitch(0);
			loc = StandLoc.getRelativeOffset(loc, new Vector(getDouble("x"),getDouble("y"),getDouble("z")));
			if(loc.distance(armorstand.armorstand.getLocation()) < getDouble("size")){
				run = not;
			}
			if(reset) {
				for(String s : armorstand.action.keySet()) {
					if(s.equals(group)) {
						for(ASBase ab : armorstand.action.get(s)) {
							ab.start = false;
						}
					}
				}
			}
		}
		return run;
	}
}

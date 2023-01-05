package armorstand.condition;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import addon.util.StandLoc;
import armorstand.ASBase;

public class OnBlock extends ASBase{
	boolean not = false;
	boolean reset = false;
	long lsing = System.currentTimeMillis();
	
	
	@Override
	public void set() {
		super.set();
		not = getBoolean("not"); 
		reset = getBoolean("tr");
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(run) {
			run = !not;
			
			Location loc = armorstand.armorstand.getLocation();
			loc = StandLoc.getRelativeOffset(loc,new Vector(getDouble("x"),getDouble("y"),getDouble("z")));
			
			if(loc.getBlock().getTypeId() == (int)getDouble("id")) {
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

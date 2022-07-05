package armorstand.condition;

import org.bukkit.Location;
import armorstand.ASBase;

public class IsMove extends ASBase{
	boolean not = false;
	boolean reset = false;
	Location lloc;
	
	long lsing = System.currentTimeMillis();
	
	@Override
	public void set() {
		super.set();
		not = getBoolean("not"); 
		reset = getBoolean("tr");
		if(lloc == null) lloc = armorstand.armorstand.getLocation();
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(run) {
			run = !not;
			if(lloc.distance(armorstand.armorstand.getLocation()) > getDouble("size",0.1)) {
				run = not;
			}

			lloc = armorstand.armorstand.getLocation();
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

package armorstand.condition;

import org.bukkit.Location;
import armorstand.ASBase;

public class Distance extends ASBase{
	Location loc;
	double distance;
	boolean reset = false;
	boolean not = false;
	
	
	@Override
	public void set() {
		super.set();
		not = getBoolean("not"); 
		reset = getBoolean("tr");
		loc = armorstand.armorstand.getLocation();
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(run) {
			run = !not;
			distance += loc.distance(armorstand.armorstand.getLocation());
			if(distance < getDouble("size")){
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

package armorstand.condition;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.util.ASTarget;
import addon.util.ConfigRep;
import armorstand.ASBase;

public class SingnalDistance extends ASBase{
	double size;
	double vsize;
	boolean reset = false;
	boolean not = false;
	
	
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
			

			if(loc.distance(armorstand.nextLocation) < (int)getDouble("range")){
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

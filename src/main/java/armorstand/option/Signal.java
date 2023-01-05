package armorstand.option;

import armorstand.ASBase;
import armorstand.BaseArmorStand;

public class Signal extends ASBase{
	int ticks = 0;
	String name = "";
	String target = "self";
	Boolean next;
	
	@Override
	public void set() {
		super.set();
		ticks = (int) getDouble("signal-tick");
		target = getValue("target");
		next = getBoolean("n");
		if(target == null) {
			target = "self";
		}
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(run) {
			name = getValue("name");
			
			if(target.contains("self")||target.contains("all")) {
				armorstand.sign.put(name, ticks);
			}
			if(target.contains("parts")||target.contains("all")) {
				parts(armorstand);
			}
			for(BaseArmorStand a : armorstand.parts) {
				a.sign.put(name, ticks);
			}
		}
		if(next) run = false;
		return run;
	}
	
	void parts(BaseArmorStand b){
		if(b.parts != null && !b.parts.isEmpty()) {
			for(BaseArmorStand parts : b.parts) {
				parts.sign.put(name,ticks);
				parts(parts);
			}
		}
	}
}

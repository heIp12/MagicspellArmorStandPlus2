package armorstand.condition;

import org.bukkit.Location;
import armorstand.ASBase;
import armorstand.BaseArmorStand;

public class IsParts extends ASBase{
	boolean not = false;
	boolean reset = false;
	String name;
	
	long lsing = System.currentTimeMillis();
	
	@Override
	public void set() {
		super.set();
		not = getBoolean("not"); 
		reset = getBoolean("tr");
		name = getValue("name");
		if(name == null) name = "";
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(run) {
			run = parts(armorstand,name);
			
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
	
	public boolean parts(BaseArmorStand b,String name) {
		boolean is = !not;
		
		for(BaseArmorStand bas : b.parts) {
			is = parts(bas,name);
			if(is == not) return not;
			if(bas.name.equals(name)) {
				return not;
			}
		}
		return is;
	}
}

package armorstand;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;

public class AddParts extends ASBase{
	boolean f = false;
	boolean remove = false;
	
	@Override
	public void set() {
		super.set();
		f = false;
	}
	
	@Override
	public boolean run(){
		super.run();
		if(!f) {
			f = true;
			remove = getBoolean("r");
			if(getValue("name") != null && !remove){
				armorstand.addParts(getValue("name"));
				tick = 0;
			}
			if(getValue("name") != null && remove){
				armorstand.removeParts(getValue("name"));
				tick = 0;
			}
		}
		return false;
	}
}

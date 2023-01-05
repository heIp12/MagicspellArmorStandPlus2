package armorstand.option;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;
import armorstand.ASBase;

public class GravitySet extends ASBase{
	boolean set = false;
	@Override
	public void set() {
		super.set();
		set = getBoolean("set");
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		armorstand.setGravity(set);
		return run;
	}
}

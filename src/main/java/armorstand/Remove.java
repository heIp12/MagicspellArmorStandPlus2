package armorstand;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;

public class Remove extends ASBase{

	@Override
	public void set() {
		super.set();
	}
	
	@Override
	public boolean run(){
		armorstand.remove();
		return false;
	}
}

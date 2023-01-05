package armorstand.option;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;
import armorstand.ASBase;

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

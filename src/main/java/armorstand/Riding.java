package armorstand;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;

public class Riding extends ASBase{

	@Override
	public void set() {
		super.set();
	}
	
	@Override
	public boolean run(){
		if(!armorstand.armorstand.getPassengers().contains(armorstand.castPlayer)) {
			armorstand.armorstand.setPassenger(armorstand.castPlayer);
		}
		armorstand.armorstand.setFallDistance(0);
		armorstand.castPlayer.setFallDistance(0);
		return false;
	}
}

package armorstand.option;

import org.bukkit.Material;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;
import armorstand.ASBase;

public class PlaySound extends ASBase{
	int ticks = 0;
	boolean onlyone;
	boolean one = false;
	@Override
	public void set() {
		super.set();
		ticks = (int) getDouble("sound-tick",1);
		if(ticks == 0) {
			onlyone = true;
		}
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if((run && !one && onlyone)|| (tick%ticks == 0 && run)) {
			if(onlyone) one = true;
			
			String iloc = getValue("sound");
			armorstand.armorstand.getWorld().playSound(armorstand.armorstand.getLocation(), iloc, (float)getDouble("sound-size",1), (float)getDouble("sound-pitch",1));
		}
		return run;
	}
}

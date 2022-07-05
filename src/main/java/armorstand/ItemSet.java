package armorstand;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;

public class ItemSet extends ASBase{
	int ticks = 0;
	boolean onlyone;
	boolean one = false;
	@Override
	public void set() {
		super.set();
		ticks = (int) getDouble("item-tick");
		if(ticks == 0) {
			onlyone = true;
		}
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if((run && !one && onlyone)|| (tick%ticks == 0 && run)) {
			if(onlyone) one = true;
			String iloc = getValue("loc");
			if(iloc==null || !iloc.equals("hand")) {
				iloc = "head";
			}
			ItemLoc pose = ItemLoc.valueOf(iloc.toUpperCase());
			
			armorstand.createItem(pose, (int)getDouble("id"), (int)getDouble("data"));
		}
		return run;
	}
}

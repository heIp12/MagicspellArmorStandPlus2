package armorstand.condition;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.util.ASTarget;
import addon.util.ConfigRep;
import armorstand.ASBase;

public class EntityCount extends ASBase{
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
			size = getDouble("size");
			vsize =  getDouble("vsize");
			if(getValue("size") == null) size = 1.0f;
			if(getValue("vsize") == null) vsize = size;
			
			List<LivingEntity> e = ASTarget.box(armorstand.armorstand, armorstand.castPlayer, new Vector(size,vsize,size));
			if(e == null || e.isEmpty()) {
				run = not;
			} else if(e.size() < (int)getDouble("count")){
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

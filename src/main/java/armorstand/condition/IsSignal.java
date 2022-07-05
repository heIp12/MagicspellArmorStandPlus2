package armorstand.condition;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import addon.ArmorStandPlus;
import addon.util.ASTarget;
import addon.util.ConfigRep;
import armorstand.ASBase;

public class IsSignal extends ASBase{
	boolean not = false;
	boolean reset = false;
	
	long lsing = System.currentTimeMillis();
	
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
			if(getValue("name") != null){
				if(armorstand.sign.containsKey(getValue("name")) && armorstand.sign.get(getValue("name")) > 0) {
					if(reset && lsing < System.currentTimeMillis()) {
						lsing = System.currentTimeMillis() + (long)(armorstand.sign.get(getValue("name")) *50);
						for(String s : armorstand.action.keySet()) {
							if(s.equals(group)) {
								for(ASBase ab : armorstand.action.get(s)) {
									ab.start = false;
								}
							}
							
						}
					}
					run = not;
				}
			}
			if(getValue("name") != null && getValue("name").equals("null")){
				if(armorstand.sign.size() == 0) {
					if(reset && lsing < System.currentTimeMillis()) {
						lsing = (long) (System.currentTimeMillis() + ((tick*0.05)*1000));
						for(String s : armorstand.action.keySet()) {
							if(s.equals(group)) {
								for(ASBase ab : armorstand.action.get(s)) {
									ab.start = false;
								}
							}
						}
					}
					run = not;
				}
			}
		}
		if(armorstand.tick <= 0){
			run = true;
		}
		return run;
	}
}

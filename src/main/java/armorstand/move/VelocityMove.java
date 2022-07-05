package armorstand.move;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.util.BlockUtils;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ASTarget;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.ASBase;

public class VelocityMove extends ASBase{
	boolean lockyaw = false;
	boolean lockpitch = false;
	boolean only = false;
	LivingEntity target = null;
	
	@Override
	public void set() {
		super.set();
	}
	
	@Override
	public boolean run() {
		boolean run = super.run();
		if(run) {
			boolean shift = false;
			boolean jumping = false;
			float ws = 0;
			float ad = 0;
			boolean move = false;
			String moveType = getValue("type");
			if(moveType == null) moveType = "look";
			
			for(String s : armorstand.sign.keySet()) {
				if(s.contains("move:")) {
					String l = s.split(":")[1];
					ad = Float.parseFloat(l.split(",")[0])*-1;
					ws = Float.parseFloat(l.split(",")[1]);
					jumping = Boolean.parseBoolean(l.split(",")[2]);
					shift = Boolean.parseBoolean(l.split(",")[3]);
					if(ad + ws != 0 || jumping == true || shift == true) {
						move = true;
					}
				}
			}
			if(armorstand.castPlayer.getVelocity().setY(0).distance(new Vector(0,0,0)) > 0.01) move = true;
			if(move) {
				if(getBoolean("fly") || (!getBoolean("fly") && armorstand.armorstand.isOnGround())) {
					Location loc = armorstand.armorstand.getLocation().clone();
					Vector vtr = null;
					
					if(moveType.toLowerCase().equals("key")) {
						loc.setPitch(0);
						loc.setYaw((float) getLoc().getRotate().getY());
						loc = StandLoc.getRelativeOffset(loc.zero(), new Vector(ws,0,ad).multiply(getDouble("speed")));
						vtr = loc.toVector().multiply(getDouble("speed"));
						if(getBoolean("fly") && jumping) vtr.setY(getDouble("speed")*0.1);
						if(getBoolean("fly") && shift) vtr.setY(getDouble("speed")*-0.1);
						
					} else if(moveType.toLowerCase().equals("look")) {
						vtr = armorstand.castPlayer.getLocation().getDirection().multiply(getDouble("speed"));
					}
					
					if(!getBoolean("fly")) vtr.setY(0);
					
					if(!BlockUtils.isPathable(loc.add(vtr).getBlock()) && !getBoolean("fly") || jumping && !getBoolean("fly")) {
						vtr.setY(0.6);
					}
					setVec(vtr);
				} else if(armorstand.armorstand.getFallDistance() < 1 && armorstand.armorstand.getFallDistance() > 0 && !getBoolean("fly")) {
					Location l = getLoc().getLocation();
					l.setPitch((float) getLoc().getRotate().getX());
					l.setYaw((float) getLoc().getRotate().getY());
					setVec(l.getDirection().setY(-0.5).multiply(getDouble("speed")));
				}
				
			}
		}
		
		return run;
	}
	
	public void setVec(Vector vtr) {
		if(!armorstand.armorstand.isDead()) {
			armorstand.armorstand.setVelocity(vtr);
			armorstand.nextLocation.add(vtr.multiply(2));
		}
	}
}

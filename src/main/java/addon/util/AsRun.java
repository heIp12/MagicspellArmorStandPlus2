package addon.util;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import armorstand.BaseArmorStand;

public class AsRun implements Runnable{
	Plugin plugin;
	int id;
	long tick = 0;
	long startTime = 0;
	public List<BaseArmorStand> armorstands;
	List<BaseArmorStand> removeArmorstands;
	List<BaseArmorStand> addArmorstands;
	
	public AsRun(Plugin plugin){
		this.plugin = plugin;
		armorstands = new ArrayList<BaseArmorStand>();
		removeArmorstands = new ArrayList<BaseArmorStand>();
		addArmorstands = new ArrayList<BaseArmorStand>();
		
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 1, 1);
		startTime = System.currentTimeMillis();
	}
	public void stop() {
		Bukkit.getScheduler().cancelTask(id);
	}
	
	@Override
	public void run() {
		tick++;
		if(addArmorstands.size() > 0) {
			for(BaseArmorStand bas : addArmorstands) {
				if(!armorstands.contains(bas)) {
					armorstands.add(bas);
				}
			}
			addArmorstands.clear();
		}

		try {
			for(BaseArmorStand bas : armorstands) {
				if(bas.armorstand == null) {
					removeArmorstands.add(bas);
				}
				bas.run();
			}

		} catch(ConcurrentModificationException e) {

		}
		
		
		if(removeArmorstands.size() > 0) {
			for(BaseArmorStand bas : removeArmorstands) {
				if(armorstands.contains(bas)) {
					bas.remove();
					armorstands.remove(bas);
				}
			}
			removeArmorstands.clear();
		}

	}

	public void removeAdd(BaseArmorStand baseArmorStand) {
		removeArmorstands.add(baseArmorStand);
	}

	public void Add(BaseArmorStand stand) {
		addArmorstands.add(stand);
	}
	
	public long getTick() {
		return tick;
	}
	public double getTime() {
		return (System.currentTimeMillis() - startTime) * 0.001;
	}
	
	public double getTime(Long currentTimeMillis) {
		return (System.currentTimeMillis() - currentTimeMillis) * 0.001;
	}
	public void remove() {
		stop();
		for(BaseArmorStand bas : armorstands) {
			bas.remove();
		}

		removeArmorstands.clear();
		armorstands.clear();
	}
}

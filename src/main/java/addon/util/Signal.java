package addon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import addon.ArmorStandPlus;
import armorstand.BaseArmorStand;

public class Signal {
	
//	public static void parts(BaseArmorStand b){
//		if(b.parts != null && !b.parts.isEmpty()) {
//			for(BaseArmorStand parts : b.parts) {
//				b.sign.put(signal, tick);
//				parts(parts);
//			}
//		}
//	}
	
	
	//type
	public static void signal(BaseArmorStand stand,String signal,int tick) {
		stand.sign.put(signal, tick);
	}
	public static void name_signal(BaseArmorStand stand,String signal) {
		signal(stand,signal.split(":")[0], Integer.parseInt(signal.split(":")[1]));
	}
	public static void loc_signal(BaseArmorStand stand, Location loc,int tick) {
		String signal = "[loc]"+ loc.getX()+","+ loc.getY()+","+ loc.getZ()+","+loc.getYaw()+","+loc.getPitch();
		signal(stand,signal.split(":")[0], tick);
	}
	public static void uuid_signal(BaseArmorStand stand,UUID uuid,int tick) {
		String signal = "[uuid]"+ uuid;
		signal(stand,signal, tick);
	}
	
	public static void signal(List<BaseArmorStand> stand,String signal,int tick) {
		for(BaseArmorStand as : stand) {
			signal(as,signal,tick);
		}
	}
	public static void name_signal(List<BaseArmorStand> stand,String signal) {
		for(BaseArmorStand as : stand) {
			signal(as,signal.split(":")[0], Integer.parseInt(signal.split(":")[1]));
		}
	}
	public static void loc_signal(List<BaseArmorStand> stand, Location loc,int tick) {
		String signal = "[loc]"+ loc.getX()+","+ loc.getY()+","+ loc.getZ()+","+loc.getYaw()+","+loc.getPitch();
		for(BaseArmorStand as : stand) {
			signal(as,signal.split(":")[0], tick);
		}
	}
	public static void uuid_signal(List<BaseArmorStand> stand,UUID uuid,int tick) {
		String signal = "[uuid]"+ uuid;
		for(BaseArmorStand as : stand) {
			signal(as,signal, tick);
		}
	}
	
	//filter
	public static List<BaseArmorStand> F_All() {
		List<BaseArmorStand> stands = new ArrayList<BaseArmorStand>();
		for(BaseArmorStand stand : ArmorStandPlus.timeSystem.armorstands) {
			stands.add(stand);
		}
		return stands;
	}
	public static List<BaseArmorStand> F_Owner(List<BaseArmorStand> stands,Player p) {
		List<BaseArmorStand> removeStands = new ArrayList<BaseArmorStand>();
		for(BaseArmorStand stand : stands) {
			if(stand.castPlayer == p) {
				removeStands.add(stand);
			}
		}
		for(BaseArmorStand stand : removeStands) {
			stands.remove(stand);
		}
		return stands;
	}
	public static List<BaseArmorStand> F_Range(List<BaseArmorStand> stands,Location loc, float range) {
		List<BaseArmorStand> removeStands = new ArrayList<BaseArmorStand>();
		for(BaseArmorStand stand : stands) {
			if(stand.armorstand.getLocation().distance(loc) <= range) {
				removeStands.add(stand);
			}
		}
		for(BaseArmorStand stand : removeStands) {
			stands.remove(stand);
		}
		return stands;
	}
	public static List<BaseArmorStand> F_BRange(List<BaseArmorStand> stands,Location loc, float range) {
		List<BaseArmorStand> removeStands = new ArrayList<BaseArmorStand>();
		for(BaseArmorStand stand : stands) {
			if(stand.armorstand.getLocation().distance(loc) >= range) {
				removeStands.add(stand);
			}
		}
		for(BaseArmorStand stand : removeStands) {
			stands.remove(stand);
		}
		return stands;
	}
	public static List<BaseArmorStand> F_Name(List<BaseArmorStand> stands,String name) {
		List<BaseArmorStand> removeStands = new ArrayList<BaseArmorStand>();
		for(BaseArmorStand stand : stands) {
			if(stand.name.contains(name)) {
				removeStands.add(stand);
			}
		}
		for(BaseArmorStand stand : removeStands) {
			stands.remove(stand);
		}
		return stands;
	}
	
}

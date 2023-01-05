package armorstand;

import org.bukkit.Bukkit;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.types.MoveType;
import addon.util.ConfigRep;
import addon.util.LocAndRotate;

public class ASBase {
	protected BaseArmorStand armorstand;
	
	protected String code;
	
	protected int tick = 100;
	protected boolean delect = false;
	protected MoveType type = MoveType.ALL;
	protected boolean quter = false;
	public boolean start = false;
	public long startTime;
	public String group = "";
	
	public ASBase(){
		code = "tick=10;";
	}
	
	public void set() {
		tick = (int) getDouble("tick");
		if(getValue("tick") == null) tick = armorstand.tick;
		if(getValue("st") != null && getValue("st").equals("true")) {
			startTime = System.currentTimeMillis();
		} else {
			startTime = armorstand.getSpawnTime();
		}
		if(getValue("in") != null && getValue("in").equals("true")) {
			delect = true;
		}
		if(getValue("move") != null && MoveType.valueOf(getValue("move").toUpperCase()) != null) {
			type = MoveType.valueOf(getValue("move").toUpperCase());
		}
		if(getValue("q") != null && getValue("q").equals("true")) {
			quter = true;
		}
	};
	
	public boolean run(){
		if(!start) {
			start = true;
			set();
		}
		if(tick >= 0) {
			tick--;
			if(tick < 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public void setBase(BaseArmorStand armorstand,String group) {
		this.armorstand = armorstand;
		this.group  = group;
	}
	
	public void setCode(String code) {
		this.code = code.replace(" ","").toLowerCase();
	}
	
	public String getValue(String str) {
		for(String s : code.split(";")){
			if(s.indexOf(str+"=") == 0) {
				return s.replace(str+"=", "");
			}
		}
		return null;
	}
	
	public LocAndRotate getLoc() {
		String iloc = getValue("loc");
		if(iloc==null || !iloc.equals("hand")) {
			iloc = "head";
		}
		LocAndRotate lar = armorstand.getLocation(ItemLoc.valueOf(iloc.toUpperCase()));
		lar.setQuater(quter);
		return lar;
	}
	
	public LocAndRotate getSLoc() {
		String iloc = getValue("loc");
		if(iloc==null || !iloc.equals("hand")) {
			iloc = "head";
		}
		LocAndRotate lar = armorstand.getStandLocation(ItemLoc.valueOf(iloc.toUpperCase()));
		lar.setQuater(quter);
		return lar;
	}
	
	public double getDouble(String s) {
		if(getValue(s) != null) {
			return ConfigRep.rep(getValue(s),ArmorStandPlus.timeSystem.getTime(startTime),armorstand.castPlayer);
		} else {
			return 0;
		}
	}
	public double getDouble(String s,double d) {
		if(getValue(s) != null) {
			return ConfigRep.rep(getValue(s),ArmorStandPlus.timeSystem.getTime(startTime),armorstand.castPlayer);
		} else {
			return d;
		}
	}
	public Boolean getBoolean(String s) {
		if(getValue(s) != null && getValue(s).equals("true")) {
			return true;
		} else {
			return false;
		}
	}
}

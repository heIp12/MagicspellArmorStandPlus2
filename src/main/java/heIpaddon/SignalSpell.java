package heIpaddon;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.util.MagicConfig;

import addon.ArmorStandPlus;
import addon.util.ConfigRep;
import addon.util.Signal;
import armorstand.BaseArmorStand;

public class SignalSpell extends InstantSpell{
	List<String> signal = null;
	String type = "all";
	String caster = "my";
	String range = "0";
	String name = "";
	
	public SignalSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		this.signal = getConfigStringList("signals", null);
	    this.type = getConfigString("filter-type", "all");
	    this.caster = getConfigString("filter-caster", "my");
	    this.range = getConfigString("filter-range", "0");
	    this.name = getConfigString("filter-name", "");
	}
	
	@Override
	public PostCastAction castSpell(Player p, SpellCastState arg1, float arg2, String[] arg3) {
		if(caster.contains("my")) {
			signal(p,type,signal);
		}
		if(caster.contains("all")) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				signal(player,type,signal);
			}
		}
		return PostCastAction.HANDLE_NORMALLY;
	}
	
	public void signal(LivingEntity p,String target,List<String> signal) {
		List<BaseArmorStand> stands = Signal.F_All();
		if(!range.equals("0")) {
			stands = Signal.F_Range(stands, p.getLocation(), (float) ConfigRep.rep(range, castTime, p));
		}
		if(!name.equals("")) {
			stands = Signal.F_Name(stands, name);
		}
		
		for(BaseArmorStand a : stands) {
			if(target.equals("owner") || target.equals("all")) {
				for(String s : signal) {
					a.sign.put(s.split(":")[0], Integer.parseInt(s.split(":")[1]));
				}
			}
			if(target.equals("parts") || target.equals("all")) {
				parts(a,signal);
			}
		}
	}
	
	public void signal(Player p,UUID u) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(type.equals("owner") || type.equals("all")) {
				if(a.castPlayer == p && a.caster.getUniqueId().equals(u)) {
					for(String s : signal) {
						a.sign.put(s.split(":")[0], Integer.parseInt(s.split(":")[1]));
					}
				}
			}
			if(type.equals("parts") || type.equals("all")) {
				if(a.castPlayer == p && a.caster.getUniqueId().equals(u)) {
					parts(a,signal);
				}
			}
		}
	}
	static void parts(BaseArmorStand b,List<String> signal){
		if(b.parts != null && !b.parts.isEmpty()) {
			for(BaseArmorStand parts : b.parts) {
				for(String s : signal) {
					parts.sign.put(s.split(":")[0], Integer.parseInt(s.split(":")[1]));
				}
				parts(parts,signal);
			}
		}
	}

}

package heIpaddon;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.util.MagicConfig;

import addon.ArmorStandPlus;
import armorstand.BaseArmorStand;

public class SignalSpell extends InstantSpell{
	List<String> signal = null;
	String target = "all";
	String player = "my";

	public SignalSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		this.signal = getConfigStringList("signals", null);
	    this.target = getConfigString("target", "all");
	    this.player = getConfigString("caster", "my");
	}
	
	@Override
	public PostCastAction castSpell(Player p, SpellCastState arg1, float arg2, String[] arg3) {
		if(player.contains("my")) {
			signal(p,target,signal);
		}
		if(player.contains("all")) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				signal(player,target,signal);
			}
		}
		return PostCastAction.HANDLE_NORMALLY;
	}
	
	public static void signal(Player p,String target,List<String> signal) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(target.equals("owner") || target.equals("all")) {
				if(a.castPlayer == p || p == null) {
					for(String s : signal) {
						a.sign.put(s.split(":")[0], Integer.parseInt(s.split(":")[1]));
					}
				}
			}
			if(target.equals("parts") || target.equals("all")) {
				if(a.castPlayer == p || p == null) {
					parts(a,signal);
				}
			}
		}
	}
	
	public void signal(Player p,UUID u) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(target.equals("owner") || target.equals("all")) {
				if(a.castPlayer == p && a.caster.getUniqueId().equals(u)) {
					for(String s : signal) {
						a.sign.put(s.split(":")[0], Integer.parseInt(s.split(":")[1]));
					}
				}
			}
			if(target.equals("parts") || target.equals("all")) {
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

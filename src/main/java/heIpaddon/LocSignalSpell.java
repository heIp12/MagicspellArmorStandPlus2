package heIpaddon;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;
import com.nisovin.magicspells.util.MagicConfig;

import addon.ArmorStandPlus;
import addon.util.ConfigRep;
import addon.util.Signal;
import armorstand.BaseArmorStand;

public class LocSignalSpell extends InstantSpell implements TargetedLocationSpell, TargetedEntitySpell{
	String tick;
	String name;
	boolean change = true;

	public LocSignalSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		this.tick = getConfigString("tick", "100");
		this.change = getConfigBoolean("change", true);
		this.name = getConfigString("name", "");
	}
	
	@Override
	public PostCastAction castSpell(Player p, SpellCastState arg1, float arg2, String[] arg3) {
		signal(p,p.getLocation());
		return PostCastAction.HANDLE_NORMALLY;
	}
	
	public void signal(LivingEntity p,Location loc) {
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(a.castPlayer == p && a.name.toUpperCase().contains(name.toUpperCase())) {
				if(change) {
					for(String s : a.sign.keySet()) {
						if(s.contains("[loc]")) {
							a.sign.put(s,1);
						}
					}
				}
				Signal.loc_signal(a, loc, (int) ConfigRep.rep(tick, castTime, p));
			}
		}
	}

	@Override
	public boolean castAtEntity(LivingEntity arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean castAtEntity(Player arg0, LivingEntity arg1, float arg2) {
		signal(arg0,arg1.getLocation());
		return false;
	}

	@Override
	public boolean castAtLocation(Location arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean castAtLocation(Player arg0, Location arg1, float arg2) {
		signal(arg0,arg1);
		return false;
	}

}

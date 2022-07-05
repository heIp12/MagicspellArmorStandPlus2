package heIpaddon;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;
import com.nisovin.magicspells.util.MagicConfig;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.ConfigRep;
import addon.util.StandLoc;
import armorstand.BaseArmorStand;

public class ActionSpell extends InstantSpell{

	private List<String> action;
	private boolean myspell;

	public ActionSpell(MagicConfig config, String spellName) {
		super(config, spellName);
	    this.action = getConfigStringList("actions", null);
	    this.myspell = getConfigBoolean("myspell", false);
	}
	
	public List<String> getAction() {
		return action;
	}

	@Override
	public PostCastAction castSpell(Player arg0, SpellCastState arg1, float arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

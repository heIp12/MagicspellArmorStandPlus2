package heIpaddon;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.TargetInfo;
import addon.util.ConfigRep;

public class HealSpell extends TargetedSpell implements TargetedEntitySpell{
	String damage;
	String pdamage;
	String hptype;
	boolean targetself;
	
	public HealSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		this.damage = getConfigString("heal", "0.0");
		this.pdamage = getConfigString("heal-percent", "0.0");
		this.hptype = getConfigString("heal-percent-type", "maxhp");
		this.targetself = getConfigBoolean("target-self", false);
	}

	@Override
	public boolean castAtEntity(LivingEntity arg0, float arg1) {
		return false;
	}

	@Override
	public boolean castAtEntity(Player arg0, LivingEntity arg1, float arg2) {
		if(targetself) {
			heal(arg0);
		} else {
			heal(arg1);
		}
		return true;
	}
	
	public void heal(LivingEntity target) {
		double dmg = ConfigRep.rep(damage, castTime, target);
		double pdmg = ConfigRep.rep(pdamage, castTime, target);

		if(pdmg != 0) {
			if(hptype.equalsIgnoreCase("maxhp")) {
				pdmg *= target.getMaxHealth();
			}
			if(hptype.equalsIgnoreCase("hp")) {
				pdmg *= target.getHealth();
			}
			if(hptype.equalsIgnoreCase("losthp")) {
				pdmg *= (target.getMaxHealth() - target.getHealth());
			}
			dmg += pdmg;
		}
		if(target.getMaxHealth() > target.getHealth() + dmg) {
			target.setHealth(target.getHealth() + dmg);
		} else {
			target.setHealth(target.getMaxHealth());
		}
		playSpellEffects(target, target);
	}

	@Override
	public PostCastAction castSpell(Player player, SpellCastState state, float power, String[] args) {
		if(targetself) {
			heal(player);
		} else {
			TargetInfo<LivingEntity> targets = getTargetedEntity(player, power);
			if (targets == null) {
				// Fail -- no target
				return noTarget(player);
			}
			heal(targets.getTarget());
		}
		return null;
	}
}

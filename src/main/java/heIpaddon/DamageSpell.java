package heIpaddon;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellApplyDamageEvent;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.TargetInfo;
import com.nisovin.magicspells.util.compat.EventUtil;

import addon.event.DamageSpellEvent;
import addon.util.ConfigRep;
import addon.util.Holo;
import addon.util.StandLoc;

public class DamageSpell extends TargetedSpell implements TargetedEntitySpell{
	String damage;
	String pdamage;
	String drain;
	String hptype;
	boolean nodelay;
	boolean targetself;
	boolean hologram;
	String hologramPrefix;
	String hologramSuffix;
	String hologramTime;
	String hologramMove;
	String type;
	String hologramLoc;
	
	public DamageSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		this.damage = getConfigString("damage", "0.0");
		this.pdamage = getConfigString("damage-percent", "0.0");
		this.hptype = getConfigString("damage-percent-type", "maxhp");
		this.drain = getConfigString("drain", "0.0");
		this.nodelay = getConfigBoolean("no-delay", false);
		this.targetself = getConfigBoolean("target-self", false);
		this.type = getConfigString("damage-type", "");
		this.hologram = getConfigBoolean("hologram", false);
		this.hologramPrefix = getConfigString("hologram-prefix", "§cDamage:_§l");
		this.hologramSuffix = getConfigString("hologram-suffix", "§4!!");
		this.hologramTime = getConfigString("hologram-time", "40");
		this.hologramMove = getConfigString("hologram-move", "0,0.02,0");
		this.hologramLoc = getConfigString("hologram-loc", "0,0,0");
	}

	@Override
	public boolean castAtEntity(LivingEntity arg0, float arg1) {
		return false;
	}

	@Override
	public boolean castAtEntity(Player arg0, LivingEntity arg1, float arg2) {
		if(targetself) {
			attack(arg0,arg1);
		} else {
			attack(arg1,arg0);
		}
		return true;
	}
	
	public void attack(LivingEntity target, LivingEntity attaker) {
		double dmg = ConfigRep.rep(damage, castTime, attaker);
		double pdmg = ConfigRep.rep(pdamage, castTime, attaker);
		double Drain =  ConfigRep.rep(drain, castTime, attaker);
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
		
		DamageSpellEvent event = new DamageSpellEvent(attaker,target,(float) dmg, type);
		EventUtil.call(event);
		
		dmg = event.getDamage();
		if(nodelay) target.setNoDamageTicks(0);
		target.damage(dmg, attaker);
		if(Drain > 0) {
			if(attaker.getMaxHealth() > attaker.getHealth() + dmg*Drain) {
				attaker.setHealth(attaker.getHealth() + dmg*Drain);
			} else {
				attaker.setHealth(attaker.getMaxHealth());
			}
		}
		if(hologram) {
			int time = (int) ConfigRep.rep(hologramTime, castTime, attaker);
			Vector move = ConfigRep.rep_Vector(hologramMove, castTime, attaker);
			Vector loc = ConfigRep.rep_Vector(hologramLoc, castTime, attaker);
			Location local = StandLoc.getRelativeOffset(target.getLocation(), loc);
			Holo.create(local, (hologramPrefix+(Math.round(dmg*10.0)/10.0)+hologramSuffix).replace("_", " "), time, move);
		}
		playSpellEffects(attaker, target);
	}

	@Override
	public PostCastAction castSpell(Player player, SpellCastState state, float power, String[] args) {
		if(targetself) {
			attack(player,player);
		} else {
			TargetInfo<LivingEntity> targets = getTargetedEntity(player, power);
			if (targets == null) {
				// Fail -- no target
				return noTarget(player);
			}
			attack(targets.getTarget(),player);
		}
		return null;
	}
}

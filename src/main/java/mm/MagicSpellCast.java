package mm;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.Spell.SpellCastState;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;

import addon.ArmorStandPlus;
import addon.util.NpcPlayer;
import heIpaddon.ArmorStandSpell;
import heIpaddon.SignalSpell;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.INoTargetSkill;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.ITargetedLocationSkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;

public class MagicSpellCast extends SkillMechanic implements ITargetedEntitySkill, ITargetedLocationSkill{
	protected String spellName;
    protected int power = 1;
    protected Spell spell;
    protected TargetedEntitySpell tspell;
    protected TargetedLocationSpell tlspell;
    protected ArmorStandSpell aspell;
    protected SignalSpell sspell;
    
    public MagicSpellCast(String skill, MythicLineConfig mlc)  {
        super(skill, mlc);
    	this.power = config.getInteger(new String[] {"power","p"}, 1);
        this.spellName = config.getString(new String[] {"spell", "s"}, "lightning");
        spell = MagicSpells.getSpellByInternalName(spellName);
        
        if(spell instanceof ArmorStandSpell) {
        	aspell = (ArmorStandSpell) spell;
        }
        else if(spell instanceof SignalSpell) {
        	sspell = (SignalSpell) spell;
        } else if(spell instanceof TargetedEntitySpell) {
        	tspell = (TargetedEntitySpell) spell;
        }
        else if(spell instanceof TargetedLocationSpell) {
        	tlspell = (TargetedLocationSpell) spell;
        }
	}

    public Player caster(SkillMetadata data) {
    	Entity caster = data.getCaster().getEntity().getBukkitEntity();
    	if(caster instanceof Player) {
    		return (Player) caster;
    	}
    	AbstractLocation target = data.getCaster().getLocation();
    	Location loc = new Location(Bukkit.getWorld(target.getWorld().getName()),target.getX(),target.getY(),target.getZ());
    	Player cast = NpcPlayer.npc(loc);
    	return cast;
    }

	@Override
	public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
		Player caster = caster(data);
		if(sspell != null) {
			sspell.signal(caster,data.getCaster().getEntity().getUniqueId());
		} else if(aspell != null) {
			AbstractLocation tl = target.getLocation();
			Entity e = target.getBukkitEntity();
			Location loc = new Location(Bukkit.getWorld(tl.getWorld().getName()),tl.getX(),tl.getY(),tl.getZ());
			Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
				aspell.create((LivingEntity) e, caster, loc);
			});
			
		} else if(tspell != null) {
			Entity e = target.getBukkitEntity();
			if(!(e instanceof LivingEntity)) return false;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
				tspell.castAtEntity(caster,(LivingEntity) e, power);
			});
			return true;
		} else if(tlspell != null) {
			AbstractLocation tl = target.getLocation();
			Location loc = new Location(Bukkit.getWorld(tl.getWorld().getName()),tl.getX(),tl.getY(),tl.getZ());
			Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
				tlspell.castAtLocation(caster,loc, power);
			});
			return true;
		}
		return false;
	}

	@Override
	public boolean castAtLocation(SkillMetadata data, AbstractLocation target) {
		if(tlspell != null) {
			Location loc = new Location(Bukkit.getWorld(target.getWorld().getName()),target.getX(),target.getY(),target.getZ());
			Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
				tlspell.castAtLocation(caster(data),loc, power);
			});
			return true;
		}
		return false;
	}


}

package sk;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import addon.event.DamageSpellEvent;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class skETarget extends SimpleExpression<LivingEntity>{

	@Override
	public Class<? extends LivingEntity> getReturnType() {
		return LivingEntity.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(Event arg0, boolean arg1) {
		return "s[pell][-]target";
	}

	@Override
	protected LivingEntity[] get(Event event) {
		if (!(event instanceof DamageSpellEvent)) return null;
		DamageSpellEvent e = (DamageSpellEvent)event;
		if (!(e.getAttaker() instanceof LivingEntity)) return null;
		return new LivingEntity[] { (LivingEntity)e.getTarget() };
	}
}

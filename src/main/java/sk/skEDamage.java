package sk;

import org.bukkit.event.Event;

import addon.event.DamageSpellEvent;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class skEDamage extends SimpleExpression<Number>{
	Expression<Float> setDamage = null;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
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
		return "s[pell][-]damage";
	}

	@Override
	protected Number[] get(Event event) {
		if (!(event instanceof DamageSpellEvent)) {
		      return new Number[] { Integer.valueOf(-1) };
		}
		DamageSpellEvent e = (DamageSpellEvent)event;
		if(setDamage != null) {
			Float damage = Float.parseFloat(setDamage.toString());
			e.setDamage(damage);
			return new Number[] { Double.valueOf(e.getDamage()) };
		} else {
			return new Number[] { Double.valueOf(e.getDamage()) };
		}
	}

}

package sk;

import org.bukkit.event.Event;

import addon.event.DamageSpellEvent;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class skEDamageSet extends Effect{
	Expression<Float> setDamage = null;

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		setDamage = (Expression<Float>) arg0[0];
		return true;
	}

	@Override
	public String toString(Event arg0, boolean arg1) {
		return "s[pell][-]damage set %number%";
	}

	@Override
	protected void execute(Event event) {
		DamageSpellEvent e = (DamageSpellEvent)event;
		if(setDamage.getSingle(e) != null) {
			Number damage = (Number)setDamage.getSingle(e);
			e.setDamage(Float.parseFloat(""+damage));
		}
	}

}

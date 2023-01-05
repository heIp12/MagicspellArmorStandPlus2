package sk;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import addon.event.DamageSpellEvent;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class skEType extends Condition{
	private Expression<String> type = null;

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.type = (Expression<String>) arg0[0];
		return true;
	}

	@Override
	public String toString(Event arg0, boolean arg1) {
		return "if attack type is %string%";
	}


	@Override
	public boolean check(Event event) {
		if (!(event instanceof DamageSpellEvent)) return false;
		DamageSpellEvent e = (DamageSpellEvent)event;
		return e.getType(type.getSingle(e));
	}
}

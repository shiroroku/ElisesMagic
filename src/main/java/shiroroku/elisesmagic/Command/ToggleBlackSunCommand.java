package shiroroku.elisesmagic.Command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import shiroroku.elisesmagic.World.BlackSunHandler;
import shiroroku.elisesmagic.ElisesMagic;

public class ToggleBlackSunCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		LiteralArgumentBuilder<CommandSourceStack> emsuntoggle = Commands.literal("em_ToggleBlackSun").requires((commandSource) -> commandSource.hasPermission(2)).executes(ToggleBlackSunCommand::toggleSun);
		dispatcher.register(emsuntoggle);
	}

	private static int toggleSun(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {
		BlackSunHandler.setEnabled(commandSourceStackCommandContext.getSource().getLevel(), !BlackSunHandler.isEnabled(commandSourceStackCommandContext.getSource().getLevel()));
		ElisesMagic.LOGGER.debug("Toggling black sun");
		return 1;
	}

}

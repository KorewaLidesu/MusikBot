package de.blockbuild.musikbot.commands.setup;

import com.jagrosh.jdautilities.command.CommandEvent;

import de.blockbuild.musikbot.Bot;
import de.blockbuild.musikbot.commands.SetupCommand;

public class ConfigCommand extends SetupCommand {

	public ConfigCommand(Bot bot) {
		super(bot);
		this.name = "config";
		this.aliases = new String[] { "cfg" };
		this.help = "To load, save or show the Config";
		this.arguments = "<save|load|show>";
	}

	@Override
	protected void doGuildCommand(CommandEvent event) {
		StringBuilder builder = new StringBuilder();
		if (args.startsWith("save")) {
			if (musicManager.config.writeConfig()) {
				builder.append(event.getClient().getSuccess()).append(" Config saved successfully");
				event.reply(builder.toString());
			} else {
				builder.append(event.getClient().getError()).append(" Faild to save config");
				event.reply(builder.toString());
			}
		} else if (args.startsWith("load")) {
			if (musicManager.config.readConfig()) {
				builder.append(event.getClient().getSuccess()).append(" Config load successfully");
				event.reply(builder.toString());
			} else {
				builder.append(event.getClient().getError()).append(" Faild to load config");
				event.reply(builder.toString());
			}
		} else if (args.startsWith("show")) {
			builder.append(event.getClient().getSuccess()).append(" ");

			if (event.isOwner()) {
				builder.append("**Bot Configuration**").append("\n");
				builder.append(bot.config.getRawConfiguration()).append("\n");
			}

			builder.append("**Guild Configuration**").append("\n");
			builder.append(musicManager.config.getRawConfiguration()).append("\n");
			event.replyInDm(builder.toString());
		} else {
			sendCommandInfo(event);
		}
	}

	@Override
	protected void doPrivateCommand(CommandEvent event) {
		event.reply(event.getClient().getError() + " This command cannot be used in Direct messages.");

		StringBuilder builder = new StringBuilder().append(event.getClient().getSuccess());

		builder.append(" **MusikBot** ").append("by Block-Build\n");
		builder.append("SpigotMC: `https://www.spigotmc.org/resources/the-discord-musikbot-on-minecraft.64277/`\n");
		builder.append("GitHub: `https://github.com/Block-Build/MusikBot`\n");
		builder.append("Version: `").append(bot.getMain().getDescription().getVersion()).append("`\n");
		builder.append("Do you have any problem or suggestion? Open an Issue on GitHub.");

		event.reply(builder.toString());
	}
}

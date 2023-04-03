package pl.foxey.tasks;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pl.foxey.config.Config;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class IdeasTask extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if (!e.getAuthor().isBot()) {
            String user = e.getMessage().getAuthor().getAsMention();
            String content = e.getMessage().getContentRaw();
            TextChannel chan = e.getGuild().getTextChannelById("1090789424385761280");
            e.getMessage().delete().queueAfter(1L, TimeUnit.MILLISECONDS);
            if (e.getChannel().getId().equals("1090789424385761280")) {
                EmbedBuilder x = new EmbedBuilder();
                x.setColor(Color.decode(Config.embedColorAll));
                x.setThumbnail("https://i.imgur.com/6OJwH9I.png");
                x.setTitle("***Nowa Propozycja***");
                x.setDescription("***Autor:***" +" "+ user +"\n"+
                        "```"+content+"```");
                chan.sendMessageEmbeds(x.build()).queue(message -> {
                    message.addReaction(Emoji.fromUnicode("\u2705")).queue();
                    message.addReaction(Emoji.fromUnicode("\u274C")).queue();
                });
            }
        }
    }
}

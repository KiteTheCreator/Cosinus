package pl.foxey.tasks;

import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pl.foxey.config.Config;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeMessageSender extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent e) {
        EmbedBuilder welcome = new EmbedBuilder();
        Date nowDate = new Date();
        SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Role nover = e.getGuild().getRoleById("1090776706496876604");
        //Przypisanie roli do użytkownika który dołączył na serwer
        e.getGuild().addRoleToMember(e.getUser(), nover).queue();
        welcome.setTitle("Witaj! 😀" + " " + e.getUser().getAsTag());
        welcome.setDescription("Zachęcamy do sprawdzenia następujących kanałów:\n" +
                "<#1090786293056819310> - Zapoznanie się z regulaminem serwera jest obowiązkowe.\n" +
                "<#1090786205127417856> - Tutaj pojawią się najważniejsze informacje na temat turnieju.\n" +
                "<#1090789512684249128> - Utwórz zgłoszenie, na które odpowiedzą Ci administratorzy.\n" +
                "<#1090789325509238884> - Porozmawiaj z innymi użytkownikami serwera.\n" +
                "<#1090789140230058024> - Wybierz sektor Ligi Cosinusa, który najbardziej Cie interesuje.\n" +
                "\n");
        welcome.setFooter("© Liga Cosinus •" + " " + " "+ sdf4.format(nowDate), "https://i.imgur.com/6OJwH9I.png");
        welcome.setThumbnail("https://i.imgur.com/6OJwH9I.png");
        welcome.setColor(Color.decode(Config.embedColorAll));
        e.getGuild().getTextChannelById("1090788139561394249").sendMessageEmbeds(welcome.build()).queue();

    }
}

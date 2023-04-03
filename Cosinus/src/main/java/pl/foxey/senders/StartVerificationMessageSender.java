package pl.foxey.senders;

import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;
import pl.foxey.config.Config;
import pl.foxey.utils.DataHelper;
import pl.foxey.utils.TimeHelper;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;


public class StartVerificationMessageSender extends ListenerAdapter {

    Random liczba = new Random();
    int kod = liczba.nextInt(1000, 9999);


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        String[] a = e.getMessage().getContentRaw().split(" ");
        EmbedBuilder x = new EmbedBuilder();
        Date nowDate = new Date();
        SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        if (a[0].equalsIgnoreCase(Config.commands.ver_command)) {
            e.getMessage().delete().queue();
            x.setTitle("‼️Weryfikacja konta‼️");
            x.setColor(Color.decode(Config.embedColorAll));
            x.setDescription("Witaj, aby się zweryfikować musisz zaakceptować regulamin serwera reagując przyciskiem poniżej. Ranga którą dostaniesz, ujawni resztę kanałów Discord!");
            x.setFooter("© Liga Cosinus •" + " " + " "+ sdf4.format(nowDate), "https://i.imgur.com/6OJwH9I.png");
            e.getChannel().sendMessageEmbeds(x.build()).setActionRow(Button.primary("verstart", " ✅ Zweryfikuj się!( 💻 Komputer )"), Button.primary("verstartphone", " ✅ Zweryfikuj się!(📱 Telefon )")).queue();
        }
    }
        private final HashMap<User, Long> nextUsage = new HashMap<>();

        @Override
        public void onButtonInteraction (@NotNull ButtonInteractionEvent e){
            EmbedBuilder z = new EmbedBuilder();
            EmbedBuilder code = new EmbedBuilder();
            code.setColor(Color.decode(Config.embedColorAll));
            Date nowDate = new Date();
            SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            EmbedBuilder butt = new EmbedBuilder();
            code.setDescription("Przepisz kod z guzika, aby się zweryfikować!");
            code.setColor(Color.decode(Config.embedColorAll));
            code.setFooter("© Liga Cosinus •" + " " + " "+ sdf4.format(nowDate), "https://i.imgur.com/6OJwH9I.png");
            butt.setDescription("Naciśnij guzik, aby się zweryfikować!");
            butt.setColor(Color.decode(Config.embedColorAll));
            butt.setFooter("© Liga Cosinus •" + " " + " "+ sdf4.format(nowDate), "https://i.imgur.com/6OJwH9I.png");
            if (e.getComponentId().equalsIgnoreCase("verstart")){
                e.replyEmbeds(code.build()).setActionRow(Button.secondary("kodzik", String.valueOf(kod)), Button.success("verbutton", "Przepisz kod!")).setEphemeral(true).queue();
            }
            if (e.getComponentId().equalsIgnoreCase("verstartphone")) {
                e.replyEmbeds(butt.build()).setActionRow(Button.success("verphonesuccess", "📱Zweryfikuj mnie")).setEphemeral(true).queue();
            }
            if (e.getComponentId().equalsIgnoreCase("verphonesuccess")){
                Role usr = e.getGuild().getRoleById("1090776496991371285");
                Role ver = e.getGuild().getRoleById("1090776823262101534");
                Role nover = e.getGuild().getRoleById("1090776706496876604");
                EmbedBuilder ebsucctel = new EmbedBuilder();
                ebsucctel.setColor(Color.decode(Config.embedColorAll));
                ebsucctel.setDescription("Pomyślnie zweryfikowałeś swoje konto");
                ebsucctel.setFooter("© Liga Cosinus •" + " " + " "+ sdf4.format(nowDate), "https://i.imgur.com/6OJwH9I.png");
                e.getGuild().addRoleToMember(e.getUser(), ver).queue();
                e.getGuild().addRoleToMember(e.getUser(), usr).queue();
                e.getGuild().removeRoleFromMember(e.getUser(), nover).queue();
                e.replyEmbeds(ebsucctel.build()).setEphemeral(true).queue();
            }
            if (e.getComponentId().equalsIgnoreCase("verbutton")) {
                if (!nextUsage.containsKey(e.getUser())) nextUsage.put(e.getUser(), 10L);
                if (nextUsage.get(e.getUser()) > System.currentTimeMillis()) {
                    e.reply("`#` An error has occured! Wait more:: **" + DataHelper.durationToString(nextUsage.get(e.getUser()) - System.currentTimeMillis(), true) + "**").setEphemeral(true).queue();
                    return;
                }
                nextUsage.put(e.getUser(), System.currentTimeMillis() + TimeHelper.MINUTE.getTime());
                TextInput verkod = TextInput.create("ver-kod", "Kod", TextInputStyle.SHORT)
                        .setPlaceholder("Kod Werfikacyjny")
                        .setMinLength(1)
                        .setMaxLength(4)
                        .setRequired(true)
                        .build();
                Modal verification = Modal.create("verification", "Weryfikacja: " + e.getMember().getUser().getAsTag())
                        .addActionRows(ActionRow.of(verkod))
                        .build();
                e.replyModal(verification).queue();
            }
        }

        @Override
        public void onModalInteraction (@NonNull ModalInteractionEvent e){
            if (e.getModalId().equals("verification")) {
                String verkod = e.getValue("ver-kod").getAsString();

                int verkodint = Integer.parseInt(verkod);
                Member user = e.getMember();
                Role usr = e.getGuild().getRoleById("1090776496991371285");
                Role ver = e.getGuild().getRoleById("1090776823262101534");
                Role nover = e.getGuild().getRoleById("1090776706496876604");
                Date nowDate = new Date();
                SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                EmbedBuilder ebsuccnorm = new EmbedBuilder();
                EmbedBuilder ebsdellnorm = new EmbedBuilder();
                ebsuccnorm.setColor(Color.decode(Config.embedColorAll));
                ebsuccnorm.setDescription("Pomyślnie zweryfikowałeś swoje konto");
                ebsuccnorm.setFooter("© Liga Cosinus •" + " " + " "+ sdf4.format(nowDate), "https://i.imgur.com/6OJwH9I.png");
                ebsdellnorm.setColor(Color.RED);
                ebsdellnorm.setDescription("Podałeś błędny kod weryfikacyjny, Spróbuj jeszcze raz!");
                ebsdellnorm.setFooter("© Liga Cosinus •" + " " + " "+ sdf4.format(nowDate), "https://i.imgur.com/6OJwH9I.png");
                if (verkodint == kod) {
                    e.getGuild().addRoleToMember(e.getUser(), ver).queue();
                    e.getGuild().addRoleToMember(e.getUser(), usr).queue();
                    e.getGuild().removeRoleFromMember(e.getUser(), nover).queue();
                    e.replyEmbeds(ebsuccnorm.build()).setEphemeral(true).queue();
                } else if (verkodint != kod) {
                    e.replyEmbeds(ebsdellnorm.build()).setEphemeral(true).queue();
                }

            }
        }
    }




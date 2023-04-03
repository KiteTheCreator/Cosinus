package pl.foxey.tasks;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;
import pl.foxey.config.Config;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

public class StartTicketTask extends ListenerAdapter {


    //TODO: tickety nowy system wprowadzic oparty na StringSelectMenu  + ModalInteraction + onButtonInteraction // TODO: do skończenia UI + OBI + Zamykanie ticketów 
    //TODO: Kanały pomysły

    public void onMessageReceived(MessageReceivedEvent e) {
        String[] a = e.getMessage().getContentRaw().split(" ");
        EmbedBuilder x = new EmbedBuilder();
        if (a[0].equalsIgnoreCase(Config.commands.sender_command)) {
            e.getMessage().delete().queue();
            x.setTitle("***Liga Cosinus x Tickety***");
            x.setColor(Color.decode(Config.embedColorAll));
            x.setDescription("`→` Aby stworzyć swojego ticketa musisz wybrać opcje.\n"+
                    "`→` Pamietaj, że nadużywanie ticketów bedzie karane.\n" +
                    "`→`Możesz posiadać maksymalnie 1 otwartego ticketa.");
            x.setThumbnail("https://i.imgur.com/6OJwH9I.png");
            e.getChannel().sendMessageEmbeds(x.build()).setActionRow(
                    StringSelectMenu.create("choose-help")
                            .addOption("Sprawa turnieju", "Sprawa turnieju", "Aby przejść dalej musisz wybrać opcje", Emoji.fromUnicode("\uD83C\uDF9F\uFE0F"))
                            .addOption("Potrzebuję pomocy", "Potrzebuję pomocy", "Aby przejść dalej musisz wybrać opcje", Emoji.fromUnicode("\uD83C\uDF9F\uFE0F"))
                            .addOption("Współpraca", "Współpraca", "Aby przejść dalej musisz wybrać opcje", Emoji.fromUnicode("\uD83C\uDF9F\uFE0F"))
                            .build()
            ).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent e) {
        if (e.getComponent().getId().equals("choose-help")) {
            for (int i = 0; i < e.getValues().size(); i++) {
                switch (e.getValues().get(i)) {
                    case "Sprawa turnieju":

                        TextInput problem = TextInput.create("ticket-problem-sprawa", "Wiadomość: ", TextInputStyle.PARAGRAPH)
                                .setPlaceholder("Opisz swój problem.")
                                .setMinLength(16)
                                .setMaxLength(512)
                                .setRequired(true)
                                .build();
                        Modal sprawaturniej = Modal.create("sprawaturniej", "Ticket Sprawy Turniejowej: " + " " + e.getMember().getUser().getAsTag())
                                .addActionRows( ActionRow.of(problem))
                                .build();
                        e.replyModal(sprawaturniej).queue();
                        break;
                    case "Potrzebuję pomocy":

                        TextInput problempomoc = TextInput.create("ticket-problem-pomoc", "Wiadomość: ", TextInputStyle.PARAGRAPH)
                                .setPlaceholder("Opisz swój problem.")
                                .setMinLength(16)
                                .setMaxLength(512)
                                .setRequired(true)
                                .build();
                        Modal pomoc = Modal.create("pomoc", "Ticket Współpracy: " + " " + e.getMember().getUser().getAsTag())
                                .addActionRows(ActionRow.of(problempomoc))
                                .build();
                        e.replyModal(pomoc).queue();
                        break;
                    case "Współpraca":
                        TextInput problemwsp = TextInput.create("ticket-problem-wsp", "Wiadomość: ", TextInputStyle.PARAGRAPH)
                                .setPlaceholder("Opisz swój problem.")
                                .setMinLength(16)
                                .setMaxLength(512)
                                .setRequired(true)
                                .build();
                        Modal wsp = Modal.create("wsp", "Ticket Współpracy: " + " " + e.getMember().getUser().getAsTag())
                                .addActionRows( ActionRow.of(problemwsp))
                                .build();
                        e.replyModal(wsp).queue();
                        break;
                }
            }
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {
        if (e.getModalId().equals("sprawaturniej")) {
            String body = e.getValue("ticket-problem-sprawa").getAsString();
            String name = "Sprawa:  " + e.getUser().getAsTag();
            EmbedBuilder zwrot = new EmbedBuilder();
            zwrot.setColor(Color.decode(Config.embedColorAll));
            zwrot.setDescription("Twój ticket został utworzony");
            e.replyEmbeds(zwrot.build()).setEphemeral(true).queue();
            TextChannel ticketChannel = e.getGuild().getCategoryById(Config.categories.tickets_spraw).createTextChannel(name).complete();
            ticketChannel.upsertPermissionOverride(e.getMember()).setAllowed(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND).queue();
            ticketChannel.getManager().setSlowmode(5).complete();
            EmbedBuilder x = new EmbedBuilder();
            Date nowDate = new Date();
            SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy • HH:mm");
            x.setColor(Color.decode(Config.embedColorAll));
            x.setTitle("👾 Sprawa Turniejowa: " + " "+e.getMember().getUser().getAsTag());
            x.addField("Data ticketu: ",sdf4.format(nowDate),true);
            x.addField("Użytkownik: ", e.getMember().getAsMention(), true);
            x.addField("Wiadomość: ", "```"+body+"```", false);
            x.setThumbnail("https://i.imgur.com/6OJwH9I.png");
            ticketChannel.sendMessageEmbeds(x.build()).setActionRow(Button.danger(Config.buttons.ticket_close_button_id, Config.messages.ticket_close_button_message)).queue();
            x.clear();
        }
        if (e.getModalId().equals("pomoc")){
            String bodypom = e.getValue("ticket-problem-pomoc").getAsString();
            String namepom = "Pomoc:  " + e.getUser().getAsTag();
            EmbedBuilder zwrot = new EmbedBuilder();
            zwrot.setColor(Color.decode(Config.embedColorAll));
            zwrot.setDescription("Twój ticket został utworzony");
            e.replyEmbeds(zwrot.build()).setEphemeral(true).queue();
            TextChannel ticketChannel = e.getGuild().getCategoryById(Config.categories.tickets_help).createTextChannel(namepom).complete();
            ticketChannel.upsertPermissionOverride(e.getMember()).setAllowed(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND).queue();
            ticketChannel.getManager().setSlowmode(5).complete();
            EmbedBuilder x = new EmbedBuilder();
            Date nowDate = new Date();
            SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy • HH:mm");
            x.setColor(Color.decode(Config.embedColorAll));
            x.setTitle("🛟 Sprawa Pomocy: " + " "+e.getMember().getUser().getAsTag());
            x.addField("Data ticketu: ",sdf4.format(nowDate),true);
            x.addField("Użytkownik: ", e.getMember().getAsMention(), true);
            x.addField("Wiadomość: ", "```"+bodypom+"```", false);
            x.setThumbnail("https://i.imgur.com/6OJwH9I.png");
            ticketChannel.sendMessageEmbeds(x.build()).setActionRow(Button.danger(Config.buttons.ticket_close_button_id, Config.messages.ticket_close_button_message)).queue();
            x.clear();
        }
        if (e.getModalId().equals("wsp")){
            String bodywsp = e.getValue("ticket-problem-wsp").getAsString();
            String namewsp = "Współpraca:  " + e.getUser().getAsTag();
            EmbedBuilder zwrot = new EmbedBuilder();
            zwrot.setColor(Color.decode(Config.embedColorAll));
            zwrot.setDescription("Twój ticket został utworzony");
            e.replyEmbeds(zwrot.build()).setEphemeral(true).queue();
            TextChannel ticketChannel = e.getGuild().getCategoryById(Config.categories.tickets_partners).createTextChannel(namewsp).complete();
            ticketChannel.upsertPermissionOverride(e.getMember()).setAllowed(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND).queue();
            ticketChannel.getManager().setSlowmode(5).complete();
            EmbedBuilder x = new EmbedBuilder();
            Date nowDate = new Date();
            SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy • HH:mm");
            x.setColor(Color.decode(Config.embedColorAll));
            x.setTitle("💸 Sprawa Współpracy: " + " "+e.getMember().getUser().getAsTag());
            x.addField("Data ticketu: ",sdf4.format(nowDate),true);
            x.addField("Użytkownik: ", e.getMember().getAsMention(), true);
            x.addField("Wiadomość: ", "```"+bodywsp+"```", false);
            x.setThumbnail("https://i.imgur.com/6OJwH9I.png");
            ticketChannel.sendMessageEmbeds(x.build()).setActionRow(Button.danger(Config.buttons.ticket_close_button_id, Config.messages.ticket_close_button_message)).queue();
            x.clear();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {
        if (e.getComponentId().equalsIgnoreCase(Config.buttons.ticket_close_button_id)) {
            TextChannel adminchan = e.getGuild().getTextChannelById("1090786369363792023");
            TextChannel ticketchan = e.getChannel().asTextChannel();
            String user = e.getUser().getAsMention();
            e.getChannel().delete().queueAfter(12L, TimeUnit.HOURS);
            EmbedBuilder adminlog = new EmbedBuilder();
            EmbedBuilder log = new EmbedBuilder();
            log.setColor(Color.decode(Config.embedColorAll));
            log.setTitle("❌ ***Zamknięcie Ticketu*** ❌");
            log.setDescription("Ticket został zamknięty przez: \n" + " \n" +
                    user);
            log.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            adminlog.setColor(Color.decode(Config.embedColorAll));
            adminlog.setTitle("❌ ***Zamknięcie Ticketu*** ❌");
            adminlog.setDescription("Ticket: "+ ticketchan.getName()+ " " +"został zamknięty przez: \n"  +
                    user);
            adminlog.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            adminlog.setThumbnail("https://i.imgur.com/6OJwH9I.png");
            adminchan.sendMessageEmbeds(adminlog.build()).queue();
            ticketchan.sendMessage("||@everyone||").addEmbeds(log.build()).queue();
            EmbedBuilder done = new EmbedBuilder();
            done.setColor(Color.decode(Config.embedColorAll));
            done.setDescription("Ticket zostanie zamknięty za 12 godzin");
            e.replyEmbeds(done.build()).queue();
        }
    }
}

package pl.foxey.tasks;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;
import pl.foxey.config.Config;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandMNG extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        String command = e.getName();

        if (command.equals("ogłoszenie")){

            TextInput content = TextInput.create("acccontent", "Zawartość Ogłoszenia: ", TextInputStyle.SHORT)
                    .setPlaceholder("Wpisz co chcesz ogłosić")
                    .setMinLength(0)
                    .setMaxLength(1024)
                    .setRequired(true)
                    .build();

            Modal acc = Modal.create("accnc", "📣Ogłoszenie📣").addActionRows(ActionRow.of(content)).build();
            e.replyModal(acc).queue();

        } else if (command.equals("rules")) {
            EmbedBuilder rules = new EmbedBuilder();
            rules.setTitle("📚 Regulamin 📚");
            rules.setDescription("1. **Postanowienia Ogólne**\n" +
                    "  1.1 Nieprzestrzeganie poniższego regulaminu wiąże się z otrzymaniem kary.\n" +
                    "  1.2 Nieznajomość regulaminu nie zwalnia z jego przestrzegania.\n" +
                    "  1.3 Administracja ma pełne prawa do zmieniania treści regulaminu bez wcześniejszego\n" +
                    "      powiadomienia użytkowników o zmianie.\n" +
                    "  1.4 Niniejszy regulamin wchodzi w życie z dniem 30 marca 2023 roku.\n" +
                    "2. **Zasady kanałów tekstowych**\n" +
                    "  2.1 Zakazane jest spamowanie i floodowanie.\n" +
                    "  2.2 Zabrania się pisania wielkimi literami. (CapsLock)\n" +
                    "  2.3 Zakaz używania wulgaryzmów na kanałach tekstowych, a także głosowych.\n" +
                    "  2.4 Zakazane jest prowokowanie kłótni, dyskusji które mają negatywny wpływ na serwer.\n" +
                    "  2.5 Zakaz wykorzystywania, oszukiwania i szantażowania innych użytkowników.\n" +
                    "  2.6 Zabroniony jest wszelkiego rodzaju trolling oraz inne formy zachowań anty społecznych, które\n" +
                    "      służą za przynętę do prowokowania (§2.4) różnych użytkowników.\n" +
                    "  2.7 Zakaz obrażania graczy, administracji i serwera oraz działania na ich szkody.\n" +
                    "  2.8 Reklamowanie jakichkolwiek serwerów zewnętrznych: gier, stron www, serwerów discord itp. bez\n" +
                    "      pisemnej zgody właścicieli jest karalne.\n" +
                    "  2.9 Zakaz wykorzystywania możliwych błędów na serwerze. Należy je natychmiast bezzwłocznie\n" +
                    "      zgłosić administracji z zachowaniem poufności wobec osób trzecich.\n" +
                    "  2.10 Zakazane jest poruszanie tematów wulgarnych/erotycznych/religijnych/rasistowskich itp.\n" +
                    "  2.11 Podszywanie się pod graczy będzie karane kickiem, następnie banem. Podszywanie się pod\n" +
                    "       administrację będzie skutkowało natychmiastowym banem.\n" +
                    "  2.12 Komend można używać tylko na kanale do tego stworzonym.\n" +
                    "2.13 Zakaz pisania na rzeczy niezgodnych z tematyką kanału.\n" +
                    "  2.14 Zabronione jest wysyłanie linków lub plików zawierających jakiekolwiek treści\n" +
                    "       wulgarne/rasistowskie/pornograficzne/religijne itp. oraz plików szkodliwych (wirusy).\n" +
                    "  2.15 Awatar oraz nick nie może zawierać treści obraźliwych/rasistowskich/wulgarnych itp.\n" +
                    "  2.17 Przeszkadzanie administracji jest surowo karane.\n" +
                    "3. **Zasady kanałów głosowych**\n" +
                    "  3.1 Wszystkie zasady kanałów tekstowych obowiązują także w głosowych.\n" +
                    "  3.2 Zakaz krzyczenia i mocnego podnoszenia głosu.\n" +
                    "  3.3 Zakazane jest puszczanie do mikrofonu muzyki itp.\n" +
                    "  3.4 Zabrania się puszczania różnych bliżej nieokreślonych dźwięków, przesterów itp.\n");
            rules.setColor(Color.decode(Config.embedColorAll));
            rules.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            e.getChannel().sendMessageEmbeds(rules.build()).queue();
            e.reply("Pomyślnie stworzyłeś regulamin serwera");
        } else if (command.equals("regulamintur")) {
            EmbedBuilder turrules = new EmbedBuilder();
            turrules.setTitle("🎮 Zasady Turnieju 🎮");
            turrules.setDescription("https://cutt.ly/78bz5B2");
            turrules.setColor(Color.decode(Config.embedColorAll));
            turrules.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            e.getChannel().sendMessageEmbeds(turrules.build()).queue();
            e.reply("Pomyślnie stworzyłeś regulamin turnieju");
        } else if (command.equals("zgody")){
            EmbedBuilder chujci = new EmbedBuilder();
            chujci.setTitle("Zgody do Uczestnictwa");
            chujci.setDescription("https://cutt.ly/S8bO8aU");
            chujci.setColor(Color.decode(Config.embedColorAll));
            chujci.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            e.getChannel().sendMessageEmbeds(chujci.build()).queue();
            e.reply("Wysłałeś zgody na kanał");
        } else if (command.equals("content-roles-channel")) {
            EmbedBuilder roles = new EmbedBuilder();
            roles.setColor(Color.decode(Config.embedColorAll));
            roles.setTitle("👾 ***Wybór roli*** 👾");
            roles.setDescription("Wybierz grę w której chcesz uczestniczyć w turnieju i dostawać powiadomienia oraz aktualizację.");
            roles.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            e.getChannel().sendMessageEmbeds(roles.build()).setActionRow(
                    StringSelectMenu.create("choose-role")
                            .addOption("Fortnite", "Fortnite", "Dostęp do kategorii Fortnite", Emoji.fromUnicode("\uD83D\uDC51"))
                            .addOption("Minecraft", "Minecraft", "Dostęp do kategorii Minecraft", Emoji.fromUnicode("\uD83C\uDF33"))
                            .build()
            ).queue();
            e.reply("OK").setEphemeral(true).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent e) {
        if (e.getComponent().getId().equals("choose-role")) {
            EmbedBuilder accpf = new EmbedBuilder();
            EmbedBuilder accpm = new EmbedBuilder();
            accpf.setTitle("Wybór roli Fortnite");
            accpf.setDescription("Czy na pewno chcesz wybrać role Fortnite");
            accpf.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            accpf.setColor(Color.decode(Config.embedColorAll));
            accpm.setTitle("Wybór roli Minecraft");
            accpm.setDescription("Czy na pewno chcesz wybrać role Miecraft");
            accpm.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            accpm.setColor(Color.decode(Config.embedColorAll));
            for (int i = 0; i < e.getValues().size(); i++) {
                switch (e.getValues().get(i)) {
                    case "Fortnite":
                        e.replyEmbeds(accpf.build()).setActionRow(Button.success("AkceptujeFortnite", "✅ Akceptuje")).setEphemeral(true).queue();
                        break;
                    case "Minecraft":
                        e.replyEmbeds(accpm.build()).setActionRow(Button.success("AkceptujeMinecraft", "✅ Akceptuje")).setEphemeral(true).queue();
                        break;
                }
            }
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {
        Role fort = e.getGuild().getRoleById("1090935828332687381");
        Role mine = e.getGuild().getRoleById("1090936258022363136");
        EmbedBuilder good = new EmbedBuilder();
        good.setDescription("Poprawnie nadano role");
        good.setColor(Color.decode(Config.embedColorAll));
        if (e.getComponentId().equalsIgnoreCase("AkceptujeFortnite")){
            e.getGuild().addRoleToMember(e.getUser(), fort).queue();
            e.replyEmbeds(good.build()).setEphemeral(true).queue();
        }
        if (e.getComponentId().equalsIgnoreCase("AkceptujeMinecraft")){
            e.getGuild().addRoleToMember(e.getUser(), mine).queue();
            e.replyEmbeds(good.build()).setEphemeral(true).queue();
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {
        if (e.getModalId().equals("accnc")) {
            String acccont = e.getValue("acccontent").getAsString();
            EmbedBuilder acc = new EmbedBuilder();
            EmbedBuilder succ = new EmbedBuilder();
            Date nowDate = new Date();
            SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy ・");
            acc.setTitle(":mega: Ogłoszenie :mega:");
            acc.addField("Data ogłoszenia: ", sdf4.format(nowDate), true);
            acc.addField("Administrator: ", e.getMember().getAsMention(), true);
            acc.addField("Ogłoszenie: ", "```" + acccont + "```", false);
            acc.setColor(Color.decode(Config.embedColorAll));
            acc.setFooter("© Liga Cosinus", "https://i.imgur.com/6OJwH9I.png");
            succ.setColor(Color.decode(Config.embedColorAll));
            succ.setDescription("Pomyślnie stworzono ogłoszenie");
            e.getChannel().sendMessage(" || @everyone ||").addEmbeds(acc.build()).queue();
            e.replyEmbeds(succ.build()).setEphemeral(true).queue();
            }
        }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent e) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("ogłoszenie", "Napisz co chcesz ogłosić").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)));
        commandData.add(Commands.slash("rules", "zasady-admin-only").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)));
        commandData.add(Commands.slash("regulamintur", "zasady-turnieju-admin-only").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)));
        commandData.add(Commands.slash("zgody", "zgody-turniejowe").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)));
        commandData.add(Commands.slash("content-roles-channel", "role-wysyłane-na-kanał").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)));

        e.getGuild().updateCommands().addCommands(commandData).queue();
    }
}

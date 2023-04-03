package pl.foxey;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class Main implements EventListener {

    public static void main(final String[] a) throws InterruptedException {
        new DiscordBot();
    }

    public void onEvent(GenericEvent e) {
        if (e instanceof ReadyEvent)
            System.out.println("\n\nBot has been turned on!");
    }
}
package pl.foxey;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import pl.foxey.config.Config;
import pl.foxey.senders.StartTicketMessageSender;
import pl.foxey.senders.StartVerificationMessageSender;
import pl.foxey.tasks.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Getter
@Setter
public class DiscordBot {

    public Config config;
    private static JDA jda;

    private final ScheduledExecutorService executorService;

    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(4);

    public DiscordBot() throws InterruptedException {
        executorService = Executors.newScheduledThreadPool(10);
        jda = createUser();
        if (jda == null) return;
        config = new Config();
    }

    JDA createUser() {
        JDABuilder jda = JDABuilder.createDefault("TOKEN");
        jda.addEventListeners(
                // Main
                new Main(),
                // Senders
                new StartVerificationMessageSender(),
                new WelcomeMessageSender(),
                new IdeasTask(),
                // Tasks
                new StartTicketTask(),
                //MNG
                new CommandMNG()

        );

        jda.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.setMemberCachePolicy(MemberCachePolicy.ONLINE);
        jda.setChunkingFilter(ChunkingFilter.ALL);
        jda.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.SCHEDULED_EVENTS);
        jda.enableCache(CacheFlag.ACTIVITY);
        jda.setRateLimitPool(SCHEDULED_THREAD_POOL_EXECUTOR);
        jda.setActivity(Activity.playing("Start: 32.12.2023"));
        try {
            return jda.build().awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
            return this.jda;
        }
    }

    public static JDA getJda() {
        return jda;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

}

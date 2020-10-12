package com.chipscrash.bot;

import com.chipscrash.bot.Commands.ClearCommand;
import com.chipscrash.bot.Commands.InfoCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

    public static JDA jda;
    public static String prefix = "!";

    public static void main(String[] args) throws Exception {
        jda = JDABuilder.createDefault(System.getenv("DiscordBot")).build();
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.listening("Juice Wrld"));
        jda.addEventListener(new InfoCommand());
        jda.addEventListener(new ClearCommand());
    }
}

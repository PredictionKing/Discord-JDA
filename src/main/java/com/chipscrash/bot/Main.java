package com.chipscrash.bot;

import com.chipscrash.bot.Commands.*;
import com.chipscrash.bot.Events.ReadyEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Role;

public class Main {

    public static JDA jda;
    public static String prefix = "!";
    public static Role partyRole;
    public static Role adminRole;

    public static void main(String[] args) throws Exception {
        jda = JDABuilder.createDefault(System.getenv("DiscordBot")).addEventListeners(new ReadyEvent()).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.listening("Juice Wrld"));
        jda.addEventListener(new InfoCommand());
        jda.addEventListener(new DeleteCommand());
        jda.addEventListener(new PartyCommand());
        jda.addEventListener(new HelpCommand());
        jda.addEventListener(new NSFWCommand());
    }
}

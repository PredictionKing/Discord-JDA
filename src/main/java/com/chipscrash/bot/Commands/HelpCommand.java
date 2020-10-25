package com.chipscrash.bot.Commands;

import com.chipscrash.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class HelpCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = event.getChannel();
        String memberFullName = event.getAuthor().getName()+"#"+event.getAuthor().getDiscriminator();

        if(args[0].equalsIgnoreCase(Main.prefix+"help")){
            EmbedBuilder help = new EmbedBuilder();
            help.setTitle("Legends Help");
            help.addField("!info","Shows information about the Legends Bot creators",false);
            help.addField("Creators","chips#4710 / Chris#4557", false);
            help.setColor(0x006600);
            help.setFooter("requested by "+memberFullName, event.getMember().getUser().getAvatarUrl());

            channel.sendTyping().queue();
            channel.sendMessage(help.build())
                    .queue();
            help.clear();
        }
    }
}

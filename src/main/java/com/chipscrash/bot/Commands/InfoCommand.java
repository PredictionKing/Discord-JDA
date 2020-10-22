package com.chipscrash.bot.Commands;

import com.chipscrash.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class InfoCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = event.getChannel();
        String memberFullName = event.getAuthor().getName()+"#"+event.getAuthor().getDiscriminator();

        if(args[0].equalsIgnoreCase(Main.prefix+"info")){
            File profilePicture = new File("src/main/resources/Icon_Cait_Prestige.jpg");
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("Legends Information");
            info.addField("Creators","chips#4710 / Chris#4557", false);
            info.setColor(0x006600);
            info.setThumbnail("attachment://pb.jpg");
            info.setFooter("requested by "+memberFullName, event.getMember().getUser().getAvatarUrl());

            channel.sendTyping().queue();
            channel.sendMessage(info.build())
                    .addFile(profilePicture, "pb.jpg")
                    .queue();
            info.clear();
        }
    }

}

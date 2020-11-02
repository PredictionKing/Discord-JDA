package com.chipscrash.bot.Commands;

import com.chipscrash.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.chipscrash.bot.Main.adminRole;
import static com.chipscrash.bot.Main.jda;

public class DeleteCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = event.getChannel();
        String memberFullName = event.getAuthor().getName()+"#"+event.getAuthor().getDiscriminator();

        if(args[0].equalsIgnoreCase(Main.prefix+"delete")){

            if(event.getMember().getRoles().contains(adminRole)){
                if(args.length < 2){
                    //ERROR
                }else{
                    try {
                        List<Message> messages = channel.getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                        channel.purgeMessages(messages);
                    }catch (IllegalArgumentException e){
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("⛔ Too many messages selected");
                        error.setDescription("Between 1 and 100 messages can be deleted at one time!");
                        channel.sendMessage(error.build()).queue();
                        error.clear();
                    }
                }
            }else{
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(0xff3923);
                error.setTitle("⛔ You don't have permissions");
                error.setDescription(String.format("You can only use this command with the %s Role!", adminRole.getName()));
                channel.sendMessage(error.build()).queue();
                error.clear();
            }
        }
    }
}

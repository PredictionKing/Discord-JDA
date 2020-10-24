package com.chipscrash.bot.Commands;

import com.chipscrash.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.gradle.internal.time.Time;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.chipscrash.bot.Main.jda;
import static com.chipscrash.bot.Main.partyRole;

public class PartyCommand extends ListenerAdapter {

    EmbedBuilder partyEmbed = new EmbedBuilder();
    List<String> partyEmbedIds = new ArrayList<>();
    Emote auEmote ;
    Emote ppEmote ;
    Emote tttEmote;
    Emote fgEmote ;


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = event.getChannel();
        String memberFullName = event.getAuthor().getName()+"#"+event.getAuthor().getDiscriminator();

        if(args[0].equalsIgnoreCase(Main.prefix+"party")){
            ArrayList<String> partyGames = new ArrayList<String>(
                    Arrays.asList("au", "pp", "dr", "fg", "ttt")
            );
            String showGames = partyGames.stream().map(Object::toString)
                    .collect(Collectors.joining(", "));

            int neededPlayers;

            if (args.length>1){
                auEmote = jda.getEmoteById("755836877067649225");
                ppEmote = jda.getEmoteById("750445382860931154");
                tttEmote = jda.getEmoteById("756882270282842143");
                fgEmote = jda.getEmoteById("756878627785801848");
                switch(args[1]){
                    case "au":
                        neededPlayers = 6;
                        List<Emote> emoteList = jda.getEmotes();
                        emoteList.forEach(e -> System.out.println(String.format("Name: %s | ID: %s", e.getName(), e.getId())));
                        Emote auEmote = jda.getEmoteById("755836877067649225");
                        createPartyEmbed("\uD83D\uDD2A - Among Us!",channel, neededPlayers, memberFullName,auEmote);
                        break;
                    case "pp":
                        neededPlayers = 4;
                        Emote ppEmote = jda.getEmoteById("750445382860931154");
                        createPartyEmbed("\uD83C\uDFB2 - Pummelparty!", channel, neededPlayers, memberFullName, ppEmote);
                        break;
                    case "dr":
                        neededPlayers = 5;
                        Emote drEmote = jda.getEmoteById(":heart:");
                        createPartyEmbed("\uD83D\uDC7B - Dead Realm!", channel, neededPlayers, memberFullName, drEmote);
                        break;
                    case "fg":
                        neededPlayers = 3;
                        Emote fgEmote = jda.getEmoteById(":heart:");
                        createPartyEmbed("\uD83D\uDC51 - Fall Guys", channel, neededPlayers, memberFullName, fgEmote);
                        break;
                    case "ttt":
                        neededPlayers = 6;
                        Emote tttEmote = jda.getEmoteById(":heart:");
                        createPartyEmbed("\uD83D\uDD2B - Trouble in Terrorist Town", channel, neededPlayers, memberFullName, tttEmote);
                        break;
                    default:
                        partyHelpEmbed(channel, showGames);
                        break;
                }
            }else {
                partyHelpEmbed(channel, showGames);
            }


        }
    }

    public void createPartyEmbed(String title, MessageChannel channel, int neededPlayers, String memberFullName, Emote emote){
        partyEmbed.clear();
        partyEmbed.setTitle(title);
        partyEmbed.setDescription(String.format("need at least %s players", neededPlayers));
        partyEmbed.setColor(0xfc6868);
        partyEmbed.addField("Status:", "add your reaction", false);
        partyEmbed.setFooter(String.format("Requested from %s", memberFullName));
        channel.sendMessage(partyEmbed.build()).append(partyRole.getAsMention()).queue((message -> {
            channel.addReactionById(message.getId(),emote).queue();
            partyEmbedIds.add(message.getId());
        }));

    }

    public void partyHelpEmbed(MessageChannel channel, String showGames){
        partyEmbed.setTitle("\uD83C\uDFC6 Party Command");
        partyEmbed.setDescription("Play a game with the @party");
        partyEmbed.setColor(0xfff714);
        partyEmbed.addField("Usage: !party <game>", showGames, false);
        partyEmbed.addField("pp", "Pummelparty", false);
        partyEmbed.addField("ttt", "Garrys Mod (Trouble in Terrorist Town)", false);
        partyEmbed.addField("fg", "Fall Guys", false);
        partyEmbed.addField("au", "Among Us", false);
        partyEmbed.addField("dr", "Dead Realm", false);
        channel.sendMessage(partyEmbed.build()).queue();
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        List<Emote> partyEmotes = List.of(auEmote,ppEmote,tttEmote,fgEmote);
        if(!event.getUser().isBot()){
            partyEmotes.forEach(emote -> {
                addPartyField(emote,event);
            });
        }
    }

    public void addPartyField(Emote emote, GuildMessageReactionAddEvent event){
        if(event.getReactionEmote().getEmote().equals(emote)){
            if(partyEmbedIds.contains(event.getMessageId())){
                SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
                Date date = new Date(System.currentTimeMillis());
                partyEmbed.addField(event.getUser().getName()+"#"+event.getUser().getDiscriminator(),formatter.format(date),false);
                event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
                    if(message.getReactions().stream().count()>0) {
                        System.out.println(message.getReactions().stream().count());
                        message.removeReaction(emote, message.getAuthor()).queue();
                    }
                    message.editMessage(partyEmbed.build()).queue();
                });
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {

        List<Emote> partyEmotes = List.of(auEmote,ppEmote,tttEmote,fgEmote);

            partyEmotes.forEach(emote -> {
                removePartyField(emote,event);
            });




    }

    public void removePartyField(Emote emote, GuildMessageReactionRemoveEvent event){
        if(event.getReactionEmote().getEmote().equals(emote)){
            if(partyEmbedIds.contains(event.getMessageId())){
                User user = jda.retrieveUserById(event.getUserId()).complete();
                event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
                    if(message.getReactions().stream().count()==0){
                        System.out.println(message.getReactions().stream().count());
                        message.addReaction(emote).queue();
                    }
                    partyEmbed.getFields().removeIf(field -> field.getName().contains(user.getName()));
                    message.getReactions().forEach(r -> System.out.println(r.getReactionEmote().getName()));
                    message.editMessage(partyEmbed.build()).queue();
                });
            }
        }
    }
}

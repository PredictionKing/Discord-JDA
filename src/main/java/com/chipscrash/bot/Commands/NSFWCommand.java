package com.chipscrash.bot.Commands;

import com.chipscrash.bot.Main;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import static com.chipscrash.bot.Main.adminRole;

public class NSFWCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = event.getChannel();
        String memberFullName = event.getAuthor().getName()+"#"+event.getAuthor().getDiscriminator();

        if(args[0].equalsIgnoreCase(Main.prefix+"nsfw")) {
            if (event.getMember().getRoles().contains(adminRole)) {
                try {
                    JSONObject json = readJsonFromUrl(String.format("https://www.reddit.com/r/nsfw/search.json?q=%s&restrict_sr=true&include_over_18=on", args[1]));
                    JSONArray children = json.getJSONObject("data").getJSONArray("children");
                    int random = (int) (Math.random() * children.length());
                    System.out.println(children.length());
                    System.out.println(children.getJSONObject(random).getJSONObject("data").getString("url"));
                    getRandomPicture(children, random, channel);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void getRandomPicture(JSONArray children, int randomNumber, MessageChannel channel){
        int random = (int) (Math.random()*children.length());
        if(children.getJSONObject(randomNumber).getJSONObject("data").getString("post_hint").equalsIgnoreCase("image")){
            channel.sendMessage(children.getJSONObject(randomNumber).getJSONObject("data").getString("url")).queue();
        }else{
            getRandomPicture(children,random, channel);
        }

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream is = urlConnection.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}

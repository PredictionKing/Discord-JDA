package com.chipscrash.bot.Events;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import static com.chipscrash.bot.Main.*;


public class ReadyEvent implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof net.dv8tion.jda.api.events.ReadyEvent) {
            partyRole = jda.getRolesByName("party", true).get(0);
            adminRole = jda.getRolesByName("bot", false).get(0);
            /*
            Use 'getRoleById("751142495395643552")' if you want to change the name of party
            */
        }
    }
}

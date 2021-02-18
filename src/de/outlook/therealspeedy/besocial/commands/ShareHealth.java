package de.outlook.therealspeedy.besocial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ShareHealth implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //TODO: implement sharehealth: send target half of senders hearts
        /*
        tests to pass:
            sender is member
            target is set
            target is sender >> cancel
            cooldown is not active
            target is online
            target is member
            target is not ignoring sender
            sender is not ignoring target
            sender has more than 0.5 health
            target doesn't have full health
            >> passed
         share maths:
            calculate half the hearts of sender, round down >> shareable
            calculate 20 minus targets health >> missing
            if shareable > missing then sending = missing
            else sending = shareable
            take sending from sender
            give sending to target
         */
        return false;
    }
}

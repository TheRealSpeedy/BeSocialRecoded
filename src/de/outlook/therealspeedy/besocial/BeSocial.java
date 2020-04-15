package de.outlook.therealspeedy.besocial;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.outlook.therealspeedy.besocial.commands.BeSocialCommand;
import de.outlook.therealspeedy.besocial.commands.Cuddle;
import de.outlook.therealspeedy.besocial.commands.Handshake;
import de.outlook.therealspeedy.besocial.commands.Highfive;
import de.outlook.therealspeedy.besocial.commands.Hug;
import de.outlook.therealspeedy.besocial.commands.Kiss;
import de.outlook.therealspeedy.besocial.commands.Lick;
import de.outlook.therealspeedy.besocial.commands.Poke;
import de.outlook.therealspeedy.besocial.commands.Slap;
import de.outlook.therealspeedy.besocial.commands.Stroke;
import de.outlook.therealspeedy.besocial.util.BeSocialTabCompleter;
import de.outlook.therealspeedy.besocial.util.ListStore;

public class BeSocial extends JavaPlugin {

    FileConfiguration config = this.getConfig();
    int i = 0;
    public static ListStore notMembers;
    String pluginFolder = this.getDataFolder().getAbsolutePath();

    @Override
    public void onEnable() {

        initConfig();

        if (config.getBoolean("enablePlugin")) {
            System.out.println("[BeSocial] BeSocial " + this.getDescription().getVersion() + " activated. Config initialized.");

            initCommands();

            System.out.println("[BeSocial] " + i + " commands initialized.");

        }
        else {
            System.out.println(ChatColor.RED + "[BeSocial] Plugin deactivated via config file. Stopping plugin. Please remove the BeSocial jar file from the plugins folder, if you don't want to use this plugin anymore!");
            getServer().getPluginManager().disablePlugin(this);
        }




        (new File(pluginFolder)).mkdirs();
        BeSocial.notMembers = new ListStore(new File(pluginFolder + File.separator + "notMembers.txt"));
        BeSocial.notMembers.load();
        BeSocial.notMembers.add("List of players who left the BeSocial program:");
        BeSocial.notMembers.add("Debug UUID 0000-0000-0000-000000");
        BeSocial.notMembers.save();

        System.out.println("[BeSocial] Playerlist initialized. List contains " + ((BeSocial.notMembers.length())-2) + " UUIDs.");

        System.out.println("[BeSocial] Plugin ready for use.");

    }


    @Override
    public void onDisable() {

        BeSocial.notMembers.save();
        System.out.println("[BeSocial] Playerlist with " + (BeSocial.notMembers.length()-2) + " UUIDs in it saved successfully.");
        System.out.println("[BeSocial] BeSocial " + this.getDescription().getVersion() + " deactivated.");
    }







    private void initConfig() {
        config.options().header("This is the BeSocial config. For message editing, see below the 'enable' part.");
        config.addDefault("enablePlugin", true);
        config.addDefault("enableCommand.beso", true);
        config.addDefault("enableCommand.hug", true);
        config.addDefault("enableCommand.cuddle", true);
        config.addDefault("enableCommand.kiss", true);
        config.addDefault("enableCommand.lick", true);
        config.addDefault("enableCommand.poke", true);
        config.addDefault("enableCommand.pet", true);
        config.addDefault("enableCommand.slap", true);
        config.addDefault("enableCommand.highfive", true);
        config.addDefault("enableCommand.handshake", true);
        config.addDefault("commands.CooldownSeconds", 7);
        config.addDefault("messages.prefix", "&7&o[&r&d&oBeSocial&r&7&o]");
        config.addDefault("messages.sender.error.senderNotMember", "&cI'm sorry, but you can't do that. You're not a member of the BeSocial program.");
        config.addDefault("messages.sender.error.targetNotMember", "&cI'm sorry, but you can't do that. This player isn't a member of the BeSocial program.");
        config.addDefault("messages.sender.error.targetOffline", "&cThat player is not online right now.");
        config.addDefault("messages.sender.error.selfSocial.hug", "&dYou hugged &5yourself &d! Now try hugging someone else.");
        config.addDefault("messages.sender.error.selfSocial.cuddle", "&cYou can't cuddle yourself. Try cuddling someone else.");
        config.addDefault("messages.sender.error.selfSocial.kiss", "&cQuite narcissistic, huh?");
        config.addDefault("messages.sender.error.selfSocial.lick", "&dYou licked &5yourself &d! &r&o&7(That's disgusting...)");
        config.addDefault("messages.sender.error.selfSocial.poke", "&dYou poked &5yourself &d!");
        config.addDefault("messages.sender.error.selfSocial.pet", "&cYou aren't allowed to pet yourself.");
        config.addDefault("messages.sender.error.selfSocial.slap", "&cDon't slap yourself! Everything is fine.");
        config.addDefault("messages.sender.error.selfSocial.highfive", "&dWell done!");
        config.addDefault("messages.sender.error.selfSocial.handshake", "&dBeing a gentleman is hard work.");
        config.addDefault("messages.sender.error.cooldown", "&cSorry, this command is currently cooling down. Please wait %time seconds, then try again. &r&7&o(For help, try /besocial)");
        config.addDefault("messages.sender.success.hug", "&dYou hugged &5%target &d!");
        config.addDefault("messages.sender.success.cuddle", "&dYou cuddled &5%target &d!");
        config.addDefault("messages.sender.success.kiss", "&dYou kissed &5%target &d!");
        config.addDefault("messages.sender.success.lick", "&dYou licked &5%target &d!");
        config.addDefault("messages.sender.success.poke", "&dYou poked &5%target &d!");
        config.addDefault("messages.sender.success.pet", "&dYou petted &5%target &d!");
        config.addDefault("messages.sender.success.slap", "&4You slapped &0%target &4!");
        config.addDefault("messages.sender.success.highfive", "&dYou gave &5%target &da highfive!");
        config.addDefault("messages.sender.success.handshake", "&dYou give &5%target &da handshake.");
        config.addDefault("messages.target.success.hug", "&5%sender &dhugged you!");
        config.addDefault("messages.target.success.cuddle", "&5%sender &dcuddled you!");
        config.addDefault("messages.target.success.kiss", "&5%sender &dkissed you!");
        config.addDefault("messages.target.success.lick", "&5%sender &dlicked you!");
        config.addDefault("messages.target.success.poke", "&5%sender &dpoked you!");
        config.addDefault("messages.target.success.pet", "&5%sender &dpetted you!");
        config.addDefault("messages.target.success.slap", "&0%sender &4slapped you!");
        config.addDefault("messages.target.success.highfive", "&5%sender &dgave you a highfive!");
        config.addDefault("messages.target.success.handshake", "&5%sender &dgives you a handshake.");
        config.addDefault("messages.special.leaveBeSocial", "&cIt's a pity you leave the BeSocial program. We will miss you.");
        config.addDefault("messages.special.leaveBeSocial2", "&cIf you want to join the program again, please ask an admin.");
        config.addDefault("messages.special.error", "&cERROR!");
        config.addDefault("messages.admin.notAnAdmin", "&cYou can't do that. Why? Because you don't have the &r&o&cbesocial.admin &r&cpermission.");
        config.addDefault("messages.admin.userBlocked", "&2The player &a%player &2was banned from the BeSocial program.");
        config.addDefault("messages.admin.userFreed", "&2The player &a%player &2can now use the BeSocial program's commands.");
        config.addDefault("messages.admin.listReloaded", "&2Playerlist sucessfully reloaded.");
        config.addDefault("messages.admin.listSaved", "&2Playerlist sucessfully saved.");
        config.addDefault("messages.admin.specifyUser", "&cYou have to specify an user!");
        config.addDefault("messages.admin.success", "&2Operation successful.");
        config.options().copyHeader(true);
        config.options().copyDefaults(true);
        saveConfig();
    }

    private void initCommands() {
        if (config.getBoolean("enableCommand.beso")) {
            this.getCommand("besocial").setExecutor(new BeSocialCommand());
            i++;
            this.getCommand("besocial").setTabCompleter(new BeSocialTabCompleter());
        }
        if (config.getBoolean("enableCommand.hug")) {
            this.getCommand("hug").setExecutor(new Hug());
            i++;
        }
        if (config.getBoolean("enableCommand.cuddle")) {
            this.getCommand("cuddle").setExecutor(new Cuddle());
            i++;
        }
        if (config.getBoolean("enableCommand.poke")) {
            this.getCommand("poke").setExecutor(new Poke());
            i++;
        }
        if (config.getBoolean("enableCommand.kiss")) {
            this.getCommand("kiss").setExecutor(new Kiss());
            i++;
        }
        if (config.getBoolean("enableCommand.slap")) {
            this.getCommand("slap").setExecutor(new Slap());
            i++;
        }
        if (config.getBoolean("enableCommand.lick")) {
            this.getCommand("lick").setExecutor(new Lick());
            i++;
        }
        if (config.getBoolean("enableCommand.pet")) {
            this.getCommand("pet").setExecutor(new Stroke());
            i++;
        }
        if (config.getBoolean("enableCommand.highfive")) {
            this.getCommand("highfive").setExecutor(new Highfive());
            i++;
        }
        if (config.getBoolean("enableCommand.handshake")) {
            this.getCommand("handshake").setExecutor(new Handshake());
            i++;
        }
    }
}
package de.outlook.therealspeedy.besocial;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.outlook.therealspeedy.besocial.commands.*;
import de.outlook.therealspeedy.besocial.commands.besocial.BeSocialCommand;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.outlook.therealspeedy.besocial.util.BeSocialTabCompleter;
import de.outlook.therealspeedy.besocial.util.ListStore;

public class BeSocial extends JavaPlugin {

    private FileConfiguration config = this.getConfig();
    private int initnbr = 0;
    public static ListStore notMembers;
    private String pluginFolder = this.getDataFolder().getAbsolutePath();
    private static File databaseFile;
    private static FileConfiguration database;
    public static final double currentConfigVersion = 15.1;
    public static final String name = "BeSocial";


    @Override
    public void onEnable() {

        initConfig();

        initDatabase();


        (new File(pluginFolder)).mkdirs();
        BeSocial.notMembers = new ListStore(new File(pluginFolder + File.separator + "notMembers.txt"));
        BeSocial.notMembers.load();
        BeSocial.notMembers.add("List of players who left the BeSocial program:");
        BeSocial.notMembers.add("Debug UUID 0000-0000-0000-000000");
        BeSocial.notMembers.save();

        getLogger().log(Level.INFO, "Playerlist initialized. List contains " + ((BeSocial.notMembers.length()) - 2) + " UUIDs.");

        if (config.getBoolean("enablePlayerStatisticsLogging")) {
            getLogger().log(Level.INFO, "Player interaction logging to database is ACTIVATED.");
        } else {
            getLogger().log(Level.WARNING, "Player interaction logging to database is DEACTIVATED. Parts of this plugin may not function as intended!");
        }


        if (config.getBoolean("enablePlugin")) {
            getLogger().log(Level.INFO, "BeSocial " + this.getDescription().getVersion() + " activated. Config initialized.");

            initCommands();

            getLogger().log(Level.INFO, "" + initnbr + " commands initialized.");

        } else {
            getLogger().log(Level.WARNING, "Plugin deactivated via config file. Stopping plugin. Please remove the BeSocial jar file from the plugins folder, if you don't want to use this plugin anymore!");
            getServer().getPluginManager().disablePlugin(this);
        }

        if (config.getBoolean("messages.console.askforhelp")) {
            getLogger().log(Level.INFO, "[BeSocial] §aHey! If you like this plugin please help me out. Leave a rating and comment at spigot.mc, take screenshots that I can use for the plugin page and recommend it to other server owners. (You can deactivate this message in the config file.)");
        }

        getLogger().log(Level.INFO, "Plugin ready for use.");

    }


    @Override
    public void onDisable() {

        BeSocial.notMembers.save();
        getLogger().log(Level.INFO, "Playerlist with " + (BeSocial.notMembers.length()-2) + " UUIDs in it saved successfully.");

        if (!saveDatabase()){
            this.getLogger().log(Level.SEVERE, "DATABASE SAVING FAILED! Could not write to folder!");
        } else {
            getLogger().log(Level.INFO, "Database saved.");
        }

        getLogger().log(Level.INFO, "BeSocial " + this.getDescription().getVersion() + " deactivated.");
    }







    private void initConfig() {
        //create default config values
        prepareDefaultConfigValues();
        //copy header to config
        config.options().copyHeader(true);
        //copy default values if not set already
        config.options().copyDefaults(true);
        //handle config version changes
        handleConfigVersionChange();
        //set new config version
        config.set("configVersion", currentConfigVersion);
        config.set("enableCommand.besocialLeave", "always true");
        config.set("enableCommand.besocialIgnore", "always true");
        saveConfig();
    }

    private void handleConfigVersionChange() {
        if (config.getDouble("configVersion") < 14.0) {
            config.set("enableCommand.shareHealth", true);
        }
    }


    private void initCommands() {
        //check if command is enabled in config, then enable by setting executor
        if (config.getBoolean("enableCommand.beso")) {
            this.getCommand("besocial").setExecutor(new BeSocialCommand());
            initnbr++;
            this.getCommand("besocial").setTabCompleter(new BeSocialTabCompleter());
        }
        if (config.getBoolean("enableCommand.hug")) {
            this.getCommand("hug").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.cuddle")) {
            this.getCommand("cuddle").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.poke")) {
            this.getCommand("poke").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.kiss")) {
            this.getCommand("kiss").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.slap")) {
            this.getCommand("slap").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.lick")) {
            this.getCommand("lick").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.pet")) {
            this.getCommand("pet").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.highfive")) {
            this.getCommand("highfive").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.handshake")) {
            this.getCommand("handshake").setExecutor(new SimpleSocialCommand());
            initnbr++;
        }
        if (config.getBoolean("enableCommand.sharehealth")) {
            this.getCommand("sharehealth").setExecutor(new ShareHealth());
            initnbr++;
        }
    }

    private void initDatabase() {
        databaseFile = new File(getDataFolder(), "database.yml");
        if (!databaseFile.exists()) {
            databaseFile.getParentFile().mkdirs();
            saveResource("database.yml", false);
        }

        database = new YamlConfiguration();
        try {
            database.load(databaseFile);
            getLogger().log(Level.INFO, "Database initialized.");

        } catch (Exception e) {
            e.printStackTrace();
            getLogger().log(Level.SEVERE, "Database loading error. BESOCIAL DOWN. Please check folder and file permissions!");
            this.getPluginLoader().disablePlugin(this);

        }
    }

    public static FileConfiguration getDatabase(){
        return BeSocial.database;
    }

    public static boolean saveDatabase(){
        try {
            database.save(databaseFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void prepareDefaultConfigValues() {
        //default config values; edit when needed
        config.options().header("This is the BeSocial config.");
        config.addDefault("enablePlugin", true);
        config.addDefault("configVersion", currentConfigVersion);
        config.addDefault("enablePlayerStatisticsLogging", true);
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
        config.addDefault("enableCommand.sharehealth", true);
        config.addDefault("enableCommand.besocialLeave", "always true");
        config.addDefault("enableCommand.besocialRejoin", false);
        config.addDefault("enableCommand.besocialIgnore", "always true");
        config.addDefault("commands.everyCommandHasOwnCooldown", true);
        config.addDefault("commands.CooldownSeconds", 7);
        config.addDefault("commands.RejoinCooldownSeconds", 86400);
        config.addDefault("particles.enableParticleEffect", true);
        config.addDefault("particles.onlyShowParticlesToParticipants", true);
        config.addDefault("particles.particleSpawnAreaSize", 0.99);
        config.addDefault("particles.particleAmount", 45);
        config.addDefault("particles.usedParticle.cuddle", "hearts");
        config.addDefault("particles.usedParticle.handshake", "happyVillager");
        config.addDefault("particles.usedParticle.highfive", "happyVillager");
        config.addDefault("particles.usedParticle.hug", "hearts");
        config.addDefault("particles.usedParticle.kiss", "hearts");
        config.addDefault("particles.usedParticle.lick", "fallingWater");
        config.addDefault("particles.usedParticle.poke", "composter");
        config.addDefault("particles.usedParticle.slap", "angryVillager");
        config.addDefault("particles.usedParticle.pet", "happyVillager");
        config.addDefault("particles.usedParticle.sharehealth", "hearts");
        config.addDefault("messages.prefix", "&7&o[&r&d&oBeSocial&r&7&o]");
        config.addDefault("messages.sender.error.senderNotMember", "&cI'm sorry, but you can't do that. You're not a member of the BeSocial program.");
        config.addDefault("messages.sender.error.targetNotMember", "&cI'm sorry, but you can't do that. This player isn't a member of the BeSocial program.");
        config.addDefault("messages.sender.error.targetOffline", "&cThis command can only be used if the targeted player is online.");
        config.addDefault("messages.sender.error.targetIgnoringSender", "&cI'm afraid you can't do that.");
        config.addDefault("messages.sender.error.senderIgnoringTarget", "&cSorry, you can't interact with players you're ignoring.");
        config.addDefault("messages.sender.error.selfSocial.hug", "&dYou hugged &5yourself &d! Now try hugging someone else.");
        config.addDefault("messages.sender.error.selfSocial.cuddle", "&dYou cuddled &5yourself &d! Now try hugging someone else.");
        config.addDefault("messages.sender.error.selfSocial.kiss", "&cQuite narcissistic, huh?");
        config.addDefault("messages.sender.error.selfSocial.lick", "&dYou licked &5yourself&d! &r&o&7(That's disgusting...)");
        config.addDefault("messages.sender.error.selfSocial.poke", "&dYou poked &5yourself&d!");
        config.addDefault("messages.sender.error.selfSocial.pet", "&dSo cute!");
        config.addDefault("messages.sender.error.selfSocial.slap", "&cDon't slap yourself! Everything is fine.");
        config.addDefault("messages.sender.error.selfSocial.highfive", "&dWell done!");
        config.addDefault("messages.sender.error.selfSocial.handshake", "&cBeing a politician today, huh?");
        config.addDefault("messages.sender.error.selfSocial.sharehealth", "&cThere is no point in doing this.");
        config.addDefault("messages.sender.error.cooldown", "&cSorry, this command is currently cooling down. Please wait %time seconds, then try again. &r&7&o(For help, try /besocial)");
        config.addDefault("messages.sender.error.sharehealth.notEnoughHealth", "&cSharing now would kill you.");
        config.addDefault("messages.sender.error.sharehealth.targetFullHealth", "&cThe chosen target already has full health.");
        config.addDefault("messages.sender.error.rejoinCooldown", "&cSorry, you can't rejoin yet. Please wait %time, then try again.");
        config.addDefault("messages.sender.error.rejoinAlreadyMember", "&cSorry, you can't rejoin, because you're already a member!");
        config.addDefault("messages.sender.error.ignoreAlreadyIgnoring", "&cSorry, you're already ignoring that player.");
        config.addDefault("messages.sender.error.ignoreNotIgnoring", "&cYou are currently not ignoring that player.");
        config.addDefault("messages.sender.success.hug", "&dYou hugged &5%target&d!");
        config.addDefault("messages.sender.success.cuddle", "&dYou cuddled &5%target&d!");
        config.addDefault("messages.sender.success.kiss", "&dYou kissed &5%target&d!");
        config.addDefault("messages.sender.success.lick", "&dYou licked &5%target&d!");
        config.addDefault("messages.sender.success.poke", "&dYou poked &5%target&d!");
        config.addDefault("messages.sender.success.pet", "&dYou petted &5%target&d!");
        config.addDefault("messages.sender.success.slap", "&4You slapped &c%target&4!");
        config.addDefault("messages.sender.success.highfive", "&dYou gave &5%target &da high five!");
        config.addDefault("messages.sender.success.handshake", "&dYou gave &5%target &da handshake.");
        config.addDefault("messages.sender.success.sharehealth", "&dYou send &5%healthsend &dhealth to &5%target&d!");
        config.addDefault("messages.target.success.hug", "&5%sender &dhugged you!");
        config.addDefault("messages.target.success.cuddle", "&5%sender &dcuddled you!");
        config.addDefault("messages.target.success.kiss", "&5%sender &dkissed you!");
        config.addDefault("messages.target.success.lick", "&5%sender &dlicked you!");
        config.addDefault("messages.target.success.poke", "&5%sender &dpoked you!");
        config.addDefault("messages.target.success.pet", "&5%sender &dpetted you!");
        config.addDefault("messages.target.success.slap", "&c%sender &4slapped you!");
        config.addDefault("messages.target.success.highfive", "&5%sender &dgave you a high five!");
        config.addDefault("messages.target.success.handshake", "&5%sender &dgives you a handshake.");
        config.addDefault("messages.target.success.sharehealth", "&dYou received &5%healthsend &dhealth from &5%target&d!");
        config.addDefault("messages.special.leaveBeSocial", "&cIt's a pity you leave the BeSocial program. We will miss you.");
        config.addDefault("messages.special.leaveBeSocial2", "&cIf you want to join the program again, please ask an admin.");
        config.addDefault("messages.special.rejoinBeSocial1", "&aHOORAY! Your rejoin was successful! You can now use BeSocial again.");
        config.addDefault("messages.special.rejoinForbidden", "&cYou can't rejoin the BeSocial program.");
        config.addDefault("messages.special.ignoreSuccessful", "&aYou are now ignoring &c%target&a.");
        config.addDefault("messages.special.unignoreSuccessful", "&2%target&a can now interact with you again.");
        config.addDefault("messages.special.error", "&cERROR!");
        config.addDefault("messages.admin.notAnAdmin", "&cYou can't do that. Why? Because you don't have the &r&o&cbesocial.admin &r&cpermission.");
        config.addDefault("messages.admin.userBlocked", "&2The player &a%player &2was banned from the BeSocial program.");
        config.addDefault("messages.admin.userFreed", "&2The player &a%player &2can now use BeSocial's commands.");
        config.addDefault("messages.admin.listReloaded", "&2Playerlist successfully reloaded.");
        config.addDefault("messages.admin.listSaved", "&2Playerlist successfully saved.");
        config.addDefault("messages.admin.specifyUser", "&cYou have to specify an user!");
        config.addDefault("messages.admin.success", "&2Operation successful.");
        config.addDefault("messages.console.askforhelp", true);
    }

}
package it.zpeppoh.docs;

import it.zpeppoh.docs.commands.docCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Docs extends JavaPlugin {

    private static Docs instance;

    public static Docs getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Docs.getInstance().getCommand("doc").setExecutor((CommandExecutor)new docCommand());
        Bukkit.getLogger().info("docs avviato con successo");

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("docs stoppato con successo");
    }
}

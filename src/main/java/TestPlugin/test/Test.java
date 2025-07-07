package TestPlugin.test;

import DoubleJump.DoubleJumpListener;
import ItemHandler.ItemHandlerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Test extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("[TestPlugin]: Plugin has been enabled!");
        Bukkit.getLogger().info("[TestPlugin]: Registering DoubleJumpListener...");

        // Registering the DoubleJumpListener
        getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
        getServer().getPluginManager().registerEvents(new ItemHandlerListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("[TestPlugin]: Plugin has been disabled!");
    }
}
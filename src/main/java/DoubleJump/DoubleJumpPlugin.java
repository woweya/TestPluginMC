package DoubleJump;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleJumpPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("[DoubleJumpPlugin]: Plugin has been enabled!");
        Bukkit.getLogger().info("[DoubleJumpPlugin]: Plugin has been enabled!");

        // Registering the server instance
        getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
    }
}

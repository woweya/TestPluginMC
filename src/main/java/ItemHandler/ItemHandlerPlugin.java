package ItemHandler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemHandlerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("[ItemHandlerPlugin]: Plugin has been enabled!");

        // Registering the server instance
        getServer().getPluginManager().registerEvents(new ItemHandlerListener(), this);
    }

}

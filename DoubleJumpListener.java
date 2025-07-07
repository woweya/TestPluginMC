package DoubleJump;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.bukkit.Effect;
import org.bukkit.Sound;

public class DoubleJumpListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
      
        Player p = e.getPlayer();

        // Allow flight only if the player is in survival mode, on the ground, and not already allowed to fly
        if (p.getGameMode() == GameMode.SURVIVAL && p.isOnGround() && !p.getAllowFlight()) {
            Bukkit.getLogger().info( "[DoubleJumpPlugin]: Player " + p.getName() + " is allowed to fly.");
            p.setAllowFlight(true);
        }
    }


    // This event is triggered when the player toggles flight, which is used for double jump functionality.
    // It activate the double jump by setting the player's velocity and playing sound effects.
    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {

        // Logger to see if the player toggled flight
        Bukkit.getLogger().info( "[DoubleJumpPlugin]: Player " + e.getPlayer().getName() + " toggled flight.");

        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.SURVIVAL) return;

        e.setCancelled(true);
        p.setAllowFlight(false);
        p.setFlying(false);

        Vector dir = p.getLocation().getDirection().multiply(0.5).setY(1.0);
        p.setVelocity(dir);

        p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f, 1.0f);
    }
}

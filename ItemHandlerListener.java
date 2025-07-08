package ItemHandler;


import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.List;

public class ItemHandlerListener implements Listener {

    /**
     * Listener per gestire l'evento di interazione del giocatore con un oggetto.
     * Questo metodo permette al giocatore di lanciare un fulmine su un'entità
     * entro un raggio di 50 blocchi, utilizzando un bastone.
     *
     * @param event L'evento di interazione del giocatore.
     */
    @EventHandler
    public void onItemUse(org.bukkit.event.player.PlayerInteractEvent event) {
        // Ottiene il giocatore che ha generato l'evento
        org.bukkit.entity.Player player = event.getPlayer();
        // Ottiene l'oggetto che il giocatore tiene nella mano principale
        org.bukkit.inventory.ItemStack item = player.getInventory().getItemInMainHand();

        // Controlla se l'oggetto non è aria e se è un bastone
        if (item.getType() != org.bukkit.Material.AIR && item.getType().toString().contains("STICK")) {
            // Variabile per memorizzare l'entità mirata
            Entity targetEntity = null;
            // Distanza minima iniziale per trovare l'entità più vicina
            double minDistance = 50;

            // Ottiene la posizione degli occhi del giocatore e la direzione in cui sta guardando
            org.bukkit.Location eyeLoc = player.getEyeLocation();
            Vector direction = eyeLoc.getDirection().normalize();

            // Cicla su tutte le entità vicine al giocatore entro un raggio di 50 blocchi
            for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
                // Esclude il giocatore stesso
                if (entity.equals(player)) continue;

                // Calcola il vettore dalla posizione degli occhi del giocatore all'entità
                Vector toEntity = entity.getLocation().toVector().subtract(eyeLoc.toVector());
                // Proiezione del vettore sulla direzione dello sguardo del giocatore
                double projection = toEntity.dot(direction);
                // Ignora entità dietro il giocatore o oltre i 50 blocchi
                if (projection < 0 || projection > 50) continue;

                // Calcola il punto più vicino sulla linea di vista del giocatore
                Vector closestPoint = eyeLoc.toVector().add(direction.clone().multiply(projection));
                // Calcola la distanza tra l'entità e il punto più vicino sulla linea di vista
                double distance = entity.getLocation().toVector().distance(closestPoint);

                // Se l'entità è entro 2 blocchi dalla linea di vista e più vicina di altre, la seleziona
                if (distance < 2 && projection < minDistance) {
                    minDistance = projection;
                    targetEntity = entity;
                }
            }

            // Se è stata trovata un'entità mirata
            if (targetEntity != null) {
                // Lancia un fulmine sulla posizione dell'entità
                World world = player.getWorld();
                world.strikeLightning(targetEntity.getLocation());
                // Illumina l'entità di rosso e rende visibile il suo nome
                targetEntity.setGlowing(true);
                targetEntity.setCustomName(ChatColor.RED + targetEntity.getName());
                targetEntity.setCustomNameVisible(true);
            } else {
                // Se non c'è un'entità, controlla se il giocatore sta mirando a un blocco
                org.bukkit.block.Block targetBlock = player.getTargetBlockExact(50);
                if (targetBlock != null) {
                    // Lancia un fulmine sulla posizione del blocco
                    World world = player.getWorld();
                    world.strikeLightning(targetBlock.getLocation());
                } else {
                    // Se non c'è né un'entità né un blocco, invia un messaggio al giocatore
                    player.sendMessage(ChatColor.RED + "Non stai mirando né a un'entità né a un blocco!");
                }
            }
        }
    }

}

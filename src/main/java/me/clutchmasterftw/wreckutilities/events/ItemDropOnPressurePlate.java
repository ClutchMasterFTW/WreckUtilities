package me.clutchmasterftw.wreckutilities.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityInteractEvent;

public class ItemDropOnPressurePlate implements Listener {
    @EventHandler
    public void onPressurePlateInteract(EntityInteractEvent e) {
        if(e.getEntity() instanceof Item) {
            Block block = e.getBlock();

            if(block.getType().name().contains("PRESSURE_PLATE")) {
                e.setCancelled(true);
            }
        }
    }
}

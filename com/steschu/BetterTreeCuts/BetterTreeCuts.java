package steschu;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin implements Listener {

    private PluginDescriptionFile desc = getDescription();
    
    @Override
    public void onEnable() {
        //Called when the plugin is enabled
        getLogger().info("onEnable has been invoked!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        //Called when the plugin is disabled
        getLogger().info("onDisable has been invoked!");
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
    
        if (e.isCancelled()) {
            return;
        }
        
        final Block b = e.getBlock();
        final World w = b.getWorld();
        
        final Material m = b.getBlockData().getMaterial();
        if (m.name().contains("LOG")) {
        
            b.breakNaturally();
            
            int x = b.getX();
            int y = b.getY();
            int z = b.getZ();
            
            breakTreeRec(w, w.getBlockAt(x, y + 1, z));
            e.setCancelled(true);
        }
    }
    
    private boolean isFree(Block b) {
        final Material m = b.getBlockData().getMaterial();
        return m == Material.AIR
            || m == Material.WATER
            || m == Material.GRASS
            || m == Material.DEAD_BUSH
            || m.name().contains("LEAVES");
    }
    
    private void breakTreeRec(World w, Block b) {
    
        final Material m = b.getBlockData().getMaterial();

        if (!m.name().contains("LOG")) {
            return;
        }

        int x = b.getX();
        int y = b.getY();
        int z = b.getZ();
        
        final Block be = w.getBlockAt(x + 1, y, z);
        final Block bw = w.getBlockAt(x - 1, y, z);
        final Block bn = w.getBlockAt(x, y, z + 1);
        final Block bs = w.getBlockAt(x, y, z - 1);
        
        if (!isFree(be) || !isFree(bw) || !isFree(bn) || !isFree(bs)) {
            return;
        }
        
        b.breakNaturally();
        
        breakTreeRec(w, w.getBlockAt(x, y + 1, z));
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
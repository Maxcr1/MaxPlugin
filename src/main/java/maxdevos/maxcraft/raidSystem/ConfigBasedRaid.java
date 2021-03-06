package maxdevos.maxcraft.raidSystem;

import maxdevos.maxcraft.MaxPlugin;
import maxdevos.maxcraft.raidSystem.raidMobs.*;
import maxdevos.maxcraft.raidSystem.raidEvents.KillWaveEvent;
import maxdevos.maxcraft.raidSystem.raidEvents.RaidMobKilledEvent;
import maxdevos.maxcraft.raidSystem.raidEvents.StopRaidEvent;
import maxdevos.maxcraft.util.ChatFunctions;
import maxdevos.maxcraft.util.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.raid.RaidFinishEvent;
import org.bukkit.event.raid.RaidSpawnWaveEvent;

import java.util.ArrayList;

public class ConfigBasedRaid implements Listener {

    private final MaxPlugin plugin = MaxPlugin.getInstance();
    private final Raid raid;
    private final World w;
    private int wave = 0;
    private final ArrayList<RaidPlayer> players = new ArrayList<>();
    private final RaidEventHandler handler;
    private final RaidConfig raidConfig;
    private RaidWave currentWave;
    private ArrayList<Location> locationBuffer;
    private RaidScoreboard scoreboard;

    public ConfigBasedRaid(Raid raid){

        scoreboard = new RaidScoreboard();
        locationBuffer = new ArrayList<>();
        raidConfig = new RaidConfig(plugin.getCustomConfig().getString("current-raid"));
        handler = new RaidEventHandler();
        this.raid = raid;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.w = plugin.getServer().getWorlds().get(0);
        plugin.getServer().broadcastMessage(ChatFunctions.raidPrefix + "Running Raid: " + raidConfig.getRaidName());
        plugin.getServer().broadcastMessage(ChatFunctions.raidPrefix + "This Raid is sponsored by RAID Shadow Legends");
        currentWave = new RaidWave();
        RaidPlayer.checkDevMode(players);

    }

    @EventHandler
    private void newWave(RaidSpawnWaveEvent e) {
        wave++;
        locationBuffer.clear();
        plugin.getServer().broadcastMessage(ChatFunctions.raidPrefix + "Wave # " + wave + " has spawned!");
        RaidPlayer.addNewPlayers(raid.getHeroes(), players);

        for (RaidPlayer p : players) {
            p.getPlayer().sendMessage(ChatFunctions.raidPrefix + "You have " + p.getKills() + " kills.");
        }

        if (wave != 1 && wave < 8) {
            currentWave = raidConfig.getWave(wave);
            currentWave.configWave(players, e);
            currentWave.spawnWave();
            currentWave.spawnAirdrops();
        }

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this::purgeUnnamed, 30L);
    }

    private void purgeUnnamed() {
        for(Entity e:w.getEntities()){
            if((e.getType().equals(EntityType.VEX) || e.getType().equals(EntityType.PILLAGER) ||
                    e.getType().equals(EntityType.RAVAGER) || e.getType().equals(EntityType.WITCH) ||
                    e.getType().equals(EntityType.ILLUSIONER) || e.getType().equals(EntityType.EVOKER) ||
                    e.getType().equals(EntityType.VINDICATOR)) && e.getCustomName() == null){
                e.remove();
            }
        }
    }

    @EventHandler
    private void endRaid(RaidFinishEvent e){
        plugin.getServer().broadcastMessage(ChatFunctions.raidPrefix + "The Raid is Over.");
        PlayerUtils.printKillOrder(players);
        HandlerList.unregisterAll(this);
        scoreboard.restoreScoreboard();
        handler.unregister();
    }

    @EventHandler
    private void endRaid(StopRaidEvent e){
        w.setGameRule(GameRule.DISABLE_RAIDS, true);
        currentWave.killAll();
        Bukkit.getPluginManager().callEvent(new RaidFinishEvent(raid, w, RaidPlayer.getPlayersFromRaidPlayers(players)));
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                w.setGameRule(GameRule.DISABLE_RAIDS, false), 2L);
    }

    @EventHandler
    private void killWave(KillWaveEvent e){
        currentWave.killAll();
    }

    @EventHandler
    private void killedMob(RaidMobKilledEvent e){
        if(wave == 1){
            RaidPlayer.addNewPlayers(e.player.getUniqueId(), players);
        }
        for(RaidPlayer p:players){
            if(p.getPlayer().getUniqueId().equals(e.player.getUniqueId())){
                p.addKill();
            }
            p.handleOrdinance();
        }
        scoreboard.updateScoreboard(players);
    }

    @EventHandler
    private void raidMobKilled(EntityDeathEvent e){
        if(e.getEntity().getKiller() != null && e.getEntity().getCustomName() != null){
            Bukkit.getPluginManager().callEvent(new RaidMobKilledEvent(e));
        }
    }

    @EventHandler
    public void normalQuit(PlayerQuitEvent event) {
        if(RaidPlayer.isPlayerPresent(event.getPlayer().getUniqueId(), players)){
            MaxPlugin.getServerInstance().broadcastMessage(ChatFunctions.raidPrefix +
                    event.getPlayer().getDisplayName() + " has left the action to avoid danger.  Their " +
                    "ordinance progress and kills have been reset.  Fuck you.");
            RaidPlayer.removePlayer(event.getPlayer().getUniqueId(), players);
        }
    }

    @EventHandler
    private void naturalRaidMobHandler(EntitySpawnEvent e){
        try {
            if (!ChatFunctions.isRaider(e.getEntity()) && !locationBuffer.contains(e.getLocation())) {
                if(players.isEmpty()) {
                    if (e.getEntityType().equals(EntityType.PILLAGER)) {
                        locationBuffer.add(e.getLocation());
                        RaidPillager p = new RaidPillager(e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.RAVAGER)) {
                        locationBuffer.add(e.getLocation());
                        RaidRavager p = new RaidRavager(e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.WITCH)) {
                        locationBuffer.add(e.getLocation());
                        RaidWitch p = new RaidWitch(e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.ILLUSIONER)) {
                        locationBuffer.add(e.getLocation());
                        RaidIllusioner p = new RaidIllusioner(e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.EVOKER)) {
                        locationBuffer.add(e.getLocation());
                        RaidEvoker p = new RaidEvoker(e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.VINDICATOR)) {
                        locationBuffer.add(e.getLocation());
                        RaidVindicator p = new RaidVindicator(e.getLocation());
                        currentWave.addMob(p);
                    }
                }
                else{
                    if (e.getEntityType().equals(EntityType.PILLAGER)) {
                        locationBuffer.add(e.getLocation());
                        RaidPillager p = new RaidPillager(PlayerUtils.getRandomRaidPlayer(players),e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.RAVAGER)) {
                        locationBuffer.add(e.getLocation());
                        RaidRavager p = new RaidRavager(PlayerUtils.getRandomRaidPlayer(players),e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.WITCH)) {
                        locationBuffer.add(e.getLocation());
                        RaidWitch p = new RaidWitch(PlayerUtils.getRandomRaidPlayer(players),e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.ILLUSIONER)) {
                        locationBuffer.add(e.getLocation());
                        RaidIllusioner p = new RaidIllusioner(PlayerUtils.getRandomRaidPlayer(players),e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.EVOKER)) {
                        locationBuffer.add(e.getLocation());
                        RaidEvoker p = new RaidEvoker(PlayerUtils.getRandomRaidPlayer(players),e.getLocation());
                        currentWave.addMob(p);
                    } else if (e.getEntityType().equals(EntityType.VINDICATOR)) {
                        locationBuffer.add(e.getLocation());
                        RaidVindicator p = new RaidVindicator(PlayerUtils.getRandomRaidPlayer(players),e.getLocation());
                        currentWave.addMob(p);
                    }
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}

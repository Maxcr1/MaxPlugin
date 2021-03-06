package maxdevos.maxcraft.raidSystem;

import maxdevos.maxcraft.raidSystem.raidMobs.*;
import maxdevos.maxcraft.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.raid.RaidSpawnWaveEvent;

import java.util.ArrayList;

class RaidWave {

    private ArrayList<Player> players;
    private RaidSpawnWaveEvent e;
    private int airDrops = 0;
    private int blazes = 0;
    private int creepers = 0;
    private int superCreepers = 0;
    private int endermen = 0;
    private int evokers = 0;
    private int ghasts = 0;
    private int magmaCubes = 0;
    private int phantoms = 0;
    private int pillagers = 0;
    private int ravagers = 0;
    private int skeletons = 0;
    private int vindicators = 0;
    private int witherSkeletons = 0;
    private int zombies = 0;
    private int dropBlazes = 0;
    private int dropCreepers = 0;
    private int dropEndermen = 0;
    private int dropEvokers = 0;
    private int dropGhasts = 0;
    private int dropMagmaCubes = 0;
    private int dropPhantoms = 0;
    private int dropPillagers = 0;
    private int dropRavagers = 0;
    private int dropSkeletons = 0;
    private int dropVindicators = 0;
    private int dropWitherSkeletons = 0;
    private int dropZombies = 0;
    private int dropSuperCreepers = 0;
    private int dropSuperSpiders = 0;
    private int superSpiders = 0;
    private int superZombies = 0;
    private int dropSuperZombies = 0;

    private final ArrayList<RaidMob> mobs;

    RaidWave(){
        players = new ArrayList<>();
        mobs = new ArrayList<>();
    }

    void addMob(RaidMob m){
        mobs.add(m);
    }

    void configWave(ArrayList<RaidPlayer> players, RaidSpawnWaveEvent e){
        this.players = RaidPlayer.getPlayersFromRaidPlayers(players);
        this.e = e;
    }

    void spawnWave(){
        for(int i = 0; i < blazes; i++){
            mobs.add(new RaidBlaze(PlayerUtils.getHighestPlayer(players),e));
        }
        for(int i = 0; i < blazes; i++){
            mobs.add(new RaidBlaze(PlayerUtils.getHighestPlayer(players),e));
        }
        for(int i = 0; i < superCreepers; i++){
            mobs.add(new RaidSuicideCreeper(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < superSpiders; i++){
            mobs.add(new RaidSuperSpider(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < superZombies; i++){
            mobs.add(new RaidSuperZombie(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < creepers; i++){
            mobs.add(new RaidCreeper(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < endermen; i++){
            mobs.add(new RaidEnderman(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < evokers; i++){
            mobs.add(new RaidEvoker(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < ghasts; i++){
            mobs.add(new RaidGhast(PlayerUtils.getHighestPlayer(players),e));
        }
        for(int i = 0; i < magmaCubes; i++){
            mobs.add(new RaidMagmaCube(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < phantoms; i++){
            mobs.add(new RaidPhantom(PlayerUtils.getHighestPlayer(players),e));
        }
        for(int i = 0; i < pillagers; i++){
            mobs.add(new RaidPillager(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < ravagers; i++){
            mobs.add(new RaidRavager(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < skeletons; i++){
            mobs.add(new RaidSkeleton(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < vindicators; i++){
            mobs.add(new RaidVindicator(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < witherSkeletons; i++){
            mobs.add(new RaidWitherSkeleton(PlayerUtils.getRandomPlayer(players),e));
        }
        for(int i = 0; i < zombies; i++){
            mobs.add(new RaidZombie(PlayerUtils.getRandomPlayer(players),e));
        }
    }

    void spawnAirdrops(){
        for(int f = 0; f < airDrops; f++){
            System.out.println(ChatColor.DARK_RED + "[MaxCraft] " + ChatColor.WHITE + "Airdrop");
            Player p = PlayerUtils.getRandomPlayer(players);
            for(int i = 0; i < dropBlazes; i++){
                mobs.add(new RaidBlaze(p));
            }
            for(int i = 0; i < dropCreepers; i++){
                mobs.add(new RaidCreeper(p));
            }
            for(int i = 0; i < dropSuperCreepers; i++){
                mobs.add(new RaidSuicideCreeper(p));
            }
            for(int i = 0; i < dropSuperSpiders; i++){
                mobs.add(new RaidSuperSpider(p));
            }
            for(int i = 0; i < dropSuperZombies; i++){
                mobs.add(new RaidSuperZombie(p));
            }
            for(int i = 0; i < dropEndermen; i++){
                mobs.add(new RaidEnderman(p));
            }
            for(int i = 0; i < dropEvokers; i++){
                mobs.add(new RaidEvoker(p));
            }
            for(int i = 0; i < dropGhasts; i++){
                mobs.add(new RaidGhast(p));
            }
            for(int i = 0; i < dropMagmaCubes; i++){
                mobs.add(new RaidMagmaCube(p));
            }
            for(int i = 0; i < dropPhantoms; i++){
                mobs.add(new RaidPhantom(p));
            }
            for(int i = 0; i < dropPillagers; i++){
                mobs.add(new RaidPillager(p));
            }
            for(int i = 0; i < dropRavagers; i++){
                mobs.add(new RaidRavager(p));
            }
            for(int i = 0; i < dropSkeletons; i++){
                mobs.add(new RaidSkeleton(p));
            }
            for(int i = 0; i < dropVindicators; i++){
                mobs.add(new RaidVindicator(p));
            }
            for(int i = 0; i < dropWitherSkeletons; i++){
                mobs.add(new RaidWitherSkeleton(p));
            }
            for(int i = 0; i < dropZombies; i++){
                mobs.add(new RaidZombie(p));
            }
        }
    }

    void setDropBlazes(int dropBlazes) {
        this.dropBlazes = dropBlazes;
    }

    void setDropCreepers(int dropCreepers) {
        this.dropCreepers = dropCreepers;
    }

    void setDropEndermen(int dropEndermen) {
        this.dropEndermen = dropEndermen;
    }

    void setDropEvokers(int dropEvokers) {
        this.dropEvokers = dropEvokers;
    }

    void setDropGhasts(int dropGhasts) {
        this.dropGhasts = dropGhasts;
    }

    void setDropMagmaCubes(int dropMagmaCubes) {
        this.dropMagmaCubes = dropMagmaCubes;
    }

    void setDropPhantoms(int dropPhantoms) {
        this.dropPhantoms = dropPhantoms;
    }

    void setDropPillagers(int dropPillagers) {
        this.dropPillagers = dropPillagers;
    }

    void setDropRavagers(int dropRavagers) {
        this.dropRavagers = dropRavagers;
    }

    void setDropSkeletons(int dropSkeletons) {
        this.dropSkeletons = dropSkeletons;
    }

    void setDropVindicators(int dropVindicators) {
        this.dropVindicators = dropVindicators;
    }

    void setDropWitherSkeletons(int dropWitherSkeletons) {
        this.dropWitherSkeletons = dropWitherSkeletons;
    }

    void setDropZombies(int dropZombies) {
        this.dropZombies = dropZombies;
    }

    void setAirDrops(int airDrops) {
        this.airDrops = airDrops;
    }

    void setBlazes(int blazes) {
        this.blazes = blazes;
    }

    void setCreepers(int creepers) {
        this.creepers = creepers;
    }

    void setEndermen(int endermen) {
        this.endermen = endermen;
    }

    void setEvokers(int evokers) {
        this.evokers = evokers;
    }

    void setGhasts(int ghasts) {
        this.ghasts = ghasts;
    }

    void setMagmaCubes(int magma_cubes) {
        this.magmaCubes = magma_cubes;
    }

    void setPhantoms(int phantoms) {
        this.phantoms = phantoms;
    }

    void setPillagers(int pillagers) {
        this.pillagers = pillagers;
    }

    void setRavagers(int ravagers) {
        this.ravagers = ravagers;
    }

    void setSkeletons(int skeletons) {
        this.skeletons = skeletons;
    }

    void setVindicators(int vindicators) {
        this.vindicators = vindicators;
    }

    void setWitherSkeletons(int wither_skeletons) {
        this.witherSkeletons = wither_skeletons;
    }

    void setZombies(int zombies) {
        this.zombies = zombies;
    }

    void setSuperCreepers(int superCreepers) {
        this.superCreepers = superCreepers;
    }

    void setDropSuperCreepers(int superCreepers) {
        this.dropSuperCreepers = superCreepers;
    }

    void setSuperSpiders(int superCreepers) {
        this.superSpiders = superCreepers;
    }

    void setDropSuperSpiders(int superCreepers) {
        this.dropSuperSpiders = superCreepers;
    }

    void setSuperZombies(int superCreepers) {
        this.superZombies = superCreepers;
    }

    void setDropSuperZombies(int superCreepers) {
        this.dropSuperZombies = superCreepers;
    }

    void killAll(){
        for(RaidMob m: mobs){
            m.kill();
        }
    }

}

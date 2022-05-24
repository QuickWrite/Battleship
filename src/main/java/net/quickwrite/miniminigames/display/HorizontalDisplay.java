package net.quickwrite.miniminigames.display;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public class HorizontalDisplay extends Display implements ConfigurationSerializable {

    public HorizontalDisplay(Location loc1, Location loc2){
        setPos1(loc1);
        setPos2(loc2);
        if(pos1.getBlockX() < pos2.getBlockX() && pos1.getBlockZ() < pos2.getBlockZ()) direction = Direction.NEG_Z_DIRECTION;
        else if(pos1.getBlockX() < pos2.getBlockX() && pos1.getBlockZ() > pos2.getBlockZ()) direction = Direction.POS_X_DIRECTION;
        else if(pos1.getBlockX() > pos2.getBlockX() && pos1.getBlockZ() > pos2.getBlockZ()) direction = Direction.POS_Z_DIRECTION;
        else  direction = Direction.NEG_X_DIRECTION;

        minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
    }

    public HorizontalDisplay(Map<String, Object> data){
        super();
        pos1 = (Location) data.get("pos1");
        pos2 = (Location) data.get("pos2");
        direction = Direction.valueOf((String) data.get("direction"));

        minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
    }


    @Override
    public int getWidth() {
        return Math.abs((pos1.getBlockX() - pos2.getBlockX()) * direction.xMod) + Math.abs((pos1.getBlockZ() - pos2.getBlockZ()) * direction.zMod) + 1;
    }

    @Override
    public int getHeight() {
        return Math.abs((pos1.getBlockX() - pos2.getBlockX()) * direction.zMod) + Math.abs((pos1.getBlockZ() - pos2.getBlockZ()) * direction.xMod) + 1;
    }

    @Override
    public String getNamedPosition(Location loc) {
        return null;
    }

    @Override
    public void setBlock(int x, int z, Material material) {
        if(x < 0 || x >= getWidth()) return;
        if(z < 0 || z >= getHeight()) return;

        sendBlockChange(new Location(pos1.getWorld(), minX, minY, minZ).add(x, 0, z), material);
    }

    @Override
    public void setBlock(Location loc, Material material) {
        if(!(loc.getBlockX() >= minX && loc.getBlockY() <= maxX)) return;
        if(!(loc.getBlockY() >= minY && loc.getBlockY() <= maxX)) return;
        if(!(loc.getBlockZ() >= minZ && loc.getBlockZ() <= maxZ)) return;

        sendBlockChange(loc, material);
    }

    @Override
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("pos1", pos1)
                .put("pos2", pos2)
                .put("direction", direction.name())
                .build();
    }
}

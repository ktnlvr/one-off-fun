package net.ktnlvr.one.events;

import net.ktnlvr.one.KtnlvrMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

public class LootBarrelWorldEvent implements WorldEvent {
    int x, z;

    public LootBarrelWorldEvent(MinecraftServer server) {
        WorldBorder border = server.getOverworld().getWorldBorder();
        World world = server.getOverworld();
        ServerPlayerEntity player = server.getOverworld().getRandomAlivePlayer();

        double centerX = border.getCenterX();
        double centerZ = border.getCenterZ();

        // by nature of how the point are picked, they like to be around the center
        // let's call it a feature, not a bug
        double radius = world.getRandom().nextDouble() * border.getSize() / 2;
        double angle = world.getRandom().nextDouble() * Math.TAU;
        x = (int)(centerX + radius * Math.sin(angle));
        z = (int)(centerZ + radius * Math.cos(angle));
    }

    public Text name() {
        return Text.of("Supply Crate X:" + x + " Z: " + z);
    }

    public int tickDuration() {
        return 10;
    }

    public void begin(MinecraftServer server) {
        BlockPos pos = new BlockPos(x, server.getOverworld().getHeight(), z);

        server.getOverworld().setBlockState(pos, Blocks.BARREL.getDefaultState());
        FallingBlockEntity entity = FallingBlockEntity.spawnFromBlock(server.getOverworld(), pos, Blocks.BARREL.getDefaultState());
        server.getOverworld().spawnEntity(entity);

        //KtnlvrMod.LOGGER.info("Dropping a loot barrel {}", pos);
    }

    public void end(MinecraftServer server) {

    }
}

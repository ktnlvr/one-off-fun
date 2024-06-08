package net.ktnlvr.one.events;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public interface WorldEvent {
    public Text name();

    public int tickDuration();
    public void begin(MinecraftServer server);
    public void end(MinecraftServer server);
}

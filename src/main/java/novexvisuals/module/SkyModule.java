package novexvisuals.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;

public class SkyModule extends Module {
    public static SkyModule INSTANCE;
    public enum Preset { CHERRY, GALAXY, AURORA }
    public Preset preset = Preset.CHERRY;

    public SkyModule() { super("Custom Sky", Category.SKY); INSTANCE = this; }

    public int color(float time) {
        return switch (preset) {
            case CHERRY -> 0xFFFFB7D5;
            case GALAXY -> 0xFF1A0B3D;
            case AURORA -> {
                float t = (float) (Math.sin(time * 0.02) * 0.5 + 0.5);
                int g = (int) (180 + 60 * t), b = (int) (140 + 90 * (1 - t));
                yield 0xFF000000 | (20 << 16) | (g << 8) | b;
            }
        };
    }

    public void tick(MinecraftClient mc) {
        if (!enabled || preset != Preset.CHERRY || mc.world == null || mc.player == null) return;
        var p = mc.player; var r = mc.world.random;
        for (int i = 0; i < 2; i++)
            mc.world.addParticleClient(ParticleTypes.CHERRY_LEAVES,
                p.getX() + r.nextDouble() * 24 - 12, p.getY() + 8 + r.nextDouble() * 4,
                p.getZ() + r.nextDouble() * 24 - 12, 0, -0.05, 0);
    }

    @Override public String setting() { return preset.name(); }
    @Override public void cycle() { preset = Preset.values()[(preset.ordinal() + 1) % Preset.values().length]; }
}

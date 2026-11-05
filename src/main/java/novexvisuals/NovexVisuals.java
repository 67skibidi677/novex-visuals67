package novexvisuals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import novexvisuals.gui.ModuleScreen;
import novexvisuals.module.*;
import org.lwjgl.glfw.GLFW;

public class NovexVisuals implements ClientModInitializer {
    public static final SoundEvent OPEN = reg("open");
    public static final SoundEvent TOGGLE = reg("toggle");
    public static final SoundEvent CHIME = reg("chime");
    public static KeyBinding menuKey;

    static SoundEvent reg(String n) {
        Identifier id = Identifier.of("novexvisuals", n);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    @Override
    public void onInitializeClient() {
        ModuleManager.ALL.add(new SkyModule());
        ModuleManager.ALL.add(new KnifeModule());
        ModuleManager.ALL.add(new FovModule());
        ModuleManager.ALL.add(new SoundModule());
        ModuleManager.ALL.add(new KeybindModule());

        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.novexvisuals.menu", GLFW.GLFW_KEY_P, KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (menuKey.wasPressed()) {
                mc.setScreen(new ModuleScreen());
                playSound(OPEN);
            }
            SkyModule.INSTANCE.tick(mc);
        });
    }

    public static void playSound(SoundEvent fallback) {
        SoundModule s = SoundModule.INSTANCE;
        if (s != null && !s.enabled) return;
        SoundEvent e = s != null ? s.current() : fallback;
        float v = s != null ? s.volume : 1f;
        MinecraftClient.getInstance().getSoundManager()
            .play(PositionedSoundInstance.master(e, 1.0f, v));
    }
}

package novexvisuals.module;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundModule extends Module {
    public static SoundModule INSTANCE;
    public static final String[] SOUNDS = { "open", "toggle", "chime" };
    public int index = 0;
    public float volume = 0.8f;

    public SoundModule() { super("Menu Sound", Category.SOUND); INSTANCE = this; enabled = true; }

    public SoundEvent current() { return SoundEvent.of(Identifier.of("novexvisuals", SOUNDS[index])); }
    @Override public String setting() { return SOUNDS[index] + " " + Math.round(volume * 100) + "%"; }
    @Override public void cycle() { index = (index + 1) % SOUNDS.length; }
    public void louder() { volume = Math.min(1f, volume + 0.1f); }
    public void quieter() { volume = Math.max(0f, volume - 0.1f); }
}

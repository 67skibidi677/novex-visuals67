package novexvisuals.module;

import novexvisuals.NovexVisuals;

public abstract class Module {
    public enum Category { SKY, KNIVES, SOUND, KEYS }
    public final String name;
    public final Category category;
    public boolean enabled;

    public Module(String n, Category c) { name = n; category = c; }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
        NovexVisuals.playSound(NovexVisuals.TOGGLE);
    }
    protected void onEnable() {}
    protected void onDisable() {}
    public String setting() { return ""; }
    public void cycle() {}
}

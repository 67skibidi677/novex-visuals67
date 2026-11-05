package novexvisuals.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import novexvisuals.NovexVisuals;
import novexvisuals.module.*;

public class ModuleScreen extends Screen {
    // null = ALL tab (shows every module). Opens on ALL by default.
    private static final Module.Category[] TABS = {
        null, Module.Category.SKY, Module.Category.KNIVES, Module.Category.SOUND, Module.Category.KEYS };
    private Module.Category tab = null;

    public ModuleScreen() { super(Text.literal("Novex Visuals")); }

    private static String label(Module.Category c) { return c == null ? "ALL" : c.name(); }

    @Override
    public boolean shouldCloseOnEsc() { return !KeybindModule.listening; }

    @Override
    public void render(DrawContext g, int mx, int my, float delta) {
        KeybindModule.poll();
        int w = 360, h = 220, x = (width - w) / 2, y = (height - h) / 2;
        g.fill(x, y, x + w, y + h, 0xE0121218);
        g.fill(x, y, x + w, y + 24, 0xFF1C1C28);
        g.drawTextWithShadow(textRenderer, "Novex Visuals", x + 8, y + 8, 0xFFFF9EC7);

        int tx = x + 84;
        for (Module.Category c : TABS) {
            boolean sel = c == tab;
            g.fill(tx, y + 4, tx + 50, y + 22, sel ? 0xFFFF9EC7 : 0xFF2A2A3A);
            g.drawCenteredTextWithShadow(textRenderer, label(c), tx + 25, y + 9, sel ? 0xFF000000 : 0xFFFFFFFF);
            tx += 52;
        }
        int my2 = y + 34;
        for (Module m : ModuleManager.get(tab)) {
            g.fill(x + 10, my2, x + w - 10, my2 + 22, m.enabled ? 0xFF3A2A4A : 0xFF22222E);
            g.drawTextWithShadow(textRenderer, m.name, x + 18, my2 + 7, 0xFFFFFFFF);
            g.drawTextWithShadow(textRenderer, m.setting(), x + 130, my2 + 7, 0xFFFF9EC7);
            g.drawTextWithShadow(textRenderer, m.enabled ? "ON" : "OFF", x + w - 40, my2 + 7,
                m.enabled ? 0xFF7CFFB2 : 0xFFAAAAAA);
            my2 += 26;
        }
        g.drawTextWithShadow(textRenderer, "Left-click: toggle   Right-click: change setting   Scroll (Sound tab): volume",
            x + 10, y + h - 14, 0xFF888899);
        super.render(g, mx, my, delta);
    }

    // NOTE: in some 1.21.9+ mappings this signature is mouseClicked(Click, boolean). Adjust if the compiler complains.
    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int w = 360, h = 220, x = (width - w) / 2, y = (height - h) / 2;
        int tx = x + 84;
        for (Module.Category c : TABS) {
            if (mx >= tx && mx <= tx + 50 && my >= y + 4 && my <= y + 22) {
                tab = c; NovexVisuals.playSound(NovexVisuals.TOGGLE); return true;
            }
            tx += 52;
        }
        int my2 = y + 34;
        for (Module m : ModuleManager.get(tab)) {
            if (mx >= x + 10 && mx <= x + w - 10 && my >= my2 && my <= my2 + 22) {
                if (button == 1) { m.cycle(); NovexVisuals.playSound(NovexVisuals.TOGGLE); }
                else m.toggle();
                return true;
            }
            my2 += 26;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double h, double v) {
        SoundModule s = SoundModule.INSTANCE;
        if (tab == Module.Category.SOUND && s != null) {
            if (v > 0) s.louder(); else s.quieter();
            NovexVisuals.playSound(NovexVisuals.TOGGLE);
            return true;
        }
        return super.mouseScrolled(mx, my, h, v);
    }
}

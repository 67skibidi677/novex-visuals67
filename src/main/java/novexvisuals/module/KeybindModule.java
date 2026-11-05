package novexvisuals.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import novexvisuals.NovexVisuals;
import org.lwjgl.glfw.GLFW;

/** Click it, then press any key to rebind the menu key. Esc cancels. */
public class KeybindModule extends Module {
    public static boolean listening = false;

    public KeybindModule() { super("Menu Key", Category.KEYS); }

    @Override public void toggle() { listening = true; NovexVisuals.playSound(NovexVisuals.TOGGLE); }
    @Override public void cycle() { toggle(); }

    @Override public String setting() {
        return listening ? "press a key..."
            : NovexVisuals.menuKey.getBoundKeyLocalizedText().getString();
    }

    /** Called every frame from the menu screen while listening. */
    public static void poll() {
        if (!listening) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        long window = mc.getWindow().getHandle();
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
            listening = false; return;
        }
        for (int k = GLFW.GLFW_KEY_SPACE; k <= GLFW.GLFW_KEY_LAST; k++) {
            if (k == GLFW.GLFW_KEY_ESCAPE) continue;
            if (GLFW.glfwGetKey(window, k) == GLFW.GLFW_PRESS) {
                NovexVisuals.menuKey.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(k));
                KeyBinding.updateKeysByCode();
                mc.options.write();
                listening = false;
                NovexVisuals.playSound(NovexVisuals.TOGGLE);
                return;
            }
        }
    }
}

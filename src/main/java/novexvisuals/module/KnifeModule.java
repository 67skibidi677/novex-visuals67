package novexvisuals.module;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class KnifeModule extends Module {
    public static KnifeModule INSTANCE;
    public enum Knife { BUTTERFLY, KATANA }
    public Knife knife = Knife.BUTTERFLY;

    public KnifeModule() { super("Knife Skin", Category.KNIVES); INSTANCE = this; }

    public static ItemStack swap(ItemStack stack) {
        KnifeModule m = INSTANCE;
        if (m == null || !m.enabled || !stack.isIn(ItemTags.SWORDS)) return stack;
        ItemStack copy = stack.copy();
        copy.set(DataComponentTypes.ITEM_MODEL,
            Identifier.of("novexvisuals", m.knife.name().toLowerCase()));
        return copy;
    }

    @Override public String setting() { return knife.name(); }
    @Override public void cycle() { knife = Knife.values()[(knife.ordinal() + 1) % Knife.values().length]; }
}

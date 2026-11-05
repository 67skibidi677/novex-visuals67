package novexvisuals.mixin;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.item.ItemStack;
import novexvisuals.module.KnifeModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelManager.class)
public class ItemModelMixin {
    @ModifyVariable(method = "update", at = @At("HEAD"), argsOnly = true)
    private ItemStack novex$swap(ItemStack stack) { return KnifeModule.swap(stack); }
}

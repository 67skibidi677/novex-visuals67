package novexvisuals.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import novexvisuals.module.FovModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class FovMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void novex$fov(Camera c, float d, boolean f, CallbackInfoReturnable<Float> cir) {
        FovModule m = FovModule.INSTANCE;
        if (m != null && m.enabled) cir.setReturnValue(m.fov);
    }
}

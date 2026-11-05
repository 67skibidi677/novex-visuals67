package novexvisuals.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import novexvisuals.module.SkyModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class SkyMixin {
    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void novex$sky(Vec3d pos, float delta, CallbackInfoReturnable<Integer> cir) {
        SkyModule m = SkyModule.INSTANCE;
        if (m != null && m.enabled)
            cir.setReturnValue(m.color(((ClientWorld) (Object) this).getTime() + delta));
    }
}

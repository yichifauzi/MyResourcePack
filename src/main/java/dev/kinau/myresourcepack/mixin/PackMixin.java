package dev.kinau.myresourcepack.mixin;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pack.class)
public abstract class PackMixin {

    @Shadow public abstract PackSource getPackSource();

    @Inject(method = "isFixedPosition", at = @At("HEAD"), cancellable = true)
    public void onIsFixedPosition(CallbackInfoReturnable<Boolean> cir) {
        if (getPackSource() == PackSource.SERVER)
            cir.setReturnValue(false);
    }
}

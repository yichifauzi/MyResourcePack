package dev.kinau.myresourcepack.mixin;

import dev.kinau.myresourcepack.MyResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(PackSelectionScreen.class)
public class PackSelectionScreenMixin {

    @Inject(method = "init", at = @At("HEAD"))
    public void onInit(CallbackInfo ci) {
        if (MyResourcePack.getInstance().getCurrentServer() != null)
            MyResourcePack.getInstance().setConfiguringPackOrder(true);
    }

    @Inject(method = "onClose", at = @At("HEAD"))
    public void onClose(CallbackInfo ci) {
        if (MyResourcePack.getInstance().getCurrentServer() != null)
            MyResourcePack.getInstance().setConfiguringPackOrder(true);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(PackRepository packRepository, Consumer<PackRepository> consumer, Path path, Component component, CallbackInfo ci) {
        Minecraft.getInstance().options.resourcePacks = packRepository.getSelectedPacks().stream().filter(Predicate.not(Pack::isFixedPosition)).map(Pack::getId).collect(Collectors.toCollection(ArrayList::new));
    }

}

package dev.rebuilt.dfr.mixin.jei;

import dev.rebuilt.dfr.client.JeiButtonVisibility;
import mezz.jei.api.gui.buttons.IIconButtonController;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.common.input.UserInput;
import mezz.jei.common.input.interaction.IInputInteraction;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/** A hidden JEI icon must not keep an invisible clickable hitbox. */
@Mixin(targets = "mezz.jei.gui.elements.IconButton$ButtonInputHandler", remap = false)
public abstract class JeiIconButtonInputMixin {
    @Shadow @Final private IIconButtonController controller;

    @Inject(method = "beginInput", at = @At("HEAD"), cancellable = true)
    private void dfr$disableHiddenButtonInput(
            Screen screen,
            UserInput input,
            IInternalKeyMappings keys,
            CallbackInfoReturnable<Optional<IInputInteraction>> cir
    ) {
        if (JeiButtonVisibility.isHidden(controller)) {
            cir.setReturnValue(Optional.empty());
        }
    }
}

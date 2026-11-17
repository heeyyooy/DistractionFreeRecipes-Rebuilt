package dev.rebuilt.dfr.mixin.jei;

import dev.rebuilt.dfr.client.JeiButtonVisibility;
import mezz.jei.api.gui.buttons.IIconButtonController;
import mezz.jei.gui.elements.IconButton;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Removes disabled JEI buttons from rendering, tooltips, and hover detection. */
@Mixin(value = IconButton.class, remap = false)
public abstract class JeiIconButtonMixin {
    @Shadow @Final private IIconButtonController controller;

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private void dfr$hideButton(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (JeiButtonVisibility.isHidden(controller)) {
            ci.cancel();
        }
    }

    @Inject(method = "drawTooltips", at = @At("HEAD"), cancellable = true)
    private void dfr$hideButtonTooltip(GuiGraphics graphics, int mouseX, int mouseY, CallbackInfo ci) {
        if (JeiButtonVisibility.isHidden(controller)) {
            ci.cancel();
        }
    }

    @Inject(method = "isMouseOver", at = @At("HEAD"), cancellable = true)
    private void dfr$disableButtonHover(double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {
        if (JeiButtonVisibility.isHidden(controller)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isVisible", at = @At("HEAD"), cancellable = true)
    private void dfr$disableButtonVisibility(CallbackInfoReturnable<Boolean> cir) {
        if (JeiButtonVisibility.isHidden(controller)) {
            cir.setReturnValue(false);
        }
    }
}

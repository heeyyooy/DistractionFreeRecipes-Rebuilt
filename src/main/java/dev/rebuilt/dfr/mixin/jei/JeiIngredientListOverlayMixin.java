package dev.rebuilt.dfr.mixin.jei;

import dev.rebuilt.dfr.client.GuiHelpers;
import dev.rebuilt.dfr.config.DFRConfig;
import mezz.jei.gui.input.GuiTextFieldFilter;
import mezz.jei.gui.overlay.IngredientListOverlay;
import mezz.jei.gui.overlay.ingredients.IIngredientListOverlayContents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * JEI 19.64.x integration for Minecraft 1.21.1.
 *
 * The important part is to override isListDisplayed(), not merely skip one
 * render call: JEI also uses this state for hit-testing, ingredient lookup,
 * tooltips, drag sources, and input handlers.
 */
@Mixin(value = IngredientListOverlay.class, remap = false)
public abstract class JeiIngredientListOverlayMixin {
    @Shadow @Final private GuiTextFieldFilter searchField;
    @Shadow @Final private IIngredientListOverlayContents contents;
    @Shadow private boolean isSearchDisplayed() { return false; }

    @Inject(method = "isListDisplayed", at = @At("HEAD"), cancellable = true)
    private void dfr$hideListWhenSearchIsEmpty(CallbackInfoReturnable<Boolean> cir) {
        if (DFRConfig.ENABLED.get() && searchField.getValue().isEmpty()) {
            // This disables JEI's own input/hover/tooltip paths as well as drawing.
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "drawForeground", at = @At("HEAD"))
    private void dfr$updateBounds(Minecraft minecraft, GuiGraphics graphics,
                                  int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // JEI invokes its overlay renderer for many screens. Do not retain the
        // previous gameplay bounds while a pause/options screen is active.
        if (!isSearchDisplayed()) {
            GuiHelpers.clear();
            return;
        }
        GuiHelpers.update(contents.getBackgroundArea().toMutable(), searchField.getValue().isEmpty());
    }

    @Inject(method = "drawForeground", at = @At("TAIL"))
    private void dfr$drawToggle(Minecraft minecraft, GuiGraphics graphics,
                                int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // Also guard the render itself: the current screen may change between
        // overlay callbacks, and stale coordinates must never be drawn there.
        if (!isSearchDisplayed()) {
            GuiHelpers.clear();
            return;
        }

        boolean empty = searchField.getValue().isEmpty();
        if (DFRConfig.ENABLED.get() && empty) {
            GuiHelpers.drawShowRecipesButton(graphics);
        } else if (!DFRConfig.ENABLED.get()) {
            GuiHelpers.drawHideRecipesButton(graphics);
        }
    }
}

package dev.rebuilt.dfr.mixin.jei;

import dev.rebuilt.dfr.config.DFRConfig;
import mezz.jei.common.gui.elements.ScalableDrawable;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.gui.input.GuiTextFieldFilter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FastColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** JEI draws its search background through ScalableDrawable, not EditBox's sprite path. */
@Mixin(value = GuiTextFieldFilter.class, remap = false)
public abstract class JeiSearchFieldOpacityMixin {
    @Redirect(
            method = "drawBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lmezz/jei/common/gui/elements/ScalableDrawable;draw(Lnet/minecraft/client/gui/GuiGraphics;Lmezz/jei/common/util/ImmutableRect2i;)V"
            )
    )
    private void dfr$drawJeiBackgroundWithReducedOpacity(
            ScalableDrawable drawable,
            GuiGraphics graphics,
            ImmutableRect2i bounds
    ) {
        if (!DFRConfig.LOWER_OPACITY.get()) {
            drawable.draw(graphics, bounds);
            return;
        }

        // Use a per-vertex alpha fill rather than shader color: GuiGraphics may
        // batch sprite draws, so a global shader-color change can be reset before flush.
        int color = FastColor.ARGB32.color(70, 0, 0, 0);
        graphics.fill(
                bounds.getX(),
                bounds.getY(),
                bounds.getX() + bounds.getWidth(),
                bounds.getY() + bounds.getHeight(),
                color
        );
    }
}

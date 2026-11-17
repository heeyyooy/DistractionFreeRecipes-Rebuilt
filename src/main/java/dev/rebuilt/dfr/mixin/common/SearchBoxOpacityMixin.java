package dev.rebuilt.dfr.mixin.common;

import dev.rebuilt.dfr.config.DFRConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EditBox.class)
public abstract class SearchBoxOpacityMixin {
    private static final String EMI_SEARCH = "dev.emi.emi.screen.widget.EmiSearchWidget";
    private static final String JEI_SEARCH = "mezz.jei.gui.input.GuiTextFieldFilter";

    @Redirect(
            method = "renderWidget",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"),
            require = 0
    )
    private void dfr$lowerSearchOpacity(GuiGraphics graphics, ResourceLocation sprite, int x, int y, int width, int height) {
        String className = ((Object) this).getClass().getName();
        if (DFRConfig.LOWER_OPACITY.get() && (className.equals(EMI_SEARCH) || className.equals(JEI_SEARCH))) {
            int color = FastColor.ARGB32.color(70, 0, 0, 0);
            graphics.fill(x, y, x + width, y + height, color);
            graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, color);
            return;
        }
        graphics.blitSprite(sprite, x, y, width, height);
    }
}

package dev.rebuilt.dfr.mixin.emi;

import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiSidebars;
import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenManager;
import dev.rebuilt.dfr.client.GuiHelpers;
import dev.rebuilt.dfr.config.DFRConfig;
import net.minecraft.client.renderer.Rect2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EmiScreenManager.SidebarPanel.class, remap = false)
public abstract class EmiSidebarPanelMixin {
    @Shadow public EmiScreenManager.ScreenSpace space;


    /**
     * EMI uses isVisible() not only for rendering but also for hit testing
     * (getHoveredPanel/getHoveredSpace). Returning false prevents invisible
     * index slots from continuing to produce hover tooltips and recipe actions.
     */
    @Inject(method = "isVisible", at = @At("HEAD"), cancellable = true)
    private void dfr$hideEmptyPanelsFromHitTesting(CallbackInfoReturnable<Boolean> cir) {
        EmiScreenManager.SidebarPanel panel = (EmiScreenManager.SidebarPanel) (Object) this;

        boolean hideEmptyIndex = DFRConfig.ENABLED.get()
                && panel.getType() == SidebarType.INDEX
                && EmiScreenManager.search.getValue().isEmpty();
        boolean hideEmptyFavorites = DFRConfig.HIDE_EMPTY_EMI_FAVORITES.get()
                && panel.getType() == SidebarType.FAVORITES
                && EmiSidebars.getStacks(SidebarType.FAVORITES).isEmpty();

        if (hideEmptyIndex || hideEmptyFavorites) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void dfr$renderSidebar(EmiDrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        EmiScreenManager.SidebarPanel panel = (EmiScreenManager.SidebarPanel) (Object) this;
        if (space == null) return;

        if (panel.getType() == SidebarType.FAVORITES
                && EmiSidebars.getStacks(SidebarType.FAVORITES).isEmpty()) {
            Rect2i favoritesBounds = new Rect2i(space.tx, space.ty, space.tw * 18, space.th * 18);
            if (DFRConfig.SHOW_EMI_FAVORITES_HINT.get()) {
                GuiHelpers.drawEmiFavoritesHint(context.raw(), favoritesBounds, EmiConfig.favorite.getBindText());
            }
            if (DFRConfig.HIDE_EMPTY_EMI_FAVORITES.get()) {
                ci.cancel();
            }
            return;
        }

        if (panel.getType() != SidebarType.INDEX) return;

        boolean empty = EmiScreenManager.search.getValue().isEmpty();
        Rect2i bounds = new Rect2i(space.tx, space.ty, space.tw * 18, space.th * 18);
        GuiHelpers.update(bounds, empty);

        if (!DFRConfig.ENABLED.get()) {
            GuiHelpers.drawHideRecipesButton(context.raw());
        } else if (empty) {
            GuiHelpers.drawShowRecipesButton(context.raw());
            ci.cancel();
        }
    }
}

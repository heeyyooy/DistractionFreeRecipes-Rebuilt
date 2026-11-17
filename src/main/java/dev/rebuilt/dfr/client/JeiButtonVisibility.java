package dev.rebuilt.dfr.client;

import dev.rebuilt.dfr.config.DFRConfig;
import mezz.jei.api.gui.buttons.IIconButtonController;
import mezz.jei.gui.overlay.ConfigButtonController;
import mezz.jei.gui.overlay.bookmarks.BookmarkButtonController;
import mezz.jei.gui.overlay.bookmarks.history.LookupHistoryButtonController;

/**
 * Runtime visibility rules for optional JEI toolbar buttons.
 *
 * This class deliberately lives outside the configured Mixin package.
 * Mixin reserves classes in that package for transformation and forbids
 * loading them directly as ordinary Java classes at runtime.
 */
public final class JeiButtonVisibility {
    private JeiButtonVisibility() {}

    public static boolean isHidden(IIconButtonController controller) {
        if (controller instanceof ConfigButtonController) {
            return !DFRConfig.SHOW_JEI_CONFIG_BUTTON.get();
        }
        if (controller instanceof BookmarkButtonController) {
            return !DFRConfig.SHOW_JEI_BOOKMARKS_BUTTON.get();
        }
        if (controller instanceof LookupHistoryButtonController) {
            return !DFRConfig.SHOW_JEI_LOOKUP_HISTORY_BUTTON.get();
        }
        return false;
    }
}

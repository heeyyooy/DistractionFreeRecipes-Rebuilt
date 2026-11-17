package dev.rebuilt.dfr.client;

import dev.rebuilt.dfr.config.DFRConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.util.FastColor;
import net.minecraft.network.chat.Component;

public final class GuiHelpers {
    private static Rect2i searchBounds = new Rect2i(0, 0, 0, 0);
    private static Screen ownerScreen;
    private static boolean searchEmpty;
    private static boolean frameReady;

    private GuiHelpers() {}

    public static void update(Rect2i bounds, boolean isSearchEmpty) {
        searchBounds = bounds;
        ownerScreen = Minecraft.getInstance().screen;
        searchEmpty = isSearchEmpty;
        frameReady = bounds.getWidth() > 0 && bounds.getHeight() > 0;
    }

    /** Clears bounds and click ownership when JEI is not active on this screen. */
    public static void clear() {
        searchBounds = new Rect2i(0, 0, 0, 0);
        ownerScreen = null;
        searchEmpty = false;
        frameReady = false;
    }

    public static void drawShowRecipesButton(GuiGraphics graphics) {
        Rect2i button = getShowRecipesBounds();

        if (DFRConfig.SHOW_HELP_TEXT.get()) {
            Component message = Component.translatable("distraction_free_recipes.hint.search_to_view_recipes");
            int width = Minecraft.getInstance().font.width(message);
            int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            int x = clamp(searchBounds.getX() + (searchBounds.getWidth() - width) / 2,
                    2, Math.max(2, screenWidth - width - 2));
            int y = clamp(button.getY() - 20, 2,
                    Math.max(2, screenHeight - Minecraft.getInstance().font.lineHeight - 2));
            graphics.drawString(
                    Minecraft.getInstance().font,
                    message,
                    x,
                    y,
                    FastColor.ARGB32.color(128, 200, 200, 200),
                    false
            );
        }

        if (DFRConfig.SHOW_TOGGLE_BUTTON.get()) {
            graphics.fill(button.getX(), button.getY(), button.getX() + button.getWidth(),
                    button.getY() + button.getHeight(), FastColor.ARGB32.color(50, 0, 0, 0));
            drawCentered(graphics, Component.translatable("distraction_free_recipes.button.show_recipes"), button, button.getY() + 4);
        }
    }

    public static void drawEmiFavoritesHint(GuiGraphics graphics, Rect2i bounds, Component keybindText) {
        if (bounds.getWidth() <= 0 || bounds.getHeight() <= 0) return;

        Minecraft minecraft = Minecraft.getInstance();
        Component message = Component.translatable("distraction_free_recipes.hint.add_to_favorites", keybindText);
        int textWidth = minecraft.font.width(message);
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = clamp(bounds.getX() + (bounds.getWidth() - textWidth) / 2, 2, Math.max(2, screenWidth - textWidth - 2));
        int y = clamp(bounds.getY() + (bounds.getHeight() - minecraft.font.lineHeight) / 2, 2, Math.max(2, screenHeight - minecraft.font.lineHeight - 2));

        graphics.drawString(minecraft.font, message, x, y,
                FastColor.ARGB32.color(128, 200, 200, 200), false);
    }

    public static void drawHideRecipesButton(GuiGraphics graphics) {
        if (!DFRConfig.SHOW_TOGGLE_BUTTON.get()) return;
        Rect2i button = getHideRecipesBounds();
        graphics.fill(button.getX(), button.getY(), button.getX() + button.getWidth(),
                button.getY() + button.getHeight(), FastColor.ARGB32.color(50, 0, 0, 0));
        drawCentered(graphics, Component.translatable("distraction_free_recipes.button.hide_recipes"), button, button.getY() + 4);
    }

    private static void drawCentered(GuiGraphics graphics, Component text, Rect2i bounds, int y) {
        int textWidth = Minecraft.getInstance().font.width(text);
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int x = clamp(bounds.getX() + (bounds.getWidth() - textWidth) / 2,
                2, Math.max(2, screenWidth - textWidth - 2));
        int textY = clamp(y, 2,
                Math.max(2, screenHeight - Minecraft.getInstance().font.lineHeight - 2));
        graphics.drawString(
                Minecraft.getInstance().font,
                text,
                x,
                textY,
                FastColor.ARGB32.color(128, 200, 200, 200),
                false
        );
    }

    public static Rect2i getShowRecipesBounds() {
        int centerX = searchBounds.getX() + searchBounds.getWidth() / 2;
        int buttonWidth = getButtonWidth("distraction_free_recipes.button.show_recipes");
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int x = clamp(centerX - buttonWidth / 2, 2, Math.max(2, screenWidth - buttonWidth - 2));
        int y = clamp(screenHeight / 2 + 20, 0, Math.max(0, screenHeight - 15));
        return new Rect2i(x, y, buttonWidth, 15);
    }

    public static Rect2i getHideRecipesBounds() {
        int buttonWidth = getButtonWidth("distraction_free_recipes.button.hide_recipes");
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        // Keep the toggle button immediately to the left of the JEI search field.
        int x = clamp(searchBounds.getX() - buttonWidth - 5,
                2, Math.max(2, screenWidth - buttonWidth - 2));
        return new Rect2i(x, 8, buttonWidth, 15);
    }

    /**
     * Sizes the background and hitbox from the active language's actual font
     * metrics. This prevents longer translations (notably Russian in JEI)
     * from overflowing the translucent button background.
     */
    private static int getButtonWidth(String translationKey) {
        Minecraft minecraft = Minecraft.getInstance();
        int textWidth = minecraft.font.width(Component.translatable(translationKey));
        int desiredWidth = Math.max(80, textWidth + 16);
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        return Math.min(desiredWidth, Math.max(0, screenWidth - 4));
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    public static boolean handleClick(Screen screen, int mouseX, int mouseY) {
        if (!frameReady || ownerScreen != screen) return false;

        if (DFRConfig.ENABLED.get() && searchEmpty) {
            if (DFRConfig.SHOW_TOGGLE_BUTTON.get() && getShowRecipesBounds().contains(mouseX, mouseY)) {
                DFRConfig.ENABLED.set(false);
                return true;
            }
            // The hidden index is visually absent; do not let it receive invisible clicks,
            // even when the on-screen toggle button has been disabled in the config.
            return searchBounds.contains(mouseX, mouseY);
        }

        if (!DFRConfig.ENABLED.get() && DFRConfig.SHOW_TOGGLE_BUTTON.get()
                && getHideRecipesBounds().contains(mouseX, mouseY)) {
            DFRConfig.ENABLED.set(true);
            return true;
        }
        return false;
    }
}

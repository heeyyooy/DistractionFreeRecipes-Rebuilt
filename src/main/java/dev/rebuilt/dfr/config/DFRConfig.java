package dev.rebuilt.dfr.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class DFRConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLED;
    public static final ModConfigSpec.BooleanValue HIDE_EMPTY_EMI_FAVORITES;
    public static final ModConfigSpec.BooleanValue SHOW_EMI_FAVORITES_HINT;
    public static final ModConfigSpec.BooleanValue LOWER_OPACITY;
    public static final ModConfigSpec.BooleanValue SHOW_HELP_TEXT;
    public static final ModConfigSpec.BooleanValue SHOW_TOGGLE_BUTTON;
    public static final ModConfigSpec.BooleanValue SHOW_JEI_CONFIG_BUTTON;
    public static final ModConfigSpec.BooleanValue SHOW_JEI_BOOKMARKS_BUTTON;
    public static final ModConfigSpec.BooleanValue SHOW_JEI_LOOKUP_HISTORY_BUTTON;
    public static final ModConfigSpec SPEC;

    static {
        BUILDER
                .comment("Client-side settings for the recipe viewer overlays.")
                .translation("config.distraction_free_recipes.client")
                .push("client");

        ENABLED = BUILDER
                .comment("Automatically hide the recipe/item list while the search field is empty.")
                .translation("config.distraction_free_recipes.client.enabled")
                .define("enabled", true);

        HIDE_EMPTY_EMI_FAVORITES = BUILDER
                .comment("Hide the EMI Favorites sidebar, including its background and header buttons, when it is empty.")
                .translation("config.distraction_free_recipes.client.hideEmptyEmiFavorites")
                .define("hideEmptyEmiFavorites", true);

        SHOW_EMI_FAVORITES_HINT = BUILDER
                .comment("Show a hint with the currently configured EMI favorite keybind while the Favorites sidebar is empty.")
                .translation("config.distraction_free_recipes.client.showEmiFavoritesHint")
                .define("showEmiFavoritesHint", true);

        LOWER_OPACITY = BUILDER
                .comment("Reduce the background opacity of the EMI and JEI search fields.")
                .translation("config.distraction_free_recipes.client.lowerOpacity")
                .define("lowerOpacity", false);

        SHOW_HELP_TEXT = BUILDER
                .comment("Show the Search to View Recipes hint while the list is hidden.")
                .translation("config.distraction_free_recipes.client.showHelpText")
                .define("showHelpText", true);

        SHOW_TOGGLE_BUTTON = BUILDER
                .comment("Show the in-screen Show Recipes / Hide Recipes toggle button.")
                .translation("config.distraction_free_recipes.client.showToggleButton")
                .define("showToggleButton", true);

        SHOW_JEI_CONFIG_BUTTON = BUILDER
                .comment("Show the JEI Config button.")
                .translation("config.distraction_free_recipes.client.showJeiConfigButton")
                .define("showJeiConfigButton", true);

        SHOW_JEI_BOOKMARKS_BUTTON = BUILDER
                .comment("Show the JEI Bookmarks toggle button.")
                .translation("config.distraction_free_recipes.client.showJeiBookmarksButton")
                .define("showJeiBookmarksButton", true);

        SHOW_JEI_LOOKUP_HISTORY_BUTTON = BUILDER
                .comment("Show the JEI Lookup History toggle button.")
                .translation("config.distraction_free_recipes.client.showJeiLookupHistoryButton")
                .define("showJeiLookupHistoryButton", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private DFRConfig() {}
}

package dev.rebuilt.dfr;

import dev.rebuilt.dfr.config.DFRConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(DistractionFreeRecipes.MOD_ID)
public final class DistractionFreeRecipes {
    public static final String MOD_ID = "distraction_free_recipes";
    public static final String MOD_NAME = "Distraction Free Recipes Rebuilt";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public DistractionFreeRecipes(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, DFRConfig.SPEC);
        LOGGER.info("{} loaded. TxniLib and Forgified Fabric API are not required.", MOD_NAME);
    }
}

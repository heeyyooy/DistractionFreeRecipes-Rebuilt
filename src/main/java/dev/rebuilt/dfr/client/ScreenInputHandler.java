package dev.rebuilt.dfr.client;

import dev.rebuilt.dfr.DistractionFreeRecipes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = DistractionFreeRecipes.MOD_ID, value = Dist.CLIENT)
public final class ScreenInputHandler {
    private ScreenInputHandler() {}

    @SubscribeEvent
    public static void onMousePressed(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getButton() != GLFW.GLFW_MOUSE_BUTTON_LEFT) return;
        int mouseX = (int) Math.floor(event.getMouseX());
        int mouseY = (int) Math.floor(event.getMouseY());
        if (GuiHelpers.handleClick(event.getScreen(), mouseX, mouseY)) {
            event.setCanceled(true);
        }
    }
}

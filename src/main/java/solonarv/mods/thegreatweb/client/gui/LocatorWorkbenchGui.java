package solonarv.mods.thegreatweb.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import solonarv.mods.thegreatweb.common.gui.ContainerLocatorWorkbench;
import solonarv.mods.thegreatweb.common.lib.util.ResourceLocationUtils;

public class LocatorWorkbenchGui extends GuiContainer {
    public static final int WIDTH = 256;
    public static final int HEIGHT = 256;

    private static final ResourceLocation background = ResourceLocationUtils.withModID("textures/gui/locator_workbench.png");

    public LocatorWorkbenchGui(ContainerLocatorWorkbench container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}

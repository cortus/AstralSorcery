package hellfirepvp.astralsorcery.client.gui.journal.page;

import hellfirepvp.astralsorcery.client.ClientScheduler;
import hellfirepvp.astralsorcery.client.util.TextureHelper;
import hellfirepvp.astralsorcery.client.util.resource.AssetLibrary;
import hellfirepvp.astralsorcery.client.util.resource.AssetLoader;
import hellfirepvp.astralsorcery.client.util.resource.BindableResource;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.AttunementRecipe;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: JournalPageAttunementRecipe
 * Created by HellFirePvP
 * Date: 27.10.2016 / 01:10
 */
public class JournalPageAttunementRecipe implements IJournalPage {

    private final AttunementRecipe recipe;

    public JournalPageAttunementRecipe(AttunementRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public IGuiRenderablePage buildRenderPage() {
        return new Render(recipe);
    }

    public static class Render extends JournalPageDiscoveryRecipe.Render {

        private static final BindableResource texGrid = AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "gridAtt");

        private final AttunementRecipe recipe;

        public Render(AttunementRecipe recipe) {
            super(recipe);
            this.recipe = recipe;
            this.gridTexture = texGrid;
        }

        protected void renderAltarSlots(float offsetX, float offsetY, float zLevel, AttunementRecipe recipe) {
            RenderHelper.enableGUIStandardItemLighting();
            renderAltarSlot(offsetX + 30, offsetY + 78, zLevel, recipe.getAttItems(AttunementRecipe.AltarSlot.UPPER_LEFT));
            renderAltarSlot(offsetX + 131, offsetY + 78, zLevel, recipe.getAttItems(AttunementRecipe.AltarSlot.UPPER_RIGHT));
            renderAltarSlot(offsetX +  30, offsetY + 178, zLevel, recipe.getAttItems(AttunementRecipe.AltarSlot.LOWER_LEFT));
            renderAltarSlot(offsetX + 131, offsetY + 178, zLevel, recipe.getAttItems(AttunementRecipe.AltarSlot.LOWER_RIGHT));
            RenderHelper.disableStandardItemLighting();
            TextureHelper.refreshTextureBindState();
        }

        private void renderAltarSlot(float offsetX, float offsetY, float zLevel, java.util.List<ItemStack> stacks) {
            if(stacks == null || stacks.isEmpty()) return;

            int select = ((ClientScheduler.getClientTick() + ((int) offsetX) * 40 + ((int) offsetY) * 40) / 20);
            select %= stacks.size();
            ItemStack draw = stacks.get(select);

            TextureHelper.refreshTextureBindState();
            GL11.glPushMatrix();
            GL11.glTranslated(offsetX, offsetY, zLevel + 60);
            GL11.glScaled(1.1, 1.1, 1.1);
            Rectangle r = drawItemStack(draw, 0, 0, 0);
            r = new Rectangle((int) offsetX, (int) offsetY, (int) (r.getWidth() * 1.1), (int) (r.getHeight() * 1.1));
            addRenderedStackRectangle(r, draw);
            GL11.glPopMatrix();
        }

        @Override
        public void render(float offsetX, float offsetY, float pTicks, float zLevel, float mouseX, float mouseY) {
            super.render(offsetX, offsetY, pTicks, zLevel, mouseX, mouseY);

            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            GL11.glColor4f(1F, 1F, 1F, 1F);
            renderAltarSlots(offsetX, offsetY, zLevel, recipe);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopAttrib();
        }

    }

}
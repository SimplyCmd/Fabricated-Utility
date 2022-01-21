package simplycmd.fabricated_utility.villagers.impl;

import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;
import net.minecraft.village.Merchant;

public class ReloadTextureButtonWidget extends TexturedButtonWidget {
    private final Merchant condition;

    public ReloadTextureButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, PressAction pressAction, Merchant condition) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, pressAction);
        this.condition = condition;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!GuiEvents.fabricated_utility$isValid(condition)) return false;
        return super.mouseClicked(mouseX, mouseY, button);
    }
}

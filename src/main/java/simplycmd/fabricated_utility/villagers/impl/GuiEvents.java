package simplycmd.fabricated_utility.villagers.impl;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import simplycmd.fabricated_utility.Main;
import simplycmd.fabricated_utility.villagers.api.BetterVillagersExtension;

public class GuiEvents {
    private GuiEvents(){}
    static {
        ScreenEvents.AFTER_INIT.register(((client, scren, scaledWidth, scaledHeight) -> {
            if (!(scren instanceof MerchantScreen)) return;
            ScreenEvents.afterRender(scren).register(((screen, matrices, mouseX, mouseY, tickDelta) -> {
                if (!((MerchantScreen) screen).getScreenHandler().merchant.isLeveledMerchant()) return;
                Screens.getButtons(screen).add(new ReloadTextureButtonWidget(screen.width / 2 + 104, screen.height / 2 - 22, 20, 18, fabricated_utility$isValid(((MerchantScreen) screen).getScreenHandler().merchant) ? 0 : 21, 0, fabricated_utility$isValid(((MerchantScreen) screen).getScreenHandler().merchant) ? 19 : 0,
                        new Identifier(Main.MOD_ID, "textures/gui/container/cycletrades.png"), (buttonWidget) -> {
                    buttonWidget.active = fabricated_utility$isValid(((MerchantScreen) screen).getScreenHandler().merchant);
                    if (fabricated_utility$isValid(((MerchantScreen) screen).getScreenHandler().merchant))
                        ClientPlayNetworking.send(Main.CYCLETRADES_PACKET_C2S.getIdentifier(), PacketByteBufs.empty());

                }, ((MerchantScreen) screen).getScreenHandler().merchant));
            }));
        }));
    }

    public static void init() {
    }

    public static boolean fabricated_utility$isValid(Merchant merchant) {
        return merchant.getExperience() <= 0 && merchant.isLeveledMerchant();
    }

    public static void onCycleTrades(ServerPlayerEntity player, MerchantEntity entity) {

        if (!(player.currentScreenHandler instanceof MerchantScreenHandler handler)) {
            return;
        }

        if (handler.getExperience() > 0) {
            return;
        }
        if ((entity instanceof VillagerEntity villagerEntity)) {
            entity.offers = null;
            recalculateOffers(villagerEntity);
            ItemStack slot0 = handler.getSlot(0).getStack().copy();
            ItemStack slot1 = handler.getSlot(1).getStack().copy();
            handler.getSlot(0).setStack(ItemStack.EMPTY);
            handler.getSlot(1).setStack(ItemStack.EMPTY);
            handler.getSlot(2).setStack(ItemStack.EMPTY);
            player.getInventory().offerOrDrop(slot0);
            player.getInventory().offerOrDrop(slot1);
            player.sendTradeOffers(handler.syncId, entity.getOffers(), villagerEntity.getVillagerData().getLevel(), entity.getExperience(), entity.isLeveledMerchant(), villagerEntity.canRestock());
            return;
        }

        if (!(entity instanceof BetterVillagersExtension extension)) return;
        entity.offers = null;
        entity.getOffers();
        player.sendTradeOffers(handler.syncId, entity.getOffers(), extension.betterVillagers$getLevel(), entity.getExperience(), entity.isLeveledMerchant(), extension.betterVillagers$canRestock());
    }

    public static void recalculateOffers(VillagerEntity villagerEntity) {
        resetOffers(villagerEntity);
        calculateOffers(villagerEntity);
    }

    private static void resetOffers(VillagerEntity villagerEntity) {
        for (TradeOffer tradeOffer : villagerEntity.getOffers()) {
            tradeOffer.clearSpecialPrice();
        }
    }

    private static void calculateOffers(VillagerEntity villagerEntity) {
        int i = getUniversalReputation(villagerEntity);
        if (i != 0) {
            for (TradeOffer tradeOffer : villagerEntity.getOffers()) {
                tradeOffer.increaseSpecialPrice(-MathHelper.floor((float) i * tradeOffer.getPriceMultiplier()));
            }
        }
    }

    public static int getUniversalReputation(VillagerEntity villagerEntity) {
        return villagerEntity.getGossip().getEntityReputationAssociatedGossips().keySet().stream().map(uuid -> villagerEntity.getGossip().getReputationFor(uuid, (gossipType) -> true)).reduce(0, Integer::sum);
    }

}

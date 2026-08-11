package com.chenkong.morecopperblock.mixin;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getHoverName", at = @At("HEAD"), cancellable = true)
    private void onGetHoverName(CallbackInfoReturnable<Component> cir) {
        ItemStack self = (ItemStack) (Object) this;

        // Cut alloy stairs (216-value composition = 6 cut blocks × 36)
        if (self.is(MoreCopperBlock.CUT_ALLOY_COPPER_STAIRS_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_STAIRS_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_STAIRS_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_STAIRS_ITEM.get())) {
            List<Integer> comp = self.get(AlloyCopperBlockItem.STAIRS_COMPOSITION.get());
            if (comp != null && comp.size() == 216) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutStairsName(comp));
            }
            return;
        }

        // Chiseled alloy copper blocks (216-value composition = 2 slabs × 108)
        if (self.is(MoreCopperBlock.CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK_ITEM.get())) {
            List<Integer> comp = self.get(AlloyCopperBlockItem.CHISELED_COMPOSITION.get());
            if (comp != null && comp.size() == 216) {
                cir.setReturnValue(AlloyCopperBlockItem.generateChiseledName(comp));
            }
            return;
        }

        // Cut alloy slabs (108-value composition = 3 cut blocks × 36)
        if (self.is(MoreCopperBlock.CUT_ALLOY_COPPER_SLAB_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_SLAB_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_SLAB_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_SLAB_ITEM.get())) {
            List<Integer> comp = self.get(AlloyCopperBlockItem.SLAB_COMPOSITION.get());
            if (comp != null && comp.size() == 108) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutSlabName(comp));
            }
            return;
        }

        // Cut alloy copper blocks (36-value composition)
        if (self.is(MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK_ITEM.get())) {
            List<Integer> comp = self.get(AlloyCopperBlockItem.CUT_COMPOSITION.get());
            if (comp != null && comp.size() == 36) {
                cir.setReturnValue(AlloyCopperBlockItem.generateCutLocalizedName(comp));
            }
            return;
        }

        // Regular alloy copper blocks (9-value composition)
        if (self.is(MoreCopperBlock.ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK_ITEM.get())
                || self.is(MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK_ITEM.get())) {
            List<Integer> comp = self.get(AlloyCopperBlockItem.COMPOSITION.get());
            if (comp != null && !comp.equals(AlloyCopperBlockItem.defaultComposition())) {
                cir.setReturnValue(AlloyCopperBlockItem.generateLocalizedName(comp));
            }
        }
    }
}

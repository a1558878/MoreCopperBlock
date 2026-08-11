package com.chenkong.morecopperblock.recipe;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.chenkong.morecopperblock.block.AlloyCopperBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopperCollection;

import java.util.ArrayList;
import java.util.List;

public class ChiseledAlloyCopperRecipe extends CustomRecipe {

    public ChiseledAlloyCopperRecipe() {
        super();
    }

    /**
     * Chiseled pattern: exactly 2 cut copper slabs stacked vertically (1×2).
     * Accepts both alloy and vanilla cut copper slabs.
     * If both are the same vanilla block, defers to the vanilla recipe.
     * Outputs 1 chiseled alloy copper block with averaged oxidation.
     */
    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != 1 || input.height() != 2) return false;

        boolean hasAlloy = false;
        Block firstVanilla = null;
        boolean allSameVanilla = true;

        for (int row = 0; row < 2; row++) {
            ItemStack stack = input.getItem(0, row);
            if (stack.isEmpty()) return false;
            if (!isCutCopperSlab(stack)) return false;

            Block block = ((BlockItem) stack.getItem()).getBlock();
            if (isAlloyCutSlab(block)) {
                hasAlloy = true;
            } else {
                if (firstVanilla == null) firstVanilla = block;
                else if (block != firstVanilla) allSameVanilla = false;
            }
        }

        // If both are the same vanilla slab, let the vanilla recipe handle it
        if (!hasAlloy && allSameVanilla) return false;

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<Integer> chiseledComposition = new ArrayList<>(216);
        double sum = 0;

        for (int row = 0; row < 2; row++) {
            ItemStack stack = input.getItem(0, row);
            Block block = ((BlockItem) stack.getItem()).getBlock();

            if (isAlloyCutSlab(block)) {
                List<Integer> slabComp = stack.get(AlloyCopperBlockItem.SLAB_COMPOSITION.get());
                if (slabComp != null && slabComp.size() == 108) {
                    chiseledComposition.addAll(slabComp);
                    for (int v : slabComp) sum += v % 4;
                } else {
                    for (int i = 0; i < 108; i++) chiseledComposition.add(0);
                }
            } else {
                int type = getVanillaCutSlabType(block);
                for (int i = 0; i < 108; i++) {
                    chiseledComposition.add(type);
                    sum += type % 4;
                }
            }
        }

        double avg = sum / 216.0;
        Block outBlock = getChiseledVariantByAvg(avg);

        ItemStack result = new ItemStack(outBlock, 1);
        result.set(AlloyCopperBlockItem.CHISELED_COMPOSITION.get(), chiseledComposition);
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.CHISELED_ALLOY_COPPER_RECIPE.get();
    }

    /** Accepts both alloy and vanilla cut copper slabs. */
    private static boolean isCutCopperSlab(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();
        return isAlloyCutSlab(block) || isVanillaCutSlab(block);
    }

    private static boolean isAlloyCutSlab(Block block) {
        return block == MoreCopperBlock.CUT_ALLOY_COPPER_SLAB.get()
                || block == MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_SLAB.get()
                || block == MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_SLAB.get()
                || block == MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_SLAB.get();
    }

    private static boolean isVanillaCutSlab(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER_SLAB;
        return block == c.weathering().unaffected()
                || block == c.weathering().exposed()
                || block == c.weathering().weathered()
                || block == c.weathering().oxidized()
                || block == c.waxed().unaffected()
                || block == c.waxed().exposed()
                || block == c.waxed().weathered()
                || block == c.waxed().oxidized();
    }

    private static int getVanillaCutSlabType(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER_SLAB;
        if (block == c.weathering().unaffected()) return 0;
        if (block == c.weathering().exposed()) return 1;
        if (block == c.weathering().weathered()) return 2;
        if (block == c.weathering().oxidized()) return 3;
        if (block == c.waxed().unaffected()) return 4;
        if (block == c.waxed().exposed()) return 5;
        if (block == c.waxed().weathered()) return 6;
        return 7; // waxed oxidized
    }

    private static Block getChiseledVariantByAvg(double avg) {
        if (avg < 0.5) return MoreCopperBlock.CHISELED_ALLOY_COPPER_BLOCK.get();
        if (avg < 1.5) return MoreCopperBlock.EXPOSED_CHISELED_ALLOY_COPPER_BLOCK.get();
        if (avg < 2.5) return MoreCopperBlock.WEATHERED_CHISELED_ALLOY_COPPER_BLOCK.get();
        return MoreCopperBlock.OXIDIZED_CHISELED_ALLOY_COPPER_BLOCK.get();
    }
}

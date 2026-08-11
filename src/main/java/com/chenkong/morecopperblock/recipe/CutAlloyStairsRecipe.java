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

public class CutAlloyStairsRecipe extends CustomRecipe {

    public CutAlloyStairsRecipe() {
        super();
    }

    /**
     * Stair pattern in 3×3 crafting grid:
     *   X  -  -
     *   X  X  -
     *   X  X  X
     * Accepts both alloy cut copper blocks and vanilla cut copper blocks.
     * If all 6 are the same vanilla block, defers to the vanilla recipe.
     * Outputs 4 alloy stairs with averaged oxidation.
     */
    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != 3 || input.height() != 3) return false;

        int[][] stairPositions = {
                {0, 0}, {0, 1}, {1, 1}, {0, 2}, {1, 2}, {2, 2},
        };

        boolean[][] occupied = new boolean[3][3];
        for (int[] pos : stairPositions) occupied[pos[1]][pos[0]] = true;

        boolean hasAlloy = false;
        Block firstVanilla = null;
        boolean allSameVanilla = true;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ItemStack stack = input.getItem(col, row);
                if (occupied[row][col]) {
                    if (stack.isEmpty()) return false;
                    if (!isCutCopperBlock(stack)) return false;
                    Block block = ((BlockItem) stack.getItem()).getBlock();
                    if (isAlloyCutBlock(block)) {
                        hasAlloy = true;
                    } else {
                        if (firstVanilla == null) firstVanilla = block;
                        else if (block != firstVanilla) allSameVanilla = false;
                    }
                } else {
                    if (!stack.isEmpty()) return false;
                }
            }
        }

        // If all 6 are the same vanilla block, let the vanilla recipe handle it
        if (!hasAlloy && allSameVanilla) return false;

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<Integer> stairsComposition = new ArrayList<>(216);
        int[][] stairPositions = {{0, 0}, {0, 1}, {1, 1}, {0, 2}, {1, 2}, {2, 2}};
        double sum = 0;

        for (int[] pos : stairPositions) {
            ItemStack stack = input.getItem(pos[0], pos[1]);
            Block block = ((BlockItem) stack.getItem()).getBlock();

            if (isAlloyCutBlock(block)) {
                List<Integer> cutComp = stack.get(AlloyCopperBlockItem.CUT_COMPOSITION.get());
                if (cutComp != null && cutComp.size() == 36) {
                    stairsComposition.addAll(cutComp);
                    for (int v : cutComp) sum += v % 4;
                } else {
                    for (int i = 0; i < 36; i++) stairsComposition.add(0);
                }
            } else {
                // Vanilla cut copper block — derive 36-value composition from block type
                int type = getVanillaCutBlockType(block);
                for (int i = 0; i < 36; i++) {
                    stairsComposition.add(type);
                    sum += type % 4;
                }
            }
        }

        double avg = sum / 216.0;
        Block outBlock = getStairsVariantByAvg(avg);

        ItemStack result = new ItemStack(outBlock, 4);
        result.set(AlloyCopperBlockItem.STAIRS_COMPOSITION.get(), stairsComposition);
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.CUT_ALLOY_STAIRS_RECIPE.get();
    }

    /** Accepts both alloy and vanilla cut copper blocks. */
    private static boolean isCutCopperBlock(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();
        return isAlloyCutBlock(block) || isVanillaCutBlock(block);
    }

    private static boolean isAlloyCutBlock(Block block) {
        return block == MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK.get();
    }

    private static boolean isVanillaCutBlock(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER;
        return block == c.weathering().unaffected()
                || block == c.weathering().exposed()
                || block == c.weathering().weathered()
                || block == c.weathering().oxidized()
                || block == c.waxed().unaffected()
                || block == c.waxed().exposed()
                || block == c.waxed().weathered()
                || block == c.waxed().oxidized();
    }

    /** Returns the oxidation type id for a vanilla cut copper block. */
    private static int getVanillaCutBlockType(Block block) {
        WeatheringCopperCollection<Block> c = Blocks.CUT_COPPER;
        if (block == c.weathering().unaffected()) return 0;
        if (block == c.weathering().exposed()) return 1;
        if (block == c.weathering().weathered()) return 2;
        if (block == c.weathering().oxidized()) return 3;
        if (block == c.waxed().unaffected()) return 4;
        if (block == c.waxed().exposed()) return 5;
        if (block == c.waxed().weathered()) return 6;
        return 7; // waxed oxidized
    }

    private static Block getStairsVariantByAvg(double avg) {
        if (avg < 0.5) return MoreCopperBlock.CUT_ALLOY_COPPER_STAIRS.get();
        if (avg < 1.5) return MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_STAIRS.get();
        if (avg < 2.5) return MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_STAIRS.get();
        return MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_STAIRS.get();
    }
}

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

public class CutAlloyCopperRecipe extends CustomRecipe {

    public CutAlloyCopperRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != 2 || input.height() != 2) return false;

        boolean hasAlloy = false;
        Block firstVanilla = null;
        boolean allVanillaSame = true;

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                ItemStack stack = input.getItem(col, row);
                if (stack.isEmpty()) return false;
                if (!isCopperBlock(stack)) return false;

                Block block = ((BlockItem) stack.getItem()).getBlock();
                if (isAlloyBlock(block)) {
                    hasAlloy = true;
                } else {
                    if (firstVanilla == null) firstVanilla = block;
                    else if (block != firstVanilla) allVanillaSame = false;
                }
            }
        }

        // If all 4 are the same vanilla block, let the vanilla recipe handle it
        if (!hasAlloy && allVanillaSame) return false;

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<Integer> cutComposition = new ArrayList<>(36);
        double sum = 0;

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                List<Integer> comp = getBlockComposition(input.getItem(col, row));
                for (int v : comp) {
                    cutComposition.add(v);
                    sum += v % 4;
                }
            }
        }

        double avg = sum / 36.0;

        Block output;
        if (avg < 0.5)       output = MoreCopperBlock.CUT_ALLOY_COPPER_BLOCK.get();
        else if (avg < 1.5)  output = MoreCopperBlock.EXPOSED_CUT_ALLOY_COPPER_BLOCK.get();
        else if (avg < 2.5)  output = MoreCopperBlock.WEATHERED_CUT_ALLOY_COPPER_BLOCK.get();
        else                 output = MoreCopperBlock.OXIDIZED_CUT_ALLOY_COPPER_BLOCK.get();

        ItemStack result = new ItemStack(output, 4);
        result.set(AlloyCopperBlockItem.CUT_COMPOSITION.get(), cutComposition);
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.CUT_ALLOY_COPPER_RECIPE.get();
    }

    private static boolean isAlloyBlock(Block block) {
        return block == MoreCopperBlock.ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK.get();
    }

    /** True if the stack is a vanilla copper block or an alloy copper block. */
    private static boolean isCopperBlock(ItemStack stack) {
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        Block block = bi.getBlock();

        // Alloy copper blocks
        if (block == MoreCopperBlock.ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.EXPOSED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.WEATHERED_ALLOY_COPPER_BLOCK.get()
                || block == MoreCopperBlock.OXIDIZED_ALLOY_COPPER_BLOCK.get())
            return true;

        // Vanilla copper blocks
        WeatheringCopperCollection<Block> c = Blocks.COPPER_BLOCK;
        return block == c.weathering().unaffected()
                || block == c.weathering().exposed()
                || block == c.weathering().weathered()
                || block == c.weathering().oxidized()
                || block == c.waxed().unaffected()
                || block == c.waxed().exposed()
                || block == c.waxed().weathered()
                || block == c.waxed().oxidized();
    }

    /** Gets the 9-value composition of a copper block. */
    private static List<Integer> getBlockComposition(ItemStack stack) {
        // Alloy copper block — read stored composition
        List<Integer> comp = stack.get(AlloyCopperBlockItem.COMPOSITION.get());
        if (comp != null && comp.size() == 9) return comp;

        // Vanilla copper block — derive from block type
        Block block = ((BlockItem) stack.getItem()).getBlock();
        WeatheringCopperCollection<Block> c = Blocks.COPPER_BLOCK;
        int type;
        if (block == c.weathering().unaffected() || block == c.waxed().unaffected())      type = 0;
        else if (block == c.weathering().exposed() || block == c.waxed().exposed())        type = 1;
        else if (block == c.weathering().weathered() || block == c.waxed().weathered())    type = 2;
        else                                                                                type = 3;

        List<Integer> defaultComp = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) defaultComp.add(type);
        return defaultComp;
    }
}

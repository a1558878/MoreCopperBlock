package com.chenkong.morecopperblock.block;

import com.chenkong.morecopperblock.MoreCopperBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

import java.util.*;
import java.util.stream.Collectors;

public class AlloyCopperBlockItem {
    private static final String[] TYPE_TRANSLATION_KEYS = {
            "alloy.morecopperblock.type.copper",
            "alloy.morecopperblock.type.exposed",
            "alloy.morecopperblock.type.weathered",
            "alloy.morecopperblock.type.oxidized",
            "alloy.morecopperblock.type.waxed",
            "alloy.morecopperblock.type.waxed_exposed",
            "alloy.morecopperblock.type.waxed_weathered",
            "alloy.morecopperblock.type.waxed_oxidized"
    };

    private static final String[] COUNT_TRANSLATION_KEYS = {
            null,
            "alloy.morecopperblock.count.2",
            "alloy.morecopperblock.count.3",
            "alloy.morecopperblock.count.4",
            "alloy.morecopperblock.count.5",
            "alloy.morecopperblock.count.6",
            "alloy.morecopperblock.count.7",
            "alloy.morecopperblock.count.8",
            "alloy.morecopperblock.count.9"
    };

    static final String CUT_SUFFIX_TRANSLATION_KEY = "alloy.morecopperblock.cut_suffix";
    private static final String CUT_STAIRS_SUFFIX_KEY = "alloy.morecopperblock.cut_stairs_suffix";
    private static final String CUT_SLAB_SUFFIX_KEY = "alloy.morecopperblock.cut_slab_suffix";
    private static final String CHISELED_SUFFIX_KEY = "alloy.morecopperblock.chiseled_suffix";
    private static final String SUFFIX_TRANSLATION_KEY = "alloy.morecopperblock.suffix";

    public static List<Integer> defaultComposition() {
        return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public static final Codec<List<Integer>> COMPOSITION_CODEC = Codec.INT.listOf(0, 9);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 9; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(9);
            for (int i = 0; i < 9; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MoreCopperBlock.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> COMPOSITION =
            DATA_COMPONENTS.register("alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(COMPOSITION_CODEC)
                            .networkSynchronized(COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Cut alloy (36 values = 4 input blocks × 9) ----

    public static final Codec<List<Integer>> CUT_COMPOSITION_CODEC = Codec.INT.listOf(0, 36);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> CUT_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 36; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(36);
            for (int i = 0; i < 36; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> CUT_COMPOSITION =
            DATA_COMPONENTS.register("cut_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(CUT_COMPOSITION_CODEC)
                            .networkSynchronized(CUT_COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Cut stairs (216 values = 6 cut blocks × 36) ----

    public static final Codec<List<Integer>> STAIRS_COMPOSITION_CODEC = Codec.INT.listOf(0, 216);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> STAIRS_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 216; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(216);
            for (int i = 0; i < 216; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> STAIRS_COMPOSITION =
            DATA_COMPONENTS.register("stairs_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(STAIRS_COMPOSITION_CODEC)
                            .networkSynchronized(STAIRS_COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Cut slabs (108 values = 3 cut blocks × 36) ----

    public static final Codec<List<Integer>> SLAB_COMPOSITION_CODEC = Codec.INT.listOf(0, 108);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> SLAB_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 108; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(108);
            for (int i = 0; i < 108; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> SLAB_COMPOSITION =
            DATA_COMPONENTS.register("slab_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(SLAB_COMPOSITION_CODEC)
                            .networkSynchronized(SLAB_COMPOSITION_STREAM_CODEC)
                            .build());

    // ---- Chiseled (216 values = 2 slabs × 108) ----

    public static final Codec<List<Integer>> CHISELED_COMPOSITION_CODEC = Codec.INT.listOf(0, 216);

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Integer>> CHISELED_COMPOSITION_STREAM_CODEC = new StreamCodec<>() {
        @Override public void encode(RegistryFriendlyByteBuf buf, List<Integer> list) {
            for (int i = 0; i < 216; i++) buf.writeByte(list.get(i));
        }
        @Override public List<Integer> decode(RegistryFriendlyByteBuf buf) {
            List<Integer> list = new ArrayList<>(216);
            for (int i = 0; i < 216; i++) list.add((int) buf.readByte());
            return list;
        }
    };

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> CHISELED_COMPOSITION =
            DATA_COMPONENTS.register("chiseled_alloy_composition",
                    () -> DataComponentType.<List<Integer>>builder()
                            .persistent(CHISELED_COMPOSITION_CODEC)
                            .networkSynchronized(CHISELED_COMPOSITION_STREAM_CODEC)
                            .build());

    public static Component generateLocalizedName(List<Integer> composition) {
        return buildInnerName(composition).append(Component.translatable(SUFFIX_TRANSLATION_KEY));
    }

    /** Builds the inner portion (type/count/positions) without the "铜块" suffix. */
    private static MutableComponent buildInnerName(List<Integer> composition) {
        Map<Integer, List<Integer>> groups = new LinkedHashMap<>();
        for (int pos = 0; pos < 9; pos++) {
            int type = composition.get(pos);
            groups.computeIfAbsent(type, k -> new ArrayList<>()).add(pos);
        }
        List<Map.Entry<Integer, List<Integer>>> sorted = new ArrayList<>(groups.entrySet());
        sorted.sort(Comparator.comparingInt(e -> e.getValue().get(0)));

        MutableComponent result = Component.empty();
        for (Map.Entry<Integer, List<Integer>> entry : sorted) {
            int type = entry.getKey();
            if (type == 0) continue;
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(p -> String.valueOf(p + 1))
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            result.append(Component.translatable(TYPE_TRANSLATION_KEYS[type]));
        }
        return result;
    }

    /**
     * Generates the localized name for a cut alloy copper block from its 36-element
     * composition (4 input blocks × 9 ingots each).
     * <p>
     * Format: positions of identical source blocks, count prefix (if &gt;1),
     * source composition in parentheses, then "切制铜块" suffix.
     * Example: {@code 1,3-二(1-涂蜡的斑驳-…)切制铜块}
     */
    public static Component generateCutLocalizedName(List<Integer> cutComposition) {
        return buildCutInnerName(cutComposition)
                .append(Component.translatable(CUT_SUFFIX_TRANSLATION_KEY));
    }

    /**
     * Generates the localized name for cut alloy stairs from its 216-element
     * composition (6 cut blocks × 36 values each).
     * <p>
     * Outer level groups cut blocks by their 36-value composition, then
     * each group shows the cut block's inner alloy composition.
     * Example: {@code 1,3-二[1-二(1-斑驳-2,4-二涂蜡…)]-2-[1-(1-锈蚀…)]切制铜楼梯}
     */
    public static Component generateCutStairsName(List<Integer> stairsComposition) {
        return buildCutVariantName(stairsComposition, 6)
                .append(Component.translatable(CUT_STAIRS_SUFFIX_KEY));
    }

    /**
     * Generates the localized name for cut alloy slabs from its 108-element
     * composition (3 cut blocks × 36 values each).
     */
    public static Component generateCutSlabName(List<Integer> slabComposition) {
        return buildCutVariantName(slabComposition, 3)
                .append(Component.translatable(CUT_SLAB_SUFFIX_KEY));
    }

    /**
     * Generates the localized name for chiseled alloy copper blocks from its
     * 216-element composition (2 slabs × 108 values each).
     * <p>
     * Each slab's inner name is wrapped in curly braces, prefixed by its
     * position and count. Example:
     * {@code 1-{1,3-[…]-2-[…]}雕纹铜块}
     */
    public static Component generateChiseledName(List<Integer> chiseledComposition) {
        // Split into 2 slabs of 108 values each
        List<List<Integer>> slabComps = new ArrayList<>(2);
        for (int s = 0; s < 2; s++) {
            List<Integer> comp = new ArrayList<>(108);
            for (int i = 0; i < 108; i++) {
                comp.add(chiseledComposition.get(s * 108 + i));
            }
            slabComps.add(comp);
        }

        // Group identical slabs by their 108-value composition
        Map<String, List<Integer>> groups = new LinkedHashMap<>();
        for (int s = 0; s < 2; s++) {
            String key = slabComps.get(s).toString();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s + 1);
        }

        MutableComponent result = Component.empty();

        for (Map.Entry<String, List<Integer>> entry : groups.entrySet()) {
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            int firstPos = positions.get(0) - 1;
            result.append(Component.literal("{"));
            result.append(buildCutVariantName(slabComps.get(firstPos), 3));
            result.append(Component.literal("}"));
        }

        return result.append(Component.translatable(CHISELED_SUFFIX_KEY));
    }

    /**
     * Builds the name for a stairs/slab variant by grouping cut blocks by their
     * 36-value composition. Each group shows positions, count prefix, and the
     * cut block's inner composition in square brackets.
     *
     * @param fullComposition the full composition (216 for stairs, 108 for slabs)
     * @param blockCount      number of cut blocks (6 for stairs, 3 for slabs)
     */
    private static MutableComponent buildCutVariantName(List<Integer> fullComposition, int blockCount) {
        // Split into cut-block-sized groups (36 values each)
        List<List<Integer>> cutBlockComps = new ArrayList<>(blockCount);
        for (int b = 0; b < blockCount; b++) {
            List<Integer> comp = new ArrayList<>(36);
            for (int i = 0; i < 36; i++) {
                comp.add(fullComposition.get(b * 36 + i));
            }
            cutBlockComps.add(comp);
        }

        // Group identical cut block compositions by their inner name
        Map<String, List<Integer>> groups = new LinkedHashMap<>();
        for (int b = 0; b < blockCount; b++) {
            if (isAllZero(cutBlockComps.get(b))) continue; // skip plain copper cut blocks
            String key = cutBlockComps.get(b).toString();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(b + 1);
        }

        MutableComponent result = Component.empty();

        for (Map.Entry<String, List<Integer>> entry : groups.entrySet()) {
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            // Enclose the cut block's inner composition in square brackets
            int firstPos = positions.get(0) - 1;
            result.append(Component.literal("["));
            result.append(buildCutInnerName(cutBlockComps.get(firstPos)));
            result.append(Component.literal("]"));
        }

        return result;
    }

    /** Builds the inner portion for a cut block name, shared by block/stairs/slab. */
    private static MutableComponent buildCutInnerName(List<Integer> cutComposition) {
        List<List<Integer>> blockComps = new ArrayList<>(4);
        for (int b = 0; b < 4; b++) {
            List<Integer> comp = new ArrayList<>(9);
            for (int i = 0; i < 9; i++) {
                comp.add(cutComposition.get(b * 9 + i));
            }
            blockComps.add(comp);
        }

        // Group blocks by their 9-value composition (skip plain copper blocks)
        Map<String, List<Integer>> groups = new LinkedHashMap<>();
        for (int b = 0; b < 4; b++) {
            if (isAllZero(blockComps.get(b))) continue; // skip plain copper blocks
            String key = blockComps.get(b).toString();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(b + 1);
        }

        MutableComponent result = Component.empty();

        for (Map.Entry<String, List<Integer>> entry : groups.entrySet()) {
            List<Integer> positions = entry.getValue();
            int count = positions.size();

            if (!result.getString().isEmpty()) {
                result.append(Component.literal("-"));
            }

            String posStr = positions.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            result.append(Component.literal(posStr));
            result.append(Component.literal("-"));

            if (count >= 2) {
                result.append(Component.translatable(COUNT_TRANSLATION_KEYS[count - 1]));
            }

            int firstPos = positions.get(0) - 1;
            List<Integer> comp = blockComps.get(firstPos);
            result.append(Component.literal("("));
            if (isUniform(comp)) {
                result.append(buildUniformBlockName(comp.get(0)));
            } else {
                result.append(buildInnerName(comp));
            }
            result.append(Component.literal(")"));
        }
        return result;
    }

    private static boolean isUniform(List<Integer> comp) {
        int first = comp.get(0);
        for (int i = 1; i < comp.size(); i++) {
            if (comp.get(i) != first) return false;
        }
        return true;
    }

    private static boolean isAllZero(List<Integer> comp) {
        for (int v : comp) {
            if (v != 0) return false;
        }
        return true;
    }

    /** Builds a block-level name for a uniform 9-value composition (e.g. "斑驳"). */
    private static MutableComponent buildUniformBlockName(int type) {
        return Component.translatable(TYPE_TRANSLATION_KEYS[type]);
    }
}

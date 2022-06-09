package shiroroku.elisesmagic.World;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.BlockRegistry;

import java.util.List;

public class FlowerGeneration {

	public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ARCANIA_FLOWER;
	public static Holder<PlacedFeature> ARCANIA_FLOWER_PATCH;

	public static RandomPatchConfiguration getWildCropConfiguration(Block block, int tries, int xzSpread, BlockPredicate plantedOn) {
		return new RandomPatchConfiguration(tries, xzSpread, 3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)), BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)));
	}

	private static Holder<PlacedFeature> registerPlacement(ResourceLocation id, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
		return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, id, new PlacedFeature(Holder.hackyErase(feature), List.of(modifiers)));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(ResourceLocation id, FC featureConfig) {
		return register(id, new ConfiguredFeature<>((F) Feature.RANDOM_PATCH, featureConfig));
	}

	private static <V extends T, T> Holder<V> register(ResourceLocation id, V value) {
		return (Holder<V>) BuiltinRegistries.register((Registry<T>) BuiltinRegistries.CONFIGURED_FEATURE, id, value);
	}

	public static void registerWildCropGeneration() {
		ARCANIA_FLOWER = register(new ResourceLocation(ElisesMagic.MODID, "arcania_flower"), getWildCropConfiguration(BlockRegistry.arcania_flower.get(), 64, 8, BlockPredicate.matchesTag(BlockTags.DIRT, new BlockPos(0, -1, 0))));
		ARCANIA_FLOWER_PATCH = registerPlacement(new ResourceLocation("arcania_flower"), ARCANIA_FLOWER, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	}
}

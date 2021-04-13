package tfar.moreemeraldores;

import com.google.gson.JsonElement;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MoreEmeraldOres.MODID)
public class MoreEmeraldOres {
    // Directly reference a log4j logger.

    public static final String MODID = "moreemeraldores";

    public MoreEmeraldOres() {
        // Register the setup method for modloading
        MinecraftForge.EVENT_BUS.addListener(this::setup);
    }

    public static final ConfiguredFeature<?, ?> ORE_EMERALD_MANY = register("moreemeraldores:ore_emerald_many", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.EMERALD_ORE.getDefaultState(), 17)).range(128).square().count(20));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    private void setup(final BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        List<Supplier<ConfiguredFeature<?, ?>>> features = builder.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_EMERALD_MANY);

        for (Iterator<Supplier<ConfiguredFeature<?, ?>>> iterator = features.iterator(); iterator.hasNext(); ) {
            Supplier<ConfiguredFeature<?, ?>> featureSupplier = iterator.next();
            ConfiguredFeature<?, ?> feature = featureSupplier.get();
            if (feature.feature == Feature.EMERALD_ORE) {
                iterator.remove();
            }
        }
    }
}

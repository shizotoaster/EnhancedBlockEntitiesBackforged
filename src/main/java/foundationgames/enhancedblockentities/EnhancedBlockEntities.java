package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EnhancedBlockEntities.MOD_ID)
public class EnhancedBlockEntities {
    public static final String MOD_ID = "enhancedblockentities";

    public static final Logger LOG = LogManager.getLogger("Enhanced Block Entities");

    public static final EBEConfig CONFIG = new EBEConfig();
    public EnhancedBlockEntities() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> new EBEConfigScreen(screen));

        ModelIdentifiers.init();
        EBESetup.setupResourceProviders();
        ModelPredicateProviderRegistry.register(Items.CHEST, new Identifier("is_christmas"), (stack, world, entity) -> DateUtil.isChristmas() ? 1 : 0);

        load();
    }

    public static void reload(ReloadType type) {
        load();
        if (type == ReloadType.WORLD) {
            MinecraftClient.getInstance().worldRenderer.reload();
        } else if (type == ReloadType.RESOURCES) {
            MinecraftClient.getInstance().reloadResources();
        }
    }

    public static void load() {
        CONFIG.load();

        EnhancedBlockEntityRegistry.clear();
        ResourceUtil.resetPack();

        if(CONFIG.renderEnhancedChests) {
            EBESetup.setupChests();
            EBESetup.setupRRPChests();
        }

        if(CONFIG.renderEnhancedSigns) {
            EBESetup.setupSigns();
            EBESetup.setupRRPSigns();
        }

        if(CONFIG.renderEnhancedBells) {
            EBESetup.setupBells();
            EBESetup.setupRRPBells();
        }

        if(CONFIG.renderEnhancedBeds) {
            EBESetup.setupBeds();
            EBESetup.setupRRPBeds();
        }

        if(CONFIG.renderEnhancedShulkerBoxes) {
            EBESetup.setupShulkerBoxes();
            EBESetup.setupRRPShulkerBoxes();
        }
    }
}

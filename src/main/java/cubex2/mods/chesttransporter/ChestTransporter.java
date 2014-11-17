package cubex2.mods.chesttransporter;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = "ChestTransporter", name = "Chest Transporter", version = "1.2.0")
public class ChestTransporter
{
    @Instance("ChestTransporter")
    public static ChestTransporter instance;

    public static ItemChestTransporter chestTransporter;
    public static ItemChestTransporter chestTransporterIron;
    public static ItemChestTransporter chestTransporterDiamond;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        chestTransporter = new ItemChestTransporter(4, "wood");
        chestTransporterIron = new ItemChestTransporter(19, "iron");
        chestTransporterDiamond = new ItemChestTransporter(99, "diamond");

        GameRegistry.registerItem(chestTransporter, "chesttransporter");
        GameRegistry.registerItem(chestTransporterIron, "chesttransporter_iron");
        GameRegistry.registerItem(chestTransporterDiamond, "chesttransporter_diamond");

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chestTransporter), "S S", "SSS", " S ", 'S', Items.stick));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chestTransporterIron), "S S", "SSS", " S ", 'S', Items.iron_ingot));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chestTransporterDiamond), "S S", "SSS", " S ", 'S', Items.diamond));
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt)
    {
        TransportableChest chest = new TransportableChest(Blocks.chest, -1, 1, "vanilla");
        ChestRegistry.register(chest);
        ChestRegistry.registerMinecart(EntityMinecartChest.class, chest);

        ChestRegistry.register(new TransportableChest(Blocks.trapped_chest, -1, 2, "vanilla_trapped"));

        if (Loader.isModLoaded("IronChest"))
        {
            Block block = GameData.getBlockRegistry().getObject("IronChest:BlockIronChest");
            if (block != null && block != Blocks.air)
            {
                String[] names = new String[]{"iron", "gold", "diamond", "copper", "tin", "crystal", "obsidian"};
                for (int i = 0; i < 7; i++)
                {
                    ChestRegistry.register(new TransportableChest(block, i, 3 + i, names[i]));
                }
            }
        }

        if (Loader.isModLoaded("MultiPageChest"))
        {
            Block block = GameData.getBlockRegistry().getObject("MultiPageChest:multipagechest");
            if (block != null && block != Blocks.air)
            {
                ChestRegistry.register(new TransportableChest(block, -1, 10, "multipagechest"));
            }
        }

        if (Loader.isModLoaded("factorization"))
        {
            Block block = GameData.getBlockRegistry().getObject("factorization:FzBlock");
            if (block != null && block != Blocks.air)
            {
                ChestRegistry.register(new FzBarrel(block, 2, 11));
            }
        }

        if (Loader.isModLoaded("varietychests"))
        {
            Block block = GameData.getBlockRegistry().getObject("varietychests:customchest");
            if (block != null && block != Blocks.air)
            {
                ChestRegistry.register(new VariertyChest(block, -1, 12, false));
            }
            block = GameData.getBlockRegistry().getObject("varietychests:customglowingchest");
            if (block != null && block != Blocks.air)
            {
                ChestRegistry.register(new VariertyChest(block, -1, 13, true));
            }
        }

        if (Loader.isModLoaded("compactchests"))
        {
            String[] names = new String[]{"quadruple", "sextuple", "triple", "double", "quintuple"};
            for (int i = 0; i < names.length; i++)
            {
                Block block = GameData.getBlockRegistry().getObject("compactchests:" + names[i] + "Chest");
                if (block != null && block != Blocks.air)
                {
                    ChestRegistry.register(new TransportableChest(block, -1, 14 + i, "cc_" + names[i]));
                }
            }
        }

        if (Loader.isModLoaded("ironchestminecarts") && Loader.isModLoaded("IronChest"))
        {
            String[] classNames = new String[]{"ganymedes01.ironchestminecarts.minecarts.types.EntityMinecartIronChest",
                    "ganymedes01.ironchestminecarts.minecarts.types.EntityMinecartGoldChest",
                    "ganymedes01.ironchestminecarts.minecarts.types.EntityMinecartDiamondChest",
                    "ganymedes01.ironchestminecarts.minecarts.types.EntityMinecartCopperChest",
                    "ganymedes01.ironchestminecarts.minecarts.types.EntityMinecartSilverChest",
                    "ganymedes01.ironchestminecarts.minecarts.types.EntityMinecartCrystalChest",
                    "ganymedes01.ironchestminecarts.minecarts.types.EntityMinecartObsidianChest"};

            try
            {
                for (int i = 0; i < 7; i++)
                {
                    ChestRegistry.registerMinecart((Class<? extends EntityMinecartChest>) Class.forName(classNames[i]), ChestRegistry.dvToChest.get(3 + i));
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


}
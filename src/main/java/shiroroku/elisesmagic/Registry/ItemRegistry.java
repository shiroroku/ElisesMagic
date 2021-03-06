package shiroroku.elisesmagic.Registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Item.*;
import shiroroku.elisesmagic.Item.Tarot.*;

public class ItemRegistry {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElisesMagic.MODID);

	public static final RegistryObject<Item> chalk = ITEMS.register("chalk", () -> new ChalkItem(new Item.Properties().durability(64)));
	public static final RegistryObject<Item> magic_chalk = ITEMS.register("magic_chalk", () -> new ChalkItem(new Item.Properties().durability(128), true));
	public static final RegistryObject<Item> spell_book_novice = ITEMS.register("spell_book_novice", () -> new SpellBookItem(defProp().durability(4)));
	public static final RegistryObject<Item> spell_book_apprentice = ITEMS.register("spell_book_apprentice", () -> new SpellBookItem(defProp().durability(8)));
	public static final RegistryObject<Item> spell_book_master = ITEMS.register("spell_book_master", () -> new SpellBookItem(defProp().durability(16)));
	public static final RegistryObject<Item> arcania_essence = simpleItem("arcania_essence");
	public static final RegistryObject<Item> infernal_essence = simpleItem("infernal_essence");
	public static final RegistryObject<Item> esoteric_essence = simpleItem("esoteric_essence");
	public static final RegistryObject<Item> arcania_gem = simpleItem("arcania_gem");
	public static final RegistryObject<Item> esoteric_gem = simpleItem("esoteric_gem");
	public static final RegistryObject<Item> arcania_blade = ITEMS.register("arcania_blade", ArcaniaBladeItem::new);
	public static final RegistryObject<Item> phytorage_blade = ITEMS.register("phytorage_blade", PhytorageBladeItem::new);
	public static final RegistryObject<Item> phytorage = simpleItem("phytorage");
	public static final RegistryObject<Item> animated_growth = simpleItem("animated_growth");
	public static final RegistryObject<Item> mirror_of_talent = ITEMS.register("mirror_of_talent", MirrorOfTalentItem::new);
	public static final RegistryObject<Item> tarot_deck = ITEMS.register("tarot_deck", TarotDeckItem::new);
	public static final RegistryObject<Item> chronobrand = ITEMS.register("chronobrand", ChronobrandItem::new);

	public static final RegistryObject<Item> death = ITEMS.register("death", DeathTarot::new);
	public static final RegistryObject<Item> judgement = ITEMS.register("judgement", TarotItem::new);
	public static final RegistryObject<Item> justice = ITEMS.register("justice", JusticeTarot::new);
	public static final RegistryObject<Item> strength = ITEMS.register("strength", StrengthTarot::new);
	public static final RegistryObject<Item> temperance = ITEMS.register("temperance", TemperanceTarot::new);
	public static final RegistryObject<Item> the_chariot = ITEMS.register("the_chariot", TheChariotTarot::new);
	public static final RegistryObject<Item> the_devil = ITEMS.register("the_devil", TheDevilTarot::new);
	public static final RegistryObject<Item> the_emperor = ITEMS.register("the_emperor", TheEmperorTarot::new);
	public static final RegistryObject<Item> the_empress = ITEMS.register("the_empress", TarotItem::new);
	public static final RegistryObject<Item> the_fool = ITEMS.register("the_fool", TarotItem::new);
	public static final RegistryObject<Item> the_hanged_man = ITEMS.register("the_hanged_man", TarotItem::new);
	public static final RegistryObject<Item> the_hermit = ITEMS.register("the_hermit", TheHermitTarot::new);
	public static final RegistryObject<Item> the_hierophant = ITEMS.register("the_hierophant", HierophantTarot::new);
	public static final RegistryObject<Item> the_high_priestess = ITEMS.register("the_high_priestess", TarotItem::new);
	public static final RegistryObject<Item> the_lovers = ITEMS.register("the_lovers", TheLoversTarot::new);
	public static final RegistryObject<Item> the_magician = ITEMS.register("the_magician", TarotItem::new);
	public static final RegistryObject<Item> the_moon = ITEMS.register("the_moon", TheMoonTarot::new);
	public static final RegistryObject<Item> the_star = ITEMS.register("the_star", TarotItem::new);
	public static final RegistryObject<Item> the_sun = ITEMS.register("the_sun", TheSunTarot::new);
	public static final RegistryObject<Item> the_tower = ITEMS.register("the_tower", TheTowerTarot::new);
	public static final RegistryObject<Item> the_world = ITEMS.register("the_world", TheWorldTarot::new);
	public static final RegistryObject<Item> wheel_of_fortune = ITEMS.register("wheel_of_fortune", WheelOfFortuneTarot::new);

	private static Item.Properties defProp() {
		return new Item.Properties().tab(ElisesMagic.CREATIVETAB);
	}

	private static RegistryObject<Item> simpleItem(String ID) {
		return ITEMS.register(ID, () -> new Item(defProp()));
	}

	private static RegistryObject<Item> simpleItem(String ID, Item.Properties properties) {
		return ITEMS.register(ID, () -> new Item(properties));
	}

	public static void register() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}

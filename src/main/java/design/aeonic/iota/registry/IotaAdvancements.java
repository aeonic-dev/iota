package design.aeonic.iota.registry;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import design.aeonic.iota.Iota;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class IotaAdvancements {

    public static final ResourceLocation MAIN_GROUP_BACKGROUND = new ResourceLocation(
            "textures/block/anvil.png");

    public static AdvancementEntry IOTA = new AdvancementEntry(() ->
        Advancement.Builder.advancement()
                .display(IotaBlocks.KILN.block(), getNameKey("iota"), getDescKey("iota", "Have a look around."), MAIN_GROUP_BACKGROUND,
                        FrameType.TASK, false, false, false)
                .addCriterion("has_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .build(new ResourceLocation(Iota.MOD_ID, "main/iota")));

    public static AdvancementEntry PIRACY = new AdvancementEntry(() ->
            Advancement.Builder.advancement()
                    .parent(IOTA.get())
                    .display(Items.MUSIC_DISC_11, getNameKey("piracy", "Piracy. It's a crime."), getDescKey("piracy", "Steal a disc dropped by another player dying"), MAIN_GROUP_BACKGROUND,
                            FrameType.CHALLENGE, true, true, false)
                    .addCriterion("never", new ImpossibleTrigger.TriggerInstance()) // will be triggered manually
                    .build(new ResourceLocation(Iota.MOD_ID, "main/piracy")));

    public static void register() {
        Iota.REG.addDataGenerator(ProviderType.ADVANCEMENT, prv -> {
            prv.accept(IOTA.get());
            prv.accept(PIRACY.get());
        });
    }

    private static TranslatableComponent getNameKey(String name) {
        return getNameKey(name, null);
    }

    private static TranslatableComponent getNameKey(String name, @Nullable String englishName) {
        return getLocalized(name, "title", englishName);
    }

    private static TranslatableComponent getDescKey(String name, String englishDescription) {
        return getLocalized(name, "description", englishDescription);
    }

    private static TranslatableComponent getLocalized(String name, String suffix, @Nullable String englishName) {
        var s = getLocalizationKey("main", name, suffix);
        Iota.REG.addRawLang(s, englishName == null ? RegistrateLangProvider.toEnglishName(name) : englishName);
        return new TranslatableComponent(s);
    }

    private static String getLocalizationKey(String category, String name, String suffix) {
        return String.format("advancements.%s.%s.%s.%s", Iota.MOD_ID, category, name, suffix);
    }

    public static class AdvancementEntry {
        private final Supplier<Advancement> supplier;
        private Advancement advancement;

        public AdvancementEntry(Supplier<Advancement> supplier) {
            this.supplier = supplier;
        }

        public Advancement get() {
            if (advancement == null)
                return advancement = supplier.get();
            return advancement;
        }
    }
}

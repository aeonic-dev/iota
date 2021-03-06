package design.aeonic.iota.config;

import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import design.aeonic.iota.Iota;
import design.aeonic.iota.base.misc.ConfigHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class IotaConfigHelper {
    static <T> ForgeConfigSpec.ConfigValue<T> configVar(ForgeConfigSpec.Builder builder, String varName, String comment, BiFunction<ForgeConfigSpec.Builder, String, ForgeConfigSpec.ConfigValue<T>> def) {
        return def.apply(builder
                .comment(comment)
                .translation(String.format("%s.config.%s", Iota.MOD_ID, varName)), varName);
    }

    static <T> ConfigHelper.ConfigObject<T> configObj(ForgeConfigSpec.Builder builder, String varName, String comment, BiFunction<ForgeConfigSpec.Builder, String, ConfigHelper.ConfigObject<T>> def) {
        return def.apply(builder
                .comment(comment)
                .translation(String.format("%s.config.%s", Iota.MOD_ID, varName)), varName);
    }

    public record TagList(List<ResourceLocation> tagLocations) {
        public static final Codec<TagList> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                    ResourceLocation.CODEC.listOf().fieldOf("tags").forGetter(TagList::tagLocations)
                ).apply(instance, TagList::new));

        @SafeVarargs
        public TagList(Tag.Named<Item>... tags) {
            this(Arrays.stream(tags).map(Tag.Named::getName).collect(Collectors.toList()));
        }

        public List<Tag<Item>> getTags() {
            List<Tag<Item>> tags = new ArrayList<>();
            for (var loc: tagLocations) {
                tags.add(SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, loc, (tagName) -> new JsonSyntaxException("Unknown item tag '" + tagName + "'")));
            }
            return tags;
        }
    }

    public record ItemList(List<ResourceLocation> itemLocations) {
        public static final Codec<ItemList> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                    ResourceLocation.CODEC.listOf().fieldOf("items").forGetter(ItemList::itemLocations)
                ).apply(instance, ItemList::new));

        public ItemList(Item... items) {
            this(Arrays.stream(items).map(Item::getRegistryName).collect(Collectors.toList()));
        }

        public List<Item> getItems() {
            List<Item> items = new ArrayList<>();
            for (var loc: itemLocations) {
                var item = ForgeRegistries.ITEMS.getValue(loc);
                if (item != null)
                    items.add(item);
                else
                    throw new AssertionError("Item " + loc + " could not be loaded from its registry key!");
            }
            return items;
        }
    }

    public static class TagListCache {
        private static Map<TagList, TagListCache> instances = new HashMap<>();

        public static TagListCache of(TagList list) {
            if (!instances.containsKey(list))
                instances.put(list, new TagListCache(list));
            return instances.get(list);
        }

        private TagList tagList;
        private List<Tag<Item>> tagCache = new ArrayList<>();
        private int tagCacheNum = -1;
        private TagListCache(TagList list) { tagList = list; }

        public List<Tag<Item>> get(int cacheNum) { return get(cacheNum, false); }
        public List<Tag<Item>> get(int cacheNum, boolean forceClear) {
            if (tagCacheNum != -1 && tagCacheNum == cacheNum)
                return tagCache;
            return tagCache = tagList.getTags();
        }
    }
}

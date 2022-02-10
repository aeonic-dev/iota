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
import net.minecraftforge.common.ForgeConfigSpec;

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
                    ResourceLocation.CODEC.listOf().fieldOf("ingredientTags").forGetter(TagList::tagLocations)
                ).apply(instance, TagList::new));

        public TagList(Tag.Named... tags) {
            this(Arrays.asList(tags).stream().map(tag -> tag.getName()).collect(Collectors.toList()));
        }

        public List<Tag> getTags() {
            List<Tag> tags = new ArrayList();
            for (var loc: tagLocations) {
                tags.add(SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, loc, (tagName) -> new JsonSyntaxException("Unknown item tag '" + tagName + "'")));
            }
            return tags;
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
        private List<Tag> tagCache = new ArrayList<>();
        private int tagCacheNum = -1;
        private TagListCache(TagList list) { tagList = list; }

        public List<Tag> get(int cacheNum) { return get(cacheNum, false); }
        public List<Tag> get(int cacheNum, boolean forceClear) {
            if (tagCacheNum != -1 && tagCacheNum == cacheNum)
                return tagCache;
            return tagCache = tagList.getTags();
        }
    }
}

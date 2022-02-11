# iota

Iota is yet another QOL and Vanilla+ mod, adding a few things to the game that we thought should have been there in the first place and modifying some things we didn't like. Yep, one of those.

## Notable features
More information on these features and how to use them will be available in the wiki. Every notable feature of Iota is configurable and can be enabled, disabled and modified through the mod's config.

### Additions

#### Kiln
* Smelts cobblestones, sands, and more.
* Faster than a plain old furnace, similar to the blast furnace and smoker. Mirrors the originals as closely as possible; the only difference is switching recipes mid-smelt won't reset cooking progress (though it will scale to the correct recipe's total time). We'll call it a feature.
* Recipes are loaded from existing furnace recipes, if the inputs/outputs conform to certain tags set in the server config.
* Custom recipes can be added by tags in the configs or with datapack recipes. Simply use the vanilla furnace recipe format with the type `iota:kiln`.

### Tweaks

#### Redstone
* Dispensers can now interact with cauldrons!
  * Empty buckets will pick up water and lava from filled cauldrons
  * Full buckets will be emptied into an empty cauldron; if the cauldron is already full, the bucket will be spit out as normal
  * _Water prisons! Wow!_
    * Buckets containing animals act the same as full buckets, but also spawn their contained animal
    * By default this works with vanilla buckets only, but others can be added via the `iota:items/bucket_has_mob` tag
    * Empty buckets right clicked on a full water cauldron will pick up a bucketable entity within one block of the cauldron's center
  * Can be disabled or tweaked in the common config
  * Badly hardcoded in true vanilla fashion!


#### Mobs
* Villagers, wandering traders and turtles can now be leashed
* Villagers and wandering traders now follow players holding emeralds
* Parrots now follow players holding any `forge:seeds` item, significantly faster than their normal flight speed
* Snow golems now follow players holding snowballs, slightly faster than their normal walking speed
* Cows, mooshrooms, pigs, chickens, rabbits and sheep now follow players holding their respective food items 30% faster—no more waiting around for your farm animals to catch up!


#### Miscellaneous
* Nether portals now allow crying obsidian within their frame, as well as anything else in the handy `iota:blocks/nether_portal_frame` tag! Add blocks to your heart's desire.

### Integration

* Resource-driven [JEI item info](https://blamejared.com/docsImages/JEITweakerAddInfo.png)
  * You can simply add item information keys to a loaded lang file, and they'll be integrated with JEI!
  * This can be done by loading a lang file with one of KubeJS, ResourceLoader etc, or via [any of Vanilla's resource loading methods](https://minecraft.fandom.com/wiki/Resource_Pack#Behavior).
  * This system allows for easy item explanations with localization for pack makers.
  * Examples soon
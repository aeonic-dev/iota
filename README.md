# iota

Iota is yet another QOL and Vanilla+ mod, adding a few things to the game that we thought should have been there in the first place and modifying some things we didn't like. Yep, one of those.

## Notable features
More information on these features and how to use them will be available in the wiki. Every notable feature of Iota is configurable and can be enabled, disabled and modified through the mod's config.

### Additions

* Kiln
  * Smelts cobblestones, sands, and more.
  * Faster than a plain old furnace, similar to the blast furnace and smoker. Mirrors the originals as closely as possible; the only difference is switching recipes mid-smelt won't reset cooking progress (though it will scale to the correct recipe's total time). We'll call it a feature.
  * Recipes are loaded from existing furnace recipes, if the inputs/outputs conform to certain tags set in the server config.
  * Custom recipes can be added by tags in the configs or with datapack recipes. Simply use the vanilla furnace recipe format with the type `iota:kiln`.

### Tweaks

* Villagers, wandering traders and turtles can now be leashed
* Villagers and wandering traders now follow players holding emeralds
* Parrots now follow players holding any `forge:seeds` item, significantly faster than their normal flight speed
* Snow golems now follow players holding snowballs, slightly faster than their normal walking speed
* Cows, mooshrooms, pigs, chickens, rabbits and sheep now follow players holding their respective food items 30% faster—no more waiting around for your farm animals to catch up!

### Integration

* Resource-driven [JEI item info](https://blamejared.com/docsImages/JEITweakerAddInfo.png)
  * You can simply add item information keys to a loaded lang file, and they'll be integrated with JEI!
  * This can be done by loading a lang file with one of KubeJS, ResourceLoader etc, or via [any of Vanilla's resource loading methods](https://minecraft.fandom.com/wiki/Resource_Pack#Behavior).
  * This system allows for easy item explanations with localization for pack makers.
  * Examples soon
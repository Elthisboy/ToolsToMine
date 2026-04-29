# ToolsToMine

## Project Identity
- **Name:** ToolsToMine
- **Mod ID:** `toolstomine`
- **Version:** `${version}` (Resolved at build time)

## Technical Summary
The **ToolsToMine** mod expands the player's arsenal with highly specialized, power-mining tools and controlled explosives designed specifically for adventure or custom maps. It registers new custom items and entities to introduce Area-of-Effect (AoE) mining capabilities (drills and chainsaws) alongside "safe" explosives. The mod's core logic bypasses standard vanilla block-breaking and explosion mechanics by utilizing custom entity rendering (`ModEntities`) and specialized item handlers (`SafeGrenadeItem`, `SafeTntItem`, `AreaMineItem`) to ensure that terrain destruction is meticulously controlled.

## Feature Breakdown
- **Area-of-Effect Mining Tools:** Introduces Chainsaws and Drills that break blocks in a specific radius (e.g., 3x3) around the targeted block, significantly speeding up excavation.
- **Safe TNT:** A custom TNT variant that provides the visual and auditory feedback of an explosion—and potentially entity damage—without destroying the surrounding environmental blocks.
- **Safe Grenade:** A throwable explosive item that detonates on impact. Designed for combat scenarios on custom maps where terrain preservation is critical.
- **Custom Explosive Entities:** Registers proprietary entities to handle the explosion logic securely, bypassing vanilla TNT rules and preventing accidental map griefing.

## Command Registry
*Note: This mod does not introduce any traditional chat commands. All functionality is accessed directly through crafting, giving, or using the custom physical items (`AreaMineItem`, `SafeTntItem`, `SafeGrenadeItem`).*

## Configuration Schema
*Note: This specific mod does not generate a JSON configuration file in the `config/` folder. All parameters regarding drill mining radiuses, explosive damage, and tool durability are currently hardcoded directly into their respective Java classes.*

## Developer Info
- **Author:** el_this_boy
- **Platform:** Fabric 1.21.1

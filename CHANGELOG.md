# April Fools 2024
**WORK IN PROGRESS!**

## Gameplay
### Features
- **Improved Armor**
    - Wearing too much armor will now make you too heavy to swim or sprint.
    - Player vision has been improved when wearing helmets.
    - Wearing a heavy helmet will make the wearer slightly shorter.
### Blocks
- **Mysterious Ore** and **Deepslate Mysterious Ore**.
    - Mysterious Ore
        - Drops 1-2 Mysterious Gem.
    - Deepslate Mysterious Ore
        - Drops 2-3 Mysterious Gem.
    - Spawns between -48 and 16 in the Overworld.
    - Requires Iron or higher to mine.
- **Mysterious Block**
    - It can be crafted from 9 Mysterous Gem.
    - It can be used as a beacon base.
- **Exchanger**
    - It can be crafted from 5 Mysterious Gem, 3 Mysterious Block, and a Dropper.
    - When powered, the Exchanger will replace the stored item and dispense the new random item.
      - The random item is based on input item rarity.
      - Common items output from the common loottable.
      - Uncommon items output from the uncommon and common loottables.
      - Rare items output from the rare and uncommon loottables.
      - Epic items output from the epic and rare loottables.
      - _64% chance to have an item from the equivalent loottable, except for common items._  
        - If the output item doesn't match input rarity, two items will be dispensed.  
        - _1% chance of legendary item regardless of the input item._  
          - When inputting items from the legendary loot table, they will use their item rarity (or overrrides).  
      - Items in the loot tables have been chosen based on item rarity and how hard it is to obtain the item in survival.  
      - Once an item has been exchanged, it can't be exchanged again.
### Items
- **Mysterious Gem**
    - _This suspicious gemstone has baffled adventurers for eons._
### Technical Changes
#### Tag Lists
- Added `exchanger/epic_override`, `exchanger/rare_override`, `exchanger/uncommon_override`, and `exchanger/common_override` item tag lists.
    - These tag lists are checked before item rarity when dispensing from an exchanger if the input item's rarity matches the item's default rarity.
#### Loot Tables
- Added `exchanger/common`, `exchanger/uncommon`, `exchanger/rare`, `exchanger/epic`, and `exchanger/legendary` loot tables.
#### Components
- Added `mclmaf2024:exchanged` component.
    - This component adds "Exchanged" to the item stack's tooltip, and prevents the item from being Re-Exchanged.
- Added `mclmaf2024:flipped` component.
    - This component enables the `flipped` easter egg when the item stack is worn on the entity's helmet slot.
        - You can also enable this easter egg by naming an head wearable item stack `MCLegoMan`, `Dinnerbone`, or `Grumm` and wearing it on your helmet slot.
- Added `mclmaf2024:kappa` component.
    - This component enables the `kappa` easter egg when the item stack is worn on the entity's helmet slot.
        - You can also enable this easter egg by naming a turtle helmet `JudgeAlexander`, and wearing it on your helmet slot.

## How to install

#  
_Licensed under LGPL-3.0-or-later._  
_This mod was created for April Fools' and will not be updated._  
_Not affiliated or endorsed by Mojang Studios or Microsoft._  
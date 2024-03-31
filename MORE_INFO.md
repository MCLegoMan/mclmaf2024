### Exchanger  
- The exchanger's output loottable can be overriden using components.  
  - Here's an example: `/give @a minecraft:dirt[minecraft:container_loot={"loot_table":"mclmaf2024:exchanger/epic"}]`  
    - This command will give you a dirt that will output the `mclmaf2024:exchanger/epic` loottable.
  - The mod checks **components**, then **item rarity tags** (if the item stack rarity matches the default rarity), and then **item stack rarity**, before exchanging the item stack.
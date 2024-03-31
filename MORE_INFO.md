**Exchanger**  
- The exchanger's output loottable can be overriden using components.  
  - Here's an example: `/give @a minecraft:dirt[minecraft:container_loot={"loot_table":"mclmaf2024:exchanger/epic"}]`  
    - This command will give you a dirt that will output the `mclmaf2024:exchanger/epic` loottable.
  - The mod checks **components**, then **item rarity tags** (if the item stack rarity matches the default rarity), and then **item stack rarity**, before exchanging the item stack.

**Wearable Items**
- Items that can be worn in the helmet slot can force some easter eggs using components.
  - **Item Components:**
    - `mclmaf2024:flipped` - Forces the Flipped Easter Egg.
    - `mclmaf2024:kappa` - Forces the Kappa Easter Egg.
    - `mclmaf2024:love_and_hugs` - Forces the Love and Hugs Easter Egg.
    - `mclmaf2024:disable_helmet_shader` - Force disables the helmet shader that gets rendered when an item is in the helmet slot.
- Here's an example: `/give @a minecraft:diamond_helmet[mclmaf2024:kappa=true]`
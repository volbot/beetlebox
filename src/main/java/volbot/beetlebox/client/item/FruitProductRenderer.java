package volbot.beetlebox.client.item;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.TextureManager;

public class FruitProductRenderer extends ItemRenderer {

	public FruitProductRenderer(TextureManager manager, BakedModelManager bakery, ItemColors colors,
			BuiltinModelItemRenderer builtinModelItemRenderer) {
		super(manager, bakery, colors, builtinModelItemRenderer);
	}

}

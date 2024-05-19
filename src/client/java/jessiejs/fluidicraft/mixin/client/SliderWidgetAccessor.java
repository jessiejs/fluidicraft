package jessiejs.fluidicraft.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;

@Mixin(SliderWidget.class)
public interface SliderWidgetAccessor {
	@Accessor
	double getValue();
}

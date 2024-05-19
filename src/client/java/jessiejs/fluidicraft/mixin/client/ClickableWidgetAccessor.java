package jessiejs.fluidicraft.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.ClickableWidget;

@Mixin(ClickableWidget.class)
public interface ClickableWidgetAccessor {
    @Accessor
    float getAlpha();
}

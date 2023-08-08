package jessiejs.fluidicraft.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.MinecraftClient;

@Mixin(SliderWidget.class)
public class SliderWidgetMixin {
	private SliderWidget getBase() {
		return ((SliderWidget) ((Object) this));
	}

	private float getAlpha() throws Exception {
		SliderWidget self = getBase();
		return self.getClass().getField("alpha").getFloat(self);
	}

	private double getValue() throws Exception {
		SliderWidget self = getBase();
		return self.getClass().getField("value").getDouble(self);
	}

	@Overwrite
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) throws Exception {
		SliderWidget self = getBase();
		MinecraftClient minecraftClient = MinecraftClient.getInstance();

		context.setShaderColor(1.0f, 1.0f, 1.0f, getAlpha());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

		context.fill(self.getX(), self.getY(), self.getX() + self.getWidth(), self.getY() + self.getHeight(),
				0xFF111111);

		int meterInset = 2;

		int headWidth = 10;
		int headX = (int) Math.round(self.getX() + (self.getWidth() - headWidth) * getValue());
		int headInset = 2;
		int headRaise = 2;

		context.fill(self.getX(),self.getY(),headX,self.getY() + self.getHeight(),0xFF0C0C0C);
		context.enableScissor(self.getX(),self.getY(),headX,self.getY() + self.getHeight());
		context.fill(self.getX() + meterInset,self.getY() + meterInset,headX - meterInset,self.getY() + self.getHeight() - meterInset,0xFF1C1C1C);
		context.disableScissor();

		context.drawCenteredTextWithShadow(minecraftClient.textRenderer, self.getMessage(), self.getX() + self.getWidth() / 2, self.getY() + self.getHeight() / 2 - minecraftClient.textRenderer.fontHeight / 2, 0xFFFFFFFF);

		context.fill(headX, self.getY() - headRaise, headX + headWidth, self.getY() + self.getHeight(), 0xFF222222);
		context.fill(headX + headInset, self.getY() + headInset - headRaise, headX + headWidth - headInset,
				self.getY() + self.getHeight() - headInset - headRaise, 0xFF424242);
	}
}

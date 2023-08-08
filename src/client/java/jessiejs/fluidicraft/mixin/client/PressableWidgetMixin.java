package jessiejs.fluidicraft.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(PressableWidget.class)
public class PressableWidgetMixin {
	private float animatedHeight = 0;
	private float animatedHeightVelocity = 0;

	private PressableWidget getBase() {
		return ((PressableWidget)((Object)this));
	}

	private float getAlpha() throws Exception {
		PressableWidget self = getBase();
		return self.getClass().getField("alpha").getFloat(self);
	}

    private int getTextureY() {
		PressableWidget self = getBase();
        int i = 1;
        if (!self.active) {
            i = 0;
        } else if (self.isSelected()) {
            i = 2;
        }
        return 46 + i * 20;
    }

	@Overwrite
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) throws Exception {
		PressableWidget self = getBase();
		float alpha = getAlpha();

		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		context.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();

		float targetHeight = 2;

		if (self.isSelected()) {
			targetHeight = 5;
		}

		if (!self.active) {
			targetHeight = 0;
		}

		animatedHeightVelocity *= 1 - delta * 1;
		animatedHeightVelocity += (targetHeight - animatedHeight) * delta * 5f;
		animatedHeight += animatedHeightVelocity * delta;

		int raised = Math.round(animatedHeight);
		int inset = 2;

		int borderColor = 0xFF222222;
		int innerColor = 0xFF424242;
		int textColor = 0xFFFFFFFF;

		if (self.isSelected()) {
			borderColor = 0xFF333333;
			innerColor = 0xFF535353;
			textColor = 0xFFFFFFFF;
		}

		if (!self.active) {
			borderColor = 0xFF111111;
			innerColor = 0xFF202020;
			textColor = 0xFFBBBBBB;
		}

		context.fill(self.getX(), self.getY() - raised, self.getX() + self.getWidth(), self.getY() + self.getHeight(), borderColor);
		context.fill(self.getX() + inset, self.getY() - raised + inset, self.getX() + self.getWidth() - inset, self.getY() + self.getHeight() - raised - inset, innerColor);
		//context.drawNineSlicedTexture(ClickableWidget.WIDGETS_TEXTURE, self.getX(), self.getY(), self.getWidth(), self.getHeight(), 20,
		//		4, 200, 20, 0, getTextureY());
		context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		//self.drawMessage(context, minecraftClient.textRenderer,
		//		i | MathHelper.ceil((float) (alpha * 255.0f)) << 24);
		context.drawCenteredTextWithShadow(minecraftClient.textRenderer, self.getMessage(), self.getX() + self.getWidth() / 2, self.getY() + self.getHeight() / 2 - minecraftClient.textRenderer.fontHeight / 2 - raised, textColor);
	}
}

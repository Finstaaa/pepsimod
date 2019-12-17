/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2017-2019 DaPorkchop_
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original author of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.pepsimod.mixin.client.settings;

import net.daporkchop.pepsimod.optimization.OverrideCounter;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author DaPorkchop_
 */
@Mixin(KeyBinding.class)
public abstract class MixinKeyBinding implements OverrideCounter {
    @Shadow
    private boolean pressed;

    public int overrideCounter = 0;

    @Override
    public void incrementOverride() {
        this.overrideCounter++;
    }

    @Override
    public void decrementOverride() {
        if (--this.overrideCounter < 0) {
            this.overrideCounter = 0;
        }
    }

    @Override
    public int getOverride() {
        return this.overrideCounter;
    }

    @Redirect(
            method = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/settings/KeyBinding;pressed:Z"
            ))
    public boolean modifyIsKeyDown(KeyBinding binding)  {
        return this.pressed || this.isOverriden();
    }
}
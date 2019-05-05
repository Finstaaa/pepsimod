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

package net.daporkchop.pepsimod.mixin.client.renderer;

import net.daporkchop.pepsimod.module.impl.render.XrayMod;
import net.daporkchop.pepsimod.util.config.impl.XrayTranslator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModelRenderer.class)
public abstract class MixinBlockModelRenderer {
    @Inject(
            method = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Z)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void preRenderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder buffer, boolean checkSides, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (XrayMod.INSTANCE.state.enabled) {
            if (!XrayTranslator.INSTANCE.isTargeted(blockStateIn.getBlock())) {
                callbackInfoReturnable.setReturnValue(false);
                callbackInfoReturnable.cancel();
            }
        }
        //vanilla code follows
    }

    @Inject(
            method = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelSmooth(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void preRenderQuadsSmooth(IBlockAccess access, IBakedModel model, IBlockState stateIn, BlockPos pos, BufferBuilder bufferBuilder, boolean idk, long ok, CallbackInfoReturnable<Boolean> returnable) {
        if (XrayMod.INSTANCE.state.enabled) {
            if (!XrayTranslator.INSTANCE.isTargeted(stateIn.getBlock())) {
                returnable.setReturnValue(false);
                returnable.cancel();
            }
        }
        //vanilla code follows
    }
}

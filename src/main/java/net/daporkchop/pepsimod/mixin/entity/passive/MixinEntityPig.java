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

package net.daporkchop.pepsimod.mixin.entity.passive;

import net.daporkchop.pepsimod.module.impl.movement.EntitySpeedMod;
import net.daporkchop.pepsimod.optimization.SizeSettable;
import net.daporkchop.pepsimod.util.config.impl.EntitySpeedTranslator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import javax.annotation.Nullable;

@Mixin(EntityPig.class)
public abstract class MixinEntityPig extends EntityAnimal implements SizeSettable {
    public int riddenTicks = 0;

    public MixinEntityPig() {
        super(null);
    }

    @Shadow
    @Nullable
    public abstract Entity getControllingPassenger();

    @Overwrite
    public boolean canBeSteered() {
        Entity entity = this.getControllingPassenger();

        if (!(entity instanceof EntityPlayer)) {
            return false;
        } else {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            return (this.world.isRemote && EntitySpeedMod.INSTANCE.state.enabled) || entityplayer.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || entityplayer.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
        }
    }

    @Override
    public void onLivingUpdate() {
        this.forceSetSize(0.9f, 0.9f);
        if (this.world.isRemote && this.isBeingRidden()) {
            if (this.riddenTicks++ >= 2) {
                if (this.riddenTicks > 1000) {
                    this.riddenTicks = 1000;
                }
                AxisAlignedBB bb = EntitySpeedMod.getMergedBBs(this, this.getEntityBoundingBox());
                this.forceSetSize((float) Math.max(bb.maxX - bb.minX, bb.maxZ - bb.minZ), (float) (bb.maxY - bb.minY));
            }
        } else {
            this.riddenTicks = 0;
        }
        super.onLivingUpdate();
    }

    @Override
    public double getMountedYOffset() {
        return 0.9d * 0.75d;
    }

    @ModifyConstant(
            method = "Lnet/minecraft/entity/passive/EntityPig;travel(FFF)V",
            constant = @Constant(
                    floatValue = 1.0f,
                    ordinal = 0
            ))
    public float modifyStepHeight(float orig) {
        return this.world.isRemote && EntitySpeedMod.INSTANCE.state.enabled ? EntitySpeedMod.INSTANCE.fakedStepHeight : orig;
    }

    @ModifyConstant(
            method = "Lnet/minecraft/entity/passive/EntityPig;travel(FFF)V",
            constant = @Constant(
                    floatValue = 1.0f,
                    ordinal = 1
            ))
    public float modifyIdleSpeed(float orig) {
        return this.world.isRemote && EntitySpeedMod.INSTANCE.state.enabled ? EntitySpeedTranslator.INSTANCE.idleSpeed : orig;
    }
}

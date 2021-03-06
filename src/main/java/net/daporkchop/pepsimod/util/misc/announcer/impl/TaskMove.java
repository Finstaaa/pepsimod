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

package net.daporkchop.pepsimod.util.misc.announcer.impl;

import net.daporkchop.pepsimod.module.impl.misc.AnnouncerMod;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.util.misc.announcer.MessagePrefixes;
import net.daporkchop.pepsimod.util.misc.announcer.QueuedTask;
import net.daporkchop.pepsimod.util.misc.announcer.TaskType;
import net.minecraft.util.math.Vec3d;

public class TaskMove extends QueuedTask {
    public double dist = 0.0d;
    public Vec3d lastPos = null;

    public TaskMove(TaskType type) {
        super(type);
        this.lastPos = mc.player.getPositionVector();
    }

    public String getMessage() {
        if (!AnnouncerMod.INSTANCE.state.enabled || this.dist == 0.0d) {
            return null;
        }

        return MessagePrefixes.getMessage(TaskType.WALK, this.dist);
    }

    public void update(Vec3d nextPos)   {
        if (this.lastPos != null && nextPos != null)   {
            this.dist += this.lastPos.distanceTo(nextPos);
        }
        this.lastPos = nextPos;
    }
}

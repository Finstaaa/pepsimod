/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2017 Team Pepsi
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from Team Pepsi.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: Team Pepsi), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.daporkchop.pepsimod.wdl.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityTracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * Used to manage and produce a list of entities and their track distances.
 * Multiple implementations will usually be present at once, with different
 * priorities.
 */
public interface IEntityManager extends IWDLMod {
    /**
     * Gets a list of entity identifiers known to this extension.
     * <p>
     * There is no requirement for the structure of these names, but it is
     * recommended that implementations use the conventional name for the entity
     * (as would be found in {@link EntityList}). Matching names will be merged
     * by the mod itself, which is beneficial.
     * <p>
     * However, non-standard entities don't have a standardized name. Thus, they
     * should use the name that works best for them. For instance, entities
     * added by forge may not have a canonical name (though most will).
     * Implementations are free to use whatever names they want in this
     * circumstance.
     * <p>
     * For entities which are special-cases composed of normal entities (such as
     * holograms or player disguises) a valid convention would be to prefix the
     * entity name with "x".
     *
     * @return A list of entity identifiers known to this extension
     */
    @Nonnull
    Set<String> getProvidedEntities();

    /**
     * Gets the identifier for an entity.
     * <p>
     * This method will be called sequentially on all instances of this class,
     * in order of priority, until one returns a non-<code>null</code> value.
     *
     * @param entity The entity to identify.
     * @return The entity identifier, or <code>null</code> if this extension has
     * no name for that entity. <br>
     * If not <code>null</code>, the name MUST be one of the names
     * included in {@link #getProvidedEntities()}.
     * @see #getProvidedEntities()
     */
    @Nullable
    String getIdentifierFor(@Nonnull Entity entity);

    /**
     * Gets the track distance for the given entity.
     * <p>
     * The track distance is the distance in meters (blocks), horizontally (but
     * not taxicab) at which a server removes an entity from a client's view,
     * sending a remove entity packet. It isn't necessarily the distance at
     * which an entity stops rendering.
     * <p>
     * For vanilla entities, these values are provided in
     * {@link EntityTracker#track(Entity)}.
     * <p>
     * This method will be called when {@link #getProvidedEntities()} includes
     * the given identifier, even if this provider did not identify the entity.
     *
     * @param identifier The identifier of the type of entity to get a track distance
     *                   for.
     * @param entity     The entity that needs a track distance. May be null in static
     *                   situations, such as the entity list GUI. Provided for more
     *                   complicated situations; prefer the identifier when possible.
     * @return The entity identifier, or <code>-1</code> if this extension has
     * no track distance for that entity. Note that it is legal to
     * return -1 even if this implementation did directly identify the
     * entity.
     * @see #getProvidedEntities()
     */
    int getTrackDistance(@Nonnull String identifier, @Nullable Entity entity);

    /**
     * Gets an internal ID for the group to put that entity in. Used for
     * grouping and storing data.
     * <p>
     * This method will be called when {@link #getProvidedEntities()} includes
     * the given identifier, even if this provider did not identify the entity.
     *
     * @param identifier The identifier.
     * @return The group for the entity: an internal ID.
     */
    @Nullable
    String getGroup(@Nonnull String identifier);

    /**
     * Gets the player-visible display identifier for the given entity
     * identifier.
     * <p>
     * This method will be called when {@link #getProvidedEntities()} includes
     * the given identifier, even if this provider did not identify the entity.
     *
     * @param identifier The internal identifier for the entity.
     * @return The display version of the identifier, potentially translated, or
     * <code>null</code> if no translated identifier could be provided.
     */
    @Nullable
    String getDisplayIdentifier(@Nonnull String identifier);

    /**
     * Gets the player-visible name for the given entity group.
     *
     * @param group The internal group name.
     * @return The display name for the given group, potentially translated, or
     * <code>null</code> if no translated group name could be provided.
     */
    @Nullable
    String getDisplayGroup(@Nonnull String group);

    /**
     * Is the given type of entity enabled by default?
     *
     * @param identifier The ID for the entity.
     * @return True if that type of entity should be saved by default.
     */
    boolean enabledByDefault(@Nonnull String identifier);
}
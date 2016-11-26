/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.event;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Preconditions;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.spongepowered.api.GameState;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.BrewingStand;
import org.spongepowered.api.block.tileentity.carrier.Furnace;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.source.RconSource;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.manipulator.immutable.ImmutableColoredData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableCommandData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableDisplayNameData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableDyeableData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableFireworkEffectData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableFireworkRocketData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableMobSpawnerData;
import org.spongepowered.api.data.manipulator.immutable.ImmutablePotionEffectData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableRepresentedItemData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableRepresentedPlayerData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableRotationalData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableSkullData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableTargetedLocationData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableWetData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableAttachedData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableAxisData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableBigMushroomData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableBrickData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableComparatorData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableConnectedDirectionData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDecayableData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDelayableData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDirectionalData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDirtData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDisarmedData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDisguisedBlockData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDoublePlantData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableDropData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableExtendedData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableFilledData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableFluidLevelData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableGrowthData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableHingeData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableInWallData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableLayeredData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableLogAxisData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableMoistureData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableOccupiedData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableOpenData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutablePistonData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutablePlantData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutablePortionData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutablePoweredData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutablePrismarineData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableQuartzData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableRailDirectionData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableRedstonePoweredData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableSandData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableSandstoneData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableSeamlessData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableShrubData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableSlabData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableSnowedData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableStairShapeData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableStoneData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableTreeData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableWallData;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableWireAttachmentData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableAchievementData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableAffectsSpawningData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableAgeableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableAgentData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableAggressiveData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableAngerableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableArmorStandData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableArtData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableBodyPartRotationalData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableBreathingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableBreedableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableCareerData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableChargedData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableCriticalHitData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableCustomNameVisibleData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableDamageableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableDamagingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableDespawnDelayData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableDominantHandData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableElderData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableExpOrbData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableExperienceHolderData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableExpirableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableExplosionRadiusData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableFallDistanceData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableFallingBlockData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableFlammableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableFlyingAbilityData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableFlyingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableFoodData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableFuseData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableGameModeData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableGlowingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableGravityData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableGriefingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableHealthData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableHealthScalingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableHorseData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableIgniteableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableInvisibilityData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableInvulnerabilityData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableJoinData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableKnockbackData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableLeashData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableMinecartBlockData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableMovementSpeedData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableOcelotData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutablePassengerData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutablePersistingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutablePickupDelayData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutablePickupRuleData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutablePigSaddleData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutablePlayerCreatedData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutablePlayingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableRabbitData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableRespawnLocation;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableScreamingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableShatteringData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableShearedData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSilentData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSittingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSizeData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSkeletonData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSkinData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSleepingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSlimeData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSneakingData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableSprintData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableStatisticData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableStuckArrowsData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableTameableData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableTradeOfferData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableVehicleData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableVelocityData;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableZombieData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableAuthorData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableBlockItemData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableBreakableData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableCoalData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableCookedFishData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableDurabilityData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableEnchantmentData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableFishData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableGenerationData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableGoldenAppleData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableHideData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableInventoryItemData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableLoreData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableMapItemData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutablePagedData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutablePlaceableData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableSpawnableData;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableStoredEnchantmentData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableBannerData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableBeaconData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableBrewingStandData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableCooldownData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableFurnaceData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableLockableData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableNoteData;
import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableSignData;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.SkinPart;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.ai.Goal;
import org.spongepowered.api.entity.ai.task.AITask;
import org.spongepowered.api.entity.explosive.Explosive;
import org.spongepowered.api.entity.explosive.FusedExplosive;
import org.spongepowered.api.entity.living.Ageable;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Humanoid;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.projectile.FishHook;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.achievement.GrantAchievementEvent;
import org.spongepowered.api.event.action.CollideEvent;
import org.spongepowered.api.event.action.FishingEvent;
import org.spongepowered.api.event.action.InteractEvent;
import org.spongepowered.api.event.action.LightningEvent;
import org.spongepowered.api.event.action.SleepingEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.NotifyNeighborBlockEvent;
import org.spongepowered.api.event.block.TargetBlockEvent;
import org.spongepowered.api.event.block.TickBlockEvent;
import org.spongepowered.api.event.block.tileentity.BrewingEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.block.tileentity.SmeltEvent;
import org.spongepowered.api.event.block.tileentity.TargetTileEntityEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageModifier;
import org.spongepowered.api.event.cause.entity.health.HealthModifier;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.command.TabCompleteEvent;
import org.spongepowered.api.event.data.ChangeColoredData;
import org.spongepowered.api.event.data.ChangeCommandData;
import org.spongepowered.api.event.data.ChangeDisplayNameData;
import org.spongepowered.api.event.data.ChangeDyeableData;
import org.spongepowered.api.event.data.ChangeFireworkEffectData;
import org.spongepowered.api.event.data.ChangeFireworkRocketData;
import org.spongepowered.api.event.data.ChangeMobSpawnerData;
import org.spongepowered.api.event.data.ChangePotionEffectData;
import org.spongepowered.api.event.data.ChangeRepresentedItemData;
import org.spongepowered.api.event.data.ChangeRepresentedPlayerData;
import org.spongepowered.api.event.data.ChangeRotationalData;
import org.spongepowered.api.event.data.ChangeSkullData;
import org.spongepowered.api.event.data.ChangeTargetedLocationData;
import org.spongepowered.api.event.data.ChangeWetData;
import org.spongepowered.api.event.data.block.ChangeAttachedData;
import org.spongepowered.api.event.data.block.ChangeAxisData;
import org.spongepowered.api.event.data.block.ChangeBigMushroomData;
import org.spongepowered.api.event.data.block.ChangeBrickData;
import org.spongepowered.api.event.data.block.ChangeComparatorData;
import org.spongepowered.api.event.data.block.ChangeConnectedDirectionData;
import org.spongepowered.api.event.data.block.ChangeDecayableData;
import org.spongepowered.api.event.data.block.ChangeDelayableData;
import org.spongepowered.api.event.data.block.ChangeDirectionalData;
import org.spongepowered.api.event.data.block.ChangeDirtData;
import org.spongepowered.api.event.data.block.ChangeDisarmedData;
import org.spongepowered.api.event.data.block.ChangeDisguisedBlockData;
import org.spongepowered.api.event.data.block.ChangeDoublePlantData;
import org.spongepowered.api.event.data.block.ChangeDropData;
import org.spongepowered.api.event.data.block.ChangeExtendedData;
import org.spongepowered.api.event.data.block.ChangeFilledData;
import org.spongepowered.api.event.data.block.ChangeFluidLevelData;
import org.spongepowered.api.event.data.block.ChangeGrowthData;
import org.spongepowered.api.event.data.block.ChangeHingeData;
import org.spongepowered.api.event.data.block.ChangeInWallData;
import org.spongepowered.api.event.data.block.ChangeLayeredData;
import org.spongepowered.api.event.data.block.ChangeLogAxisData;
import org.spongepowered.api.event.data.block.ChangeMoistureData;
import org.spongepowered.api.event.data.block.ChangeOccupiedData;
import org.spongepowered.api.event.data.block.ChangeOpenData;
import org.spongepowered.api.event.data.block.ChangePistonData;
import org.spongepowered.api.event.data.block.ChangePlantData;
import org.spongepowered.api.event.data.block.ChangePortionData;
import org.spongepowered.api.event.data.block.ChangePoweredData;
import org.spongepowered.api.event.data.block.ChangePrismarineData;
import org.spongepowered.api.event.data.block.ChangeQuartzData;
import org.spongepowered.api.event.data.block.ChangeRailDirectionData;
import org.spongepowered.api.event.data.block.ChangeRedstonePoweredData;
import org.spongepowered.api.event.data.block.ChangeSandData;
import org.spongepowered.api.event.data.block.ChangeSandstoneData;
import org.spongepowered.api.event.data.block.ChangeSeamlessData;
import org.spongepowered.api.event.data.block.ChangeShrubData;
import org.spongepowered.api.event.data.block.ChangeSlabData;
import org.spongepowered.api.event.data.block.ChangeSnowedData;
import org.spongepowered.api.event.data.block.ChangeStairShapeData;
import org.spongepowered.api.event.data.block.ChangeStoneData;
import org.spongepowered.api.event.data.block.ChangeTreeData;
import org.spongepowered.api.event.data.block.ChangeWallData;
import org.spongepowered.api.event.data.block.ChangeWireAttachmentData;
import org.spongepowered.api.event.data.entity.ChangeAchievementData;
import org.spongepowered.api.event.data.entity.ChangeAffectsSpawningData;
import org.spongepowered.api.event.data.entity.ChangeAgeableData;
import org.spongepowered.api.event.data.entity.ChangeAgentData;
import org.spongepowered.api.event.data.entity.ChangeAggressiveData;
import org.spongepowered.api.event.data.entity.ChangeAngerableData;
import org.spongepowered.api.event.data.entity.ChangeArmorStandData;
import org.spongepowered.api.event.data.entity.ChangeArtData;
import org.spongepowered.api.event.data.entity.ChangeBodyPartRotationalData;
import org.spongepowered.api.event.data.entity.ChangeBreathingData;
import org.spongepowered.api.event.data.entity.ChangeBreedableData;
import org.spongepowered.api.event.data.entity.ChangeCareerData;
import org.spongepowered.api.event.data.entity.ChangeChargedData;
import org.spongepowered.api.event.data.entity.ChangeCriticalHitData;
import org.spongepowered.api.event.data.entity.ChangeCustomNameVisibleData;
import org.spongepowered.api.event.data.entity.ChangeDamageableData;
import org.spongepowered.api.event.data.entity.ChangeDamagingData;
import org.spongepowered.api.event.data.entity.ChangeDespawnDelayData;
import org.spongepowered.api.event.data.entity.ChangeDominantHandData;
import org.spongepowered.api.event.data.entity.ChangeElderData;
import org.spongepowered.api.event.data.entity.ChangeExpOrbData;
import org.spongepowered.api.event.data.entity.ChangeExperienceHolderData;
import org.spongepowered.api.event.data.entity.ChangeExpirableData;
import org.spongepowered.api.event.data.entity.ChangeExplosionRadiusData;
import org.spongepowered.api.event.data.entity.ChangeFallDistanceData;
import org.spongepowered.api.event.data.entity.ChangeFallingBlockData;
import org.spongepowered.api.event.data.entity.ChangeFlammableData;
import org.spongepowered.api.event.data.entity.ChangeFlyingAbilityData;
import org.spongepowered.api.event.data.entity.ChangeFlyingData;
import org.spongepowered.api.event.data.entity.ChangeFoodData;
import org.spongepowered.api.event.data.entity.ChangeFuseData;
import org.spongepowered.api.event.data.entity.ChangeGameModeData;
import org.spongepowered.api.event.data.entity.ChangeGlowingData;
import org.spongepowered.api.event.data.entity.ChangeGravityData;
import org.spongepowered.api.event.data.entity.ChangeGriefingData;
import org.spongepowered.api.event.data.entity.ChangeHealthData;
import org.spongepowered.api.event.data.entity.ChangeHealthScalingData;
import org.spongepowered.api.event.data.entity.ChangeHorseData;
import org.spongepowered.api.event.data.entity.ChangeIgniteableData;
import org.spongepowered.api.event.data.entity.ChangeInvisibilityData;
import org.spongepowered.api.event.data.entity.ChangeInvulnerabilityData;
import org.spongepowered.api.event.data.entity.ChangeJoinData;
import org.spongepowered.api.event.data.entity.ChangeKnockbackData;
import org.spongepowered.api.event.data.entity.ChangeLeashData;
import org.spongepowered.api.event.data.entity.ChangeMinecartBlockData;
import org.spongepowered.api.event.data.entity.ChangeMovementSpeedData;
import org.spongepowered.api.event.data.entity.ChangeOcelotData;
import org.spongepowered.api.event.data.entity.ChangePassengerData;
import org.spongepowered.api.event.data.entity.ChangePersistingData;
import org.spongepowered.api.event.data.entity.ChangePickupDelayData;
import org.spongepowered.api.event.data.entity.ChangePickupRuleData;
import org.spongepowered.api.event.data.entity.ChangePigSaddleData;
import org.spongepowered.api.event.data.entity.ChangePlayerCreatedData;
import org.spongepowered.api.event.data.entity.ChangePlayingData;
import org.spongepowered.api.event.data.entity.ChangeRabbitData;
import org.spongepowered.api.event.data.entity.ChangeRespawnLocationData;
import org.spongepowered.api.event.data.entity.ChangeScreamingData;
import org.spongepowered.api.event.data.entity.ChangeShatteringData;
import org.spongepowered.api.event.data.entity.ChangeShearedData;
import org.spongepowered.api.event.data.entity.ChangeSilentData;
import org.spongepowered.api.event.data.entity.ChangeSittingData;
import org.spongepowered.api.event.data.entity.ChangeSizeData;
import org.spongepowered.api.event.data.entity.ChangeSkeletonData;
import org.spongepowered.api.event.data.entity.ChangeSkinData;
import org.spongepowered.api.event.data.entity.ChangeSleepingData;
import org.spongepowered.api.event.data.entity.ChangeSlimeData;
import org.spongepowered.api.event.data.entity.ChangeSneakingData;
import org.spongepowered.api.event.data.entity.ChangeSprintData;
import org.spongepowered.api.event.data.entity.ChangeStatisticData;
import org.spongepowered.api.event.data.entity.ChangeStuckArrowsData;
import org.spongepowered.api.event.data.entity.ChangeTameableData;
import org.spongepowered.api.event.data.entity.ChangeTradeOfferData;
import org.spongepowered.api.event.data.entity.ChangeVehicleData;
import org.spongepowered.api.event.data.entity.ChangeVelocityData;
import org.spongepowered.api.event.data.entity.ChangeZombieData;
import org.spongepowered.api.event.data.item.ChangeAuthorData;
import org.spongepowered.api.event.data.item.ChangeBlockItemData;
import org.spongepowered.api.event.data.item.ChangeBreakableData;
import org.spongepowered.api.event.data.item.ChangeCoalData;
import org.spongepowered.api.event.data.item.ChangeCookedFishData;
import org.spongepowered.api.event.data.item.ChangeDurabilityData;
import org.spongepowered.api.event.data.item.ChangeEnchantmentData;
import org.spongepowered.api.event.data.item.ChangeFishData;
import org.spongepowered.api.event.data.item.ChangeGenerationData;
import org.spongepowered.api.event.data.item.ChangeGoldenAppleData;
import org.spongepowered.api.event.data.item.ChangeHideData;
import org.spongepowered.api.event.data.item.ChangeInventoryItemData;
import org.spongepowered.api.event.data.item.ChangeLoreData;
import org.spongepowered.api.event.data.item.ChangeMapItemData;
import org.spongepowered.api.event.data.item.ChangePagedData;
import org.spongepowered.api.event.data.item.ChangePlaceableData;
import org.spongepowered.api.event.data.item.ChangeSpawnableData;
import org.spongepowered.api.event.data.item.ChangeStoredEnchantmentData;
import org.spongepowered.api.event.data.tileentity.ChangeBannerData;
import org.spongepowered.api.event.data.tileentity.ChangeBeaconData;
import org.spongepowered.api.event.data.tileentity.ChangeBrewingStandData;
import org.spongepowered.api.event.data.tileentity.ChangeCooldownData;
import org.spongepowered.api.event.data.tileentity.ChangeFurnaceData;
import org.spongepowered.api.event.data.tileentity.ChangeLockableData;
import org.spongepowered.api.event.data.tileentity.ChangeNoteData;
import org.spongepowered.api.event.data.tileentity.ChangeSignData;
import org.spongepowered.api.event.economy.EconomyTransactionEvent;
import org.spongepowered.api.event.entity.AffectEntityEvent;
import org.spongepowered.api.event.entity.AttackEntityEvent;
import org.spongepowered.api.event.entity.BreedEntityEvent;
import org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent;
import org.spongepowered.api.event.entity.ChangeEntityExperienceEvent;
import org.spongepowered.api.event.entity.ChangeEntityPotionEffectEvent;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.entity.ConstructEntityEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.ExpireEntityEvent;
import org.spongepowered.api.event.entity.HarvestEntityEvent;
import org.spongepowered.api.event.entity.HealEntityEvent;
import org.spongepowered.api.event.entity.IgniteEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.LeashEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.RideEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.entity.TameEntityEvent;
import org.spongepowered.api.event.entity.TargetEntityEvent;
import org.spongepowered.api.event.entity.UnleashEntityEvent;
import org.spongepowered.api.event.entity.ai.AITaskEvent;
import org.spongepowered.api.event.entity.explosive.DefuseExplosiveEvent;
import org.spongepowered.api.event.entity.explosive.DetonateExplosiveEvent;
import org.spongepowered.api.event.entity.explosive.PrimeExplosiveEvent;
import org.spongepowered.api.event.entity.explosive.TargetExplosiveEvent;
import org.spongepowered.api.event.entity.explosive.TargetFusedExplosiveEvent;
import org.spongepowered.api.event.entity.item.ItemMergeItemEvent;
import org.spongepowered.api.event.entity.item.TargetItemEvent;
import org.spongepowered.api.event.entity.living.TargetAgentEvent;
import org.spongepowered.api.event.entity.living.TargetLivingEvent;
import org.spongepowered.api.event.entity.living.humanoid.AnimateHandEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeLevelEvent;
import org.spongepowered.api.event.entity.living.humanoid.HandInteractEvent;
import org.spongepowered.api.event.entity.living.humanoid.TargetHumanoidEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.KickPlayerEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.PlayerChangeClientSettingsEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.ResourcePackStatusEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.entity.projectile.TargetProjectileEvent;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStateEvent;
import org.spongepowered.api.event.game.state.GameStoppedEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.item.inventory.AffectItemStackEvent;
import org.spongepowered.api.event.item.inventory.AffectSlotEvent;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.item.inventory.TargetContainerEvent;
import org.spongepowered.api.event.item.inventory.TargetInventoryEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.event.network.BanIpEvent;
import org.spongepowered.api.event.network.ChannelRegistrationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.network.PardonIpEvent;
import org.spongepowered.api.event.network.rcon.RconConnectionEvent;
import org.spongepowered.api.event.server.ClientPingServerEvent;
import org.spongepowered.api.event.server.query.QueryServerEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.event.statistic.ChangeStatisticEvent;
import org.spongepowered.api.event.user.BanUserEvent;
import org.spongepowered.api.event.user.PardonUserEvent;
import org.spongepowered.api.event.user.TargetUserEvent;
import org.spongepowered.api.event.world.ChangeWorldGameRuleEvent;
import org.spongepowered.api.event.world.ChangeWorldWeatherEvent;
import org.spongepowered.api.event.world.ConstructPortalEvent;
import org.spongepowered.api.event.world.ConstructWorldPropertiesEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.event.world.GenerateChunkEvent;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.event.world.SaveWorldEvent;
import org.spongepowered.api.event.world.TargetWorldEvent;
import org.spongepowered.api.event.world.UnloadWorldEvent;
import org.spongepowered.api.event.world.chunk.ForcedChunkEvent;
import org.spongepowered.api.event.world.chunk.LoadChunkEvent;
import org.spongepowered.api.event.world.chunk.PopulateChunkEvent;
import org.spongepowered.api.event.world.chunk.TargetChunkEvent;
import org.spongepowered.api.event.world.chunk.UnforcedChunkEvent;
import org.spongepowered.api.event.world.chunk.UnloadChunkEvent;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.network.RemoteConnection;
import org.spongepowered.api.network.status.StatusClient;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.resourcepack.ResourcePack;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.statistic.achievement.Achievement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.chat.ChatVisibility;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.util.Tuple;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.ChunkTicketManager;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.PortalAgent;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.explosion.Explosion;
import org.spongepowered.api.world.gen.Populator;
import org.spongepowered.api.world.storage.WorldProperties;
import org.spongepowered.api.world.weather.Weather;

public class SpongeEventFactory {
    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.achievement.GrantAchievementEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param achievement The achievement
     * @param formatter The formatter
     * @param targetEntity The target entity
     * @param messageCancelled The message cancelled
     * @return A new target player grant achievement event
     */
    public static GrantAchievementEvent.TargetPlayer createGrantAchievementEventTargetPlayer(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, Achievement achievement, MessageEvent.MessageFormatter formatter, Player targetEntity, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("achievement", achievement);
        values.put("formatter", formatter);
        values.put("targetEntity", targetEntity);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(GrantAchievementEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.CollideEvent.Impact}.
     * 
     * @param cause The cause
     * @param impactPoint The impact point
     * @return A new impact collide event
     */
    public static CollideEvent.Impact createCollideEventImpact(Cause cause, Location<World> impactPoint) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("impactPoint", impactPoint);
        return SpongeEventFactoryUtils.createEventImpl(CollideEvent.Impact.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.FishingEvent.HookEntity}.
     * 
     * @param cause The cause
     * @param originalFishHook The original fish hook
     * @param fishHook The fish hook
     * @param targetEntity The target entity
     * @return A new hook entity fishing event
     */
    public static FishingEvent.HookEntity createFishingEventHookEntity(Cause cause, EntitySnapshot originalFishHook, FishHook fishHook, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalFishHook", originalFishHook);
        values.put("fishHook", fishHook);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(FishingEvent.HookEntity.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.FishingEvent.Start}.
     * 
     * @param cause The cause
     * @param originalFishHook The original fish hook
     * @param fishHook The fish hook
     * @return A new start fishing event
     */
    public static FishingEvent.Start createFishingEventStart(Cause cause, EntitySnapshot originalFishHook, FishHook fishHook) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalFishHook", originalFishHook);
        values.put("fishHook", fishHook);
        return SpongeEventFactoryUtils.createEventImpl(FishingEvent.Start.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.FishingEvent.Stop}.
     * 
     * @param cause The cause
     * @param originalExperience The original experience
     * @param experience The experience
     * @param originalFishHook The original fish hook
     * @param fishHook The fish hook
     * @param itemStackTransaction The item stack transaction
     * @param targetEntity The target entity
     * @return A new stop fishing event
     */
    public static FishingEvent.Stop createFishingEventStop(Cause cause, int originalExperience, int experience, EntitySnapshot originalFishHook, FishHook fishHook, List<Transaction<ItemStackSnapshot>> itemStackTransaction, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalExperience", originalExperience);
        values.put("experience", experience);
        values.put("originalFishHook", originalFishHook);
        values.put("fishHook", fishHook);
        values.put("itemStackTransaction", itemStackTransaction);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(FishingEvent.Stop.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.InteractEvent}.
     * 
     * @param cause The cause
     * @param interactionPoint The interaction point
     * @return A new interact event
     */
    public static InteractEvent createInteractEvent(Cause cause, Optional<Vector3d> interactionPoint) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("interactionPoint", interactionPoint);
        return SpongeEventFactoryUtils.createEventImpl(InteractEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.LightningEvent.Post}.
     * 
     * @param cause The cause
     * @return A new post lightning event
     */
    public static LightningEvent.Post createLightningEventPost(Cause cause) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(LightningEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.LightningEvent.Pre}.
     * 
     * @param cause The cause
     * @return A new pre lightning event
     */
    public static LightningEvent.Pre createLightningEventPre(Cause cause) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(LightningEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.LightningEvent.Strike}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new strike lightning event
     */
    public static LightningEvent.Strike createLightningEventStrike(Cause cause, List<Entity> entities, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(LightningEvent.Strike.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.SleepingEvent.Finish}.
     * 
     * @param cause The cause
     * @param bed The bed
     * @param targetEntity The target entity
     * @return A new finish sleeping event
     */
    public static SleepingEvent.Finish createSleepingEventFinish(Cause cause, BlockSnapshot bed, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("bed", bed);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(SleepingEvent.Finish.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.SleepingEvent.Post}.
     * 
     * @param cause The cause
     * @param bed The bed
     * @param spawnTransform The spawn transform
     * @param targetEntity The target entity
     * @param spawnSet The spawn set
     * @return A new post sleeping event
     */
    public static SleepingEvent.Post createSleepingEventPost(Cause cause, BlockSnapshot bed, Optional<Transform<World>> spawnTransform, Entity targetEntity, boolean spawnSet) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("bed", bed);
        values.put("spawnTransform", spawnTransform);
        values.put("targetEntity", targetEntity);
        values.put("spawnSet", spawnSet);
        return SpongeEventFactoryUtils.createEventImpl(SleepingEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.SleepingEvent.Pre}.
     * 
     * @param cause The cause
     * @param bed The bed
     * @param targetEntity The target entity
     * @return A new pre sleeping event
     */
    public static SleepingEvent.Pre createSleepingEventPre(Cause cause, BlockSnapshot bed, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("bed", bed);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(SleepingEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.action.SleepingEvent.Tick}.
     * 
     * @param cause The cause
     * @param bed The bed
     * @param targetEntity The target entity
     * @return A new tick sleeping event
     */
    public static SleepingEvent.Tick createSleepingEventTick(Cause cause, BlockSnapshot bed, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("bed", bed);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(SleepingEvent.Tick.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.ChangeBlockEvent.Break}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new break change block event
     */
    public static ChangeBlockEvent.Break createChangeBlockEventBreak(Cause cause, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockEvent.Break.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.ChangeBlockEvent.Decay}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new decay change block event
     */
    public static ChangeBlockEvent.Decay createChangeBlockEventDecay(Cause cause, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockEvent.Decay.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.ChangeBlockEvent.Grow}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new grow change block event
     */
    public static ChangeBlockEvent.Grow createChangeBlockEventGrow(Cause cause, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockEvent.Grow.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.ChangeBlockEvent.Modify}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new modify change block event
     */
    public static ChangeBlockEvent.Modify createChangeBlockEventModify(Cause cause, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockEvent.Modify.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.ChangeBlockEvent.Place}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new place change block event
     */
    public static ChangeBlockEvent.Place createChangeBlockEventPlace(Cause cause, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockEvent.Place.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.ChangeBlockEvent.Post}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new post change block event
     */
    public static ChangeBlockEvent.Post createChangeBlockEventPost(Cause cause, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.ChangeBlockEvent.Pre}.
     * 
     * @param cause The cause
     * @param locations The locations
     * @param targetWorld The target world
     * @return A new pre change block event
     */
    public static ChangeBlockEvent.Pre createChangeBlockEventPre(Cause cause, List<Location<World>> locations, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("locations", locations);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.CollideBlockEvent}.
     * 
     * @param cause The cause
     * @param targetBlock The target block
     * @param targetLocation The target location
     * @param targetSide The target side
     * @return A new collide block event
     */
    public static CollideBlockEvent createCollideBlockEvent(Cause cause, BlockState targetBlock, Location<World> targetLocation, Direction targetSide) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetBlock", targetBlock);
        values.put("targetLocation", targetLocation);
        values.put("targetSide", targetSide);
        return SpongeEventFactoryUtils.createEventImpl(CollideBlockEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.CollideBlockEvent.Impact}.
     * 
     * @param cause The cause
     * @param impactPoint The impact point
     * @param targetBlock The target block
     * @param targetLocation The target location
     * @param targetSide The target side
     * @return A new impact collide block event
     */
    public static CollideBlockEvent.Impact createCollideBlockEventImpact(Cause cause, Location<World> impactPoint, BlockState targetBlock, Location<World> targetLocation, Direction targetSide) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("impactPoint", impactPoint);
        values.put("targetBlock", targetBlock);
        values.put("targetLocation", targetLocation);
        values.put("targetSide", targetSide);
        return SpongeEventFactoryUtils.createEventImpl(CollideBlockEvent.Impact.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.InteractBlockEvent.Primary.MainHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetBlock The target block
     * @param targetSide The target side
     * @return A new main hand primary interact block event
     */
    public static InteractBlockEvent.Primary.MainHand createInteractBlockEventPrimaryMainHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, BlockSnapshot targetBlock, Direction targetSide) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetBlock", targetBlock);
        values.put("targetSide", targetSide);
        return SpongeEventFactoryUtils.createEventImpl(InteractBlockEvent.Primary.MainHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.InteractBlockEvent.Primary.OffHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetBlock The target block
     * @param targetSide The target side
     * @return A new off hand primary interact block event
     */
    public static InteractBlockEvent.Primary.OffHand createInteractBlockEventPrimaryOffHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, BlockSnapshot targetBlock, Direction targetSide) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetBlock", targetBlock);
        values.put("targetSide", targetSide);
        return SpongeEventFactoryUtils.createEventImpl(InteractBlockEvent.Primary.OffHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.InteractBlockEvent.Secondary.MainHand}.
     * 
     * @param cause The cause
     * @param originalUseBlockResult The original use block result
     * @param useBlockResult The use block result
     * @param originalUseItemResult The original use item result
     * @param useItemResult The use item result
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetBlock The target block
     * @param targetSide The target side
     * @return A new main hand secondary interact block event
     */
    public static InteractBlockEvent.Secondary.MainHand createInteractBlockEventSecondaryMainHand(Cause cause, Tristate originalUseBlockResult, Tristate useBlockResult, Tristate originalUseItemResult, Tristate useItemResult, HandType handType, Optional<Vector3d> interactionPoint, BlockSnapshot targetBlock, Direction targetSide) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalUseBlockResult", originalUseBlockResult);
        values.put("useBlockResult", useBlockResult);
        values.put("originalUseItemResult", originalUseItemResult);
        values.put("useItemResult", useItemResult);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetBlock", targetBlock);
        values.put("targetSide", targetSide);
        return SpongeEventFactoryUtils.createEventImpl(InteractBlockEvent.Secondary.MainHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.InteractBlockEvent.Secondary.OffHand}.
     * 
     * @param cause The cause
     * @param originalUseBlockResult The original use block result
     * @param useBlockResult The use block result
     * @param originalUseItemResult The original use item result
     * @param useItemResult The use item result
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetBlock The target block
     * @param targetSide The target side
     * @return A new off hand secondary interact block event
     */
    public static InteractBlockEvent.Secondary.OffHand createInteractBlockEventSecondaryOffHand(Cause cause, Tristate originalUseBlockResult, Tristate useBlockResult, Tristate originalUseItemResult, Tristate useItemResult, HandType handType, Optional<Vector3d> interactionPoint, BlockSnapshot targetBlock, Direction targetSide) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalUseBlockResult", originalUseBlockResult);
        values.put("useBlockResult", useBlockResult);
        values.put("originalUseItemResult", originalUseItemResult);
        values.put("useItemResult", useItemResult);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetBlock", targetBlock);
        values.put("targetSide", targetSide);
        return SpongeEventFactoryUtils.createEventImpl(InteractBlockEvent.Secondary.OffHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.NotifyNeighborBlockEvent}.
     * 
     * @param cause The cause
     * @param originalNeighbors The original neighbors
     * @param neighbors The neighbors
     * @return A new notify neighbor block event
     */
    public static NotifyNeighborBlockEvent createNotifyNeighborBlockEvent(Cause cause, Map<Direction, BlockState> originalNeighbors, Map<Direction, BlockState> neighbors) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalNeighbors", originalNeighbors);
        values.put("neighbors", neighbors);
        return SpongeEventFactoryUtils.createEventImpl(NotifyNeighborBlockEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.TargetBlockEvent}.
     * 
     * @param cause The cause
     * @param targetBlock The target block
     * @return A new target block event
     */
    public static TargetBlockEvent createTargetBlockEvent(Cause cause, BlockSnapshot targetBlock) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetBlock", targetBlock);
        return SpongeEventFactoryUtils.createEventImpl(TargetBlockEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.TickBlockEvent.Random}.
     * 
     * @param cause The cause
     * @param targetBlock The target block
     * @return A new random tick block event
     */
    public static TickBlockEvent.Random createTickBlockEventRandom(Cause cause, BlockSnapshot targetBlock) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetBlock", targetBlock);
        return SpongeEventFactoryUtils.createEventImpl(TickBlockEvent.Random.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.TickBlockEvent.Scheduled}.
     * 
     * @param cause The cause
     * @param targetBlock The target block
     * @return A new scheduled tick block event
     */
    public static TickBlockEvent.Scheduled createTickBlockEventScheduled(Cause cause, BlockSnapshot targetBlock) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetBlock", targetBlock);
        return SpongeEventFactoryUtils.createEventImpl(TickBlockEvent.Scheduled.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.BrewingEvent.Finish}.
     * 
     * @param cause The cause
     * @param brewedItemStacks The brewed item stacks
     * @param ingredient The ingredient
     * @param targetTile The target tile
     * @return A new finish brewing event
     */
    public static BrewingEvent.Finish createBrewingEventFinish(Cause cause, List<ItemStackSnapshot> brewedItemStacks, ItemStackSnapshot ingredient, BrewingStand targetTile) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("brewedItemStacks", brewedItemStacks);
        values.put("ingredient", ingredient);
        values.put("targetTile", targetTile);
        return SpongeEventFactoryUtils.createEventImpl(BrewingEvent.Finish.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.BrewingEvent.Interrupt}.
     * 
     * @param cause The cause
     * @param brewedItemStacks The brewed item stacks
     * @param ingredient The ingredient
     * @param targetTile The target tile
     * @return A new interrupt brewing event
     */
    public static BrewingEvent.Interrupt createBrewingEventInterrupt(Cause cause, List<ItemStackSnapshot> brewedItemStacks, ItemStackSnapshot ingredient, BrewingStand targetTile) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("brewedItemStacks", brewedItemStacks);
        values.put("ingredient", ingredient);
        values.put("targetTile", targetTile);
        return SpongeEventFactoryUtils.createEventImpl(BrewingEvent.Interrupt.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.BrewingEvent.Start}.
     * 
     * @param cause The cause
     * @param ingredient The ingredient
     * @param targetTile The target tile
     * @param transactions The transactions
     * @return A new start brewing event
     */
    public static BrewingEvent.Start createBrewingEventStart(Cause cause, ItemStackSnapshot ingredient, BrewingStand targetTile, List<? extends Transaction<ItemStackSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ingredient", ingredient);
        values.put("targetTile", targetTile);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(BrewingEvent.Start.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.BrewingEvent.Tick}.
     * 
     * @param cause The cause
     * @param ingredient The ingredient
     * @param targetTile The target tile
     * @param transactions The transactions
     * @return A new tick brewing event
     */
    public static BrewingEvent.Tick createBrewingEventTick(Cause cause, ItemStackSnapshot ingredient, BrewingStand targetTile, List<? extends Transaction<ItemStackSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ingredient", ingredient);
        values.put("targetTile", targetTile);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(BrewingEvent.Tick.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.ChangeSignEvent}.
     * 
     * @param cause The cause
     * @param originalText The original text
     * @param text The text
     * @param targetTile The target tile
     * @return A new change sign event
     */
    public static ChangeSignEvent createChangeSignEvent(Cause cause, ImmutableSignData originalText, SignData text, Sign targetTile) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalText", originalText);
        values.put("text", text);
        values.put("targetTile", targetTile);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSignEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.SmeltEvent.ConsumeFuel}.
     * 
     * @param cause The cause
     * @param fuel The fuel
     * @param targetTile The target tile
     * @param transactions The transactions
     * @return A new consume fuel smelt event
     */
    public static SmeltEvent.ConsumeFuel createSmeltEventConsumeFuel(Cause cause, ItemStackSnapshot fuel, Furnace targetTile, List<? extends Transaction<ItemStackSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fuel", fuel);
        values.put("targetTile", targetTile);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(SmeltEvent.ConsumeFuel.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.SmeltEvent.Finish}.
     * 
     * @param cause The cause
     * @param fuel The fuel
     * @param smeltedItems The smelted items
     * @param targetTile The target tile
     * @return A new finish smelt event
     */
    public static SmeltEvent.Finish createSmeltEventFinish(Cause cause, ItemStackSnapshot fuel, List<ItemStackSnapshot> smeltedItems, Furnace targetTile) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fuel", fuel);
        values.put("smeltedItems", smeltedItems);
        values.put("targetTile", targetTile);
        return SpongeEventFactoryUtils.createEventImpl(SmeltEvent.Finish.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.SmeltEvent.Interrupt}.
     * 
     * @param cause The cause
     * @param fuel The fuel
     * @param smeltedItems The smelted items
     * @param targetTile The target tile
     * @return A new interrupt smelt event
     */
    public static SmeltEvent.Interrupt createSmeltEventInterrupt(Cause cause, ItemStackSnapshot fuel, List<ItemStackSnapshot> smeltedItems, Furnace targetTile) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fuel", fuel);
        values.put("smeltedItems", smeltedItems);
        values.put("targetTile", targetTile);
        return SpongeEventFactoryUtils.createEventImpl(SmeltEvent.Interrupt.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.SmeltEvent.Start}.
     * 
     * @param cause The cause
     * @param fuel The fuel
     * @param targetTile The target tile
     * @param transactions The transactions
     * @return A new start smelt event
     */
    public static SmeltEvent.Start createSmeltEventStart(Cause cause, ItemStackSnapshot fuel, Furnace targetTile, List<? extends Transaction<ItemStackSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fuel", fuel);
        values.put("targetTile", targetTile);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(SmeltEvent.Start.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.SmeltEvent.Tick}.
     * 
     * @param cause The cause
     * @param fuel The fuel
     * @param targetTile The target tile
     * @param transactions The transactions
     * @return A new tick smelt event
     */
    public static SmeltEvent.Tick createSmeltEventTick(Cause cause, ItemStackSnapshot fuel, Furnace targetTile, List<? extends Transaction<ItemStackSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fuel", fuel);
        values.put("targetTile", targetTile);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(SmeltEvent.Tick.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.block.tileentity.TargetTileEntityEvent}.
     * 
     * @param cause The cause
     * @param targetTile The target tile
     * @return A new target tile entity event
     */
    public static TargetTileEntityEvent createTargetTileEntityEvent(Cause cause, TileEntity targetTile) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetTile", targetTile);
        return SpongeEventFactoryUtils.createEventImpl(TargetTileEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.command.SendCommandEvent}.
     * 
     * @param cause The cause
     * @param arguments The arguments
     * @param command The command
     * @param result The result
     * @return A new send command event
     */
    public static SendCommandEvent createSendCommandEvent(Cause cause, String arguments, String command, CommandResult result) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("arguments", arguments);
        values.put("command", command);
        values.put("result", result);
        return SpongeEventFactoryUtils.createEventImpl(SendCommandEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.command.TabCompleteEvent.Chat}.
     * 
     * @param cause The cause
     * @param originalTabCompletions The original tab completions
     * @param tabCompletions The tab completions
     * @param rawMessage The raw message
     * @param targetPos The target pos
     * @param usingBlock The using block
     * @return A new chat tab complete event
     */
    public static TabCompleteEvent.Chat createTabCompleteEventChat(Cause cause, List<String> originalTabCompletions, List<String> tabCompletions, String rawMessage, Optional<Location<World>> targetPos, boolean usingBlock) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalTabCompletions", originalTabCompletions);
        values.put("tabCompletions", tabCompletions);
        values.put("rawMessage", rawMessage);
        values.put("targetPos", targetPos);
        values.put("usingBlock", usingBlock);
        return SpongeEventFactoryUtils.createEventImpl(TabCompleteEvent.Chat.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.command.TabCompleteEvent.Command}.
     * 
     * @param cause The cause
     * @param originalTabCompletions The original tab completions
     * @param tabCompletions The tab completions
     * @param arguments The arguments
     * @param command The command
     * @param rawMessage The raw message
     * @param targetPos The target pos
     * @param usingBlock The using block
     * @return A new command tab complete event
     */
    public static TabCompleteEvent.Command createTabCompleteEventCommand(Cause cause, List<String> originalTabCompletions, List<String> tabCompletions, String arguments, String command, String rawMessage, Optional<Location<World>> targetPos, boolean usingBlock) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalTabCompletions", originalTabCompletions);
        values.put("tabCompletions", tabCompletions);
        values.put("arguments", arguments);
        values.put("command", command);
        values.put("rawMessage", rawMessage);
        values.put("targetPos", targetPos);
        values.put("usingBlock", usingBlock);
        return SpongeEventFactoryUtils.createEventImpl(TabCompleteEvent.Command.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeColoredData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change colored data
     */
    public static ChangeColoredData createChangeColoredData(Cause cause, Optional<ImmutableColoredData> modifiedData, Optional<ImmutableColoredData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeColoredData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeCommandData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change command data
     */
    public static ChangeCommandData createChangeCommandData(Cause cause, Optional<ImmutableCommandData> modifiedData, Optional<ImmutableCommandData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeCommandData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeDisplayNameData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change display name data
     */
    public static ChangeDisplayNameData createChangeDisplayNameData(Cause cause, Optional<ImmutableDisplayNameData> modifiedData, Optional<ImmutableDisplayNameData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDisplayNameData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeDyeableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change dyeable data
     */
    public static ChangeDyeableData createChangeDyeableData(Cause cause, Optional<ImmutableDyeableData> modifiedData, Optional<ImmutableDyeableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDyeableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeFireworkEffectData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change firework effect data
     */
    public static ChangeFireworkEffectData createChangeFireworkEffectData(Cause cause, Optional<ImmutableFireworkEffectData> modifiedData, Optional<ImmutableFireworkEffectData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFireworkEffectData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeFireworkRocketData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change firework rocket data
     */
    public static ChangeFireworkRocketData createChangeFireworkRocketData(Cause cause, Optional<ImmutableFireworkRocketData> modifiedData, Optional<ImmutableFireworkRocketData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFireworkRocketData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeMobSpawnerData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change mob spawner data
     */
    public static ChangeMobSpawnerData createChangeMobSpawnerData(Cause cause, Optional<ImmutableMobSpawnerData> modifiedData, Optional<ImmutableMobSpawnerData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeMobSpawnerData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangePotionEffectData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change potion effect data
     */
    public static ChangePotionEffectData createChangePotionEffectData(Cause cause, Optional<ImmutablePotionEffectData> modifiedData, Optional<ImmutablePotionEffectData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePotionEffectData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeRepresentedItemData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change represented item data
     */
    public static ChangeRepresentedItemData createChangeRepresentedItemData(Cause cause, Optional<ImmutableRepresentedItemData> modifiedData, Optional<ImmutableRepresentedItemData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeRepresentedItemData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeRepresentedPlayerData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change represented player data
     */
    public static ChangeRepresentedPlayerData createChangeRepresentedPlayerData(Cause cause, Optional<ImmutableRepresentedPlayerData> modifiedData, Optional<ImmutableRepresentedPlayerData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeRepresentedPlayerData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeRotationalData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change rotational data
     */
    public static ChangeRotationalData createChangeRotationalData(Cause cause, Optional<ImmutableRotationalData> modifiedData, Optional<ImmutableRotationalData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeRotationalData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeSkullData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change skull data
     */
    public static ChangeSkullData createChangeSkullData(Cause cause, Optional<ImmutableSkullData> modifiedData, Optional<ImmutableSkullData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSkullData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeTargetedLocationData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change targeted location data
     */
    public static ChangeTargetedLocationData createChangeTargetedLocationData(Cause cause, Optional<ImmutableTargetedLocationData> modifiedData, Optional<ImmutableTargetedLocationData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeTargetedLocationData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.ChangeWetData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change wet data
     */
    public static ChangeWetData createChangeWetData(Cause cause, Optional<ImmutableWetData> modifiedData, Optional<ImmutableWetData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeWetData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeAttachedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change attached data
     */
    public static ChangeAttachedData createChangeAttachedData(Cause cause, Optional<ImmutableAttachedData> modifiedData, Optional<ImmutableAttachedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAttachedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeAxisData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change axis data
     */
    public static ChangeAxisData createChangeAxisData(Cause cause, Optional<ImmutableAxisData> modifiedData, Optional<ImmutableAxisData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAxisData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeBigMushroomData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change big mushroom data
     */
    public static ChangeBigMushroomData createChangeBigMushroomData(Cause cause, Optional<ImmutableBigMushroomData> modifiedData, Optional<ImmutableBigMushroomData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBigMushroomData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeBrickData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change brick data
     */
    public static ChangeBrickData createChangeBrickData(Cause cause, Optional<ImmutableBrickData> modifiedData, Optional<ImmutableBrickData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBrickData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeComparatorData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change comparator data
     */
    public static ChangeComparatorData createChangeComparatorData(Cause cause, Optional<ImmutableComparatorData> modifiedData, Optional<ImmutableComparatorData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeComparatorData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeConnectedDirectionData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change connected direction data
     */
    public static ChangeConnectedDirectionData createChangeConnectedDirectionData(Cause cause, Optional<ImmutableConnectedDirectionData> modifiedData, Optional<ImmutableConnectedDirectionData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeConnectedDirectionData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDecayableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change decayable data
     */
    public static ChangeDecayableData createChangeDecayableData(Cause cause, Optional<ImmutableDecayableData> modifiedData, Optional<ImmutableDecayableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDecayableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDelayableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change delayable data
     */
    public static ChangeDelayableData createChangeDelayableData(Cause cause, Optional<ImmutableDelayableData> modifiedData, Optional<ImmutableDelayableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDelayableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDirectionalData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change directional data
     */
    public static ChangeDirectionalData createChangeDirectionalData(Cause cause, Optional<ImmutableDirectionalData> modifiedData, Optional<ImmutableDirectionalData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDirectionalData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDirtData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change dirt data
     */
    public static ChangeDirtData createChangeDirtData(Cause cause, Optional<ImmutableDirtData> modifiedData, Optional<ImmutableDirtData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDirtData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDisarmedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change disarmed data
     */
    public static ChangeDisarmedData createChangeDisarmedData(Cause cause, Optional<ImmutableDisarmedData> modifiedData, Optional<ImmutableDisarmedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDisarmedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDisguisedBlockData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change disguised block data
     */
    public static ChangeDisguisedBlockData createChangeDisguisedBlockData(Cause cause, Optional<ImmutableDisguisedBlockData> modifiedData, Optional<ImmutableDisguisedBlockData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDisguisedBlockData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDoublePlantData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change double plant data
     */
    public static ChangeDoublePlantData createChangeDoublePlantData(Cause cause, Optional<ImmutableDoublePlantData> modifiedData, Optional<ImmutableDoublePlantData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDoublePlantData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeDropData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change drop data
     */
    public static ChangeDropData createChangeDropData(Cause cause, Optional<ImmutableDropData> modifiedData, Optional<ImmutableDropData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDropData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeExtendedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change extended data
     */
    public static ChangeExtendedData createChangeExtendedData(Cause cause, Optional<ImmutableExtendedData> modifiedData, Optional<ImmutableExtendedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeExtendedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeFilledData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change filled data
     */
    public static ChangeFilledData createChangeFilledData(Cause cause, Optional<ImmutableFilledData> modifiedData, Optional<ImmutableFilledData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFilledData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeFluidLevelData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change fluid level data
     */
    public static ChangeFluidLevelData createChangeFluidLevelData(Cause cause, Optional<ImmutableFluidLevelData> modifiedData, Optional<ImmutableFluidLevelData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFluidLevelData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeGrowthData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change growth data
     */
    public static ChangeGrowthData createChangeGrowthData(Cause cause, Optional<ImmutableGrowthData> modifiedData, Optional<ImmutableGrowthData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGrowthData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeHingeData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change hinge data
     */
    public static ChangeHingeData createChangeHingeData(Cause cause, Optional<ImmutableHingeData> modifiedData, Optional<ImmutableHingeData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeHingeData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeInWallData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change in wall data
     */
    public static ChangeInWallData createChangeInWallData(Cause cause, Optional<ImmutableInWallData> modifiedData, Optional<ImmutableInWallData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInWallData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeLayeredData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change layered data
     */
    public static ChangeLayeredData createChangeLayeredData(Cause cause, Optional<ImmutableLayeredData> modifiedData, Optional<ImmutableLayeredData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeLayeredData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeLogAxisData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change log axis data
     */
    public static ChangeLogAxisData createChangeLogAxisData(Cause cause, Optional<ImmutableLogAxisData> modifiedData, Optional<ImmutableLogAxisData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeLogAxisData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeMoistureData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change moisture data
     */
    public static ChangeMoistureData createChangeMoistureData(Cause cause, Optional<ImmutableMoistureData> modifiedData, Optional<ImmutableMoistureData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeMoistureData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeOccupiedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change occupied data
     */
    public static ChangeOccupiedData createChangeOccupiedData(Cause cause, Optional<ImmutableOccupiedData> modifiedData, Optional<ImmutableOccupiedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeOccupiedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeOpenData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change open data
     */
    public static ChangeOpenData createChangeOpenData(Cause cause, Optional<ImmutableOpenData> modifiedData, Optional<ImmutableOpenData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeOpenData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangePistonData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change piston data
     */
    public static ChangePistonData createChangePistonData(Cause cause, Optional<ImmutablePistonData> modifiedData, Optional<ImmutablePistonData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePistonData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangePlantData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change plant data
     */
    public static ChangePlantData createChangePlantData(Cause cause, Optional<ImmutablePlantData> modifiedData, Optional<ImmutablePlantData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePlantData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangePortionData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change portion data
     */
    public static ChangePortionData createChangePortionData(Cause cause, Optional<ImmutablePortionData> modifiedData, Optional<ImmutablePortionData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePortionData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangePoweredData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change powered data
     */
    public static ChangePoweredData createChangePoweredData(Cause cause, Optional<ImmutablePoweredData> modifiedData, Optional<ImmutablePoweredData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePoweredData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangePrismarineData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change prismarine data
     */
    public static ChangePrismarineData createChangePrismarineData(Cause cause, Optional<ImmutablePrismarineData> modifiedData, Optional<ImmutablePrismarineData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePrismarineData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeQuartzData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change quartz data
     */
    public static ChangeQuartzData createChangeQuartzData(Cause cause, Optional<ImmutableQuartzData> modifiedData, Optional<ImmutableQuartzData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeQuartzData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeRailDirectionData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change rail direction data
     */
    public static ChangeRailDirectionData createChangeRailDirectionData(Cause cause, Optional<ImmutableRailDirectionData> modifiedData, Optional<ImmutableRailDirectionData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeRailDirectionData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeRedstonePoweredData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change redstone powered data
     */
    public static ChangeRedstonePoweredData createChangeRedstonePoweredData(Cause cause, Optional<ImmutableRedstonePoweredData> modifiedData, Optional<ImmutableRedstonePoweredData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeRedstonePoweredData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeSandData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sand data
     */
    public static ChangeSandData createChangeSandData(Cause cause, Optional<ImmutableSandData> modifiedData, Optional<ImmutableSandData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSandData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeSandstoneData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sandstone data
     */
    public static ChangeSandstoneData createChangeSandstoneData(Cause cause, Optional<ImmutableSandstoneData> modifiedData, Optional<ImmutableSandstoneData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSandstoneData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeSeamlessData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change seamless data
     */
    public static ChangeSeamlessData createChangeSeamlessData(Cause cause, Optional<ImmutableSeamlessData> modifiedData, Optional<ImmutableSeamlessData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSeamlessData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeShrubData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change shrub data
     */
    public static ChangeShrubData createChangeShrubData(Cause cause, Optional<ImmutableShrubData> modifiedData, Optional<ImmutableShrubData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeShrubData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeSlabData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change slab data
     */
    public static ChangeSlabData createChangeSlabData(Cause cause, Optional<ImmutableSlabData> modifiedData, Optional<ImmutableSlabData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSlabData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeSnowedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change snowed data
     */
    public static ChangeSnowedData createChangeSnowedData(Cause cause, Optional<ImmutableSnowedData> modifiedData, Optional<ImmutableSnowedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSnowedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeStairShapeData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change stair shape data
     */
    public static ChangeStairShapeData createChangeStairShapeData(Cause cause, Optional<ImmutableStairShapeData> modifiedData, Optional<ImmutableStairShapeData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeStairShapeData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeStoneData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change stone data
     */
    public static ChangeStoneData createChangeStoneData(Cause cause, Optional<ImmutableStoneData> modifiedData, Optional<ImmutableStoneData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeStoneData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeTreeData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change tree data
     */
    public static ChangeTreeData createChangeTreeData(Cause cause, Optional<ImmutableTreeData> modifiedData, Optional<ImmutableTreeData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeTreeData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeWallData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change wall data
     */
    public static ChangeWallData createChangeWallData(Cause cause, Optional<ImmutableWallData> modifiedData, Optional<ImmutableWallData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeWallData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.block.ChangeWireAttachmentData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change wire attachment data
     */
    public static ChangeWireAttachmentData createChangeWireAttachmentData(Cause cause, Optional<ImmutableWireAttachmentData> modifiedData, Optional<ImmutableWireAttachmentData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeWireAttachmentData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeAchievementData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change achievement data
     */
    public static ChangeAchievementData createChangeAchievementData(Cause cause, Optional<ImmutableAchievementData> modifiedData, Optional<ImmutableAchievementData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAchievementData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeAffectsSpawningData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change affects spawning data
     */
    public static ChangeAffectsSpawningData createChangeAffectsSpawningData(Cause cause, Optional<ImmutableAffectsSpawningData> modifiedData, Optional<ImmutableAffectsSpawningData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAffectsSpawningData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeAgeableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change ageable data
     */
    public static ChangeAgeableData createChangeAgeableData(Cause cause, Optional<ImmutableAgeableData> modifiedData, Optional<ImmutableAgeableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAgeableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeAgentData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change agent data
     */
    public static ChangeAgentData createChangeAgentData(Cause cause, Optional<ImmutableAgentData> modifiedData, Optional<ImmutableAgentData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAgentData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeAggressiveData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change aggressive data
     */
    public static ChangeAggressiveData createChangeAggressiveData(Cause cause, Optional<ImmutableAggressiveData> modifiedData, Optional<ImmutableAggressiveData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAggressiveData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeAngerableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change angerable data
     */
    public static ChangeAngerableData createChangeAngerableData(Cause cause, Optional<ImmutableAngerableData> modifiedData, Optional<ImmutableAngerableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAngerableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeArmorStandData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change armor stand data
     */
    public static ChangeArmorStandData createChangeArmorStandData(Cause cause, Optional<ImmutableArmorStandData> modifiedData, Optional<ImmutableArmorStandData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeArmorStandData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeArtData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change art data
     */
    public static ChangeArtData createChangeArtData(Cause cause, Optional<ImmutableArtData> modifiedData, Optional<ImmutableArtData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeArtData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeBodyPartRotationalData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change body part rotational data
     */
    public static ChangeBodyPartRotationalData createChangeBodyPartRotationalData(Cause cause, Optional<ImmutableBodyPartRotationalData> modifiedData, Optional<ImmutableBodyPartRotationalData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBodyPartRotationalData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeBreathingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change breathing data
     */
    public static ChangeBreathingData createChangeBreathingData(Cause cause, Optional<ImmutableBreathingData> modifiedData, Optional<ImmutableBreathingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBreathingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeBreedableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change breedable data
     */
    public static ChangeBreedableData createChangeBreedableData(Cause cause, Optional<ImmutableBreedableData> modifiedData, Optional<ImmutableBreedableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBreedableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeCareerData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change career data
     */
    public static ChangeCareerData createChangeCareerData(Cause cause, Optional<ImmutableCareerData> modifiedData, Optional<ImmutableCareerData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeCareerData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeChargedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change charged data
     */
    public static ChangeChargedData createChangeChargedData(Cause cause, Optional<ImmutableChargedData> modifiedData, Optional<ImmutableChargedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeChargedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeCriticalHitData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change critical hit data
     */
    public static ChangeCriticalHitData createChangeCriticalHitData(Cause cause, Optional<ImmutableCriticalHitData> modifiedData, Optional<ImmutableCriticalHitData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeCriticalHitData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeCustomNameVisibleData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change custom name visible data
     */
    public static ChangeCustomNameVisibleData createChangeCustomNameVisibleData(Cause cause, Optional<ImmutableCustomNameVisibleData> modifiedData, Optional<ImmutableCustomNameVisibleData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeCustomNameVisibleData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeDamageableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change damageable data
     */
    public static ChangeDamageableData createChangeDamageableData(Cause cause, Optional<ImmutableDamageableData> modifiedData, Optional<ImmutableDamageableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDamageableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeDamagingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change damaging data
     */
    public static ChangeDamagingData createChangeDamagingData(Cause cause, Optional<ImmutableDamagingData> modifiedData, Optional<ImmutableDamagingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDamagingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeDespawnDelayData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change despawn delay data
     */
    public static ChangeDespawnDelayData createChangeDespawnDelayData(Cause cause, Optional<ImmutableDespawnDelayData> modifiedData, Optional<ImmutableDespawnDelayData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDespawnDelayData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeDominantHandData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change dominant hand data
     */
    public static ChangeDominantHandData createChangeDominantHandData(Cause cause, Optional<ImmutableDominantHandData> modifiedData, Optional<ImmutableDominantHandData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDominantHandData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeElderData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change elder data
     */
    public static ChangeElderData createChangeElderData(Cause cause, Optional<ImmutableElderData> modifiedData, Optional<ImmutableElderData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeElderData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeExpOrbData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change exp orb data
     */
    public static ChangeExpOrbData createChangeExpOrbData(Cause cause, Optional<ImmutableExpOrbData> modifiedData, Optional<ImmutableExpOrbData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeExpOrbData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeExperienceHolderData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change experience holder data
     */
    public static ChangeExperienceHolderData createChangeExperienceHolderData(Cause cause, Optional<ImmutableExperienceHolderData> modifiedData, Optional<ImmutableExperienceHolderData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeExperienceHolderData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeExpirableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change expirable data
     */
    public static ChangeExpirableData createChangeExpirableData(Cause cause, Optional<ImmutableExpirableData> modifiedData, Optional<ImmutableExpirableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeExpirableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeExplosionRadiusData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change explosion radius data
     */
    public static ChangeExplosionRadiusData createChangeExplosionRadiusData(Cause cause, Optional<ImmutableExplosionRadiusData> modifiedData, Optional<ImmutableExplosionRadiusData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeExplosionRadiusData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeFallDistanceData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change fall distance data
     */
    public static ChangeFallDistanceData createChangeFallDistanceData(Cause cause, Optional<ImmutableFallDistanceData> modifiedData, Optional<ImmutableFallDistanceData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFallDistanceData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeFallingBlockData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change falling block data
     */
    public static ChangeFallingBlockData createChangeFallingBlockData(Cause cause, Optional<ImmutableFallingBlockData> modifiedData, Optional<ImmutableFallingBlockData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFallingBlockData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeFlammableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change flammable data
     */
    public static ChangeFlammableData createChangeFlammableData(Cause cause, Optional<ImmutableFlammableData> modifiedData, Optional<ImmutableFlammableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFlammableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeFlyingAbilityData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change flying ability data
     */
    public static ChangeFlyingAbilityData createChangeFlyingAbilityData(Cause cause, Optional<ImmutableFlyingAbilityData> modifiedData, Optional<ImmutableFlyingAbilityData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFlyingAbilityData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeFlyingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change flying data
     */
    public static ChangeFlyingData createChangeFlyingData(Cause cause, Optional<ImmutableFlyingData> modifiedData, Optional<ImmutableFlyingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFlyingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeFoodData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change food data
     */
    public static ChangeFoodData createChangeFoodData(Cause cause, Optional<ImmutableFoodData> modifiedData, Optional<ImmutableFoodData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFoodData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeFuseData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change fuse data
     */
    public static ChangeFuseData createChangeFuseData(Cause cause, Optional<ImmutableFuseData> modifiedData, Optional<ImmutableFuseData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFuseData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeGameModeData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change game mode data
     */
    public static ChangeGameModeData createChangeGameModeData(Cause cause, Optional<ImmutableGameModeData> modifiedData, Optional<ImmutableGameModeData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGameModeData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeGlowingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change glowing data
     */
    public static ChangeGlowingData createChangeGlowingData(Cause cause, Optional<ImmutableGlowingData> modifiedData, Optional<ImmutableGlowingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGlowingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeGravityData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change gravity data
     */
    public static ChangeGravityData createChangeGravityData(Cause cause, Optional<ImmutableGravityData> modifiedData, Optional<ImmutableGravityData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGravityData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeGriefingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change griefing data
     */
    public static ChangeGriefingData createChangeGriefingData(Cause cause, Optional<ImmutableGriefingData> modifiedData, Optional<ImmutableGriefingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGriefingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeHealthData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change health data
     */
    public static ChangeHealthData createChangeHealthData(Cause cause, Optional<ImmutableHealthData> modifiedData, Optional<ImmutableHealthData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeHealthData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeHealthScalingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change health scaling data
     */
    public static ChangeHealthScalingData createChangeHealthScalingData(Cause cause, Optional<ImmutableHealthScalingData> modifiedData, Optional<ImmutableHealthScalingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeHealthScalingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeHorseData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change horse data
     */
    public static ChangeHorseData createChangeHorseData(Cause cause, Optional<ImmutableHorseData> modifiedData, Optional<ImmutableHorseData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeHorseData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeIgniteableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change igniteable data
     */
    public static ChangeIgniteableData createChangeIgniteableData(Cause cause, Optional<ImmutableIgniteableData> modifiedData, Optional<ImmutableIgniteableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeIgniteableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeInvisibilityData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change invisibility data
     */
    public static ChangeInvisibilityData createChangeInvisibilityData(Cause cause, Optional<ImmutableInvisibilityData> modifiedData, Optional<ImmutableInvisibilityData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInvisibilityData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeInvulnerabilityData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change invulnerability data
     */
    public static ChangeInvulnerabilityData createChangeInvulnerabilityData(Cause cause, Optional<ImmutableInvulnerabilityData> modifiedData, Optional<ImmutableInvulnerabilityData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInvulnerabilityData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeJoinData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change join data
     */
    public static ChangeJoinData createChangeJoinData(Cause cause, Optional<ImmutableJoinData> modifiedData, Optional<ImmutableJoinData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeJoinData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeKnockbackData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change knockback data
     */
    public static ChangeKnockbackData createChangeKnockbackData(Cause cause, Optional<ImmutableKnockbackData> modifiedData, Optional<ImmutableKnockbackData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeKnockbackData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeLeashData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change leash data
     */
    public static ChangeLeashData createChangeLeashData(Cause cause, Optional<ImmutableLeashData> modifiedData, Optional<ImmutableLeashData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeLeashData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeMinecartBlockData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change minecart block data
     */
    public static ChangeMinecartBlockData createChangeMinecartBlockData(Cause cause, Optional<ImmutableMinecartBlockData> modifiedData, Optional<ImmutableMinecartBlockData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeMinecartBlockData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeMovementSpeedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change movement speed data
     */
    public static ChangeMovementSpeedData createChangeMovementSpeedData(Cause cause, Optional<ImmutableMovementSpeedData> modifiedData, Optional<ImmutableMovementSpeedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeMovementSpeedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeOcelotData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change ocelot data
     */
    public static ChangeOcelotData createChangeOcelotData(Cause cause, Optional<ImmutableOcelotData> modifiedData, Optional<ImmutableOcelotData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeOcelotData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangePassengerData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change passenger data
     */
    public static ChangePassengerData createChangePassengerData(Cause cause, Optional<ImmutablePassengerData> modifiedData, Optional<ImmutablePassengerData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePassengerData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangePersistingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change persisting data
     */
    public static ChangePersistingData createChangePersistingData(Cause cause, Optional<ImmutablePersistingData> modifiedData, Optional<ImmutablePersistingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePersistingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangePickupDelayData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change pickup delay data
     */
    public static ChangePickupDelayData createChangePickupDelayData(Cause cause, Optional<ImmutablePickupDelayData> modifiedData, Optional<ImmutablePickupDelayData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePickupDelayData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangePickupRuleData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change pickup rule data
     */
    public static ChangePickupRuleData createChangePickupRuleData(Cause cause, Optional<ImmutablePickupRuleData> modifiedData, Optional<ImmutablePickupRuleData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePickupRuleData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangePigSaddleData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change pig saddle data
     */
    public static ChangePigSaddleData createChangePigSaddleData(Cause cause, Optional<ImmutablePigSaddleData> modifiedData, Optional<ImmutablePigSaddleData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePigSaddleData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangePlayerCreatedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change player created data
     */
    public static ChangePlayerCreatedData createChangePlayerCreatedData(Cause cause, Optional<ImmutablePlayerCreatedData> modifiedData, Optional<ImmutablePlayerCreatedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePlayerCreatedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangePlayingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change playing data
     */
    public static ChangePlayingData createChangePlayingData(Cause cause, Optional<ImmutablePlayingData> modifiedData, Optional<ImmutablePlayingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePlayingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeRabbitData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change rabbit data
     */
    public static ChangeRabbitData createChangeRabbitData(Cause cause, Optional<ImmutableRabbitData> modifiedData, Optional<ImmutableRabbitData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeRabbitData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeRespawnLocationData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change respawn location data
     */
    public static ChangeRespawnLocationData createChangeRespawnLocationData(Cause cause, Optional<ImmutableRespawnLocation> modifiedData, Optional<ImmutableRespawnLocation> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeRespawnLocationData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeScreamingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change screaming data
     */
    public static ChangeScreamingData createChangeScreamingData(Cause cause, Optional<ImmutableScreamingData> modifiedData, Optional<ImmutableScreamingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeScreamingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeShatteringData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change shattering data
     */
    public static ChangeShatteringData createChangeShatteringData(Cause cause, Optional<ImmutableShatteringData> modifiedData, Optional<ImmutableShatteringData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeShatteringData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeShearedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sheared data
     */
    public static ChangeShearedData createChangeShearedData(Cause cause, Optional<ImmutableShearedData> modifiedData, Optional<ImmutableShearedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeShearedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSilentData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change silent data
     */
    public static ChangeSilentData createChangeSilentData(Cause cause, Optional<ImmutableSilentData> modifiedData, Optional<ImmutableSilentData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSilentData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSittingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sitting data
     */
    public static ChangeSittingData createChangeSittingData(Cause cause, Optional<ImmutableSittingData> modifiedData, Optional<ImmutableSittingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSittingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSizeData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change size data
     */
    public static ChangeSizeData createChangeSizeData(Cause cause, Optional<ImmutableSizeData> modifiedData, Optional<ImmutableSizeData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSizeData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSkeletonData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change skeleton data
     */
    public static ChangeSkeletonData createChangeSkeletonData(Cause cause, Optional<ImmutableSkeletonData> modifiedData, Optional<ImmutableSkeletonData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSkeletonData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSkinData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change skin data
     */
    public static ChangeSkinData createChangeSkinData(Cause cause, Optional<ImmutableSkinData> modifiedData, Optional<ImmutableSkinData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSkinData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSleepingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sleeping data
     */
    public static ChangeSleepingData createChangeSleepingData(Cause cause, Optional<ImmutableSleepingData> modifiedData, Optional<ImmutableSleepingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSleepingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSlimeData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change slime data
     */
    public static ChangeSlimeData createChangeSlimeData(Cause cause, Optional<ImmutableSlimeData> modifiedData, Optional<ImmutableSlimeData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSlimeData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSneakingData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sneaking data
     */
    public static ChangeSneakingData createChangeSneakingData(Cause cause, Optional<ImmutableSneakingData> modifiedData, Optional<ImmutableSneakingData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSneakingData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeSprintData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sprint data
     */
    public static ChangeSprintData createChangeSprintData(Cause cause, Optional<ImmutableSprintData> modifiedData, Optional<ImmutableSprintData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSprintData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeStatisticData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change statistic data
     */
    public static ChangeStatisticData createChangeStatisticData(Cause cause, Optional<ImmutableStatisticData> modifiedData, Optional<ImmutableStatisticData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeStatisticData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeStuckArrowsData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change stuck arrows data
     */
    public static ChangeStuckArrowsData createChangeStuckArrowsData(Cause cause, Optional<ImmutableStuckArrowsData> modifiedData, Optional<ImmutableStuckArrowsData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeStuckArrowsData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeTameableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change tameable data
     */
    public static ChangeTameableData createChangeTameableData(Cause cause, Optional<ImmutableTameableData> modifiedData, Optional<ImmutableTameableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeTameableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeTradeOfferData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change trade offer data
     */
    public static ChangeTradeOfferData createChangeTradeOfferData(Cause cause, Optional<ImmutableTradeOfferData> modifiedData, Optional<ImmutableTradeOfferData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeTradeOfferData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeVehicleData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change vehicle data
     */
    public static ChangeVehicleData createChangeVehicleData(Cause cause, Optional<ImmutableVehicleData> modifiedData, Optional<ImmutableVehicleData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeVehicleData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeVelocityData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change velocity data
     */
    public static ChangeVelocityData createChangeVelocityData(Cause cause, Optional<ImmutableVelocityData> modifiedData, Optional<ImmutableVelocityData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeVelocityData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.entity.ChangeZombieData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change zombie data
     */
    public static ChangeZombieData createChangeZombieData(Cause cause, Optional<ImmutableZombieData> modifiedData, Optional<ImmutableZombieData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeZombieData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeAuthorData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change author data
     */
    public static ChangeAuthorData createChangeAuthorData(Cause cause, Optional<ImmutableAuthorData> modifiedData, Optional<ImmutableAuthorData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeAuthorData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeBlockItemData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change block item data
     */
    public static ChangeBlockItemData createChangeBlockItemData(Cause cause, Optional<ImmutableBlockItemData> modifiedData, Optional<ImmutableBlockItemData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBlockItemData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeBreakableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change breakable data
     */
    public static ChangeBreakableData createChangeBreakableData(Cause cause, Optional<ImmutableBreakableData> modifiedData, Optional<ImmutableBreakableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBreakableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeCoalData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change coal data
     */
    public static ChangeCoalData createChangeCoalData(Cause cause, Optional<ImmutableCoalData> modifiedData, Optional<ImmutableCoalData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeCoalData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeCookedFishData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change cooked fish data
     */
    public static ChangeCookedFishData createChangeCookedFishData(Cause cause, Optional<ImmutableCookedFishData> modifiedData, Optional<ImmutableCookedFishData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeCookedFishData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeDurabilityData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change durability data
     */
    public static ChangeDurabilityData createChangeDurabilityData(Cause cause, Optional<ImmutableDurabilityData> modifiedData, Optional<ImmutableDurabilityData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeDurabilityData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeEnchantmentData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change enchantment data
     */
    public static ChangeEnchantmentData createChangeEnchantmentData(Cause cause, Optional<ImmutableEnchantmentData> modifiedData, Optional<ImmutableEnchantmentData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEnchantmentData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeFishData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change fish data
     */
    public static ChangeFishData createChangeFishData(Cause cause, Optional<ImmutableFishData> modifiedData, Optional<ImmutableFishData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFishData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeGenerationData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change generation data
     */
    public static ChangeGenerationData createChangeGenerationData(Cause cause, Optional<ImmutableGenerationData> modifiedData, Optional<ImmutableGenerationData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGenerationData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeGoldenAppleData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change golden apple data
     */
    public static ChangeGoldenAppleData createChangeGoldenAppleData(Cause cause, Optional<ImmutableGoldenAppleData> modifiedData, Optional<ImmutableGoldenAppleData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGoldenAppleData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeHideData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change hide data
     */
    public static ChangeHideData createChangeHideData(Cause cause, Optional<ImmutableHideData> modifiedData, Optional<ImmutableHideData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeHideData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeInventoryItemData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change inventory item data
     */
    public static ChangeInventoryItemData createChangeInventoryItemData(Cause cause, Optional<ImmutableInventoryItemData> modifiedData, Optional<ImmutableInventoryItemData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInventoryItemData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeLoreData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change lore data
     */
    public static ChangeLoreData createChangeLoreData(Cause cause, Optional<ImmutableLoreData> modifiedData, Optional<ImmutableLoreData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeLoreData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeMapItemData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change map item data
     */
    public static ChangeMapItemData createChangeMapItemData(Cause cause, Optional<ImmutableMapItemData> modifiedData, Optional<ImmutableMapItemData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeMapItemData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangePagedData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change paged data
     */
    public static ChangePagedData createChangePagedData(Cause cause, Optional<ImmutablePagedData> modifiedData, Optional<ImmutablePagedData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePagedData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangePlaceableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change placeable data
     */
    public static ChangePlaceableData createChangePlaceableData(Cause cause, Optional<ImmutablePlaceableData> modifiedData, Optional<ImmutablePlaceableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangePlaceableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeSpawnableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change spawnable data
     */
    public static ChangeSpawnableData createChangeSpawnableData(Cause cause, Optional<ImmutableSpawnableData> modifiedData, Optional<ImmutableSpawnableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSpawnableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.item.ChangeStoredEnchantmentData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change stored enchantment data
     */
    public static ChangeStoredEnchantmentData createChangeStoredEnchantmentData(Cause cause, Optional<ImmutableStoredEnchantmentData> modifiedData, Optional<ImmutableStoredEnchantmentData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeStoredEnchantmentData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeBannerData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change banner data
     */
    public static ChangeBannerData createChangeBannerData(Cause cause, Optional<ImmutableBannerData> modifiedData, Optional<ImmutableBannerData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBannerData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeBeaconData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change beacon data
     */
    public static ChangeBeaconData createChangeBeaconData(Cause cause, Optional<ImmutableBeaconData> modifiedData, Optional<ImmutableBeaconData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBeaconData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeBrewingStandData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change brewing stand data
     */
    public static ChangeBrewingStandData createChangeBrewingStandData(Cause cause, Optional<ImmutableBrewingStandData> modifiedData, Optional<ImmutableBrewingStandData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeBrewingStandData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeCooldownData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change cooldown data
     */
    public static ChangeCooldownData createChangeCooldownData(Cause cause, Optional<ImmutableCooldownData> modifiedData, Optional<ImmutableCooldownData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeCooldownData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeFurnaceData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change furnace data
     */
    public static ChangeFurnaceData createChangeFurnaceData(Cause cause, Optional<ImmutableFurnaceData> modifiedData, Optional<ImmutableFurnaceData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeFurnaceData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeLockableData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change lockable data
     */
    public static ChangeLockableData createChangeLockableData(Cause cause, Optional<ImmutableLockableData> modifiedData, Optional<ImmutableLockableData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeLockableData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeNoteData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change note data
     */
    public static ChangeNoteData createChangeNoteData(Cause cause, Optional<ImmutableNoteData> modifiedData, Optional<ImmutableNoteData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeNoteData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.data.tileentity.ChangeSignData}.
     * 
     * @param cause The cause
     * @param modifiedData The modified data
     * @param originalData The original data
     * @param targetHolder The target holder
     * @return A new change sign data
     */
    public static ChangeSignData createChangeSignData(Cause cause, Optional<ImmutableSignData> modifiedData, Optional<ImmutableSignData> originalData, DataHolder targetHolder) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("modifiedData", modifiedData);
        values.put("originalData", originalData);
        values.put("targetHolder", targetHolder);
        return SpongeEventFactoryUtils.createEventImpl(ChangeSignData.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.economy.EconomyTransactionEvent}.
     * 
     * @param cause The cause
     * @param transactionResult The transaction result
     * @return A new economy transaction event
     */
    public static EconomyTransactionEvent createEconomyTransactionEvent(Cause cause, TransactionResult transactionResult) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("transactionResult", transactionResult);
        return SpongeEventFactoryUtils.createEventImpl(EconomyTransactionEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.AffectEntityEvent}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new affect entity event
     */
    public static AffectEntityEvent createAffectEntityEvent(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(AffectEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.AttackEntityEvent}.
     * 
     * @param cause The cause
     * @param originalFunctions The original functions
     * @param targetEntity The target entity
     * @param knockbackModifier The knockback modifier
     * @param originalDamage The original damage
     * @return A new attack entity event
     */
    public static AttackEntityEvent createAttackEntityEvent(Cause cause, List<Tuple<DamageModifier, Function<? super Double, Double>>> originalFunctions, Entity targetEntity, int knockbackModifier, double originalDamage) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalFunctions", originalFunctions);
        values.put("targetEntity", targetEntity);
        values.put("knockbackModifier", knockbackModifier);
        values.put("originalDamage", originalDamage);
        return SpongeEventFactoryUtils.createEventImpl(AttackEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.BreedEntityEvent.Breed}.
     * 
     * @param cause The cause
     * @param interactionPoint The interaction point
     * @param offspringEntity The offspring entity
     * @param targetEntity The target entity
     * @return A new breed breed entity event
     */
    public static BreedEntityEvent.Breed createBreedEntityEventBreed(Cause cause, Optional<Vector3d> interactionPoint, Ageable offspringEntity, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("interactionPoint", interactionPoint);
        values.put("offspringEntity", offspringEntity);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(BreedEntityEvent.Breed.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.BreedEntityEvent.FindMate}.
     * 
     * @param cause The cause
     * @param originalResult The original result
     * @param result The result
     * @param interactionPoint The interaction point
     * @param targetEntity The target entity
     * @param hasAllowResult The has allow result
     * @return A new find mate breed entity event
     */
    public static BreedEntityEvent.FindMate createBreedEntityEventFindMate(Cause cause, TristateResult.Result originalResult, TristateResult.Result result, Optional<Vector3d> interactionPoint, Entity targetEntity, boolean hasAllowResult) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalResult", originalResult);
        values.put("result", result);
        values.put("interactionPoint", interactionPoint);
        values.put("targetEntity", targetEntity);
        values.put("hasAllowResult", hasAllowResult);
        return SpongeEventFactoryUtils.createEventImpl(BreedEntityEvent.FindMate.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent}.
     * 
     * @param cause The cause
     * @param originalItemStack The original item stack
     * @param itemStack The item stack
     * @param targetEntity The target entity
     * @param targetInventory The target inventory
     * @return A new change entity equipment event
     */
    public static ChangeEntityEquipmentEvent createChangeEntityEquipmentEvent(Cause cause, Optional<ItemStackSnapshot> originalItemStack, Optional<Transaction<ItemStackSnapshot>> itemStack, Entity targetEntity, Slot targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalItemStack", originalItemStack);
        values.put("itemStack", itemStack);
        values.put("targetEntity", targetEntity);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityEquipmentEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent.TargetHumanoid}.
     * 
     * @param cause The cause
     * @param originalItemStack The original item stack
     * @param itemStack The item stack
     * @param targetEntity The target entity
     * @param targetInventory The target inventory
     * @return A new target humanoid change entity equipment event
     */
    public static ChangeEntityEquipmentEvent.TargetHumanoid createChangeEntityEquipmentEventTargetHumanoid(Cause cause, Optional<ItemStackSnapshot> originalItemStack, Optional<Transaction<ItemStackSnapshot>> itemStack, Humanoid targetEntity, Slot targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalItemStack", originalItemStack);
        values.put("itemStack", itemStack);
        values.put("targetEntity", targetEntity);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityEquipmentEvent.TargetHumanoid.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent.TargetLiving}.
     * 
     * @param cause The cause
     * @param originalItemStack The original item stack
     * @param itemStack The item stack
     * @param targetEntity The target entity
     * @param targetInventory The target inventory
     * @return A new target living change entity equipment event
     */
    public static ChangeEntityEquipmentEvent.TargetLiving createChangeEntityEquipmentEventTargetLiving(Cause cause, Optional<ItemStackSnapshot> originalItemStack, Optional<Transaction<ItemStackSnapshot>> itemStack, Living targetEntity, Slot targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalItemStack", originalItemStack);
        values.put("itemStack", itemStack);
        values.put("targetEntity", targetEntity);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityEquipmentEvent.TargetLiving.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param originalItemStack The original item stack
     * @param itemStack The item stack
     * @param targetEntity The target entity
     * @param targetInventory The target inventory
     * @return A new target player change entity equipment event
     */
    public static ChangeEntityEquipmentEvent.TargetPlayer createChangeEntityEquipmentEventTargetPlayer(Cause cause, Optional<ItemStackSnapshot> originalItemStack, Optional<Transaction<ItemStackSnapshot>> itemStack, Player targetEntity, Slot targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalItemStack", originalItemStack);
        values.put("itemStack", itemStack);
        values.put("targetEntity", targetEntity);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityEquipmentEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityExperienceEvent}.
     * 
     * @param cause The cause
     * @param originalExperience The original experience
     * @param experience The experience
     * @param targetEntity The target entity
     * @return A new change entity experience event
     */
    public static ChangeEntityExperienceEvent createChangeEntityExperienceEvent(Cause cause, int originalExperience, int experience, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalExperience", originalExperience);
        values.put("experience", experience);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityExperienceEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityPotionEffectEvent.Expire}.
     * 
     * @param cause The cause
     * @param currentEffects The current effects
     * @param potionEffect The potion effect
     * @param targetEntity The target entity
     * @return A new expire change entity potion effect event
     */
    public static ChangeEntityPotionEffectEvent.Expire createChangeEntityPotionEffectEventExpire(Cause cause, List<PotionEffect> currentEffects, PotionEffect potionEffect, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("currentEffects", currentEffects);
        values.put("potionEffect", potionEffect);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityPotionEffectEvent.Expire.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityPotionEffectEvent.Gain}.
     * 
     * @param cause The cause
     * @param currentEffects The current effects
     * @param potionEffect The potion effect
     * @param targetEntity The target entity
     * @return A new gain change entity potion effect event
     */
    public static ChangeEntityPotionEffectEvent.Gain createChangeEntityPotionEffectEventGain(Cause cause, List<PotionEffect> currentEffects, PotionEffect potionEffect, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("currentEffects", currentEffects);
        values.put("potionEffect", potionEffect);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityPotionEffectEvent.Gain.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ChangeEntityPotionEffectEvent.Remove}.
     * 
     * @param cause The cause
     * @param currentEffects The current effects
     * @param potionEffect The potion effect
     * @param targetEntity The target entity
     * @return A new remove change entity potion effect event
     */
    public static ChangeEntityPotionEffectEvent.Remove createChangeEntityPotionEffectEventRemove(Cause cause, List<PotionEffect> currentEffects, PotionEffect potionEffect, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("currentEffects", currentEffects);
        values.put("potionEffect", potionEffect);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeEntityPotionEffectEvent.Remove.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.CollideEntityEvent}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new collide entity event
     */
    public static CollideEntityEvent createCollideEntityEvent(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(CollideEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.CollideEntityEvent.Impact}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param impactPoint The impact point
     * @param targetWorld The target world
     * @return A new impact collide entity event
     */
    public static CollideEntityEvent.Impact createCollideEntityEventImpact(Cause cause, List<Entity> entities, Location<World> impactPoint, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("impactPoint", impactPoint);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(CollideEntityEvent.Impact.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ConstructEntityEvent.Post}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @param targetType The target type
     * @param transform The transform
     * @return A new post construct entity event
     */
    public static ConstructEntityEvent.Post createConstructEntityEventPost(Cause cause, Entity targetEntity, EntityType targetType, Transform<World> transform) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        values.put("targetType", targetType);
        values.put("transform", transform);
        return SpongeEventFactoryUtils.createEventImpl(ConstructEntityEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ConstructEntityEvent.Pre}.
     * 
     * @param cause The cause
     * @param targetType The target type
     * @param transform The transform
     * @return A new pre construct entity event
     */
    public static ConstructEntityEvent.Pre createConstructEntityEventPre(Cause cause, EntityType targetType, Transform<World> transform) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetType", targetType);
        values.put("transform", transform);
        return SpongeEventFactoryUtils.createEventImpl(ConstructEntityEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.DamageEntityEvent}.
     * 
     * @param cause The cause
     * @param originalFunctions The original functions
     * @param targetEntity The target entity
     * @param originalDamage The original damage
     * @return A new damage entity event
     */
    public static DamageEntityEvent createDamageEntityEvent(Cause cause, List<Tuple<DamageModifier, Function<? super Double, Double>>> originalFunctions, Entity targetEntity, double originalDamage) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalFunctions", originalFunctions);
        values.put("targetEntity", targetEntity);
        values.put("originalDamage", originalDamage);
        return SpongeEventFactoryUtils.createEventImpl(DamageEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.DestructEntityEvent}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param formatter The formatter
     * @param targetEntity The target entity
     * @param messageCancelled The message cancelled
     * @return A new destruct entity event
     */
    public static DestructEntityEvent createDestructEntityEvent(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, MessageEvent.MessageFormatter formatter, Entity targetEntity, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("formatter", formatter);
        values.put("targetEntity", targetEntity);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(DestructEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.DestructEntityEvent.Death}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param formatter The formatter
     * @param targetEntity The target entity
     * @param messageCancelled The message cancelled
     * @return A new death destruct entity event
     */
    public static DestructEntityEvent.Death createDestructEntityEventDeath(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, MessageEvent.MessageFormatter formatter, Living targetEntity, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("formatter", formatter);
        values.put("targetEntity", targetEntity);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(DestructEntityEvent.Death.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ExpireEntityEvent.TargetItem}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target item expire entity event
     */
    public static ExpireEntityEvent.TargetItem createExpireEntityEventTargetItem(Cause cause, Item targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ExpireEntityEvent.TargetItem.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.HarvestEntityEvent.TargetHumanoid}.
     * 
     * @param cause The cause
     * @param originalExperience The original experience
     * @param experience The experience
     * @param targetEntity The target entity
     * @return A new target humanoid harvest entity event
     */
    public static HarvestEntityEvent.TargetHumanoid createHarvestEntityEventTargetHumanoid(Cause cause, int originalExperience, int experience, Humanoid targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalExperience", originalExperience);
        values.put("experience", experience);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(HarvestEntityEvent.TargetHumanoid.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.HarvestEntityEvent.TargetLiving}.
     * 
     * @param cause The cause
     * @param originalExperience The original experience
     * @param experience The experience
     * @param targetEntity The target entity
     * @return A new target living harvest entity event
     */
    public static HarvestEntityEvent.TargetLiving createHarvestEntityEventTargetLiving(Cause cause, int originalExperience, int experience, Living targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalExperience", originalExperience);
        values.put("experience", experience);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(HarvestEntityEvent.TargetLiving.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.HarvestEntityEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param originalExperience The original experience
     * @param experience The experience
     * @param targetEntity The target entity
     * @param keepsInventory The keeps inventory
     * @param keepsLevel The keeps level
     * @param level The level
     * @return A new target player harvest entity event
     */
    public static HarvestEntityEvent.TargetPlayer createHarvestEntityEventTargetPlayer(Cause cause, int originalExperience, int experience, Player targetEntity, boolean keepsInventory, boolean keepsLevel, int level) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalExperience", originalExperience);
        values.put("experience", experience);
        values.put("targetEntity", targetEntity);
        values.put("keepsInventory", keepsInventory);
        values.put("keepsLevel", keepsLevel);
        values.put("level", level);
        return SpongeEventFactoryUtils.createEventImpl(HarvestEntityEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.HealEntityEvent}.
     * 
     * @param cause The cause
     * @param originalFunctions The original functions
     * @param targetEntity The target entity
     * @param originalHealAmount The original heal amount
     * @return A new heal entity event
     */
    public static HealEntityEvent createHealEntityEvent(Cause cause, List<Tuple<HealthModifier, Function<? super Double, Double>>> originalFunctions, Entity targetEntity, double originalHealAmount) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalFunctions", originalFunctions);
        values.put("targetEntity", targetEntity);
        values.put("originalHealAmount", originalHealAmount);
        return SpongeEventFactoryUtils.createEventImpl(HealEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.IgniteEntityEvent}.
     * 
     * @param cause The cause
     * @param originalFireTicks The original fire ticks
     * @param fireTicks The fire ticks
     * @param targetEntity The target entity
     * @return A new ignite entity event
     */
    public static IgniteEntityEvent createIgniteEntityEvent(Cause cause, int originalFireTicks, int fireTicks, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalFireTicks", originalFireTicks);
        values.put("fireTicks", fireTicks);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(IgniteEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.InteractEntityEvent.Primary.MainHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetEntity The target entity
     * @return A new main hand primary interact entity event
     */
    public static InteractEntityEvent.Primary.MainHand createInteractEntityEventPrimaryMainHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(InteractEntityEvent.Primary.MainHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.InteractEntityEvent.Primary.OffHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetEntity The target entity
     * @return A new off hand primary interact entity event
     */
    public static InteractEntityEvent.Primary.OffHand createInteractEntityEventPrimaryOffHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(InteractEntityEvent.Primary.OffHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.InteractEntityEvent.Secondary.MainHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetEntity The target entity
     * @return A new main hand secondary interact entity event
     */
    public static InteractEntityEvent.Secondary.MainHand createInteractEntityEventSecondaryMainHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(InteractEntityEvent.Secondary.MainHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.InteractEntityEvent.Secondary.OffHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param targetEntity The target entity
     * @return A new off hand secondary interact entity event
     */
    public static InteractEntityEvent.Secondary.OffHand createInteractEntityEventSecondaryOffHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(InteractEntityEvent.Secondary.OffHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.LeashEntityEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new leash entity event
     */
    public static LeashEntityEvent createLeashEntityEvent(Cause cause, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(LeashEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.MoveEntityEvent}.
     * 
     * @param cause The cause
     * @param fromTransform The from transform
     * @param toTransform The to transform
     * @param targetEntity The target entity
     * @return A new move entity event
     */
    public static MoveEntityEvent createMoveEntityEvent(Cause cause, Transform<World> fromTransform, Transform<World> toTransform, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fromTransform", fromTransform);
        values.put("toTransform", toTransform);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(MoveEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.MoveEntityEvent.Teleport}.
     * 
     * @param cause The cause
     * @param fromTransform The from transform
     * @param toTransform The to transform
     * @param targetEntity The target entity
     * @return A new teleport move entity event
     */
    public static MoveEntityEvent.Teleport createMoveEntityEventTeleport(Cause cause, Transform<World> fromTransform, Transform<World> toTransform, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fromTransform", fromTransform);
        values.put("toTransform", toTransform);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(MoveEntityEvent.Teleport.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.MoveEntityEvent.Teleport.Portal}.
     * 
     * @param cause The cause
     * @param fromTransform The from transform
     * @param toTransform The to transform
     * @param portalAgent The portal agent
     * @param targetEntity The target entity
     * @param usePortalAgent The use portal agent
     * @return A new portal teleport move entity event
     */
    public static MoveEntityEvent.Teleport.Portal createMoveEntityEventTeleportPortal(Cause cause, Transform<World> fromTransform, Transform<World> toTransform, PortalAgent portalAgent, Entity targetEntity, boolean usePortalAgent) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fromTransform", fromTransform);
        values.put("toTransform", toTransform);
        values.put("portalAgent", portalAgent);
        values.put("targetEntity", targetEntity);
        values.put("usePortalAgent", usePortalAgent);
        return SpongeEventFactoryUtils.createEventImpl(MoveEntityEvent.Teleport.Portal.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.RideEntityEvent.Dismount}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new dismount ride entity event
     */
    public static RideEntityEvent.Dismount createRideEntityEventDismount(Cause cause, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(RideEntityEvent.Dismount.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.RideEntityEvent.Mount}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new mount ride entity event
     */
    public static RideEntityEvent.Mount createRideEntityEventMount(Cause cause, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(RideEntityEvent.Mount.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.SpawnEntityEvent}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new spawn entity event
     */
    public static SpawnEntityEvent createSpawnEntityEvent(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(SpawnEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.SpawnEntityEvent.ChunkLoad}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new chunk load spawn entity event
     */
    public static SpawnEntityEvent.ChunkLoad createSpawnEntityEventChunkLoad(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(SpawnEntityEvent.ChunkLoad.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.SpawnEntityEvent.Custom}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new custom spawn entity event
     */
    public static SpawnEntityEvent.Custom createSpawnEntityEventCustom(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(SpawnEntityEvent.Custom.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.SpawnEntityEvent.Spawner}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new spawner spawn entity event
     */
    public static SpawnEntityEvent.Spawner createSpawnEntityEventSpawner(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(SpawnEntityEvent.Spawner.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.TameEntityEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new tame entity event
     */
    public static TameEntityEvent createTameEntityEvent(Cause cause, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TameEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.TargetEntityEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target entity event
     */
    public static TargetEntityEvent createTargetEntityEvent(Cause cause, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.UnleashEntityEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new unleash entity event
     */
    public static UnleashEntityEvent createUnleashEntityEvent(Cause cause, Entity targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(UnleashEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ai.AITaskEvent.Add}.
     * 
     * @param cause The cause
     * @param originalPriority The original priority
     * @param priority The priority
     * @param goal The goal
     * @param targetEntity The target entity
     * @param task The task
     * @return A new add a i task event
     */
    public static AITaskEvent.Add createAITaskEventAdd(Cause cause, int originalPriority, int priority, Goal<? extends Agent> goal, Agent targetEntity, AITask<? extends Agent> task) {
        Preconditions.checkArgument(((goal.getOwner()) == targetEntity), String.format("The target entity \'%s\' is not the owner of the goal \'%s\'!", goal, targetEntity));
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalPriority", originalPriority);
        values.put("priority", priority);
        values.put("goal", goal);
        values.put("targetEntity", targetEntity);
        values.put("task", task);
        return SpongeEventFactoryUtils.createEventImpl(AITaskEvent.Add.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.ai.AITaskEvent.Remove}.
     * 
     * @param cause The cause
     * @param goal The goal
     * @param targetEntity The target entity
     * @param task The task
     * @param priority The priority
     * @return A new remove a i task event
     */
    public static AITaskEvent.Remove createAITaskEventRemove(Cause cause, Goal<? extends Agent> goal, Agent targetEntity, AITask<? extends Agent> task, int priority) {
        Preconditions.checkArgument(((goal.getOwner()) == targetEntity), String.format("The target entity \'%s\' is not the owner of the goal \'%s\'!", goal, targetEntity));
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("goal", goal);
        values.put("targetEntity", targetEntity);
        values.put("task", task);
        values.put("priority", priority);
        return SpongeEventFactoryUtils.createEventImpl(AITaskEvent.Remove.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.explosive.DefuseExplosiveEvent.Post}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new post defuse explosive event
     */
    public static DefuseExplosiveEvent.Post createDefuseExplosiveEventPost(Cause cause, FusedExplosive targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(DefuseExplosiveEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.explosive.DefuseExplosiveEvent.Pre}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new pre defuse explosive event
     */
    public static DefuseExplosiveEvent.Pre createDefuseExplosiveEventPre(Cause cause, FusedExplosive targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(DefuseExplosiveEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.explosive.DetonateExplosiveEvent}.
     * 
     * @param cause The cause
     * @param explosionBuilder The explosion builder
     * @param originalExplosion The original explosion
     * @param targetEntity The target entity
     * @return A new detonate explosive event
     */
    public static DetonateExplosiveEvent createDetonateExplosiveEvent(Cause cause, Explosion.Builder explosionBuilder, Explosion originalExplosion, Explosive targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("explosionBuilder", explosionBuilder);
        values.put("originalExplosion", originalExplosion);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(DetonateExplosiveEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.explosive.PrimeExplosiveEvent.Post}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new post prime explosive event
     */
    public static PrimeExplosiveEvent.Post createPrimeExplosiveEventPost(Cause cause, FusedExplosive targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(PrimeExplosiveEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.explosive.PrimeExplosiveEvent.Pre}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new pre prime explosive event
     */
    public static PrimeExplosiveEvent.Pre createPrimeExplosiveEventPre(Cause cause, FusedExplosive targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(PrimeExplosiveEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.explosive.TargetExplosiveEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target explosive event
     */
    public static TargetExplosiveEvent createTargetExplosiveEvent(Cause cause, Explosive targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetExplosiveEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.explosive.TargetFusedExplosiveEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target fused explosive event
     */
    public static TargetFusedExplosiveEvent createTargetFusedExplosiveEvent(Cause cause, FusedExplosive targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetFusedExplosiveEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.item.ItemMergeItemEvent}.
     * 
     * @param cause The cause
     * @param itemToMerge The item to merge
     * @param targetEntity The target entity
     * @return A new item merge item event
     */
    public static ItemMergeItemEvent createItemMergeItemEvent(Cause cause, Item itemToMerge, Item targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("itemToMerge", itemToMerge);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ItemMergeItemEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.item.TargetItemEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target item event
     */
    public static TargetItemEvent createTargetItemEvent(Cause cause, Item targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetItemEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.TargetAgentEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target agent event
     */
    public static TargetAgentEvent createTargetAgentEvent(Cause cause, Agent targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetAgentEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.TargetLivingEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target living event
     */
    public static TargetLivingEvent createTargetLivingEvent(Cause cause, Living targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetLivingEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.AnimateHandEvent}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param targetEntity The target entity
     * @return A new animate hand event
     */
    public static AnimateHandEvent createAnimateHandEvent(Cause cause, HandType handType, Humanoid targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(AnimateHandEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent}.
     * 
     * @param cause The cause
     * @param originalGameMode The original game mode
     * @param gameMode The game mode
     * @param targetEntity The target entity
     * @return A new change game mode event
     */
    public static ChangeGameModeEvent createChangeGameModeEvent(Cause cause, GameMode originalGameMode, GameMode gameMode, Humanoid targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalGameMode", originalGameMode);
        values.put("gameMode", gameMode);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGameModeEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param originalGameMode The original game mode
     * @param gameMode The game mode
     * @param targetEntity The target entity
     * @return A new target player change game mode event
     */
    public static ChangeGameModeEvent.TargetPlayer createChangeGameModeEventTargetPlayer(Cause cause, GameMode originalGameMode, GameMode gameMode, Player targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalGameMode", originalGameMode);
        values.put("gameMode", gameMode);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeGameModeEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.ChangeLevelEvent}.
     * 
     * @param cause The cause
     * @param originalLevel The original level
     * @param level The level
     * @param targetEntity The target entity
     * @return A new change level event
     */
    public static ChangeLevelEvent createChangeLevelEvent(Cause cause, int originalLevel, int level, Humanoid targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalLevel", originalLevel);
        values.put("level", level);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeLevelEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.ChangeLevelEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param originalLevel The original level
     * @param level The level
     * @param targetEntity The target entity
     * @return A new target player change level event
     */
    public static ChangeLevelEvent.TargetPlayer createChangeLevelEventTargetPlayer(Cause cause, int originalLevel, int level, Player targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalLevel", originalLevel);
        values.put("level", level);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeLevelEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.HandInteractEvent}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @return A new hand interact event
     */
    public static HandInteractEvent createHandInteractEvent(Cause cause, HandType handType, Optional<Vector3d> interactionPoint) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        return SpongeEventFactoryUtils.createEventImpl(HandInteractEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.TargetHumanoidEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target humanoid event
     */
    public static TargetHumanoidEvent createTargetHumanoidEvent(Cause cause, Humanoid targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetHumanoidEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.player.KickPlayerEvent}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param formatter The formatter
     * @param targetEntity The target entity
     * @param messageCancelled The message cancelled
     * @return A new kick player event
     */
    public static KickPlayerEvent createKickPlayerEvent(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, MessageEvent.MessageFormatter formatter, Player targetEntity, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("formatter", formatter);
        values.put("targetEntity", targetEntity);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(KickPlayerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.player.PlayerChangeClientSettingsEvent}.
     * 
     * @param cause The cause
     * @param chatVisibility The chat visibility
     * @param displayedSkinParts The displayed skin parts
     * @param locale The locale
     * @param targetEntity The target entity
     * @param chatColorsEnabled The chat colors enabled
     * @param viewDistance The view distance
     * @return A new player change client settings event
     */
    public static PlayerChangeClientSettingsEvent createPlayerChangeClientSettingsEvent(Cause cause, ChatVisibility chatVisibility, Set<SkinPart> displayedSkinParts, Locale locale, Player targetEntity, boolean chatColorsEnabled, int viewDistance) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("chatVisibility", chatVisibility);
        values.put("displayedSkinParts", displayedSkinParts);
        values.put("locale", locale);
        values.put("targetEntity", targetEntity);
        values.put("chatColorsEnabled", chatColorsEnabled);
        values.put("viewDistance", viewDistance);
        return SpongeEventFactoryUtils.createEventImpl(PlayerChangeClientSettingsEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.player.ResourcePackStatusEvent}.
     * 
     * @param cause The cause
     * @param pack The pack
     * @param player The player
     * @param status The status
     * @return A new resource pack status event
     */
    public static ResourcePackStatusEvent createResourcePackStatusEvent(Cause cause, ResourcePack pack, Player player, ResourcePackStatusEvent.ResourcePackStatus status) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("pack", pack);
        values.put("player", player);
        values.put("status", status);
        return SpongeEventFactoryUtils.createEventImpl(ResourcePackStatusEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent}.
     * 
     * @param cause The cause
     * @param fromTransform The from transform
     * @param toTransform The to transform
     * @param originalPlayer The original player
     * @param targetEntity The target entity
     * @param bedSpawn The bed spawn
     * @param death The death
     * @return A new respawn player event
     */
    public static RespawnPlayerEvent createRespawnPlayerEvent(Cause cause, Transform<World> fromTransform, Transform<World> toTransform, Player originalPlayer, Player targetEntity, boolean bedSpawn, boolean death) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fromTransform", fromTransform);
        values.put("toTransform", toTransform);
        values.put("originalPlayer", originalPlayer);
        values.put("targetEntity", targetEntity);
        values.put("bedSpawn", bedSpawn);
        values.put("death", death);
        return SpongeEventFactoryUtils.createEventImpl(RespawnPlayerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target player event
     */
    public static TargetPlayerEvent createTargetPlayerEvent(Cause cause, Player targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetPlayerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new launch projectile event
     */
    public static LaunchProjectileEvent createLaunchProjectileEvent(Cause cause, Projectile targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(LaunchProjectileEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.entity.projectile.TargetProjectileEvent}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @return A new target projectile event
     */
    public static TargetProjectileEvent createTargetProjectileEvent(Cause cause, Projectile targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(TargetProjectileEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.GameReloadEvent}.
     * 
     * @param cause The cause
     * @return A new game reload event
     */
    public static GameReloadEvent createGameReloadEvent(Cause cause) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(GameReloadEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameAboutToStartServerEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game about to start server event
     */
    public static GameAboutToStartServerEvent createGameAboutToStartServerEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameAboutToStartServerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameConstructionEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game construction event
     */
    public static GameConstructionEvent createGameConstructionEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameConstructionEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameInitializationEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game initialization event
     */
    public static GameInitializationEvent createGameInitializationEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameInitializationEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameLoadCompleteEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game load complete event
     */
    public static GameLoadCompleteEvent createGameLoadCompleteEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameLoadCompleteEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GamePostInitializationEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game post initialization event
     */
    public static GamePostInitializationEvent createGamePostInitializationEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GamePostInitializationEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GamePreInitializationEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game pre initialization event
     */
    public static GamePreInitializationEvent createGamePreInitializationEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GamePreInitializationEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameStartedServerEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game started server event
     */
    public static GameStartedServerEvent createGameStartedServerEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameStartedServerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameStartingServerEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game starting server event
     */
    public static GameStartingServerEvent createGameStartingServerEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameStartingServerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameStateEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game state event
     */
    public static GameStateEvent createGameStateEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameStateEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameStoppedEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game stopped event
     */
    public static GameStoppedEvent createGameStoppedEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameStoppedEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameStoppedServerEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game stopped server event
     */
    public static GameStoppedServerEvent createGameStoppedServerEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameStoppedServerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameStoppingEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game stopping event
     */
    public static GameStoppingEvent createGameStoppingEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameStoppingEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.game.state.GameStoppingServerEvent}.
     * 
     * @param cause The cause
     * @param state The state
     * @return A new game stopping server event
     */
    public static GameStoppingServerEvent createGameStoppingServerEvent(Cause cause, GameState state) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("state", state);
        return SpongeEventFactoryUtils.createEventImpl(GameStoppingServerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.AffectItemStackEvent}.
     * 
     * @param cause The cause
     * @param transactions The transactions
     * @return A new affect item stack event
     */
    public static AffectItemStackEvent createAffectItemStackEvent(Cause cause, List<? extends Transaction<ItemStackSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(AffectItemStackEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.AffectSlotEvent}.
     * 
     * @param cause The cause
     * @param transactions The transactions
     * @return A new affect slot event
     */
    public static AffectSlotEvent createAffectSlotEvent(Cause cause, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(AffectSlotEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ChangeInventoryEvent.Equipment}.
     * 
     * @param cause The cause
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new equipment change inventory event
     */
    public static ChangeInventoryEvent.Equipment createChangeInventoryEventEquipment(Cause cause, Inventory targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInventoryEvent.Equipment.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ChangeInventoryEvent.Held}.
     * 
     * @param cause The cause
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new held change inventory event
     */
    public static ChangeInventoryEvent.Held createChangeInventoryEventHeld(Cause cause, Inventory targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInventoryEvent.Held.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ChangeInventoryEvent.Pickup}.
     * 
     * @param cause The cause
     * @param targetEntity The target entity
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new pickup change inventory event
     */
    public static ChangeInventoryEvent.Pickup createChangeInventoryEventPickup(Cause cause, Item targetEntity, Inventory targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetEntity", targetEntity);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInventoryEvent.Pickup.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ChangeInventoryEvent.Transfer}.
     * 
     * @param cause The cause
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new transfer change inventory event
     */
    public static ChangeInventoryEvent.Transfer createChangeInventoryEventTransfer(Cause cause, Inventory targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ChangeInventoryEvent.Transfer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Creative}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new creative click inventory event
     */
    public static ClickInventoryEvent.Creative createClickInventoryEventCreative(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Creative.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Double}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new double click inventory event
     */
    public static ClickInventoryEvent.Double createClickInventoryEventDouble(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Double.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Drag.Primary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new primary drag click inventory event
     */
    public static ClickInventoryEvent.Drag.Primary createClickInventoryEventDragPrimary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Drag.Primary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Drag.Secondary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new secondary drag click inventory event
     */
    public static ClickInventoryEvent.Drag.Secondary createClickInventoryEventDragSecondary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Drag.Secondary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Drop.Full}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param entities The entities
     * @param targetInventory The target inventory
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new full drop click inventory event
     */
    public static ClickInventoryEvent.Drop.Full createClickInventoryEventDropFull(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, List<Entity> entities, Container targetInventory, World targetWorld, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("entities", entities);
        values.put("targetInventory", targetInventory);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Drop.Full.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Drop.Outside.Primary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param entities The entities
     * @param targetInventory The target inventory
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new primary outside drop click inventory event
     */
    public static ClickInventoryEvent.Drop.Outside.Primary createClickInventoryEventDropOutsidePrimary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, List<Entity> entities, Container targetInventory, World targetWorld, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("entities", entities);
        values.put("targetInventory", targetInventory);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Drop.Outside.Primary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Drop.Outside.Secondary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param entities The entities
     * @param targetInventory The target inventory
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new secondary outside drop click inventory event
     */
    public static ClickInventoryEvent.Drop.Outside.Secondary createClickInventoryEventDropOutsideSecondary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, List<Entity> entities, Container targetInventory, World targetWorld, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("entities", entities);
        values.put("targetInventory", targetInventory);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Drop.Outside.Secondary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Drop.Single}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param entities The entities
     * @param targetInventory The target inventory
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new single drop click inventory event
     */
    public static ClickInventoryEvent.Drop.Single createClickInventoryEventDropSingle(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, List<Entity> entities, Container targetInventory, World targetWorld, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("entities", entities);
        values.put("targetInventory", targetInventory);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Drop.Single.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Middle}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new middle click inventory event
     */
    public static ClickInventoryEvent.Middle createClickInventoryEventMiddle(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Middle.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.NumberPress}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @param number The number
     * @return A new number press click inventory event
     */
    public static ClickInventoryEvent.NumberPress createClickInventoryEventNumberPress(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions, int number) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        values.put("number", number);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.NumberPress.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Primary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new primary click inventory event
     */
    public static ClickInventoryEvent.Primary createClickInventoryEventPrimary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Primary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Secondary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new secondary click inventory event
     */
    public static ClickInventoryEvent.Secondary createClickInventoryEventSecondary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Secondary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Shift.Primary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new primary shift click inventory event
     */
    public static ClickInventoryEvent.Shift.Primary createClickInventoryEventShiftPrimary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Shift.Primary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.ClickInventoryEvent.Shift.Secondary}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @param transactions The transactions
     * @return A new secondary shift click inventory event
     */
    public static ClickInventoryEvent.Shift.Secondary createClickInventoryEventShiftSecondary(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory, List<SlotTransaction> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ClickInventoryEvent.Shift.Secondary.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.DropItemEvent.Custom}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new custom drop item event
     */
    public static DropItemEvent.Custom createDropItemEventCustom(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(DropItemEvent.Custom.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.DropItemEvent.Destruct}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new destruct drop item event
     */
    public static DropItemEvent.Destruct createDropItemEventDestruct(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(DropItemEvent.Destruct.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.DropItemEvent.Dispense}.
     * 
     * @param cause The cause
     * @param entities The entities
     * @param targetWorld The target world
     * @return A new dispense drop item event
     */
    public static DropItemEvent.Dispense createDropItemEventDispense(Cause cause, List<Entity> entities, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("entities", entities);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(DropItemEvent.Dispense.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.DropItemEvent.Pre}.
     * 
     * @param cause The cause
     * @param originalDroppedItems The original dropped items
     * @param droppedItems The dropped items
     * @return A new pre drop item event
     */
    public static DropItemEvent.Pre createDropItemEventPre(Cause cause, List<ItemStackSnapshot> originalDroppedItems, List<ItemStackSnapshot> droppedItems) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalDroppedItems", originalDroppedItems);
        values.put("droppedItems", droppedItems);
        return SpongeEventFactoryUtils.createEventImpl(DropItemEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.InteractInventoryEvent.Close}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @return A new close interact inventory event
     */
    public static InteractInventoryEvent.Close createInteractInventoryEventClose(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(InteractInventoryEvent.Close.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.InteractInventoryEvent.Open}.
     * 
     * @param cause The cause
     * @param cursorTransaction The cursor transaction
     * @param targetInventory The target inventory
     * @return A new open interact inventory event
     */
    public static InteractInventoryEvent.Open createInteractInventoryEventOpen(Cause cause, Transaction<ItemStackSnapshot> cursorTransaction, Container targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("cursorTransaction", cursorTransaction);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(InteractInventoryEvent.Open.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.InteractItemEvent.Primary.MainHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param itemStack The item stack
     * @return A new main hand primary interact item event
     */
    public static InteractItemEvent.Primary.MainHand createInteractItemEventPrimaryMainHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, ItemStackSnapshot itemStack) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("itemStack", itemStack);
        return SpongeEventFactoryUtils.createEventImpl(InteractItemEvent.Primary.MainHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.InteractItemEvent.Primary.OffHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param itemStack The item stack
     * @return A new off hand primary interact item event
     */
    public static InteractItemEvent.Primary.OffHand createInteractItemEventPrimaryOffHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, ItemStackSnapshot itemStack) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("itemStack", itemStack);
        return SpongeEventFactoryUtils.createEventImpl(InteractItemEvent.Primary.OffHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.InteractItemEvent.Secondary.MainHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param itemStack The item stack
     * @return A new main hand secondary interact item event
     */
    public static InteractItemEvent.Secondary.MainHand createInteractItemEventSecondaryMainHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, ItemStackSnapshot itemStack) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("itemStack", itemStack);
        return SpongeEventFactoryUtils.createEventImpl(InteractItemEvent.Secondary.MainHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.InteractItemEvent.Secondary.OffHand}.
     * 
     * @param cause The cause
     * @param handType The hand type
     * @param interactionPoint The interaction point
     * @param itemStack The item stack
     * @return A new off hand secondary interact item event
     */
    public static InteractItemEvent.Secondary.OffHand createInteractItemEventSecondaryOffHand(Cause cause, HandType handType, Optional<Vector3d> interactionPoint, ItemStackSnapshot itemStack) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("handType", handType);
        values.put("interactionPoint", interactionPoint);
        values.put("itemStack", itemStack);
        return SpongeEventFactoryUtils.createEventImpl(InteractItemEvent.Secondary.OffHand.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.TargetContainerEvent}.
     * 
     * @param cause The cause
     * @param targetInventory The target inventory
     * @return A new target container event
     */
    public static TargetContainerEvent createTargetContainerEvent(Cause cause, Container targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(TargetContainerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.TargetInventoryEvent}.
     * 
     * @param cause The cause
     * @param targetInventory The target inventory
     * @return A new target inventory event
     */
    public static TargetInventoryEvent createTargetInventoryEvent(Cause cause, Inventory targetInventory) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetInventory", targetInventory);
        return SpongeEventFactoryUtils.createEventImpl(TargetInventoryEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.UseItemStackEvent.Finish}.
     * 
     * @param cause The cause
     * @param originalRemainingDuration The original remaining duration
     * @param remainingDuration The remaining duration
     * @param itemStackInUse The item stack in use
     * @return A new finish use item stack event
     */
    public static UseItemStackEvent.Finish createUseItemStackEventFinish(Cause cause, int originalRemainingDuration, int remainingDuration, ItemStackSnapshot itemStackInUse) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalRemainingDuration", originalRemainingDuration);
        values.put("remainingDuration", remainingDuration);
        values.put("itemStackInUse", itemStackInUse);
        return SpongeEventFactoryUtils.createEventImpl(UseItemStackEvent.Finish.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.UseItemStackEvent.Replace}.
     * 
     * @param cause The cause
     * @param originalRemainingDuration The original remaining duration
     * @param remainingDuration The remaining duration
     * @param itemStackInUse The item stack in use
     * @param itemStackResult The item stack result
     * @return A new replace use item stack event
     */
    public static UseItemStackEvent.Replace createUseItemStackEventReplace(Cause cause, int originalRemainingDuration, int remainingDuration, ItemStackSnapshot itemStackInUse, Transaction<ItemStackSnapshot> itemStackResult) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalRemainingDuration", originalRemainingDuration);
        values.put("remainingDuration", remainingDuration);
        values.put("itemStackInUse", itemStackInUse);
        values.put("itemStackResult", itemStackResult);
        return SpongeEventFactoryUtils.createEventImpl(UseItemStackEvent.Replace.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.UseItemStackEvent.Reset}.
     * 
     * @param cause The cause
     * @param originalRemainingDuration The original remaining duration
     * @param remainingDuration The remaining duration
     * @param itemStackInUse The item stack in use
     * @return A new reset use item stack event
     */
    public static UseItemStackEvent.Reset createUseItemStackEventReset(Cause cause, int originalRemainingDuration, int remainingDuration, ItemStackSnapshot itemStackInUse) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalRemainingDuration", originalRemainingDuration);
        values.put("remainingDuration", remainingDuration);
        values.put("itemStackInUse", itemStackInUse);
        return SpongeEventFactoryUtils.createEventImpl(UseItemStackEvent.Reset.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.UseItemStackEvent.Start}.
     * 
     * @param cause The cause
     * @param originalRemainingDuration The original remaining duration
     * @param remainingDuration The remaining duration
     * @param itemStackInUse The item stack in use
     * @return A new start use item stack event
     */
    public static UseItemStackEvent.Start createUseItemStackEventStart(Cause cause, int originalRemainingDuration, int remainingDuration, ItemStackSnapshot itemStackInUse) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalRemainingDuration", originalRemainingDuration);
        values.put("remainingDuration", remainingDuration);
        values.put("itemStackInUse", itemStackInUse);
        return SpongeEventFactoryUtils.createEventImpl(UseItemStackEvent.Start.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.UseItemStackEvent.Stop}.
     * 
     * @param cause The cause
     * @param originalRemainingDuration The original remaining duration
     * @param remainingDuration The remaining duration
     * @param itemStackInUse The item stack in use
     * @return A new stop use item stack event
     */
    public static UseItemStackEvent.Stop createUseItemStackEventStop(Cause cause, int originalRemainingDuration, int remainingDuration, ItemStackSnapshot itemStackInUse) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalRemainingDuration", originalRemainingDuration);
        values.put("remainingDuration", remainingDuration);
        values.put("itemStackInUse", itemStackInUse);
        return SpongeEventFactoryUtils.createEventImpl(UseItemStackEvent.Stop.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.item.inventory.UseItemStackEvent.Tick}.
     * 
     * @param cause The cause
     * @param originalRemainingDuration The original remaining duration
     * @param remainingDuration The remaining duration
     * @param itemStackInUse The item stack in use
     * @return A new tick use item stack event
     */
    public static UseItemStackEvent.Tick createUseItemStackEventTick(Cause cause, int originalRemainingDuration, int remainingDuration, ItemStackSnapshot itemStackInUse) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalRemainingDuration", originalRemainingDuration);
        values.put("remainingDuration", remainingDuration);
        values.put("itemStackInUse", itemStackInUse);
        return SpongeEventFactoryUtils.createEventImpl(UseItemStackEvent.Tick.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.message.MessageChannelEvent}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param formatter The formatter
     * @param messageCancelled The message cancelled
     * @return A new message channel event
     */
    public static MessageChannelEvent createMessageChannelEvent(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, MessageEvent.MessageFormatter formatter, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("formatter", formatter);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(MessageChannelEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.message.MessageChannelEvent.Chat}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param formatter The formatter
     * @param rawMessage The raw message
     * @param messageCancelled The message cancelled
     * @return A new chat message channel event
     */
    public static MessageChannelEvent.Chat createMessageChannelEventChat(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, MessageEvent.MessageFormatter formatter, Text rawMessage, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("formatter", formatter);
        values.put("rawMessage", rawMessage);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(MessageChannelEvent.Chat.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.BanIpEvent}.
     * 
     * @param cause The cause
     * @param ban The ban
     * @return A new ban ip event
     */
    public static BanIpEvent createBanIpEvent(Cause cause, Ban.Ip ban) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ban", ban);
        return SpongeEventFactoryUtils.createEventImpl(BanIpEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.ChannelRegistrationEvent.Register}.
     * 
     * @param cause The cause
     * @param channel The channel
     * @return A new register channel registration event
     */
    public static ChannelRegistrationEvent.Register createChannelRegistrationEventRegister(Cause cause, String channel) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("channel", channel);
        return SpongeEventFactoryUtils.createEventImpl(ChannelRegistrationEvent.Register.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.ChannelRegistrationEvent.Unregister}.
     * 
     * @param cause The cause
     * @param channel The channel
     * @return A new unregister channel registration event
     */
    public static ChannelRegistrationEvent.Unregister createChannelRegistrationEventUnregister(Cause cause, String channel) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("channel", channel);
        return SpongeEventFactoryUtils.createEventImpl(ChannelRegistrationEvent.Unregister.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.ClientConnectionEvent.Auth}.
     * 
     * @param cause The cause
     * @param connection The connection
     * @param formatter The formatter
     * @param profile The profile
     * @param messageCancelled The message cancelled
     * @return A new auth client connection event
     */
    public static ClientConnectionEvent.Auth createClientConnectionEventAuth(Cause cause, RemoteConnection connection, MessageEvent.MessageFormatter formatter, GameProfile profile, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("connection", connection);
        values.put("formatter", formatter);
        values.put("profile", profile);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(ClientConnectionEvent.Auth.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.ClientConnectionEvent.Disconnect}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param formatter The formatter
     * @param targetEntity The target entity
     * @param messageCancelled The message cancelled
     * @return A new disconnect client connection event
     */
    public static ClientConnectionEvent.Disconnect createClientConnectionEventDisconnect(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, MessageEvent.MessageFormatter formatter, Player targetEntity, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("formatter", formatter);
        values.put("targetEntity", targetEntity);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(ClientConnectionEvent.Disconnect.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.ClientConnectionEvent.Join}.
     * 
     * @param cause The cause
     * @param originalChannel The original channel
     * @param channel The channel
     * @param formatter The formatter
     * @param targetEntity The target entity
     * @param messageCancelled The message cancelled
     * @return A new join client connection event
     */
    public static ClientConnectionEvent.Join createClientConnectionEventJoin(Cause cause, MessageChannel originalChannel, Optional<MessageChannel> channel, MessageEvent.MessageFormatter formatter, Player targetEntity, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalChannel", originalChannel);
        values.put("channel", channel);
        values.put("formatter", formatter);
        values.put("targetEntity", targetEntity);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(ClientConnectionEvent.Join.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.ClientConnectionEvent.Login}.
     * 
     * @param cause The cause
     * @param fromTransform The from transform
     * @param toTransform The to transform
     * @param connection The connection
     * @param formatter The formatter
     * @param profile The profile
     * @param targetUser The target user
     * @param messageCancelled The message cancelled
     * @return A new login client connection event
     */
    public static ClientConnectionEvent.Login createClientConnectionEventLogin(Cause cause, Transform<World> fromTransform, Transform<World> toTransform, RemoteConnection connection, MessageEvent.MessageFormatter formatter, GameProfile profile, User targetUser, boolean messageCancelled) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("fromTransform", fromTransform);
        values.put("toTransform", toTransform);
        values.put("connection", connection);
        values.put("formatter", formatter);
        values.put("profile", profile);
        values.put("targetUser", targetUser);
        values.put("messageCancelled", messageCancelled);
        return SpongeEventFactoryUtils.createEventImpl(ClientConnectionEvent.Login.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.PardonIpEvent}.
     * 
     * @param cause The cause
     * @param ban The ban
     * @return A new pardon ip event
     */
    public static PardonIpEvent createPardonIpEvent(Cause cause, Ban.Ip ban) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ban", ban);
        return SpongeEventFactoryUtils.createEventImpl(PardonIpEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.rcon.RconConnectionEvent.Connect}.
     * 
     * @param cause The cause
     * @param source The source
     * @return A new connect rcon connection event
     */
    public static RconConnectionEvent.Connect createRconConnectionEventConnect(Cause cause, RconSource source) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("source", source);
        return SpongeEventFactoryUtils.createEventImpl(RconConnectionEvent.Connect.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.rcon.RconConnectionEvent.Disconnect}.
     * 
     * @param cause The cause
     * @param source The source
     * @return A new disconnect rcon connection event
     */
    public static RconConnectionEvent.Disconnect createRconConnectionEventDisconnect(Cause cause, RconSource source) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("source", source);
        return SpongeEventFactoryUtils.createEventImpl(RconConnectionEvent.Disconnect.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.network.rcon.RconConnectionEvent.Login}.
     * 
     * @param cause The cause
     * @param source The source
     * @return A new login rcon connection event
     */
    public static RconConnectionEvent.Login createRconConnectionEventLogin(Cause cause, RconSource source) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("source", source);
        return SpongeEventFactoryUtils.createEventImpl(RconConnectionEvent.Login.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.server.ClientPingServerEvent}.
     * 
     * @param cause The cause
     * @param client The client
     * @param response The response
     * @return A new client ping server event
     */
    public static ClientPingServerEvent createClientPingServerEvent(Cause cause, StatusClient client, ClientPingServerEvent.Response response) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("client", client);
        values.put("response", response);
        return SpongeEventFactoryUtils.createEventImpl(ClientPingServerEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.server.ClientPingServerEvent.Response.Players}.
     * 
     * @param profiles The profiles
     * @param max The max
     * @param online The online
     * @return A new players response client ping server event
     */
    public static ClientPingServerEvent.Response.Players createClientPingServerEventResponsePlayers(List<GameProfile> profiles, int max, int online) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("profiles", profiles);
        values.put("max", max);
        values.put("online", online);
        return SpongeEventFactoryUtils.createEventImpl(ClientPingServerEvent.Response.Players.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.server.query.QueryServerEvent.Basic}.
     * 
     * @param cause The cause
     * @param address The address
     * @param gameType The game type
     * @param map The map
     * @param motd The motd
     * @param maxPlayerCount The max player count
     * @param maxSize The max size
     * @param playerCount The player count
     * @param size The size
     * @return A new basic query server event
     */
    public static QueryServerEvent.Basic createQueryServerEventBasic(Cause cause, InetSocketAddress address, String gameType, String map, String motd, int maxPlayerCount, int maxSize, int playerCount, int size) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("address", address);
        values.put("gameType", gameType);
        values.put("map", map);
        values.put("motd", motd);
        values.put("maxPlayerCount", maxPlayerCount);
        values.put("maxSize", maxSize);
        values.put("playerCount", playerCount);
        values.put("size", size);
        return SpongeEventFactoryUtils.createEventImpl(QueryServerEvent.Basic.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.server.query.QueryServerEvent.Full}.
     * 
     * @param cause The cause
     * @param address The address
     * @param customValuesMap The custom values map
     * @param gameId The game id
     * @param gameType The game type
     * @param map The map
     * @param motd The motd
     * @param players The players
     * @param plugins The plugins
     * @param version The version
     * @param maxPlayerCount The max player count
     * @param maxSize The max size
     * @param playerCount The player count
     * @param size The size
     * @return A new full query server event
     */
    public static QueryServerEvent.Full createQueryServerEventFull(Cause cause, InetSocketAddress address, Map<String, String> customValuesMap, String gameId, String gameType, String map, String motd, List<String> players, String plugins, String version, int maxPlayerCount, int maxSize, int playerCount, int size) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("address", address);
        values.put("customValuesMap", customValuesMap);
        values.put("gameId", gameId);
        values.put("gameType", gameType);
        values.put("map", map);
        values.put("motd", motd);
        values.put("players", players);
        values.put("plugins", plugins);
        values.put("version", version);
        values.put("maxPlayerCount", maxPlayerCount);
        values.put("maxSize", maxSize);
        values.put("playerCount", playerCount);
        values.put("size", size);
        return SpongeEventFactoryUtils.createEventImpl(QueryServerEvent.Full.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.service.ChangeServiceProviderEvent}.
     * 
     * @param cause The cause
     * @param newProviderRegistration The new provider registration
     * @param previousProviderRegistration The previous provider registration
     * @return A new change service provider event
     */
    public static ChangeServiceProviderEvent createChangeServiceProviderEvent(Cause cause, ProviderRegistration<?> newProviderRegistration, Optional<ProviderRegistration<?>> previousProviderRegistration) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("newProviderRegistration", newProviderRegistration);
        values.put("previousProviderRegistration", previousProviderRegistration);
        return SpongeEventFactoryUtils.createEventImpl(ChangeServiceProviderEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.statistic.ChangeStatisticEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param originalValue The original value
     * @param value The value
     * @param statistic The statistic
     * @param targetEntity The target entity
     * @return A new target player change statistic event
     */
    public static ChangeStatisticEvent.TargetPlayer createChangeStatisticEventTargetPlayer(Cause cause, long originalValue, long value, Statistic statistic, Player targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalValue", originalValue);
        values.put("value", value);
        values.put("statistic", statistic);
        values.put("targetEntity", targetEntity);
        return SpongeEventFactoryUtils.createEventImpl(ChangeStatisticEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.user.BanUserEvent}.
     * 
     * @param cause The cause
     * @param ban The ban
     * @param targetUser The target user
     * @return A new ban user event
     */
    public static BanUserEvent createBanUserEvent(Cause cause, Ban.Profile ban, User targetUser) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ban", ban);
        values.put("targetUser", targetUser);
        return SpongeEventFactoryUtils.createEventImpl(BanUserEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.user.BanUserEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param ban The ban
     * @param targetEntity The target entity
     * @param targetUser The target user
     * @return A new target player ban user event
     */
    public static BanUserEvent.TargetPlayer createBanUserEventTargetPlayer(Cause cause, Ban.Profile ban, Player targetEntity, User targetUser) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ban", ban);
        values.put("targetEntity", targetEntity);
        values.put("targetUser", targetUser);
        return SpongeEventFactoryUtils.createEventImpl(BanUserEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.user.PardonUserEvent}.
     * 
     * @param cause The cause
     * @param ban The ban
     * @param targetUser The target user
     * @return A new pardon user event
     */
    public static PardonUserEvent createPardonUserEvent(Cause cause, Ban.Profile ban, User targetUser) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ban", ban);
        values.put("targetUser", targetUser);
        return SpongeEventFactoryUtils.createEventImpl(PardonUserEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.user.PardonUserEvent.TargetPlayer}.
     * 
     * @param cause The cause
     * @param ban The ban
     * @param targetEntity The target entity
     * @param targetUser The target user
     * @return A new target player pardon user event
     */
    public static PardonUserEvent.TargetPlayer createPardonUserEventTargetPlayer(Cause cause, Ban.Profile ban, Player targetEntity, Player targetUser) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("ban", ban);
        values.put("targetEntity", targetEntity);
        values.put("targetUser", targetUser);
        return SpongeEventFactoryUtils.createEventImpl(PardonUserEvent.TargetPlayer.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.user.TargetUserEvent}.
     * 
     * @param cause The cause
     * @param targetUser The target user
     * @return A new target user event
     */
    public static TargetUserEvent createTargetUserEvent(Cause cause, User targetUser) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetUser", targetUser);
        return SpongeEventFactoryUtils.createEventImpl(TargetUserEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.ChangeWorldGameRuleEvent}.
     * 
     * @param cause The cause
     * @param originalValue The original value
     * @param value The value
     * @param name The name
     * @param targetWorld The target world
     * @return A new change world game rule event
     */
    public static ChangeWorldGameRuleEvent createChangeWorldGameRuleEvent(Cause cause, String originalValue, String value, String name, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalValue", originalValue);
        values.put("value", value);
        values.put("name", name);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(ChangeWorldGameRuleEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.ChangeWorldWeatherEvent}.
     * 
     * @param cause The cause
     * @param originalDuration The original duration
     * @param duration The duration
     * @param originalWeather The original weather
     * @param weather The weather
     * @param initialWeather The initial weather
     * @param targetWorld The target world
     * @return A new change world weather event
     */
    public static ChangeWorldWeatherEvent createChangeWorldWeatherEvent(Cause cause, int originalDuration, int duration, Weather originalWeather, Weather weather, Weather initialWeather, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("originalDuration", originalDuration);
        values.put("duration", duration);
        values.put("originalWeather", originalWeather);
        values.put("weather", weather);
        values.put("initialWeather", initialWeather);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(ChangeWorldWeatherEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.ConstructPortalEvent}.
     * 
     * @param cause The cause
     * @param portalLocation The portal location
     * @return A new construct portal event
     */
    public static ConstructPortalEvent createConstructPortalEvent(Cause cause, Location<World> portalLocation) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("portalLocation", portalLocation);
        return SpongeEventFactoryUtils.createEventImpl(ConstructPortalEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.ConstructWorldPropertiesEvent}.
     * 
     * @param cause The cause
     * @param worldArchetype The world archetype
     * @param worldProperties The world properties
     * @return A new construct world properties event
     */
    public static ConstructWorldPropertiesEvent createConstructWorldPropertiesEvent(Cause cause, WorldArchetype worldArchetype, WorldProperties worldProperties) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("worldArchetype", worldArchetype);
        values.put("worldProperties", worldProperties);
        return SpongeEventFactoryUtils.createEventImpl(ConstructWorldPropertiesEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.ExplosionEvent.Detonate}.
     * 
     * @param cause The cause
     * @param affectedLocations The affected locations
     * @param entities The entities
     * @param explosion The explosion
     * @param targetWorld The target world
     * @return A new detonate explosion event
     */
    public static ExplosionEvent.Detonate createExplosionEventDetonate(Cause cause, List<Location<World>> affectedLocations, List<Entity> entities, Explosion explosion, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("affectedLocations", affectedLocations);
        values.put("entities", entities);
        values.put("explosion", explosion);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(ExplosionEvent.Detonate.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.ExplosionEvent.Post}.
     * 
     * @param cause The cause
     * @param explosion The explosion
     * @param targetWorld The target world
     * @param transactions The transactions
     * @return A new post explosion event
     */
    public static ExplosionEvent.Post createExplosionEventPost(Cause cause, Explosion explosion, World targetWorld, List<Transaction<BlockSnapshot>> transactions) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("explosion", explosion);
        values.put("targetWorld", targetWorld);
        values.put("transactions", transactions);
        return SpongeEventFactoryUtils.createEventImpl(ExplosionEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.ExplosionEvent.Pre}.
     * 
     * @param cause The cause
     * @param explosion The explosion
     * @param targetWorld The target world
     * @return A new pre explosion event
     */
    public static ExplosionEvent.Pre createExplosionEventPre(Cause cause, Explosion explosion, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("explosion", explosion);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(ExplosionEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.GenerateChunkEvent.Post}.
     * 
     * @param cause The cause
     * @param targetChunk The target chunk
     * @return A new post generate chunk event
     */
    public static GenerateChunkEvent.Post createGenerateChunkEventPost(Cause cause, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(GenerateChunkEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.GenerateChunkEvent.Pre}.
     * 
     * @param cause The cause
     * @param targetChunk The target chunk
     * @return A new pre generate chunk event
     */
    public static GenerateChunkEvent.Pre createGenerateChunkEventPre(Cause cause, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(GenerateChunkEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.LoadWorldEvent}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @return A new load world event
     */
    public static LoadWorldEvent createLoadWorldEvent(Cause cause, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(LoadWorldEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.SaveWorldEvent}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @return A new save world event
     */
    public static SaveWorldEvent createSaveWorldEvent(Cause cause, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(SaveWorldEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.SaveWorldEvent.Post}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @return A new post save world event
     */
    public static SaveWorldEvent.Post createSaveWorldEventPost(Cause cause, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(SaveWorldEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.SaveWorldEvent.Pre}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @return A new pre save world event
     */
    public static SaveWorldEvent.Pre createSaveWorldEventPre(Cause cause, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(SaveWorldEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.TargetWorldEvent}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @return A new target world event
     */
    public static TargetWorldEvent createTargetWorldEvent(Cause cause, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(TargetWorldEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.UnloadWorldEvent}.
     * 
     * @param cause The cause
     * @param targetWorld The target world
     * @return A new unload world event
     */
    public static UnloadWorldEvent createUnloadWorldEvent(Cause cause, World targetWorld) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetWorld", targetWorld);
        return SpongeEventFactoryUtils.createEventImpl(UnloadWorldEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.ForcedChunkEvent}.
     * 
     * @param cause The cause
     * @param chunkCoords The chunk coords
     * @param ticket The ticket
     * @return A new forced chunk event
     */
    public static ForcedChunkEvent createForcedChunkEvent(Cause cause, Vector3i chunkCoords, ChunkTicketManager.LoadingTicket ticket) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("chunkCoords", chunkCoords);
        values.put("ticket", ticket);
        return SpongeEventFactoryUtils.createEventImpl(ForcedChunkEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.LoadChunkEvent}.
     * 
     * @param cause The cause
     * @param targetChunk The target chunk
     * @return A new load chunk event
     */
    public static LoadChunkEvent createLoadChunkEvent(Cause cause, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(LoadChunkEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.PopulateChunkEvent.Populate}.
     * 
     * @param cause The cause
     * @param populator The populator
     * @param targetChunk The target chunk
     * @return A new populate populate chunk event
     */
    public static PopulateChunkEvent.Populate createPopulateChunkEventPopulate(Cause cause, Populator populator, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("populator", populator);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(PopulateChunkEvent.Populate.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.PopulateChunkEvent.Post}.
     * 
     * @param cause The cause
     * @param appliedPopulators The applied populators
     * @param targetChunk The target chunk
     * @return A new post populate chunk event
     */
    public static PopulateChunkEvent.Post createPopulateChunkEventPost(Cause cause, List<Populator> appliedPopulators, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("appliedPopulators", appliedPopulators);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(PopulateChunkEvent.Post.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.PopulateChunkEvent.Pre}.
     * 
     * @param cause The cause
     * @param pendingPopulators The pending populators
     * @param targetChunk The target chunk
     * @return A new pre populate chunk event
     */
    public static PopulateChunkEvent.Pre createPopulateChunkEventPre(Cause cause, List<Populator> pendingPopulators, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("pendingPopulators", pendingPopulators);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(PopulateChunkEvent.Pre.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.TargetChunkEvent}.
     * 
     * @param cause The cause
     * @param targetChunk The target chunk
     * @return A new target chunk event
     */
    public static TargetChunkEvent createTargetChunkEvent(Cause cause, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(TargetChunkEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.UnforcedChunkEvent}.
     * 
     * @param cause The cause
     * @param chunkCoords The chunk coords
     * @param ticket The ticket
     * @return A new unforced chunk event
     */
    public static UnforcedChunkEvent createUnforcedChunkEvent(Cause cause, Vector3i chunkCoords, ChunkTicketManager.LoadingTicket ticket) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("chunkCoords", chunkCoords);
        values.put("ticket", ticket);
        return SpongeEventFactoryUtils.createEventImpl(UnforcedChunkEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.spongepowered.api.event.world.chunk.UnloadChunkEvent}.
     * 
     * @param cause The cause
     * @param targetChunk The target chunk
     * @return A new unload chunk event
     */
    public static UnloadChunkEvent createUnloadChunkEvent(Cause cause, Chunk targetChunk) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("cause", cause);
        values.put("targetChunk", targetChunk);
        return SpongeEventFactoryUtils.createEventImpl(UnloadChunkEvent.class, values);
    }
}


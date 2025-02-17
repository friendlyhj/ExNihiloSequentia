package novamachina.exnihilosequentia.common.crafting.sieve;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import novamachina.exnihilosequentia.common.crafting.ExNihiloRecipeSerializer;
import novamachina.exnihilosequentia.common.crafting.SerializableRecipe;
import novamachina.exnihilosequentia.common.init.ExNihiloRecipeTypes;
import novamachina.exnihilosequentia.common.init.ExNihiloSerializers;
import novamachina.exnihilosequentia.common.item.mesh.MeshType;

public class SieveRecipe extends SerializableRecipe {

  @Nonnull private final ResourceLocation recipeId;
  @Nonnull private final List<MeshWithChance> rolls;
  @Nonnull private ItemStack drop;
  @Nonnull private Ingredient input;
  private boolean isWaterlogged;

  public SieveRecipe(
      @Nonnull final ResourceLocation id,
      @Nonnull final Ingredient input,
      @Nonnull final ItemStack drop,
      @Nonnull final List<MeshWithChance> rolls,
      boolean isWaterlogged) {
    super(drop, ExNihiloRecipeTypes.SIEVE_RECIPE_TYPE.get(), id);
    this.recipeId = id;
    this.input = input;
    this.drop = drop;
    this.rolls = rolls;
    this.isWaterlogged = isWaterlogged;
  }

  public void addRoll(@Nonnull final String meshString, final float chance) {
    @Nonnull final MeshType mesh = MeshType.valueOf(meshString);
    addRoll(mesh, chance);
  }

  public void addRoll(@Nonnull final MeshType mesh, final float chance) {
    this.rolls.add(new MeshWithChance(mesh, chance));
  }

  @Nonnull
  public SieveRecipe filterByMesh(@Nonnull final MeshType meshType, final boolean flattenRecipes) {
    @Nonnull final List<MeshWithChance> possibleMeshes = new ArrayList<>();
    for (@Nonnull final MeshWithChance mesh : rolls) {
      if (flattenRecipes) {
        if (mesh.getMesh().getLevel() <= meshType.getLevel()) {
          possibleMeshes.add(mesh);
        }
      } else {
        if (mesh.getMesh().getLevel() == meshType.getLevel()) {
          possibleMeshes.add(mesh);
        }
      }
    }
    return new SieveRecipe(recipeId, input, drop, possibleMeshes, isWaterlogged);
  }

  @Nonnull
  public ItemStack getDrop() {
    return drop.copy();
  }

  public void setDrop(@Nonnull final ItemStack drop) {
    this.drop = drop;
  }

  @Nonnull
  public Ingredient getInput() {
    return input;
  }

  public void setInput(@Nonnull final Ingredient input) {
    this.input = input;
  }

  @Nonnull
  public ResourceLocation getRecipeId() {
    return recipeId;
  }

  @Override
  @Nonnull
  public ItemStack getResultItem() {
    return drop.copy();
  }

  @Nonnull
  public List<MeshWithChance> getRolls() {
    return rolls;
  }

  public boolean isWaterlogged() {
    return isWaterlogged;
  }

  public void setWaterlogged() {
    this.isWaterlogged = true;
  }

  @Override
  @Nonnull
  public String toString() {
    return "SieveRecipe{"
        + "input="
        + input
        + ", drop="
        + drop
        + ", rolls="
        + rolls
        + ", isWaterlogged="
        + isWaterlogged
        + ", recipeId="
        + recipeId
        + '}';
  }

  @Override
  @Nullable
  protected ExNihiloRecipeSerializer<SieveRecipe> getENSerializer() {
    return ExNihiloSerializers.SIEVE_RECIPE_SERIALIZER.get();
  }
}

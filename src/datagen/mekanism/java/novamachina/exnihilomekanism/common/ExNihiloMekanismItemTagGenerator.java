package novamachina.exnihilomekanism.common;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import novamachina.exnihilomekanism.common.init.ExNihiloMekanismItems;
import novamachina.exnihilomekanism.common.utility.ExNihiloMekanismConstants;
import novamachina.exnihilosequentia.api.datagen.AbstractItemTagGenerator;
import novamachina.exnihilosequentia.api.tag.ExNihiloTags;

public class ExNihiloMekanismItemTagGenerator extends AbstractItemTagGenerator {

  public ExNihiloMekanismItemTagGenerator(DataGenerator generator,
      BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
    super(generator, blockTagsProvider, ExNihiloMekanismConstants.ModIds.EX_NIHILO_MEKANISM,
        existingFileHelper);
  }

  @Override
  protected void addTags() {
    registerOre(ExNihiloMekanismItems.OSMIUM,
        new ExNihiloTags.OreTag(ExNihiloMekanismItems.OSMIUM));
  }
}

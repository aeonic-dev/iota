package design.aeonic.iota;

import com.tterrag.registrate.Registrate;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.LeadItem;
import net.minecraftforge.fml.common.Mod;

@Mod(Iota.MOD_ID)
public class Iota {
	
    public static final String MOD_ID = "iota";
    public static final String MOD_NAME = "Iota";
    
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID).creativeModeTab(() -> CreativeModeTab.TAB_MISC);

    public Iota() {}

}

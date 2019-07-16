package pl.pabilo8.immersiveintelligence.common.items;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.energy.wires.IWireCoil;
import blusunrize.immersiveengineering.api.energy.wires.WireType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pl.pabilo8.immersiveintelligence.common.blocks.metal.TileEntityDataConnector;
import pl.pabilo8.immersiveintelligence.common.blocks.metal.TileEntityDataRelay;
import pl.pabilo8.immersiveintelligence.common.wire.IIWireType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Pabilo8 on 2019-05-31.
 */
public class ItemIIDataWireCoil extends ItemIIBase implements IWireCoil
{
	public ItemIIDataWireCoil()
	{
		super("data_wire", 64);
	}

	@Override
	public WireType getWireType(ItemStack stack)
	{
		return IIWireType.DATA;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(stack.getTagCompound()!=null&&stack.getTagCompound().hasKey("linkingPos"))
		{
			int[] link = stack.getTagCompound().getIntArray("linkingPos");
			if(link!=null&&link.length > 3)
			{
				tooltip.add(I18n.format(Lib.DESC_INFO+"attachedToDim", link[1], link[2], link[3], link[0]));
			}
		}
	}


	@Nonnull
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		return ApiUtils.doCoilUse(this, player, world, pos, hand, side, hitX, hitY, hitZ);
	}

	public boolean canConnectCable(WireType wire, TileEntity targetEntity)
	{
		//We specifically only support whitelisted TEs here.
		//Without this, you can connect the AF wire to any connectable block that doesn't specifically deny it.
		return !(!(targetEntity instanceof TileEntityDataConnector)&&
				!(targetEntity instanceof TileEntityDataRelay));
	}
}
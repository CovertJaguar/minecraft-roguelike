package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;

public class SegmentWheat extends SegmentBase {


	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir, 3);
		editor.setBlock(cursor, Blocks.water);
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		start.add(Cardinal.UP, 2);
		end.add(dir);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), true, true);
		
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 1);
		editor.fillRectSolid(rand, start, end, new MetaBlock(Blocks.air), true, true);
		start.add(Cardinal.DOWN, 1);
		end.add(Cardinal.DOWN, 2);

		editor.fillRectSolid(rand, start, end, new MetaBlock(Blocks.farmland), true, true);
		start.add(Cardinal.UP, 1);
		end.add(Cardinal.UP, 1);
		BlockJumble crops = new BlockJumble();
		crops.addBlock(new MetaBlock(Blocks.wheat));
		crops.addBlock(new MetaBlock(Blocks.carrots));
		crops.addBlock(new MetaBlock(Blocks.potatoes));
		editor.fillRectSolid(rand, start, end, crops, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		cursor.add(Cardinal.UP, 1);
		MetaBlock pumpkin = new MetaBlock(Blocks.lit_pumpkin.getDefaultState());
		pumpkin.withProperty(BlockPumpkin.FACING, Cardinal.getFacing(Cardinal.reverse(dir)));
		pumpkin.setBlock(editor, cursor);
		
		
		for(Cardinal d : orth){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			cursor.add(Cardinal.UP, 1);
			MetaBlock stair = theme.getSecondaryStair();
			WorldEditor.blockOrientation(stair, Cardinal.reverse(d), true);
			editor.setBlock(rand, cursor, stair, true, true);
		}
	}
}
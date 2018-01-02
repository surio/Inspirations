package knightminer.inspirations.shared.client;

import java.util.List;

import com.google.common.collect.ImmutableMap;

import knightminer.inspirations.shared.InspirationsShared;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.IExtendedBlockState;

public class TextureModel extends BakedModelWrapper<IBakedModel> {

	private IModel model;
	private final VertexFormat format;
	private final String textureKey;
	public TextureModel(IBakedModel originalModel, IModel model, VertexFormat format, String textureKey) {
		super(originalModel);
		this.model = model;
		this.format = format;
		this.textureKey = textureKey;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		IBakedModel bakedModel = this.originalModel;
		if(state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = (IExtendedBlockState) state;

			String texture = extendedState.getValue(InspirationsShared.TEXTURE);
			if(texture != null) {
				IModel retextured = model.retexture(ImmutableMap.of(textureKey, texture));
				bakedModel = retextured.bake(retextured.getDefaultState(), format, ModelLoader.defaultTextureGetter());
			}
		}
		return bakedModel.getQuads(state, side, rand);
	}
}
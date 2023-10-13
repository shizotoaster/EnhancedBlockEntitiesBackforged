package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class ShulkerBoxBlockEntityMixin extends BlockEntity implements ModelStateHolder {
    @Shadow public abstract float getAnimationProgress(float delta);

    @Unique private int enhanced_bes$modelState = 0;

    public ShulkerBoxBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Inject(method = "updateAnimation", at = @At("TAIL"))
    private void enhanced_bes$updateModelState(CallbackInfo ci) {
        int mState = this.getAnimationProgress(0) > 0 ? 1 : 0;
        if (mState != enhanced_bes$modelState) setModelState(mState, world, pos);
    }

    @Override
    public int getModelState() {
        return enhanced_bes$modelState;
    }

    @Override
    public void applyModelState(int state) {
        this.enhanced_bes$modelState = state;
    }
}
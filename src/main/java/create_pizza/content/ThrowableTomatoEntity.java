package create_pizza.content;

import create_pizza.CreatePizzaClient;
import create_pizza.CreatePizzaEntityTypes;
import create_pizza.CreatePizzaItems;
import create_pizza.CreatePizzaParticles;
import create_pizza.CreatePizzaDamageSources;
import create_pizza.CreatePizzaSoundEvents;
import create_pizza.config.CreatePizzaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.concurrent.TimeUnit;

public class ThrowableTomatoEntity extends ThrowableItemProjectile {

	public ThrowableTomatoEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
		super(entityType, world);
	}

	public ThrowableTomatoEntity(Level world, LivingEntity owner) {
		super(CreatePizzaEntityTypes.THROWABLE_TOMATO, owner, world); // null will be changed later
	}

	public ThrowableTomatoEntity(Level world, double x, double y, double z) {
		super(CreatePizzaEntityTypes.THROWABLE_TOMATO, x, y, z, world); // null will be changed later
	}

	@Override
	protected Item getDefaultItem() {
		return CreatePizzaItems.THROWABLE_TOMATO.get();
	}

	private ParticleOptions getParticle() {
		ItemStack itemStack = this.getItemRaw();
		return (ParticleOptions)(itemStack.isEmpty() ? CreatePizzaParticles.TOMATO_PARTICLE : new ItemParticleOption(ParticleTypes.ITEM, itemStack));
	}
	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			ParticleOptions particleOptions = this.getParticle();

			for(int i = 0; i < 8; ++i) {
				this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
			}
		}

	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) { // called on entity hit.
		super.onHitEntity(entityHitResult);
		Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
		entity.playSound(CreatePizzaSoundEvents.SPLAT.getMainEvent(), 1F, (float) (0.8 + this.level().random.nextFloat() * 0.4));
		entity.hurt(CreatePizzaDamageSources.tomatoHit(entity.level()), 1);
		if (entity.equals(Minecraft.getInstance().player)) {
			CreatePizzaClient.TOMATO_SPLAT_OVERLAY_COOLDOWN = 60;
			CreatePizzaClient.TOMATO_SPLAT_OVERLAY_Y = 0;
			playSound(CreatePizzaSoundEvents.SPLAT.getMainEvent(), 1F, (float) (0.8 + this.level().random.nextFloat() * 0.4));

		}
		this.kill();
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) { // called on collision with a block
		super.onHitBlock(blockHitResult);

		if (!this.level().isClientSide) { // checks if the world is client
			this.playSound(CreatePizzaSoundEvents.SPLAT.getMainEvent(), 1F, (float) (0.8 + this.level().random.nextFloat() * 0.4));
			this.level().broadcastEntityEvent(this, (byte)3); // particle?
			this.kill(); // kills the projectile
		}

	}
}

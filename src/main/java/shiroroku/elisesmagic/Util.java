package shiroroku.elisesmagic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<BlockPos> midpointCircle(BlockPos origin, int radius) {

		List<BlockPos> circle = new ArrayList<>();
		int x = radius;
		int z = 0;

		circle.add(origin.offset(radius, 0, 0));
		circle.add(origin.offset(-radius, 0, 0));
		circle.add(origin.offset(0, 0, radius));
		circle.add(origin.offset(0, 0, -radius));

		int P = 1 - radius;
		while (x > z) {
			z++;

			if (P <= 0) {
				P = P + 2 * z + 1;
			} else {
				P = P + 2 * z - 2 * x + 1;
				x--;
			}

			if (x < z) {
				break;
			}

			circle.add(origin.offset(x, 0, z));
			circle.add(origin.offset(-x, 0, z));
			circle.add(origin.offset(x, 0, -z));
			circle.add(origin.offset(-x, 0, -z));

			if (x != z) {
				circle.add(origin.offset(z, 0, x));
				circle.add(origin.offset(-z, 0, x));
				circle.add(origin.offset(z, 0, -x));
				circle.add(origin.offset(-z, 0, -x));
			}
		}
		return circle;
		/*
		List<BlockPos> circle = new ArrayList<>();
		int d = 1 - radius;
		int x = 0;
		int z = radius;
		int ox = origin.getX();
		int oy = origin.getY();
		int oz = origin.getZ();

		do {
			circle.add(new BlockPos(ox + x, oy, oz + z));
			circle.add(new BlockPos(ox + x, oy, oz - z));
			circle.add(new BlockPos(ox - x, oy, oz + z));
			circle.add(new BlockPos(ox - x, oy, oz - z));
			circle.add(new BlockPos(ox + z, oy, oz + x));
			circle.add(new BlockPos(ox + z, oy, oz - x));
			circle.add(new BlockPos(ox - z, oy, oz + x));
			circle.add(new BlockPos(ox - z, oy, oz - x));

			if (d < 0) {
				d += 2 * x + 1;
			} else {
				//d += 2 * (x - z) + 1;
				d += 2 * x - 2 * z + 1;
				z--;
			}
			x++;
		}while (x <= z);
		return circle;*/
	}

	public static List<BlockPos> midpointCircleInside(BlockPos origin, int radius) {
		List<BlockPos> circle = new ArrayList<>();
		for (int r = 0; r < radius; r++) {
			circle.addAll(midpointCircle(origin, r));
		}
		return circle;
	}

	public static Vec3 lerp2Vec3(Vec3 pointA, Vec3 pointControl, Vec3 pointB, float lerp) {
		Vec3 lerp1 = pointA.lerp(pointControl, lerp);
		Vec3 lerp2 = pointControl.lerp(pointB, lerp);
		return lerp1.lerp(lerp2, lerp);
	}

	public static Vec3 rotateAround(Vec3 point, Vec3 origin, float angle) {
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		float x = (float) (origin.x + (point.x - origin.x) * cos - (point.z - origin.z) * sin);
		float z = (float) (origin.z + (point.x - origin.x) * sin + (point.z - origin.z) * cos);
		return new Vec3(x, point.y, z);
	}

	public static void renderAABBBounds(Level worldIn, AABB aabb) {
		if (worldIn.isClientSide) {
			for (double ix = aabb.minX; ix <= aabb.maxX; ix++) {
				for (double iy = aabb.minY; iy <= aabb.maxY; iy++) {
					for (double iz = aabb.minZ; iz <= aabb.maxZ; iz++) {
						ParticleOptions particle = ParticleTypes.FLAME;
						if (ix == aabb.minX || ix == aabb.maxX) {
							worldIn.addParticle(particle, ix, iy, iz, 0, 0, 0);
						}
						if (iy == aabb.minY || iy == aabb.maxY) {
							worldIn.addParticle(particle, ix, iy, iz, 0, 0, 0);
						}
						if (iz == aabb.minZ || iz == aabb.maxZ) {
							worldIn.addParticle(particle, ix, iy, iz, 0, 0, 0);
						}
					}
				}
			}
		}
	}

}

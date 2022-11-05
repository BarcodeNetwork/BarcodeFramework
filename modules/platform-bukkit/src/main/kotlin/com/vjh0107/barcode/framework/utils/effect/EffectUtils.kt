package com.vjh0107.barcode.framework.utils.effect

import org.bukkit.Location
import org.bukkit.potion.PotionEffectType

object EffectUtils {
    fun getHollowCube(corner1: Location, corner2: Location, particleDistance: Double): List<Location> {
        val result: MutableList<Location> = ArrayList()
        val world = corner1.world
        val startX = corner1.x.coerceAtMost(corner2.x).toInt()
        val startY = corner1.y.coerceAtMost(corner2.y).toInt()
        val startZ = corner1.z.coerceAtMost(corner2.z).toInt()
        val endX = corner1.x.coerceAtLeast(corner2.x).toInt()
        val endY = corner1.y.coerceAtLeast(corner2.y).toInt()
        val endZ = corner1.z.coerceAtLeast(corner2.z).toInt()
        for (x in startX..endX + 1) {
            for (y in startY..endY + 1) {
                for (z in startZ..endZ + 1) {
                    var edge = false
                    if ((x == startX || x == endX + 1) &&
                        (y == startY || y == endY + 1)
                    ) edge = true
                    if ((z == startZ || z == endZ + 1) &&
                        (y == startY || y == endY + 1)
                    ) edge = true
                    if ((x == startX || x == endX + 1) &&
                        (z == startZ || z == endZ + 1)
                    ) edge = true
                    if (edge) {
                        result.add(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
                    }
                }
            }
        }
        return result
    }

    /**
     * 포션 영구 지속 효과 갱신 시간을 포션 타입에 따라 적당하게 제공해준다.
     *
     * 멀미를 5초 이하의 주기로 갱신할 경우, 자연스럽게 연결되지 않으며,
     * 야간투시는 10초 이하로 남았을 경우 깜빡이고, 실명은 적용까지 시간이 좀 걸린다.
     * 너무 갱신하는것은 렉이 걸릴 수 있으니, 포션에 따른 적당한 갱신 시간을 제공한다.
     *
     * @param type 포션 타입
     */
    fun getPermanentPotionEffectInterval(type: PotionEffectType): Int {
        return if (type == PotionEffectType.NIGHT_VISION || type == PotionEffectType.CONFUSION) 260 else if (type == PotionEffectType.BLINDNESS) 140 else 80
    }
}
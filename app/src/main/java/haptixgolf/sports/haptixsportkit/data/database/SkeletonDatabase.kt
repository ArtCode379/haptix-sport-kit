package haptixgolf.sports.haptixsportkit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import haptixgolf.sports.haptixsportkit.data.dao.CartItemDao
import haptixgolf.sports.haptixsportkit.data.dao.OrderDao
import haptixgolf.sports.haptixsportkit.data.database.converter.Converters
import haptixgolf.sports.haptixsportkit.data.entity.CartItemEntity
import haptixgolf.sports.haptixsportkit.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class YJIJWDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}